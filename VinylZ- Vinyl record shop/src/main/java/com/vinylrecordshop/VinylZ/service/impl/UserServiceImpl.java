package com.vinylrecordshop.VinylZ.service.impl;

import com.vinylrecordshop.VinylZ.model.Role;
import com.vinylrecordshop.VinylZ.model.User;
import com.vinylrecordshop.VinylZ.model.Vinyl;
import com.vinylrecordshop.VinylZ.model.exceptions.InvalidUsernameException;
import com.vinylrecordshop.VinylZ.repository.UserRepository;
import com.vinylrecordshop.VinylZ.repository.VinylRepository;
import com.vinylrecordshop.VinylZ.service.UserService;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final VinylRepository vinylRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, VinylRepository vinylRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vinylRepository = vinylRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
    }

    @Override
    public User create(String username, String password, Role role, List<Vinyl> orders) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, role, orders);

        return userRepository.save(user);
    }

    @Override
    public List<Vinyl> getCartItems(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);

        return user.getOrders();
    }

    @Override
    public void addVinylToCart(String username, Long vinylId) {
        User user = findByUsername(username);
        Vinyl vinyl = vinylRepository.findById(vinylId).orElseThrow(() -> new RuntimeException("Vinyl not found"));

        user.getOrders().add(vinyl);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(user.getRole())
        );
    }

    @Override
    public void removeVinylFromCart(String username, Long vinylId) {
        User user = findByUsername(username);
        Vinyl vinyl = vinylRepository.findById(vinylId).orElseThrow(() -> new RuntimeException("Vinyl not found"));

        user.getOrders().remove(vinyl);
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
