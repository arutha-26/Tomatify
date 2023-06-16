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
        prevEl: ".swiper-button-prev",
    },

    breakpoints: {
        1024: {
            spaceBetween: 72,
        }
    }
})

$(document).ready(function(){
    $(window).on('scroll', function(){
        if ($(window).scrollTop()){
            $("header").addClass('bcd');
        }else{
            $("header").removeClass('bcd');
        }
    })
})