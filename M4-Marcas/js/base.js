/* ================== */
/* |SPACE BACKGROUND| */
/* ================== */

// Get the document height
function getDocumentHeight() {
    return Math.max(
        document.body.scrollHeight,
        document.documentElement.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.offsetHeight
    );
}

// Calculate density based on document height
function calculateDensity() {
    const docHeight = getDocumentHeight();
    const viewportHeight = window.innerHeight;
    const densityFactor = Math.max(1, docHeight / viewportHeight);
    
    return {
        stars: Math.floor(200 * densityFactor),
        planets: Math.floor(2 + densityFactor)
    };
}

// Stars layers with different sizes and opacities
const STAR_LAYERS = [
    { size: 2, opacity: 0.8 },
    { size: 4, opacity: 0.6 },
    { size: 6, opacity: 0.4 }
];

// Generate stars and clear existing ones when the window is resized
function generateStars() {
    const container = document.getElementById('stars-container');
    const { stars: starCount } = calculateDensity();
    const docHeight = getDocumentHeight();
    
    // Clear existing stars becouse of the resizing of the window
    const existingStars = container.querySelectorAll('.star');
    if (existingStars.length > 0) {
        existingStars.forEach(star => star.remove());
    }
    
    // Create a document fragment to get better performance
    const fragment = document.createDocumentFragment();

    for (let i = 0; i < starCount; i++) {
        const layerIndex = Math.floor(Math.random() * 3);
        const layer = STAR_LAYERS[layerIndex];
        const star = document.createElement('div');
        
        star.className = `star layer-${layerIndex + 1}`;
        star.style.left = `${Math.random() * 100}%`;
        star.style.top = `${Math.random() * docHeight}px`;
        star.style.width = `${layer.size}px`;
        star.style.height = `${layer.size}px`;
        star.style.opacity = layer.opacity;
        star.style.animationDelay = `${Math.random() * 5}s`;
        star.style.animationDuration = `${3 + Math.random() * 7}s`;
        star.setAttribute('data-layer', layerIndex + 1);
        
        fragment.appendChild(star);
    }
    
    container.appendChild(fragment);
}


// Planet colors, shadows and rings
const PLANET_COLORS = [
    { main: '#4a6fa5', shadow: '#1a2a4a', ring: 'rgba(210, 210, 230, 0.9)' },
    { main: '#c45c3e', shadow: '#5e2c20', ring: 'rgba(230, 180, 150, 0.9)' },
    { main: '#7a4a82', shadow: '#3a2342', ring: 'rgba(200, 180, 220, 0.9)' },
    { main: '#4a8f5e', shadow: '#1e4a2a', ring: 'rgba(180, 230, 200, 0.9)' },
    { main: '#d4a55e', shadow: '#7a4a20', ring: 'rgba(240, 220, 180, 0.9)' }
];

// Función para generar planetas optimizada
function generatePlanets() {
    const container = document.getElementById('stars-container');
    const { planets: planetCount } = calculateDensity();
    const docHeight = getDocumentHeight();
    
    // Limpiar planetas existentes
    container.querySelectorAll('.planet, .planet-ring').forEach(el => el.remove());
    
    const fragment = document.createDocumentFragment();
    
    for (let i = 0; i < planetCount; i++) {
        const size = Math.floor(Math.random() * 120) + 80;
        const colorSet = PLANET_COLORS[Math.floor(Math.random() * PLANET_COLORS.length)];
        const hasRing = Math.random() > 0.6;
        const xPos = Math.random() * 70 + 5;
        const yPos = Math.random() * docHeight;
        
        const planet = document.createElement('div');
        planet.className = `planet planet-${i+1}`;
        planet.style.width = `${size}px`;
        planet.style.height = `${size}px`;
        planet.style.left = `${xPos}%`;
        planet.style.top = `${yPos}px`;
        planet.style.background = `radial-gradient(circle at 25% 25%, ${colorSet.main}, ${colorSet.shadow} 75%)`;
        planet.setAttribute('data-layer', i + 3);
        planet.setAttribute('data-original-y', (yPos / 100) * window.innerHeight);
        
        if (hasRing) {
            const ringSize = size * (1.5 + Math.random() * 0.5);
            const ringThickness = size * (0.1 + Math.random() * 0.05);
            
            const ring = document.createElement('div');
            ring.className = 'planet-ring';
            ring.style.width = `${ringSize}px`;
            ring.style.height = `${ringThickness}px`;
            ring.style.borderWidth = `${ringThickness/2}px`;
            ring.style.borderColor = colorSet.ring;
            ring.style.top = `${(size - ringThickness)/2}px`;
            ring.style.left = `${(size - ringSize)/2}px`;
            ring.style.transform = `rotate(${Math.random() * 360}deg)`;
            planet.appendChild(ring);
        }
        
        fragment.appendChild(planet);
    }
    
    container.appendChild(fragment);
}

