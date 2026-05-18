let curSlide;

document.addEventListener('DOMContentLoaded', () => {
    registerSlideObserver();
    registerTopBtn();
    registerPrevBtn();
    registerNextBtn();
});

function registerSlideObserver() {
    let slides = document.querySelectorAll('section');
    curSlide = slides[0];

    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                curSlide = entry.target;
            }
        });
    }, {
        threshold: 0.6
    });

    slides.forEach(slide => observer.observe(slide));
}

function registerTopBtn() {
    let topBtn = document.getElementById('top-btn');
    topBtn.addEventListener('click', () => {
        window.scrollTo(0, 0);
    });
}

function registerPrevBtn() {
    let prevBtn = document.getElementById('prev-btn');
    prevBtn.addEventListener('click', () => {
        const prevSlide = curSlide.previousElementSibling;
        if (prevSlide && prevSlide.tagName === 'SECTION') {
            prevSlide.scrollIntoView();
        }
    });
}

function registerNextBtn() {
    let nextBtn = document.getElementById('next-btn');
    nextBtn.addEventListener('click', () => {
        const nextSlide = curSlide.nextElementSibling;
        if (nextSlide && nextSlide.tagName === 'SECTION') {
            nextSlide.scrollIntoView();
        }
    });
}
