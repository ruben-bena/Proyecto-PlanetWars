// ... existing code ...
// Slider de fotos para la sección game-gallery

document.addEventListener("DOMContentLoaded", function() {
    const images = [
        "img/index/placeholder-1.png",
        "img/index/placeholder-1.png",
        "img/index/placeholder-1.png",
        "img/index/placeholder-1.png",
        "img/index/placeholder-1.png",
        "img/index/placeholder-1.png",
    ];

    const sliderImagesContainer = document.querySelector('.slider-images');
    const sliderDotsContainer = document.querySelector('.slider-dots');
    let currentIndex = 0;

    // Crear imágenes
    images.forEach((src, idx) => {
        const img = document.createElement('img');
        img.src = src;
        img.className = 'slider-image' + (idx === 0 ? ' active' : '');
        img.alt = `Imagen ${idx+1}`;
        sliderImagesContainer.appendChild(img);

        // Crear dots
        const dot = document.createElement('span');
        dot.className = 'slider-dot' + (idx === 0 ? ' active' : '');
        dot.addEventListener('click', () => showSlide(idx));
        sliderDotsContainer.appendChild(dot);
    });

    function showSlide(idx) {
        const imgs = document.querySelectorAll('.slider-image');
        const dots = document.querySelectorAll('.slider-dot');
        imgs.forEach((img, i) => img.classList.toggle('active', i === idx));
        dots.forEach((dot, i) => dot.classList.toggle('active', i === idx));
        currentIndex = idx;
    }

    document.querySelector('.slider-btn-left').addEventListener('click', () => {
        let idx = (currentIndex - 1 + images.length) % images.length;
        showSlide(idx);
    });
    document.querySelector('.slider-btn-right').addEventListener('click', () => {
        let idx = (currentIndex + 1) % images.length;
        showSlide(idx);
    });
});