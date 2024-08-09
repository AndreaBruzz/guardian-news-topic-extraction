document.addEventListener('DOMContentLoaded', () => {
    // Estrae il tag dall'URL corrente
    console.log("Il DOM è stato caricato");
    const pathSegments = window.location.pathname.split('/');
    const tag = pathSegments[1]; // Assumendo che il tag sia la prima parte dell'URL

    // Effettua una richiesta al backend per ottenere il valore di tag
    fetch(`/api/${tag}`)
        .then(response => response.json())
        .then(data => {
            const tagValue = data.tag;
            console.log('Tag:', tagValue);

            // Esempio: cambia il contenuto di una parte della pagina in base al tag
            const h1 = document.querySelector('h1');
            h1.textContent = `Topics for ${tagValue}:`;
        })
        .catch(error => console.error('Errore:', error));

    const form = document.getElementById('topics-form');
    const submitButton = form.querySelector('button[type="submit"]');
    const responseMessage = document.getElementById('response-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Disabilita il pulsante di invio e mostra il messaggio di caricamento
        submitButton.disabled = true;
        submitButton.textContent = 'Modeling...';
        responseMessage.textContent = '';

        const topicsQuery = document.getElementById('topicsQuery').value;
        const numOfTopics = document.getElementById('numOfTopics').value;
        const numOfTopWords = document.getElementById('numOfTopWords').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        const data = {
            topicsQuery: topicsQuery,
            numOfTopics: numOfTopics,
            numOfTopWords: numOfTopWords,
            startDate: startDate,
            endDate: endDate
        };

        await new Promise(resolve => setTimeout(resolve, 3000));
        submitButton.disabled = false;
        submitButton.textContent = 'Submit';
        /*try {
            const response = await fetch('http://localhost:8080/api/model', {
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
            submitButton.disabled = false;
            submitButton.textContent = 'Submit';
        }*/
    });
});