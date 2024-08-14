document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('monitor-form');
    const submitButton = form.querySelector('button[type="submit"]');
    const responseMessage = document.getElementById('response-message');
    const collectionsContainer = document.getElementById('collections-container');
    const delay = ms => new Promise(res => setTimeout(res, ms));

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Disabilita il pulsante di invio e mostra il messaggio di caricamento
        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Collecting...';
        responseMessage.textContent = '';

        const issueQuery = document.getElementById('issueQuery').value;
        const tag = document.getElementById('tag').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        const data = {
            issueQuery: issueQuery,
            tag: tag,
            startDate: startDate,
            endDate: endDate
        };

        try {
            const response = await fetch('http://localhost:8080/api/collect', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                showResponseMessage(`✔️ Status: ${result.status}`, 'success');
            } else {
                const result = await response.json();
                showResponseMessage(`❌ Status: ${result.status}, Message: ${result.message}`, 'error');
            }
        } catch (error) {
            console.error('Error:', error);
            showResponseMessage('❌ An error occurred while submitting the data.', 'error');
        } finally {
            // Riabilita il pulsante di invio e ripristina il testo originale
            await delay(3000);
            location.reload();
        }
    });

    function showResponseMessage(message, type) {
        responseMessage.textContent = message;
        responseMessage.className = type === 'success' ? 'text-success' : 'text-danger';
        responseMessage.style.opacity = 1;
        setTimeout(() => {
            responseMessage.style.transition = 'opacity 1s';
            responseMessage.style.opacity = 0;
        }, 3000);
    }

    async function fetchCollections() {
        try {
            const response = await fetch('http://localhost:8080/api/collections');
            if (response.ok) {
                const result = await response.json();
                displayCollections(result.data);
            } else {
                console.error('Failed to fetch collections');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    function displayCollections(collections) {
        if (!collectionsContainer) {
            console.error('Collections container not found');
            return;
        }
        collectionsContainer.innerHTML = ''; // Clear previous content
        collections.forEach(collection => {
            const col = document.createElement('div');
            col.classList.add('col-md-4', 'mb-4');
            const card = document.createElement('div');
            card.classList.add('card', 'h-100', 'bg-secondary', 'text-light', 'collection-card', 'shadow-sm');
            card.innerHTML = `
                <div class="card-body d-flex flex-column align-items-center justify-content-center">
                    <h4 class="card-title text-center">${(collection.collection).toUpperCase()}</h4>
                    <button class="btn btn-danger mt-3 delete-collection-btn">Delete</button>
                </div>`;
            card.addEventListener('click', () => {
                window.location.href = `/topics?collectionId=${collection.collection}`;
            });
            col.appendChild(card);
            collectionsContainer.appendChild(col);

            const deleteButton = card.querySelector('.delete-collection-btn');
            deleteButton.addEventListener('click', async () => {
                event.stopPropagation();
                if (confirm(`Are you sure you want to delete the collection "${collection.collection}"?`)) {
                    await deleteCollection(collection.collection);
                }
            });
        });

        const cards = document.querySelectorAll('.collection-card');
        cards.forEach(card => {
            card.addEventListener('mouseover', () => {
                card.classList.add('shadow-lg');
            });
            card.addEventListener('mouseout', () => {
                card.classList.remove('shadow-lg');
            });
        });
    }

    async function deleteCollection(collectionId) {
        try {
            const response = await fetch(`http://localhost:8080/api/collections/${collectionId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert(`Collection "${collectionId}" has been successfully deleted.`);
                fetchCollections();
            } else {
                alert(`Failed to delete collection "${collectionId}".`);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred while trying to delete the collection.');
        }
    }

    // Fetch and display collections on page load
    fetchCollections();
});
