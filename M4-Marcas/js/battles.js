// Función para cargar batallas
document.getElementById('form-buscar-batalla').addEventListener('submit', function(e) {
    e.preventDefault();
    const numeroBatalla = document.getElementById('numero-batalla').value;
    cargarBatalla(numeroBatalla);
});

function cargarBatalla(numero) {
    const rutaBatalla = `batallas/batalla${numero}.html`;
    const contenedor = document.getElementById('resultado-batalla');
    
    fetch(rutaBatalla)
        .then(response => {
            if (!response.ok) {
                throw new Error('Batalla no encontrada');
            }
            return response.text();
        })
        .then(html => {
            contenedor.innerHTML = html;
        })
        .catch(error => {
            contenedor.innerHTML = `<p class="error">${error.message}</p>`;
        });
}