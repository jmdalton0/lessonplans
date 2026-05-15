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
    for (let i = 2; i < slides.length; i++) {
        let slide = slides.item(i);

        slide.addEventListener('click', () => {
            slide.scrollIntoView();
        });

        //slide.addEventListener('touchend', () => {
            //slide.scrollIntoView();
        //})
    }
}
