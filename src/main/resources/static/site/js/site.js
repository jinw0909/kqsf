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

    // const ua = navigator.userAgent;
    // const isKakaoInApp = /KAKAOTALK/i.test(ua);
    //
    // if (isKakaoInApp) {
    //     document.documentElement.classList.add("is-kakao-inapp");
    // }

    const ua = navigator.userAgent;

    const isMobile = /Android|iPhone|iPad|iPod/i.test(ua);

    // iOS Safari
    const isMobileSafari =
        /iPhone|iPad|iPod/i.test(ua) &&
        /Safari/i.test(ua) &&
        !/CriOS|FxiOS|EdgiOS|Whale|KAKAOTALK/i.test(ua);

    // Android Chrome 또는 iOS Chrome
    const isMobileChrome =
        isMobile &&
        (
            /Chrome/i.test(ua) ||
            /CriOS/i.test(ua)
        ) &&
        !/SamsungBrowser|Whale|EdgA|EdgiOS|KAKAOTALK/i.test(ua);

    // 모바일인데 Chrome/Safari가 아니면 absolute
    const isOtherMobile = isMobile && !isMobileChrome && !isMobileSafari;

    if (isMobileSafari) {
        document.documentElement.classList.add("is-mobile-safari");
    } else if (isMobileChrome) {
        document.documentElement.classList.add("is-mobile-chrome");
    } else if (isOtherMobile) {
        document.documentElement.classList.add("is-mobile-absolute-bg");
    }

});