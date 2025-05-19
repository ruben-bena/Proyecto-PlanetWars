/* ======== */
/* |SLIDER| */
/* ======== */
document.addEventListener("DOMContentLoaded", function() {
    // Image list 
    const images = [
        "img/index/screenshot1.png",
        "img/index/screenshot2.png",
        "img/index/screenshot3.png",
        "img/index/screenshot4.png",
        "img/index/screenshot5.png",
    ];

    // Select the slider elements
    const sliderImagesContainer = document.querySelector('.slider-images');
    const sliderDotsContainer = document.querySelector('.slider-dots');
    let currentIndex = 0;

    // Create image elements and dots
    images.forEach((src, idx) => {
        // Create image elements
        const img = document.createElement('img');
        img.src = src;
        img.className = 'slider-image' + (idx === 0 ? ' active' : '');
        img.alt = `Imagen ${idx+1}`;
        sliderImagesContainer.appendChild(img);

        // Create dot elements
        const dot = document.createElement('span');
        dot.className = 'slider-dot' + (idx === 0 ? ' active' : '');
        dot.addEventListener('click', () => showSlide(idx));
        sliderDotsContainer.appendChild(dot);
    });

    // Shows the active image and dot
    function showSlide(idx) {
        const imgs = document.querySelectorAll('.slider-image');
        const dots = document.querySelectorAll('.slider-dot');
        imgs.forEach((img, i) => img.classList.toggle('active', i === idx));
        dots.forEach((dot, i) => dot.classList.toggle('active', i === idx));
        currentIndex = idx;
    }

    // Buttons interaction
    document.querySelector('.slider-btn-left').addEventListener('click', () => {
        let idx = (currentIndex - 1 + images.length) % images.length;
        showSlide(idx);
    });
    document.querySelector('.slider-btn-right').addEventListener('click', () => {
        let idx = (currentIndex + 1) % images.length;
        showSlide(idx);
    });
});