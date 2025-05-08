package mk.ukim.finki.emtlab.service.domain.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.domain.Wishlist;
import mk.ukim.finki.emtlab.model.enumerations.Role;
import mk.ukim.finki.emtlab.model.exceptions.*;
import mk.ukim.finki.emtlab.repository.UserRepository;
import mk.ukim.finki.emtlab.repository.WishlistRepository;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WishlistRepository wishlistRepository;



    @Override
    public Optional<User> save(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        Wishlist wishlist = new Wishlist();
        wishlistRepository.save(wishlist);

        User user = new User(username,
                passwordEncoder.encode(password),
                name,
                surname,
                userRole);

        user.setWishlist(wishlist);

        return Optional.of(userRepository.save(user));
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }

        throw new InvalidUserCredentialsException();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                username));
    }

}
