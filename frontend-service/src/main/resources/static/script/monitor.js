document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('monitor-form');
    const submitButton = form.querySelector('button[type="submit"]');
    const responseMessage = document.getElementById('response-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Disabilita il pulsante di invio e mostra il messaggio di caricamento
        submitButton.disabled = true;
        submitButton.textContent = 'Submitting...';
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
            const response = await fetch('http://localhost:8080/collect', {
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
        }
    });
});