// Efecto de parallax optimizado con throttling
let lastScrollTime = 0;
function parallaxEffect() {
    const now = Date.now();
    if (now - lastScrollTime < 16) return; // ~60fps
    lastScrollTime = now;
    
    const scrollPosition = window.scrollY;
    
    // Mover estrellas
    document.querySelectorAll('.star').forEach(star => {
        const layer = parseInt(star.getAttribute('data-layer'));
        star.style.transform = `translateY(-${scrollPosition * layer * 0.15}px)`;
    });
    
    // Mover planetas
    document.querySelectorAll('.planet').forEach(planet => {
        const layer = parseInt(planet.getAttribute('data-layer'));
        const originalY = parseFloat(planet.getAttribute('data-original-y'));
        
        if (!isNaN(layer) && !isNaN(originalY)) {
            const yOffset = scrollPosition * layer * 0.08;
            const xOffset = Math.sin(scrollPosition * 0.0005 * layer) * 20;
            planet.style.transform = `translate(calc(-50% + ${xOffset}px), -${yOffset}px)`;
        }
    });
}

// Observador de altura optimizado
let lastHeight = 0;
let heightCheckTimeout;
function checkDocumentHeight() {
    clearTimeout(heightCheckTimeout);
    
    heightCheckTimeout = setTimeout(() => {
        const currentHeight = getDocumentHeight();
        if (Math.abs(currentHeight - lastHeight) > window.innerHeight * 0.5) {
            lastHeight = currentHeight;
            generateStars();
            generatePlanets();
        }
        checkDocumentHeight();
    }, 200); // Verificar cada 200ms en lugar de en cada frame
}

// Inicialización optimizada
function initSpaceEffects() {
    if (!document.getElementById('stars-container')) {
        const starsContainer = document.createElement('div');
        starsContainer.id = 'stars-container';
        document.body.insertBefore(starsContainer, document.body.firstChild);
    }
    
    generateStars();
    generatePlanets();
    
    // Event listeners con debouncing
    window.addEventListener('scroll', () => {
        requestAnimationFrame(parallaxEffect);
    });
    
    const resizeHandler = debounce(() => {
        document.querySelectorAll('.planet').forEach(planet => {
            const rect = planet.getBoundingClientRect();
            planet.setAttribute('data-original-y', rect.top + window.scrollY);
        });
        generateStars();
        generatePlanets();
    }, 100);
    
    window.addEventListener('resize', resizeHandler);
    
    // Iniciar observador de altura
    lastHeight = getDocumentHeight();
    checkDocumentHeight();
    
    // Disparar evento scroll inicial
    window.dispatchEvent(new Event('scroll'));
}

// Función debounce para optimización
function debounce(func, wait) {
    let timeout;
    return function() {
        const context = this, args = arguments;
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(context, args), wait);
    };
}

// Iniciar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', initSpaceEffects);



/* ================= */
/* |SMARTPHONE MENU| */
/* ================= */
document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.querySelector('.menu-toggle');
    const menu = document.querySelector('.menu');
    
    menuToggle.addEventListener('click', function() {
        menu.classList.toggle('active');
        menuToggle.classList.toggle('active');
    });
});