document.addEventListener('DOMContentLoaded', () => {
    var urlParams = new URLSearchParams(window.location.search);
    var collectionId = urlParams.get('collectionId');

    // Evito che l'utente arrivi alla pagina del topic senza avere la collectionId
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
        submitButton.textContent = 'Searching...\nThis may take a while.';
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

                // Rimuovo eventuale contenitore esistente
                const existingContainer = document.querySelector('.topics-container');
                if (existingContainer) {
                    existingContainer.remove();
                }

                // Creazione del contenitore per i topics
                const topicsContainer = document.createElement('div');
                topicsContainer.classList.add('topics-container', 'mt-4');

                // Ciclo attraverso i topics ricevuti e li visualizzo
                if (result.topics && result.topics.length > 0) {
                    result.topics.forEach(topic => {
                        const topicCard = document.createElement('div');
                        topicCard.classList.add('card', 'mb-3');

                        const topicCardBody = document.createElement('div');
                        topicCardBody.classList.add('card-body');

                        const topicTitle = document.createElement('h5');
                        topicTitle.classList.add('card-title');
                        topicTitle.textContent = `Topic ${topic.id}`;

                        const topicWords = document.createElement('p');
                        topicWords.classList.add('card-text');
                        topicWords.textContent = topic.topWords.join(', ');

                        topicCardBody.appendChild(topicTitle);
                        topicCardBody.appendChild(topicWords);
                        topicCard.appendChild(topicCardBody);
                        topicsContainer.appendChild(topicCard);
                    });

                    // Aggiungo i topics alla pagina
                    document.body.appendChild(topicsContainer);
                } else {
                    responseMessage.textContent = 'No topics found for the given query.';
                    responseMessage.style.color = 'red';
                }
            } else {
                const result = await response.json();
                console.info('Error response:', result);
                responseMessage.textContent = `Status: ${result.status}, Message: ${result.message}`;
                responseMessage.style.color = 'red';
            }
        } catch (error) {
            console.error('Error:', error);
            responseMessage.textContent = 'An error occurred while submitting the data.';
            responseMessage.style.color = 'red';
        } finally {
            submitButton.disabled = false;
            submitButton.textContent = 'Search Topics';
        }
    });
});
