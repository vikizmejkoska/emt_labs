package mk.ukim.finki.emtlab.dto;


import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.Role;

public record DisplayUserDto(Long id, String username, String password, String name, String surname, Role role,
                             Long wishlistId) {

    public static DisplayUserDto from(User user) {
        return new DisplayUserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getWishlist().getId()
        );
    }

    public User to() {
        return new User(username, password, name, surname, role);
    }
}
