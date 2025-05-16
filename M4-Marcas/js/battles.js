/* ======================= */
/* |XML DOWNLOADER BUTTON| */
/* ======================= */
// Variable to store the current battle number
let currentBattleNumber = null;

// Function to handle the download button click
function downloadBattleXML() {
    // Path to the XML file
    const xmlPath = `battles/xml/battle${currentBattleNumber}.xml`;
    
    // Create a temporary anchor element to trigger the download
    const link = document.createElement('a');
    link.href = xmlPath;
    link.download = `battle${currentBattleNumber}.xml`;
    
    // Append to the body, trigger click, and remove
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}



/* ======================== */
/* |BATTLE REPORT SEARCHER| */
/* ======================== */
document.addEventListener('DOMContentLoaded', function() {
    // Select the elements for the battle report searcher
    const formBuscarBatalla = document.getElementById('form-buscar-batalla');
    const resultadoBatalla = document.getElementById('resultado-batalla');
    
    // Add an event listener to the form
    formBuscarBatalla.addEventListener('submit', function(e) {
        // Prevent default form submission, to avoid page reload
        e.preventDefault(); 
        
        // Obtain the battle number from the input field
        const numeroBatalla = document.getElementById('numero-batalla').value;

        // Update the current battle number
        currentBattleNumber = numeroBatalla;
        
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
            }
        );
    }
});