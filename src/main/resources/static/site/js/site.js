document.addEventListener("DOMContentLoaded", () => {

    const setStableViewportHeight = () => {
        const vh = window.innerHeight;
        document.documentElement.style.setProperty("--stable-vh", `${vh}px`);
    };

    setStableViewportHeight();

    const header = document.getElementById("siteHeader");
    const menuButton = document.getElementById("siteMenuButton");
    const siteNav = document.getElementById("siteNav");

    const handleHeaderScroll = () => {
        if (!header) return;

        if (window.scrollY > 12) {
            header.classList.add("is-scrolled");
        } else {
            header.classList.remove("is-scrolled");
        }
    };

    handleHeaderScroll();
    window.addEventListener("scroll", handleHeaderScroll, { passive: true });

    if (menuButton && siteNav) {
        menuButton.addEventListener("click", () => {
            siteNav.classList.toggle("is-open");
            menuButton.classList.toggle("is-open");
        });

        siteNav.querySelectorAll("a").forEach((link) => {
            link.addEventListener("click", () => {
                siteNav.classList.remove("is-open");
                menuButton.classList.remove("is-open");
            });
        });
    }

    const revealTargets = document.querySelectorAll(
        ".metric-card, .pillar-card, .split-section, .product-main-card, .qsf-step, .contact-cta"
    );

    if ("IntersectionObserver" in window) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    entry.target.classList.add("is-visible");
                    observer.unobserve(entry.target);
                }
            });
        }, {
            threshold: 0.14
        });

        revealTargets.forEach((target) => {
            target.classList.add("reveal");
            observer.observe(target);
        });
    }

    console.log('site.js loaded');

    const exploreSwiperEl = document.querySelector('.kqsf-explore-swiper');
    console.log('exploreSwiperEl:', exploreSwiperEl);
    console.log('Swiper:', typeof Swiper);

    if (exploreSwiperEl && typeof Swiper !== 'undefined') {
        new Swiper(exploreSwiperEl, {
            loop: false,
            speed: 650,
            slidesPerView: 1,
            spaceBetween: 0,

            allowTouchMove: true,
            simulateTouch: true,

            pagination: {
                el: '.kqsf-explore-pagination',
                clickable: true
            },

            navigation: {
                nextEl: '.kqsf-explore-button--next',
                prevEl: '.kqsf-explore-button--prev'
            },

            keyboard: {
                enabled: true
            }
        });

        console.log('KQSF explore swiper initialized');
    }


});