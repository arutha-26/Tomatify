//navbar responsif
let navbar = document.querySelector('.nav__links');

document.querySelector('#gambar').onclick = () =>{
    navbar.classList.toggle('active');
}

window.onscroll = () =>{
    navbar.classList.remove('active');
}

let swiperProducts = new Swiper(".doc-container", {
    spaceBetween: 32,
    grabCursor: true,
    centeredSlides: true,
    slidesPerView: 'auto',
    loop: true,

    navigation: {
        nextEl: ".swiper-button-next",
        prevEL: ".swiper-button-prev",
    },

    breakpoints: {
        1024: {
            spaceBetween: 72,
        }
    }
})