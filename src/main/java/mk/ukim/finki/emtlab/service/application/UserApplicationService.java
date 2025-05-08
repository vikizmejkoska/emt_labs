package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.CreateUserDto;
import mk.ukim.finki.emtlab.dto.DisplayUserDto;
import mk.ukim.finki.emtlab.dto.LoginResponseDto;
import mk.ukim.finki.emtlab.dto.LoginUserDto;

import java.util.Optional;

public interface UserApplicationService {

    Optional<DisplayUserDto> save(CreateUserDto createUserDto);
    Optional<LoginResponseDto> login(LoginUserDto userLoginDto);
}

