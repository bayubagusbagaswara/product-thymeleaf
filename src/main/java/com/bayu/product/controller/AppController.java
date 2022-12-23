package com.bayu.product.controller;

import com.bayu.product.model.Product;
import com.bayu.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    private final ProductService productService;

    public AppController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Product> listProducts = productService.listAll();

        // artinya listProducts akan ditampilkan default pada homePage
        model.addAttribute("listProducts", listProducts);

        return "index";
    }

    // controller untuk menampilkan page Create New Product
    @RequestMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        return "new_product";
    }

    // controller untuk menghandler proses save dari form Create New Product
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);

        return "redirect:/";
    }

    // controller untuk menghandle proses edit product
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = productService.get(id);
        mav.addObject("product", product);

        return mav;
    }

    // controller untuk menghandle proses delete product
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        productService.delete(id);
        return "redirect:/";
    }
}
