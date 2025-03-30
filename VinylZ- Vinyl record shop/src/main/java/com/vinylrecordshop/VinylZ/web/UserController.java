package com.vinylrecordshop.VinylZ.web;

import com.vinylrecordshop.VinylZ.model.Genre;
import com.vinylrecordshop.VinylZ.model.User;
import com.vinylrecordshop.VinylZ.model.Vinyl;
import com.vinylrecordshop.VinylZ.repository.UserRepository;
import com.vinylrecordshop.VinylZ.service.UserService;
import com.vinylrecordshop.VinylZ.service.VinylService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class UserController {

    private final UserService userService;
    private final VinylService vinylService; // Inject VinylService to fetch vinyls

    public UserController(UserService userService, VinylService vinylService) {
        this.userService = userService;
        this.vinylService = vinylService;
    }

    @GetMapping
    public String showCart(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Vinyl> orders = user.getOrders();
        model.addAttribute("orders", orders);

        return "cart.html";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        userService.addVinylToCart(username, id);

        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        userService.removeVinylFromCart(username, id);

        return "redirect:/cart";
    }

    @PostMapping("/buy")
    public String buyCartItems(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        for (Vinyl vinyl : user.getOrders()) {
            if (vinyl.getStock() > 0) {
                vinyl.setStock(vinyl.getStock() - 1);
            }
        }

        user.getOrders().clear();

        userService.saveUser(user);

        return "redirect:/cart";
    }
}
