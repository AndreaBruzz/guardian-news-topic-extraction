document.addEventListener('DOMContentLoaded', () => {
    // Estrae il tag dall'URL corrente
    console.log("Il DOM è stato caricato");
    var urlParams = new URLSearchParams(window.location.search);
    var collectionId = urlParams.get('collectionId');

    //Evito che l'utente arrivi alla pagina del topic senza 
    //avere la collectionId
    if (collectionId === null) { 
        window.location.replace('/monitor')
    }

    const h1 = document.querySelector('h1');
    h1.textContent = `Search topics for ${collectionId} collection`;

    const form = document.getElementById('topics-form');
    const submitButton = form.querySelector('button[type="submit"]');
    const responseMessage = document.getElementById('response-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        submitButton.disabled = true;
        submitButton.textContent = 'Inedxing and searching...\nThis may take a while.';
        responseMessage.textContent = '';

        const topicsQuery = document.getElementById('topicsQuery').value;
        const numOfTopics = document.getElementById('numOfTopics').value;
        const numOfTopWords = document.getElementById('numOfTopWords').value;

        const data = {
            topicsQuery: topicsQuery,
            numOfTopics: numOfTopics,
            numOfTopWords: numOfTopWords,
            //Passo questa al backend nel body senza avere
            //una wildcard nelle route api
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
                // TODO
            } else {
                const result = await response.json();
                responseMessage.textContent = `Status: ${result.status}, Message: ${result.message}`;
                responseMessage.style.color = 'red';
            }
        } catch (error) {
            console.error('Error:', error);
            responseMessage.textContent = 'An error occurred while submitting the data.';
            responseMessage.style.color = 'red';
        }
    });
});
