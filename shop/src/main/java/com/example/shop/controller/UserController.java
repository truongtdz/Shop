package com.example.shop.controller;

import com.example.shop.entity.Comment;
import com.example.shop.entity.User;
import com.example.shop.enums.GenderEnum;
import com.example.shop.service.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    CommentService commentService;

    @Autowired
    ProductService productService;

    @GetMapping("/info")
    public ModelAndView viewUser(HttpSession session){
        return new ModelAndView("/user/information")
                .addObject("user", session.getAttribute("user"))
                .addObject("genders", GenderEnum.values())
                .addObject("updateUser", new User());
    }

    @PostMapping("/update")
    public String updateUser(HttpSession session, @ModelAttribute("updateUser") User request,
                             @RequestParam("file") MultipartFile file){
        User user = (User) session.getAttribute("user");

        userService.updateUser(request, user.getId(), file);
        
        session.setAttribute("user", userService.getUserById(user.getId()));

        return "redirect:/home";
    }

    @GetMapping("/cart")
    public ModelAndView getCart(HttpSession session){
        User user = (User) session.getAttribute("user");

        return new ModelAndView("/user/cart")
                .addObject("carts", cartService.getCart(user.getId()));
    }

    @GetMapping("/cart/{productId}")
    public ModelAndView addCart(HttpSession session, @PathVariable Long productId, Model model){
        User user = (User) session.getAttribute("user");
        if(user != null){
            if(cartService.addCart(user.getId(), productId)){
                model.addAttribute("notification", "Success");
                model.addAttribute("message", "Thêm Sản Phẩm Thành Công ");
                return new ModelAndView("/user/cart")
                        .addObject("carts", cartService.getCart(user.getId()));
            } else {
                model.addAttribute("notification", "Fail");
                model.addAttribute("message", "Sản Phẩm Này Đã Có Trong Giỏ Hàng Rồi");
                return new ModelAndView("/user/cart")
                        .addObject("carts", cartService.getCart(user.getId()));
            }
        } else{
            return new ModelAndView("redirect:/login");
        }
        
    }

    @GetMapping("/cart/{productId}/{quantity}")
    public ModelAndView replaceQuantityProduct(HttpSession session, Model model,
                                        @PathVariable("productId") Long productId,
                                        @PathVariable("quantity") Long quantity){
        User user = (User) session.getAttribute("user");
        if(cartService.replaceQuantityProduct(user.getId(), productId, quantity)){
            return new ModelAndView("redirect:/user/cart");
        }
        else {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", "Không thể mua thêm vì không đủ hàng");
            return new ModelAndView("/user/cart");
        }
    }

    @GetMapping("/cart/delete/{productId}")
    public String deleteCart(HttpSession session, @PathVariable Long productId){
        User user = (User) session.getAttribute("user");
        cartService.removeProductToCart(user.getId(), productId);
        return "redirect:/user/cart";
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if(user.getCity() == null || user.getDistrict() == null || user.getAddress() == null || user.getPhone() == null){
            model.addAttribute("message", "Vui Lòng Cập Nhập Đầy Đủ Địa Chỉ Và SDT ");
            return  new ModelAndView("/user/information")
                    .addObject("user", session.getAttribute("user"))
                    .addObject("genders", GenderEnum.values())
                    .addObject("updateUser", new User());
        } else{
            Long orderId = orderService.AddOrder(user);
            itemService.AddItem(cartService.getCart(user.getId()), orderId);
            model.addAttribute("notification", "Bạn đã đặt hàng thành công ");
            model.addAttribute("message", "Vui lòng để ý số diện thoại : " + user.getPhone());
            orderService.UpdateToTalPrice(orderId);
            productService.updateQuantityProduct(cartService.getCart(user.getId()));
            cartService.removeCartByUserId(user.getId());
            return new ModelAndView("/user/cart");
        }
    }

    @PostMapping("/comment/{productId}")
    public String comment(@ModelAttribute("newComment")Comment newComment,
                          @PathVariable Long productId, HttpSession session){
        newComment.setProduct(productService.getProductById(productId));
        newComment.setUser((User) session.getAttribute("user"));
        commentService.AddComment(newComment);
        return "redirect:/home/product/{productId}";
    }

    @GetMapping("/history")
    public ModelAndView historyBuyProduct(HttpSession session){
        User user = (User) session.getAttribute("user");
        
        if(user != null){
            return new ModelAndView("/user/history")
                .addObject("orders", orderService.historyBuy(user.getId()));
        }
        else return new ModelAndView("redirect:/login");
    }
}
