package net.torrydev.stockmanagementsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import net.torrydev.stockmanagementsystem.dto.ProductDto;
import net.torrydev.stockmanagementsystem.services.ProductDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    private ProductDaoService productService;

    @GetMapping(path = {"/","/catalog"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVELOPER','ROLE_USER')")
    public String getCatalog(Model model){
//        ProductDto dto = productService.findByProductCode(code).get();
//        log.info("==== Product with code {} found {}", code, dto);
//        model.addAttribute("product", dto);
        return "catalog";
    }

    @GetMapping("/product/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVELOPER','ROLE_USER')")
    public String getProductByProductCode(@PathVariable("code") String code, Model model){
        ProductDto dto = productService.findByProductCode(code).get();
        log.info("==== Product with code {} found {}", code, dto);
        model.addAttribute("product", dto);
        return "product";
    }
}
