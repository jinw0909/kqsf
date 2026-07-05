document.addEventListener("DOMContentLoaded", () => {

    // const setStableViewportHeight = () => {
    //     const header = document.getElementById("siteHeader");
    //     const homeHero = document.querySelector(".home-hero");
    //
    //     const viewportHeight = window.innerHeight;
    //
    //     const headerHeight = header ? header.offsetHeight : 0;
    //     const homeHeroHeight = homeHero ? homeHero.offsetHeight : 0;
    //
    //     const calculatedHeroArtHeight =
    //         viewportHeight - headerHeight - homeHeroHeight;
    //
    //     const stable40vh = viewportHeight * 0.4;
    //
    //     const heroArtHeight = Math.max(
    //         calculatedHeroArtHeight,
    //         stable40vh
    //     );
    //
    //     document.documentElement.style.setProperty(
    //         "--hero-art-height",
    //         `${Math.round(heroArtHeight)}px`
    //     );
    //
    //     document.documentElement.style.setProperty(
    //         "--stable-40vh",
    //         `${Math.round(stable40vh)}px`
    //     );
    // };

    const getViewportHeight = () => {
        return Math.min(
            window.visualViewport?.height || window.innerHeight,
            document.documentElement.clientHeight,
            window.innerHeight
        );
    };

    const getHeaderHeight = () => {
        const value = getComputedStyle(document.documentElement)
            .getPropertyValue("--header-height")
            .trim();

        return parseFloat(value) || 0;
    };

    const setStableViewportHeight = () => {
        const homeHero = document.querySelector(".home-hero");

        const viewportHeight = getViewportHeight();
        const headerHeight = getHeaderHeight();
        const homeHeroHeight = homeHero ? homeHero.offsetHeight : 0;

        const calculatedHeroArtHeight =
            viewportHeight - headerHeight - homeHeroHeight;

        const stable40vh = viewportHeight * 0.4;

        const heroArtHeight = Math.max(
            calculatedHeroArtHeight,
            stable40vh
        );

        document.documentElement.style.setProperty(
            "--hero-art-height",
            `${Math.round(heroArtHeight)}px`
        );

        document.documentElement.style.setProperty(
            "--stable-40vh",
            `${Math.round(stable40vh)}px`
        );

        console.log({
            innerHeight: window.innerHeight,
            visualViewportHeight: window.visualViewport?.height,
            clientHeight: document.documentElement.clientHeight,
            viewportHeight,
            headerHeight,
            homeHeroHeight,
            calculatedHeroArtHeight,
            stable40vh,
            heroArtHeight
        });
    };

    setStableViewportHeight();
    window.addEventListener("load", setStableViewportHeight);


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
        const navItems = siteNav.querySelectorAll(".site-nav__item.has-dropdown");

        const isMobileNav = () => window.matchMedia("(max-width: 900px)").matches;

        const closeAllDropdowns = () => {
            navItems.forEach((item) => {
                item.classList.remove("is-open");
            });
        };

        const closeMobileNav = () => {
            siteNav.classList.remove("is-open");
            menuButton.classList.remove("is-open");
            menuButton.setAttribute("aria-label", "메뉴 열기");
            closeAllDropdowns();
        };

        menuButton.addEventListener("click", () => {
            siteNav.classList.add("is-drawer-animated");

            const willOpen = !siteNav.classList.contains("is-open");

            siteNav.classList.toggle("is-open", willOpen);
            menuButton.classList.toggle("is-open", willOpen);
            menuButton.setAttribute("aria-label", willOpen ? "메뉴 닫기" : "메뉴 열기");
        });

        navItems.forEach((item) => {
            const trigger = item.querySelector(".site-nav__link");

            item.addEventListener("mouseenter", () => {
                if (isMobileNav()) return;

                navItems.forEach((navItem) => {
                    navItem.classList.remove("is-click-closed");
                    navItem.classList.remove("is-open");
                });
            });
            if (!trigger) return;

            trigger.addEventListener("click", (event) => {
                event.preventDefault();

                if (!isMobileNav()) {
                    item.classList.add("is-click-closed");
                    trigger.blur();
                    return;
                }

                const isOpen = item.classList.contains("is-open");

                navItems.forEach((navItem) => {
                    navItem.classList.remove("is-open");
                });

                item.classList.toggle("is-open", !isOpen);
            });
        });

        siteNav.querySelectorAll(".site-dropdown__link").forEach((link) => {
            link.addEventListener("click", () => {
                closeMobileNav();
            });
        });

        document.addEventListener("click", (event) => {
            if (!siteNav.contains(event.target) && !menuButton.contains(event.target)) {
                closeAllDropdowns();
            }
        });

        document.addEventListener("keydown", (event) => {
            if (event.key === "Escape") {
                closeMobileNav();
            }
        });

        window.addEventListener("resize", () => {
            siteNav.classList.remove("is-open");
            siteNav.classList.remove("is-drawer-animated");
            menuButton.classList.remove("is-open");
            closeAllDropdowns();
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



    // 여기부터 추가
    const temperatureCounters = document.querySelectorAll(".count-temperature");

    const animateTemperature = (counter) => {
        const target = Number(counter.dataset.target);
        const duration = 2800;
        const startTime = performance.now();

        const update = (currentTime) => {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);

            // 부드럽게 감속
            const easedProgress = 1 - Math.pow(1 - progress, 3);

            const currentValue = Math.round(target * easedProgress);
            counter.textContent = `${currentValue}°C`;

            if (progress < 1) {
                requestAnimationFrame(update);
            } else {
                counter.textContent = `${target}°C`;
            }
        };

        requestAnimationFrame(update);
    };

    if (temperatureCounters.length && "IntersectionObserver" in window) {
        const temperatureObserver = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (!entry.isIntersecting) return;

                temperatureCounters.forEach((counter) => {
                    animateTemperature(counter);
                });

                temperatureObserver.unobserve(entry.target);
            });
        }, {
            threshold: 0.35
        });

        const metricsSection = document.querySelector(".home-metrics");

        if (metricsSection) {
            temperatureObserver.observe(metricsSection);
        }
    }


});