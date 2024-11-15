package com.example.shop.controller;

import com.example.shop.dto.Login;
import com.example.shop.dto.NewSearch;
import com.example.shop.entity.Comment;
import com.example.shop.entity.User;
import com.example.shop.enums.CategoryEnum;
import com.example.shop.enums.SortEnum;
import com.example.shop.service.CartService;
import com.example.shop.service.CommentService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CommentService commentService;

    @Autowired
    CartService cartService;

    @GetMapping("/home")
    public ModelAndView getHome(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView mav = new ModelAndView("/web/home")
                .addObject("user", user)
                .addObject("productNewest", productService.getProductNewest())
                .addObject("quantityProductNewest", productService.getProductNewest().size())
                .addObject("productSale", productService.getProductSale())
                .addObject("quantityProductSale", productService.getProductSale().size())
                .addObject("name", null);

        if(user != null){
            mav.addObject("count", cartService.getCountByUserId(user.getId()));
        } else mav.addObject("count", 0);

        return mav;

    }

    @GetMapping("/login")
    public ModelAndView getLogin(){
        return new ModelAndView("/web/login")
                .addObject("userLogin", new User());
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userLogin") Login request, Model model, HttpSession session){
        if(request.getUsername().equals("admin")){
            return new ModelAndView("redirect:/admin");
        }
        if(userService.login(request)){
            session.setAttribute("user", userService.getUserByUsername(request.getUsername()));
            return new ModelAndView("redirect:/home");
        } else {
            model.addAttribute("message", " Tài Khoản Hoặc Mật Khẩu Không Đúng ");
            return new ModelAndView("web/login");
        }
    }

    @GetMapping("/register")
    public ModelAndView getRegister(){
        return new ModelAndView("/web/register")
                .addObject("userRegister", new User());
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userRegister") User userLogin, Model model){
        if(userService.createUser(userLogin, null)){
            return "redirect:/login";
        } else {
            model.addAttribute("message", " Tài Khoản Đã Tồn Tại ");
            return "web/register";
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("user");
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/home/product/{productId}")
    public ModelAndView getProductDetail(HttpSession session, @PathVariable Long productId){
        User user = (User) session.getAttribute("user");
        return new ModelAndView("/web/product_detail")
                .addObject("product", productService.getProductById(productId))
                .addObject("user", user)
                .addObject("newComment", new Comment())
                .addObject("comments", commentService.findByProductId(productId));
    }

    @GetMapping("/home/search-name")
    public ModelAndView searchProductByName(@RequestParam(required = false) String name) {
        return new ModelAndView("/web/search")
                .addObject("newSearch", new NewSearch())
                .addObject("products", productService.searchProductByName(name))
                .addObject("categories", CategoryEnum.values())
                .addObject("sorts", SortEnum.values());
    }

    @GetMapping("/home/search")
    public ModelAndView searchProduct() {
        return new ModelAndView("/web/search")
                .addObject("newSearch", new NewSearch())
                .addObject("products", productService.getAllProduct())
                .addObject("categories", CategoryEnum.values())
                .addObject("sorts", SortEnum.values());
    }

    @PostMapping("/home/search")
    public ModelAndView search(@ModelAttribute("newSearch") NewSearch newSearch){
        return new ModelAndView("/web/search")
                .addObject("newSearch", new NewSearch())
                .addObject("products", productService.searchProduct(newSearch))
                .addObject("categories", CategoryEnum.values())
                .addObject("sorts", SortEnum.values());
    }

    @GetMapping("/contact")
    public String getContact() {
        return "/web/contact";
    }

    @GetMapping("/introduce")
    public String getIntroduce() {
        return "/web/introduce";
    }
    
}
