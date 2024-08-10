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
        submitButton.textContent = 'Collecting articles...';
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
                responseMessage.textContent = `Status: ${result.status}`;
                responseMessage.style.color = 'green';
            } else {
                const result = await response.json();
                responseMessage.textContent = `Status: ${result.status}, Message: ${result.message}`;
                responseMessage.style.color = 'red';
            }
        } catch (error) {
            console.error('Error:', error);
            responseMessage.textContent = 'An error occurred while submitting the data.';
            responseMessage.style.color = 'red';
        } finally {
            // Riabilita il pulsante di invio e ripristina il testo originale
            await delay(3000);
            location.reload();
        }
    });

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
            card.classList.add('card', 'h-100', 'bg-secondary', 'text-light');
            card.innerHTML = `<div class="card-body">
                                <h4 class="card-title text-center">${collection.collection}</h4>
                              </div>`;
            card.addEventListener('click', () => {
                window.location.href = `/topics?collectionId=${collection.collection}`;
            });
            col.appendChild(card);
            collectionsContainer.appendChild(col);
        });
    }

    // Fetch and display collections on page load
    fetchCollections();
});
