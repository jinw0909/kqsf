package com.kqsf.site.product;

import com.kqsf.domain.product.dto.ProductCategoryResponse;
import com.kqsf.domain.product.dto.ProductResponse;
import com.kqsf.domain.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//package com.kqsf.site.product;
//
//import com.kqsf.domain.product.service.ProductQueryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/products")
//@RequiredArgsConstructor
//public class ProductPageController {
//
//    private final ProductQueryService productQueryService;
//
//    @GetMapping
//    public String list(
//            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC) Pageable pageable,
//            Model model
//    ) {
//        model.addAttribute("categories", productQueryService.findVisibleCategories());
//        model.addAttribute("products", productQueryService.findVisibleProducts(pageable));
//
//        return "site/product/list";
//    }
//
//    @GetMapping("/{categoryCode}")
//    public String category(
//            @PathVariable String categoryCode,
//            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC) Pageable pageable,
//            Model model
//    ) {
//        model.addAttribute("category", productQueryService.findCategoryByCode(categoryCode));
//        model.addAttribute("categories", productQueryService.findVisibleCategories());
//        model.addAttribute("products", productQueryService.findVisibleProductsByCategoryCode(categoryCode, pageable));
//
//        return "site/product/category";
//    }
//
//    @GetMapping("/{categoryCode}/{productCode}")
//    public String detail(
//            @PathVariable String categoryCode,
//            @PathVariable String productCode,
//            Model model
//    ) {
//        model.addAttribute("product", productQueryService.findProductDetail(categoryCode, productCode));
//
//        return "site/product/detail";
//    }
//}

import com.kqsf.domain.product.dto.ProductCategoryResponse;
import com.kqsf.domain.product.dto.ProductResponse;
import com.kqsf.domain.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductPageController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public String list(
            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC)
            Pageable pageable,
            Model model
    ) {
        List<ProductCategoryResponse> categories = productQueryService.findVisibleCategories();
        Page<ProductResponse> products = productQueryService.findVisibleProducts(pageable);

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        // Thymeleaf 공통 처리용 alias
        model.addAttribute("currentCategory", null);

        return "site/product/list";
    }

    @GetMapping("/{categoryCode}")
    public String category(
            @PathVariable String categoryCode,
            @PageableDefault(size = 12, sort = "sortOrder", direction = Sort.Direction.ASC)
            Pageable pageable,
            Model model
    ) {
        ProductCategoryResponse category = productQueryService.findCategoryByCode(categoryCode);
        List<ProductCategoryResponse> categories = productQueryService.findVisibleCategories();
        Page<ProductResponse> products = productQueryService.findVisibleProductsByCategoryCode(categoryCode, pageable);

        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        // 새 Thymeleaf에서 쓰기 편한 alias
        model.addAttribute("currentCategory", category);

        return "site/product/category";
    }

    @GetMapping("/{categoryCode}/{productCode}")
    public String detail(
            @PathVariable String categoryCode,
            @PathVariable String productCode,
            Model model
    ) {
        ProductResponse product = productQueryService.findProductDetail(categoryCode, productCode);

        model.addAttribute("product", product);

        // 있으면 추가, 아직 서비스 없으면 일단 빈 리스트로 둬도 됨
        // List<ProductResponse> relatedProducts =
        //         productQueryService.findRelatedProducts(categoryCode, productCode, 3);
        // model.addAttribute("relatedProducts", relatedProducts);

        model.addAttribute("relatedProducts", List.of());

        return "site/product/detail";
    }
}