// Fondo de estrellas con efecto parallax
document.addEventListener('DOMContentLoaded', function() {
    // Crear contenedor de estrellas si no existe
    if (!document.getElementById('stars-container')) {
        const starsContainer = document.createElement('div');
        starsContainer.id = 'stars-container';
        document.body.insertBefore(starsContainer, document.body.firstChild);
        
        // Generar estrellas
        generateStars();
    }
    
    // Efecto parallax con scroll
    window.addEventListener('scroll', parallaxEffect);
    
    // Para que el efecto funcione al cargar la página
    window.dispatchEvent(new Event('scroll'));
});

function generateStars() {
    const container = document.getElementById('stars-container');
    const starCount = 400; // Total de estrellas
    
    // Limpiar contenedor primero
    container.innerHTML = '';
    
    // Crear estrellas en posiciones aleatorias
    for (let i = 0; i < starCount; i++) {
        const star = document.createElement('div');
        const layer = Math.floor(Math.random() * 3) + 1; // Capa aleatoria (1, 2 o 3)
        
        star.classList.add('star', `layer-${layer}`);
        
        // Posición aleatoria
        const xPos = Math.random() * 100;
        const yPos = Math.random() * 100;
        
        // Tamaño y opacidad basados en la capa
        let size, opacity;
        switch(layer) {
            case 1:
                size = 2;
                opacity = 0.8;
                break;
            case 2:
                size = 4;
                opacity = 0.6;
                break;
            case 3:
                size = 6;
                opacity = 0.4;
                break;
        }
        
        // Posicionar la estrella
        star.style.left = `${xPos}%`;
        star.style.top = `${yPos}%`;
        star.style.width = `${size}px`;
        star.style.height = `${size}px`;
        star.style.opacity = opacity;
        
        // Animación de parpadeo aleatoria
        star.style.animationDelay = `${Math.random() * 5}s`;
        star.style.animationDuration = `${3 + Math.random() * 7}s`;
        
        // Almacenar la capa como atributo para el parallax
        star.setAttribute('data-layer', layer);
        
        container.appendChild(star);
    }
}

function parallaxEffect() {
    const scrollPosition = window.scrollY;
    const stars = document.querySelectorAll('.star');
    
    stars.forEach(star => {
        const layer = parseInt(star.getAttribute('data-layer'));
        const speed = layer * 0.15; // Ajusta la velocidad según la capa
        
        // Mover las estrellas a diferente velocidad según su capa
        const yOffset = scrollPosition * speed;
        star.style.transform = `translateY(-${yOffset}px)`;
    });
}