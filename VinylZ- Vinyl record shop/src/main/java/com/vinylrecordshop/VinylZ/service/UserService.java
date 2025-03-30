package com.vinylrecordshop.VinylZ.service;

import com.vinylrecordshop.VinylZ.model.Role;
import com.vinylrecordshop.VinylZ.model.User;
import com.vinylrecordshop.VinylZ.model.Vinyl;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User create(String username, String password, Role role, List<Vinyl> orders);
    void addVinylToCart(String username, Long vinylId);
    void removeVinylFromCart(String username, Long vinylId);
    void saveUser(User user);
    List<Vinyl> getCartItems(String username);
}
