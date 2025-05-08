package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> save(String username, String password, String repeatPassword, String name, String surname, Role userRole);
    User login(String username, String password);
    Optional<User> findByUsername(String username);
}