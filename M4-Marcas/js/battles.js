// battles.js

document.addEventListener('DOMContentLoaded', function() {
    const formBuscarBatalla = document.getElementById('form-buscar-batalla');
    const resultadoBatalla = document.getElementById('resultado-batalla');

    formBuscarBatalla.addEventListener('submit', function(e) {
        e.preventDefault();
        const numeroBatalla = document.getElementById('numero-batalla').value.trim();
        
        if (numeroBatalla) {
            cargarYProcesarBatalla(numeroBatalla);
        } else {
            resultadoBatalla.innerHTML = '<p class="error">Por favor ingresa un número de batalla válido</p>';
        }
    });

    async function cargarYProcesarBatalla(id) {
        try {
            // 1. Cargar el archivo XML
            const response = await fetch(`battles/xml/battle${id}.xml`);
            if (!response.ok) throw new Error('Batalla no encontrada');
            
            const xmlText = await response.text();
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlText, "text/xml");
            
            // 2. Parsear los datos del XML
            const datosBatalla = parsearXMLaJSON(xmlDoc, id);
            
            // 3. Generar el contenido HTML
            const contenidoHTML = generarHTMLBatalla(datosBatalla);
            
            // 4. Mostrar en la página
            resultadoBatalla.innerHTML = contenidoHTML;
            
            // 5. Guardar el archivo HTML (simulado)
            guardarArchivoBatalla(id, contenidoHTML);
            
        } catch (error) {
            console.error('Error:', error);
            resultadoBatalla.innerHTML = `<p class="error">Error al cargar la batalla #${id}: ${error.message}</p>`;
        }
    }

    function parsearXMLaJSON(xmlDoc, id) {
        // Función para extraer datos de un nodo específico
        const extraerDatosUnidad = (parentNode, unidad) => {
            const node = parentNode.getElementsByTagName(unidad)[0];
            return {
                units: parseInt(node.getElementsByTagName('units')[0].textContent),
                drops: parseInt(node.getElementsByTagName('drops')[0].textContent)
            };
        };

        // Función para extraer costos y pérdidas
        const extraerCostosPerdidas = (parentNode) => {
            const totalCost = parentNode.getElementsByTagName('total_cost')[0];
            const losses = parentNode.getElementsByTagName('losses')[0];
            
            return {
                total_cost: {
                    metal: parseInt(totalCost.getElementsByTagName('metal')[0].textContent),
                    deuterium: parseInt(totalCost.getElementsByTagName('deuterium')[0].textContent)
                },
                losses: {
                    metal: parseInt(losses.getElementsByTagName('metal')[0].textContent),
                    deuterium: parseInt(losses.getElementsByTagName('deuterium')[0].textContent),
                    weighted: parseInt(losses.getElementsByTagName('weighted')[0].textContent)
                }
            };
        };

        // Extraer datos del ejército del planeta
        const armyPlanetNode = xmlDoc.getElementsByTagName('army_planet')[0];
        const armyPlanet = {
            light_hunter: extraerDatosUnidad(armyPlanetNode, 'light_hunter'),
            heavy_hunter: extraerDatosUnidad(armyPlanetNode, 'heavy_hunter'),
            battle_ship: extraerDatosUnidad(armyPlanetNode, 'battle_ship'),
            armored_ship: extraerDatosUnidad(armyPlanetNode, 'armored_ship'),
            missile_launcher: extraerDatosUnidad(armyPlanetNode, 'missile_launcher'),
            ion_cannon: extraerDatosUnidad(armyPlanetNode, 'ion_cannon'),
            plasma_cannon: extraerDatosUnidad(armyPlanetNode, 'plasma_cannon'),
            ...extraerCostosPerdidas(armyPlanetNode)
        };

        // Extraer datos del ejército enemigo
        const armyEnemyNode = xmlDoc.getElementsByTagName('army_enemy')[0];
        const armyEnemy = {
            light_hunter: extraerDatosUnidad(armyEnemyNode, 'light_hunter'),
            heavy_hunter: extraerDatosUnidad(armyEnemyNode, 'heavy_hunter'),
            battle_ship: extraerDatosUnidad(armyEnemyNode, 'battle_ship'),
            armored_ship: extraerDatosUnidad(armyEnemyNode, 'armored_ship'),
            ...extraerCostosPerdidas(armyEnemyNode)
        };

        // Extraer otros datos
        const wasteNode = xmlDoc.getElementsByTagName('waste_generated')[0];
        const winner = xmlDoc.getElementsByTagName('winner')[0].textContent;

        return {
            id: id,
            army_planet: armyPlanet,
            army_enemy: armyEnemy,
            waste_generated: {
                metal: parseInt(wasteNode.getElementsByTagName('metal')[0].textContent),
                deuterium: parseInt(wasteNode.getElementsByTagName('deuterium')[0].textContent)
            },
            winner: winner
        };
    }

    function generarHTMLBatalla(datos) {
        return `
            <div class="reporte-batalla">
                <h2>BATTLE #${datos.id} STATISTICS</h2>
                
                <div class="reporte-batalla"
                    <h3>Army Comparison</h3>
                    <table class="army-comparison">
                        <thead>
                            <tr>
                                <th colspan="3">Army Planet</th>
                                <th colspan="3">Army Enemy</th>
                            </tr>
                            <tr>
                                <th>Type</th>
                                <th>Units</th>
                                <th>Drops</th>
                                <th>Type</th>
                                <th>Units</th>
                                <th>Drops</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${generarFilasUnidades(datos)}
                        </tbody>
                    </table>
                </div>
                                
                <div class="costs">
                    <h3>Costs</h3>
                    <div class="cost-planet">
                        <h3>Army Planet</h3>
                        <p>Metal: ${datos.army_planet.total_cost.metal.toLocaleString()}</p>
                        <p>Deuterium: ${datos.army_planet.total_cost.deuterium.toLocaleString()}</p>
                    </div>
                    <div class="cost-enemy">
                        <h3>Army Enemy</h3>
                        <p>Metal: ${datos.army_enemy.total_cost.metal.toLocaleString()}</p>
                        <p>Deuterium: ${datos.army_enemy.total_cost.deuterium.toLocaleString()}</p>
                    </div>
                </div>
                                
                <div class="losses">
                    <h3>Losses</h3>
                    <div class="losses-planet">
                        <h3>Army Planet</h3>
                        <p>Metal: ${datos.army_planet.losses.metal.toLocaleString()}</p>
                        <p>Deuterium: ${datos.army_planet.losses.deuterium.toLocaleString()}</p>
                        <p>Weighted: ${datos.army_planet.losses.weighted.toLocaleString()}</p>
                    </div>
                    <div class="losses-enemy">
                        <h3>Army Enemy</h3>
                        <p>Metal: ${datos.army_enemy.losses.metal.toLocaleString()}</p>
                        <p>Deuterium: ${datos.army_enemy.losses.deuterium.toLocaleString()}</p>
                        <p>Weighted: ${datos.army_enemy.losses.weighted.toLocaleString()}</p>
                    </div>
                </div>
                                
                <div class="waste">
                    <h3>Waste Generated:</h3>
                    <p>Metal: ${datos.waste_generated.metal.toLocaleString()}</p>
                    <p>Deuterium: ${datos.waste_generated.deuterium.toLocaleString()}</p>
                </div>
                
                <div class="result ${datos.winner === 'planet' ? 'victory' : 'defeat'}">
                    <h3>Winner</h3>
                    <p>${datos.winner === 'planet' ? 'Planet' : 'Enemy'}<p>
                </div>
            </div>
        `;
    }

    function generarFilasUnidades(datos) {
        // Unidades compartidas
        const unidadesCompartidas = [
            { key: 'light_hunter', name: 'Light Hunter' },
            { key: 'heavy_hunter', name: 'Heavy Hunter' },
            { key: 'battle_ship' , name: 'Battle Ship'  },
            { key: 'armored_ship', name: 'Armored Ship' }
        ];

        // Unidades exclusivas del planeta
        const unidadesPlaneta = [
            { key: 'missile_launcher', name: 'Missile Launcher' },
            { key: 'ion_cannon', name: 'Ion Cannon' },
            { key: 'plasma_cannon', name: 'Plasma Cannon' }
        ];

        // Generar filas para unidades compartidas
        let filas = unidadesCompartidas.map(unidad => `
            <tr>
                <td>${unidad.name}</td>
                <td>${datos.army_planet[unidad.key].units.toLocaleString()}</td>
                <td>${datos.army_planet[unidad.key].drops.toLocaleString()}</td>
                <td>${unidad.name}</td>
                <td>${datos.army_enemy[unidad.key].units.toLocaleString()}</td>
                <td>${datos.army_enemy[unidad.key].drops.toLocaleString()}</td>
            </tr>
        `).join('');

        // Generar filas para unidades exclusivas del planeta
        filas += unidadesPlaneta.map(unidad => `
            <tr>
                <td>${unidad.name}</td>
                <td>${datos.army_planet[unidad.key].units.toLocaleString()}</td>
                <td>${datos.army_planet[unidad.key].drops.toLocaleString()}</td>
                <td colspan="3"></td>
            </tr>
        `).join('');

        return filas;
    }

    function guardarArchivoBatalla(id, contenido) {
        // En un entorno real, esto requeriría enviar los datos al servidor
        // Aquí simulamos la acción con un mensaje de consola
        
        console.log(`Guardando archivo HTML para batalla ${id} en /battles/html/battle${id}.html`);
        console.log('Contenido:', contenido);
        
        // Para guardar realmente en el cliente (requiere interacción del usuario):
        /*
        const blob = new Blob([contenido], { type: 'text/html' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `battles/html/battle${id}.html`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
        */
        
        // En una implementación real, deberías hacer una petición al servidor:
        /*
        fetch('/guardar-batalla', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                id: id,
                contenido: contenido
            })
        });
        */
    }
});