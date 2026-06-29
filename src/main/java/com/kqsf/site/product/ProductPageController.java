package com.kqsf.site.product;

import com.kqsf.domain.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductPageController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public String list(
            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        model.addAttribute("categories", productQueryService.findVisibleCategories());
        model.addAttribute("products", productQueryService.findVisibleProducts(pageable));

        return "site/product/list";
    }

    @GetMapping("/{categoryCode}")
    public String category(
            @PathVariable String categoryCode,
            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        model.addAttribute("category", productQueryService.findCategoryByCode(categoryCode));
        model.addAttribute("categories", productQueryService.findVisibleCategories());
        model.addAttribute("products", productQueryService.findVisibleProductsByCategoryCode(categoryCode, pageable));

        return "site/product/category";
    }

    @GetMapping("/{categoryCode}/{productCode}")
    public String detail(
            @PathVariable String categoryCode,
            @PathVariable String productCode,
            Model model
    ) {
        model.addAttribute("product", productQueryService.findProductDetail(categoryCode, productCode));

        return "site/product/detail";
    }
}
