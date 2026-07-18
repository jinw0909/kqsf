document.addEventListener("DOMContentLoaded", () => {

    /* =========================================================
   VIEWPORT HEIGHT
   주소창 변화는 무시하고 폭 변경 시에만 재계산
   ========================================================= */

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
    };

    let previousViewportWidth = window.innerWidth;

    setStableViewportHeight();

    window.addEventListener("resize", () => {
        const currentViewportWidth = window.innerWidth;

        // 주소창 표시·숨김처럼 높이만 변한 경우는 무시
        if (currentViewportWidth === previousViewportWidth) {
            return;
        }

        previousViewportWidth = currentViewportWidth;
        setStableViewportHeight();
    });

    /* =========================================================
       HEADER SCROLL
       ========================================================= */

    const header = document.getElementById("siteHeader");
    const menuButton = document.getElementById("siteMenuButton");
    const mobileNav = document.getElementById("mobileNav");

    const handleHeaderScroll = () => {
        if (!header) return;

        header.classList.toggle(
            "is-scrolled",
            window.scrollY > 12
        );
    };

    handleHeaderScroll();

    window.addEventListener(
        "scroll",
        handleHeaderScroll,
        { passive: true }
    );


    /* =========================================================
       DESKTOP NAVIGATION
       ========================================================= */

    const desktopNav = document.querySelector(".desktop-nav");

    if (desktopNav) {
        const desktopItems = Array.from(
            desktopNav.querySelectorAll(".desktop-nav__item")
        );

        let activeItem = null;

        /*
         * 클릭해서 닫은 메뉴를 기억한다.
         * 클릭 직후 같은 버튼 위에 마우스가 있어도
         * 메뉴가 다시 열리는 것을 방지한다.
         */
        let suppressedItem = null;

        const updateHighlightPosition = (item) => {
            const link = item.querySelector(".desktop-nav__link");

            if (!link) return;

            const navRect = desktopNav.getBoundingClientRect();
            const linkRect = link.getBoundingClientRect();

            const highlightLeft =
                linkRect.left - navRect.left;

            desktopNav.style.setProperty(
                "--nav-highlight-left",
                `${highlightLeft}px`
            );

            desktopNav.style.setProperty(
                "--nav-highlight-width",
                `${linkRect.width}px`
            );
        };

        const closeDesktopNav = () => {
            desktopItems.forEach((item) => {
                item.classList.remove("is-active");
            });

            activeItem = null;

            desktopNav.classList.remove(
                "has-active-item"
            );
        };

        // const openDesktopItem = (item) => {
        //     if (!item || item === suppressedItem) {
        //         return;
        //     }
        //
        //     desktopItems.forEach((navItem) => {
        //         navItem.classList.toggle(
        //             "is-active",
        //             navItem === item
        //         );
        //     });
        //
        //     activeItem = item;
        //
        //     updateHighlightPosition(item);
        //
        //     desktopNav.classList.add(
        //         "has-active-item"
        //     );
        // };
        const openDesktopItem = (item) => {
            if (!item || item === suppressedItem) {
                return;
            }

            if (activeItem === item) {
                updateHighlightPosition(item);
                return;
            }

            desktopItems.forEach((navItem) => {
                navItem.classList.remove("is-active");
            });

            item.classList.add("is-active");

            activeItem = item;

            updateHighlightPosition(item);
            desktopNav.classList.add("has-active-item");
        };

        // desktopItems.forEach((item) => {
        //     const button = item.querySelector(
        //         ".desktop-nav__link"
        //     );
        //
        //     if (!button) return;
        //
        //     item.addEventListener("pointerenter", () => {
        //         /*
        //          * 클릭해서 닫은 동일 메뉴 위에
        //          * 마우스가 계속 있는 동안에는 다시 열지 않는다.
        //          */
        //         if (item === suppressedItem) {
        //             return;
        //         }
        //
        //         openDesktopItem(item);
        //     });
        //
        //     item.addEventListener("pointerleave", () => {
        //         /*
        //          * 클릭해서 닫은 메뉴에서 마우스가 빠져나오면
        //          * 억제 상태를 해제한다.
        //          */
        //         if (item === suppressedItem) {
        //             suppressedItem = null;
        //         }
        //
        //         /*
        //          * 현재 활성 메뉴 영역을 완전히 벗어난 경우 닫는다.
        //          *
        //          * desktop-dropdown은 item 내부에 있으므로
        //          * 버튼에서 상세 메뉴로 이동할 때는 닫히지 않는다.
        //          */
        //         if (activeItem === item) {
        //             closeDesktopNav();
        //         }
        //     });
        //
        //     button.addEventListener("click", (event) => {
        //         event.preventDefault();
        //
        //         /*
        //          * 어떤 메인 메뉴든 클릭하면
        //          * 상세 메뉴와 강조 효과를 모두 닫는다.
        //          */
        //         suppressedItem = item;
        //
        //         closeDesktopNav();
        //
        //         button.blur();
        //     });
        //
        //     /*
        //      * 키보드 포커스로 접근했을 때도 메뉴를 연다.
        //      */
        //     button.addEventListener("focus", () => {
        //         suppressedItem = null;
        //         openDesktopItem(item);
        //     });
        // });
        desktopItems.forEach((item) => {
            const button = item.querySelector(
                ".desktop-nav__link"
            );

            if (!button) return;

            item.addEventListener("pointerenter", () => {
                if (item === suppressedItem) {
                    return;
                }

                /*
                 * 다른 메뉴로 이동했다면 클릭 억제 상태 해제
                 */
                if (
                    suppressedItem &&
                    item !== suppressedItem
                ) {
                    suppressedItem = null;
                }

                openDesktopItem(item);
            });

            button.addEventListener("click", (event) => {
                event.preventDefault();

                suppressedItem = item;
                closeDesktopNav();
                button.blur();
            });

            button.addEventListener("focus", () => {
                suppressedItem = null;
                openDesktopItem(item);
            });
        });

        /*
         * 각 메뉴 사이를 움직일 때는 닫지 않고,
         * PC 네비게이션 전체를 벗어났을 때만 닫는다.
         */
        desktopNav.addEventListener("pointerleave", (event) => {
            const nextElement = event.relatedTarget;

            if (
                nextElement instanceof Node &&
                desktopNav.contains(nextElement)
            ) {
                return;
            }

            suppressedItem = null;
            closeDesktopNav();
        });


        /*
         * 상세 메뉴 내부에서 포커스가 이동할 때는 유지하고,
         * 전체 네비게이션 밖으로 포커스가 나가면 닫는다.
         */
        desktopNav.addEventListener("focusout", (event) => {
            const nextFocusedElement = event.relatedTarget;

            if (
                nextFocusedElement &&
                desktopNav.contains(nextFocusedElement)
            ) {
                return;
            }

            closeDesktopNav();
        });

        /*
         * ESC 키로 닫기
         */
        document.addEventListener("keydown", (event) => {
            if (event.key !== "Escape") return;

            if (activeItem) {
                suppressedItem = activeItem;
            }

            closeDesktopNav();

            document.activeElement?.blur();
        });

        /*
         * 화면 크기 변경 시 강조 배경 위치 재계산
         */
        window.addEventListener("resize", () => {
            if (activeItem) {
                updateHighlightPosition(activeItem);
            }
        });
    }


    /* =========================================================
       MOBILE NAVIGATION
       ========================================================= */

    if (menuButton && mobileNav) {
        const mobileItems = mobileNav.querySelectorAll(
            ".mobile-nav__item"
        );

        const closeMobileNav = () => {
            mobileNav.classList.remove("is-open");
            menuButton.classList.remove("is-open");

            menuButton.setAttribute(
                "aria-label",
                "메뉴 열기"
            );

            mobileItems.forEach((item) => {
                item.classList.remove("is-open");
            });

            document.documentElement.classList.remove(
                "is-nav-open"
            );

            document.body.classList.remove(
                "is-nav-open"
            );
        };

        menuButton.addEventListener("click", () => {
            const willOpen =
                !mobileNav.classList.contains("is-open");

            mobileNav.classList.toggle(
                "is-open",
                willOpen
            );

            menuButton.classList.toggle(
                "is-open",
                willOpen
            );

            menuButton.setAttribute(
                "aria-label",
                willOpen ? "메뉴 닫기" : "메뉴 열기"
            );

            document.documentElement.classList.toggle(
                "is-nav-open",
                willOpen
            );

            document.body.classList.toggle(
                "is-nav-open",
                willOpen
            );
        });

        mobileItems.forEach((item) => {
            const trigger = item.querySelector(
                ".mobile-nav__trigger"
            );

            if (!trigger) return;

            trigger.addEventListener("click", () => {
                const isOpen =
                    item.classList.contains("is-open");

                mobileItems.forEach((navItem) => {
                    navItem.classList.remove("is-open");
                });

                item.classList.toggle(
                    "is-open",
                    !isOpen
                );
            });
        });

        mobileNav.querySelectorAll("a").forEach((link) => {
            link.addEventListener(
                "click",
                closeMobileNav
            );
        });

        /*
         * 모바일 상태에서 PC 크기로 변경되면
         * 열린 모바일 메뉴를 초기화한다.
         */
        window.addEventListener("resize", () => {
            if (window.innerWidth > 900) {
                closeMobileNav();
            }
        });
    }


    /* =========================================================
       SCROLL REVEAL
       ========================================================= */

    const revealTargets = document.querySelectorAll(
        ".metric-card, " +
        ".pillar-card, " +
        ".split-section, " +
        ".product-main-card, " +
        ".qsf-step, " +
        ".contact-cta"
    );

    if ("IntersectionObserver" in window) {
        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (!entry.isIntersecting) return;

                    entry.target.classList.add(
                        "is-visible"
                    );

                    observer.unobserve(entry.target);
                });
            },
            {
                threshold: 0.14
            }
        );

        revealTargets.forEach((target) => {
            target.classList.add("reveal");
            observer.observe(target);
        });
    } else {
        revealTargets.forEach((target) => {
            target.classList.add(
                "reveal",
                "is-visible"
            );
        });
    }


    /* =========================================================
       EXPLORE SWIPER
       ========================================================= */

    const exploreSwiperEl = document.querySelector(
        ".kqsf-explore-swiper"
    );

    if (
        exploreSwiperEl &&
        typeof Swiper !== "undefined"
    ) {
        new Swiper(exploreSwiperEl, {
            loop: false,
            speed: 650,
            slidesPerView: 1,
            spaceBetween: 0,

            allowTouchMove: true,
            simulateTouch: true,

            pagination: {
                el: ".kqsf-explore-pagination",
                clickable: true
            },

            navigation: {
                nextEl: ".kqsf-explore-button--next",
                prevEl: ".kqsf-explore-button--prev"
            },

            keyboard: {
                enabled: true
            }
        });
    }


    /* =========================================================
   TEMPERATURE COUNTER
   아래로 스크롤하며 진입할 때만 다시 실행
   ========================================================= */

    const temperatureCounters = document.querySelectorAll(
        ".count-temperature"
    );

    const metricsSection = document.querySelector(
        ".home-metrics"
    );

    const animateTemperature = (counter) => {
        const target = Number(counter.dataset.target);
        const duration = 2800;
        const startTime = performance.now();

        if (!Number.isFinite(target)) {
            return;
        }

        if (counter.animationFrameId) {
            cancelAnimationFrame(
                counter.animationFrameId
            );
        }

        counter.textContent = "0°C";

        const update = (currentTime) => {
            const elapsed =
                currentTime - startTime;

            const progress = Math.min(
                elapsed / duration,
                1
            );

            const easedProgress =
                1 - Math.pow(1 - progress, 3);

            const currentValue = Math.round(
                target * easedProgress
            );

            counter.textContent =
                `${currentValue}°C`;

            if (progress < 1) {
                counter.animationFrameId =
                    requestAnimationFrame(update);
            } else {
                counter.textContent =
                    `${target}°C`;

                counter.animationFrameId = null;
            }
        };

        counter.animationFrameId =
            requestAnimationFrame(update);
    };

    if (
        metricsSection &&
        temperatureCounters.length &&
        "IntersectionObserver" in window
    ) {
        let previousScrollY = window.scrollY;
        let scrollDirection = "down";
        let wasVisible = false;

        window.addEventListener(
            "scroll",
            () => {
                const currentScrollY = window.scrollY;

                if (currentScrollY > previousScrollY) {
                    scrollDirection = "down";
                } else if (currentScrollY < previousScrollY) {
                    scrollDirection = "up";
                }

                previousScrollY = currentScrollY;
            },
            { passive: true }
        );

        const temperatureObserver =
            new IntersectionObserver(
                (entries) => {
                    entries.forEach((entry) => {
                        if (entry.isIntersecting) {
                            /*
                             * 아래로 스크롤하면서 섹션이 화면에 들어온 경우만 실행.
                             * 위로 스크롤해 다시 들어온 경우에는 실행하지 않는다.
                             */
                            if (
                                !wasVisible &&
                                scrollDirection === "down"
                            ) {
                                temperatureCounters.forEach(
                                    animateTemperature
                                );
                            }

                            wasVisible = true;
                        } else {
                            wasVisible = false;
                        }
                    });
                },
                {
                    threshold: 0.35
                }
            );

        temperatureObserver.observe(metricsSection);
    } else if (
        metricsSection &&
        temperatureCounters.length
    ) {
        temperatureCounters.forEach(
            animateTemperature
        );
    }

});