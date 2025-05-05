// Slider automático (para la página principal)
if (document.querySelector('.slider-juego')) {
    let currentSlide = 0;
    const slides = document.querySelectorAll('.slide');
    
    function showSlide(n) {
        slides.forEach(slide => {
            slide.style.display = 'none';
        });
        
        currentSlide = (n + slides.length) % slides.length;
        slides[currentSlide].style.display = 'block';
    }
    
    function nextSlide() {
        showSlide(currentSlide + 1);
    }
    
    // Iniciar slider
    showSlide(0);
    setInterval(nextSlide, 5000);
}