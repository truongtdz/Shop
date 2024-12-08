package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.enums.CategoryEnum;
import com.example.shop.enums.GenderEnum;
import com.example.shop.service.ItemService;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ModelAndView admin(){
        return new ModelAndView("/admin/admin")
                .addObject("countUser", userService.getCount())
                .addObject("countProduct", productService.getCount())
                .addObject("countOrder", orderService.getCount())
                .addObject("icrease", orderService.getIcrease());
    }

    @GetMapping("/user")
    public ModelAndView userManagement(){
        return new ModelAndView("admin/user/management")
                .addObject("users", userService.getAllUser());
    }

    @GetMapping("/user/create")
    public ModelAndView createUser(){
        return new ModelAndView("/admin/user/create")
                .addObject("newUser", new User())
                .addObject("genders", GenderEnum.values());
    }

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute("newUser") User newUser,
                             @RequestParam("file") MultipartFile file, Model model){
        if(userService.createUser(newUser, file)){
            model.addAttribute("notification", "Success");
            return "redirect:/admin/user";
        } else {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", "Người dùng đã tồn tại ");
            return "/admin/admin";
        }
    }

    @GetMapping("/user/update/{userId}")
    public ModelAndView updateUser(@PathVariable Long userId){
        return new ModelAndView("/admin/user/update")
                .addObject("user", userService.getUserById(userId))
                .addObject("updateUser", new User())
                .addObject("genders", GenderEnum.values());
    }

    @PostMapping("/user/update/{userId}")
    public String updateUser(@ModelAttribute("updateUser") User updateUser, @PathVariable Long userId,
                             @RequestParam("file") MultipartFile file ,Model model){
        userService.updateUser(updateUser, userId, file);
        model.addAttribute("notification", "Success");
        return "/admin/admin";
    }

    @GetMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/admin/user";
    }


    @GetMapping("/product")
    public ModelAndView productManagement(){
        return new ModelAndView("admin/product/management")
                .addObject("products", productService.getAllProduct());
    }

    @GetMapping("/product/create")
    public ModelAndView createProduct(){
        return new ModelAndView("/admin/product/create")
                .addObject("newProduct", new Product())
                .addObject("categories", CategoryEnum.values());
    }

    @PostMapping("/product/create")
    public String createProduct(@ModelAttribute("newProduct") Product newProduct,
                                @RequestParam("file") MultipartFile image, Model model){
        if(productService.createProduct(newProduct, image)){
            model.addAttribute("notification", "Success");
            return "redirect:/admin/product";
        } else {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", "Sản phẩm đã tồn tại ");
            return "/admin/admin";
        }
        
    }

    @GetMapping("/product/update/{productId}")
    public ModelAndView updateProduct(@PathVariable Long productId){
        return new ModelAndView("/admin/product/update")
                .addObject("product", productService.getProductById(productId))
                .addObject("updateProduct", new Product())
                .addObject("categories", CategoryEnum.values());
    }

    @PostMapping("/product/update/{productId}")
    public String updateProduct(@ModelAttribute("updateProduct") Product updateProduct,
                                @RequestParam("file") MultipartFile image,
                                @PathVariable Long productId, Model model){
        if(productService.updateProduct(updateProduct, productId, image)){
            model.addAttribute("notification", "Success"); 
            return "redirect:/admin/product";
        } else {
            model.addAttribute("notification", "Fail"); 
            model.addAttribute("message", "Sản phẩm đã tồn tại ");
            return "/admin/admin";
            
        }
        
       
    }

    @GetMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, Model model) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }

    @GetMapping("/order")
    public ModelAndView orderManagement(){
        return new ModelAndView("/admin/order/management")
                .addObject("orders", orderService.getAllOrder());
    }

    @GetMapping("/order/{orderId}")
    public ModelAndView getOrderById(@PathVariable Long orderId){
        return new ModelAndView("/admin/order/view")
                .addObject("order", orderService.getOrderById(orderId))
                .addObject("items", itemService.getItemByOrderId(orderId));
    }
}
