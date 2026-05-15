document.addEventListener('DOMContentLoaded', () => {
    registerBackToTopButton();
    registerNextSlideClick();
});

function registerBackToTopButton() {
    let btn = document.getElementById('back-to-top');
    btn.addEventListener('click', () => {
        window.scrollTo(0, 0);
    });
}

function registerNextSlideClick() {
    let slides = document.querySelectorAll('section');
    console.log('slides');
}