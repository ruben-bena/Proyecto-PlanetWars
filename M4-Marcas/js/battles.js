/* ======================== */
/* |BATTLE REPORT SEARCHER| */
/* ======================== */
document.addEventListener('DOMContentLoaded', function() {
    // Select the elements for the battle report searcher
    const formBuscarBatalla = document.getElementById('form-buscar-batalla');
    const resultadoBatalla = document.getElementById('resultado-batalla');

    // Initialize the current battle number
    let currentBattleNumber = null;
    
    // Add an event listener to the form
    formBuscarBatalla.addEventListener('submit', function(e) {
        // Prevent default form submission, to avoid page reload
        e.preventDefault(); 
        
        // Obtain the battle number from the input field
        const numeroBatalla = document.getElementById('numero-batalla').value;
        currentBattleNumber = numeroBatalla; // Almacenar el número de batalla
        
        // Load the battle file
        cargarBatalla(numeroBatalla);
    });
    
    // Function to load the battle file
    function cargarBatalla(numero) {
        // Search for the battle file
        const rutaBatalla = `battles/html/battle${numero}.html`;
        
        // Fetch the battle file
        fetch(rutaBatalla)
            // Check if the response is ok, if its ok, convert it to text
            .then(response => {
                if (!response.ok) {
                    throw new Error('Batalla no encontrada');
                }
                return response.text();
            })
            // Convert the response to text
            .then(html => {
                // Insert the HTML content into the result container
                resultadoBatalla.innerHTML = html;
            })
            // Handle if the battle file is not found
            .catch(error => {
                resultadoBatalla.innerHTML = `
                    <div class="error">
                        <h3>Error</h3>
                        <p>No se encontró la batalla #${numero}</p>
                    </div>
                `;
            });
    }

    

    /* ======================= */
    /* |XML DOWNLOADER BUTTON| */
    /* ======================= */
    // Delegate the click event to the download button
    resultadoBatalla.addEventListener('click', function(e) {
        if (e.target && e.target.id === 'downloadXML') {
            if (currentBattleNumber) {
                const rutaXML = `battles/xml/battle${currentBattleNumber}.xml`;
                
                // Create a link element to download the XML file
                const link = document.createElement('a');
                link.href = rutaXML;
                link.download = `battle${currentBattleNumber}.xml`;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    });
});