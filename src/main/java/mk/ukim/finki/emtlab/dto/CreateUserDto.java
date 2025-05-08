package mk.ukim.finki.emtlab.dto;


import mk.ukim.finki.emtlab.model.enumerations.Role;

public record CreateUserDto(String username, String password, String repeatPassword, String name, String surname,
                            Role role) {

}
