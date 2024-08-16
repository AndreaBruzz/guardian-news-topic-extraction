document.addEventListener('DOMContentLoaded', () => {
    var urlParams = new URLSearchParams(window.location.search);
    var collectionId = urlParams.get('collectionId');

    if (collectionId === null) { 
        window.location.replace('/monitor');
    }

    const h1 = document.querySelector('h1');
    h1.textContent = `Search topics for ${collectionId.toUpperCase()} collection`;

    const form = document.getElementById('topics-form');
    const submitButton = form.querySelector('button[type="submit"]');
    const responseMessage = document.getElementById('response-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Collecting...';
        responseMessage.textContent = '';

        const query = document.getElementById('query').value;
        const numOfTopics = document.getElementById('numOfTopics').value;
        const numOfTopWords = document.getElementById('numOfTopWords').value;

        const data = {
            query: query,
            numOfTopics: numOfTopics,
            numOfTopWords: numOfTopWords,
            collectionId: collectionId,
        };

        try {
            const response = await fetch('http://localhost:8080/api/topics', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                console.info('Response received:', result);

                const existingContainer = document.querySelector('.topics-container');
                if (existingContainer) {
                    existingContainer.remove();
                }

                const topicsContainer = document.createElement('div');
                topicsContainer.classList.add('topics-container', 'mt-4', 'row');

                if (result.topics && result.topics.length > 0) {
                    result.topics.forEach(topic => {
                        const col = document.createElement('div');
                        col.classList.add('col-md-4', 'mb-4');

                        const card = document.createElement('div');
                        card.classList.add('card', 'h-100', 'bg-secondary', 'text-light', 'collection-card', 'shadow-sm');

                        const cardBody = document.createElement('div');
                        cardBody.classList.add('card-body', 'd-flex', 'flex-column', 'align-items-center', 'justify-content-center');

                        const topicTitle = document.createElement('h5');
                        topicTitle.classList.add('card-title', 'text-center');
                        topicTitle.textContent = `Topic ${topic.id}`;

                        const topicWords = document.createElement('p');
                        topicWords.classList.add('card-text', 'text-center');
                        topicWords.textContent = topic.topWords.join(', ');

                        cardBody.appendChild(topicTitle);
                        cardBody.appendChild(topicWords);
                        card.appendChild(cardBody);
                        col.appendChild(card);
                        topicsContainer.appendChild(col);
                    });

                    document.body.appendChild(topicsContainer);
                } else {
                    responseMessage.textContent = 'No topics found for the given query.';
                    responseMessage.className = 'text-danger';
                }
            } else {
                const result = await response.json();
                console.info('Error response:', result);
                responseMessage.textContent = `Status: ${result.status}, Message: ${result.message}`;
                responseMessage.className = 'text-danger';
            }
        } catch (error) {
            console.error('Error:', error);
            responseMessage.textContent = 'An error occurred while submitting the data.';
            responseMessage.className = 'text-danger';
        } finally {
            submitButton.disabled = false;
            submitButton.textContent = 'Search Topics';
        }
    });
});
