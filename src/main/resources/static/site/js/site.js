document.addEventListener("DOMContentLoaded", () => {
    const menuButton = document.querySelector("[data-menu-button]");
    const siteNav = document.querySelector("[data-site-nav]");

    if (menuButton && siteNav) {
        menuButton.addEventListener("click", () => {
            siteNav.classList.toggle("is-open");

            const isOpen = siteNav.classList.contains("is-open");
            menuButton.setAttribute("aria-label", isOpen ? "메뉴 닫기" : "메뉴 열기");
        });

        siteNav.querySelectorAll("a").forEach((link) => {
            link.addEventListener("click", () => {
                siteNav.classList.remove("is-open");
                menuButton.setAttribute("aria-label", "메뉴 열기");
            });
        });
    }

    const header = document.querySelector(".site-header");

    if (header) {
        const updateHeader = () => {
            if (window.scrollY > 16) {
                header.classList.add("is-scrolled");
            } else {
                header.classList.remove("is-scrolled");
            }
        };

        updateHeader();
        window.addEventListener("scroll", updateHeader, { passive: true });
    }
});