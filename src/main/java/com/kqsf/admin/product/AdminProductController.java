package com.kqsf.admin.product;

import com.kqsf.domain.product.dto.ProductCategoryForm;
import com.kqsf.domain.product.dto.ProductForm;
import com.kqsf.domain.product.dto.ProductProcessingOptionForm;
import com.kqsf.domain.product.entity.Product;
import com.kqsf.domain.product.entity.ProductCategory;
import com.kqsf.domain.product.entity.ProductProcessingOption;
import com.kqsf.domain.product.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductAdminService productAdminService;

    @GetMapping("/categories")
    public String categoryList(Model model) {
        model.addAttribute("categories", productAdminService.findAllCategories());
        return "admin/product/category-list";
    }

    @GetMapping("/categories/new")
    public String categoryCreateForm(Model model) {
        model.addAttribute("form", new ProductCategoryForm());
        return "admin/product/category-new";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute ProductCategoryForm form) {
        productAdminService.createCategory(form);
        return "redirect:/admin/products/categories";
    }

    @GetMapping("/categories/{id}/edit")
    public String categoryEditForm(
            @PathVariable Long id,
            Model model
    ) {
        ProductCategory category = productAdminService.findCategory(id);

        model.addAttribute("categoryId", id);
        model.addAttribute("form", ProductCategoryForm.from(category));

        return "admin/product/category-update";
    }

    @PostMapping("/categories/{id}/edit")
    public String updateCategory(
            @PathVariable Long id,
            @ModelAttribute ProductCategoryForm form
    ) {
        productAdminService.updateCategory(id, form);
        return "redirect:/admin/products/categories";
    }

    @GetMapping
    public String productList(
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        model.addAttribute("categories", productAdminService.findAllCategories());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("products", productAdminService.findProductPage(categoryId, pageable));

        return "admin/product/list";
    }

    @GetMapping("/new")
    public String productCreateForm(Model model) {
        model.addAttribute("form", new ProductForm());
        model.addAttribute("categories", productAdminService.findAllCategories());

        return "admin/product/new";
    }

    @PostMapping
    public String createProduct(@ModelAttribute ProductForm form) {
        Long productId = productAdminService.createProduct(form);
        return "redirect:/admin/products/" + productId;
    }

    @GetMapping("/{id}")
    public String productDetail(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("product", productAdminService.findProduct(id));
        return "admin/product/detail";
    }

    @GetMapping("/{id}/edit")
    public String productEditForm(
            @PathVariable Long id,
            Model model
    ) {
        Product product = productAdminService.findProduct(id);

        model.addAttribute("productId", id);
        model.addAttribute("form", ProductForm.from(product));
        model.addAttribute("categories", productAdminService.findAllCategories());

        return "admin/product/update";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductForm form
    ) {
        productAdminService.updateProduct(id, form);
        return "redirect:/admin/products/" + id;
    }

    @GetMapping("/{productId}/options/new")
    public String optionCreateForm(
            @PathVariable Long productId,
            Model model
    ) {
        Product product = productAdminService.findProduct(productId);

        model.addAttribute("product", product);
        model.addAttribute("form", new ProductProcessingOptionForm());

        return "admin/product/option-new";
    }

    @PostMapping("/{productId}/options")
    public String createOption(
            @PathVariable Long productId,
            @ModelAttribute ProductProcessingOptionForm form
    ) {
        productAdminService.createOption(productId, form);
        return "redirect:/admin/products/" + productId;
    }

    @GetMapping("/{productId}/options/{optionId}/edit")
    public String optionEditForm(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            Model model
    ) {
        Product product = productAdminService.findProduct(productId);
        ProductProcessingOption option = productAdminService.findOption(productId, optionId);

        model.addAttribute("product", product);
        model.addAttribute("optionId", optionId);
        model.addAttribute("form", ProductProcessingOptionForm.from(option));

        return "admin/product/option-update";
    }

    @PostMapping("/{productId}/options/{optionId}/edit")
    public String updateOption(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @ModelAttribute ProductProcessingOptionForm form
    ) {
        productAdminService.updateOption(productId, optionId, form);
        return "redirect:/admin/products/" + productId;
    }
}