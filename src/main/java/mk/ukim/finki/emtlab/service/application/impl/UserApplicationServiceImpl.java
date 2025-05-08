package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.config.security.JwtHelper;
import mk.ukim.finki.emtlab.dto.CreateUserDto;
import mk.ukim.finki.emtlab.dto.DisplayUserDto;
import mk.ukim.finki.emtlab.dto.LoginResponseDto;
import mk.ukim.finki.emtlab.dto.LoginUserDto;
import mk.ukim.finki.emtlab.service.application.UserApplicationService;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userDomainService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userDomainService, JwtHelper jwtHelper) {
        this.userDomainService = userDomainService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<DisplayUserDto> save(CreateUserDto createUserDto) {
        return userDomainService.save(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.repeatPassword(),
                createUserDto.name(),
                createUserDto.surname(),
                createUserDto.role()
        ).map(DisplayUserDto::from);
    }

    @Override
    public Optional<LoginResponseDto> login(LoginUserDto userLoginDto) {
        String token = jwtHelper.generateToken(userDomainService.login(
                userLoginDto.username(),
                userLoginDto.password()
        ));
        return Optional.of(new LoginResponseDto(token));
    }
}
