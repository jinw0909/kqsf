document.addEventListener("DOMContentLoaded", () => {
    const processingSwiperEl = document.querySelector(".processing-swiper");

    if (processingSwiperEl && typeof Swiper !== "undefined") {
        new Swiper(processingSwiperEl, {
            slidesPerView: 1,
            spaceBetween: 18,
            speed: 450,

            pagination: {
                el: ".processing-swiper-pagination",
                clickable: true
            },

            navigation: {
                prevEl: ".processing-swiper-button-prev",
                nextEl: ".processing-swiper-button-next"
            },

            breakpoints: {
                768: {
                    slidesPerView: 2,
                    spaceBetween: 22
                },
                1100: {
                    slidesPerView: 3,
                    spaceBetween: 24
                }
            }
        });
    }
});