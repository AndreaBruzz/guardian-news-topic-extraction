document.addEventListener('DOMContentLoaded', () => {
    loadComponent('header-placeholder', '../header.html');
    loadComponent('footer-placeholder', '../footer.html');
});

function loadComponent(placeholderId, filePath) {
    fetch(filePath)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            document.getElementById(placeholderId).innerHTML = data;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
