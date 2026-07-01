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

    const infraSlider = document.querySelector("[data-infra-slider]");

    if (infraSlider) {
        const track = infraSlider.querySelector(".infra-slider__track");
        const slides = Array.from(infraSlider.querySelectorAll(".infra-slide"));
        const prevButton = infraSlider.querySelector(".infra-slider__arrow--prev");
        const nextButton = infraSlider.querySelector(".infra-slider__arrow--next");

        let currentIndex = 0;

        const updateSlider = () => {
            track.style.transform = `translateX(-${currentIndex * 100}%)`;

            slides.forEach((slide, index) => {
                slide.classList.toggle("is-active", index === currentIndex);
            });

            prevButton.classList.toggle("is-hidden", currentIndex === 0);
            nextButton.classList.toggle("is-hidden", currentIndex === slides.length - 1);
        };

        prevButton.addEventListener("click", () => {
            if (currentIndex === 0) return;
            currentIndex -= 1;
            updateSlider();
        });

        nextButton.addEventListener("click", () => {
            if (currentIndex === slides.length - 1) return;
            currentIndex += 1;
            updateSlider();
        });

        updateSlider();
    }

});