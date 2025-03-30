package com.vinylrecordshop.VinylZ.config;

import com.vinylrecordshop.VinylZ.model.Role;
import com.vinylrecordshop.VinylZ.model.User;
import com.vinylrecordshop.VinylZ.model.Vinyl;
import com.vinylrecordshop.VinylZ.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    public static final String ADMIN = "admin";
    public static final String CUSTOMER = "customer";

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    List<Vinyl> orders;

    @PostConstruct
    public void initData() {
        User admin = this.userService.create(ADMIN, ADMIN, Role.ADMIN, orders);
        User customer = this.userService.create(CUSTOMER, CUSTOMER, Role.USER, orders);

    }
}
