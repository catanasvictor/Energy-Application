package ro.tuc.ds2020.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
import ro.tuc.ds2020.dtos.LoginInformationDTO;
import ro.tuc.ds2020.dtos.LoginResponseDTO;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.Collections;
import java.util.Locale;

@Service
public class LoginResponseService {

    private final UserRepository userRepository;

    public LoginResponseService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDTO login(LoginInformationDTO loginDTO) throws ApiExceptionResponse {

        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw ApiExceptionResponse.builder().errors(Collections.singletonList("Wrong username or password"))
                    .message("User not found").status(HttpStatus.NOT_FOUND).build();
        }
        LoginResponseDTO responseDTO;
        String role = user.getRole().name().toUpperCase(Locale.ROOT);
        responseDTO = LoginResponseDTO.builder().id(user.getId()).role(role).build();

        if (loginDTO.getPassword().equals(user.getPassword())) {
            userRepository.save(user);
            return responseDTO;
        }
        throw ApiExceptionResponse.builder().errors(Collections.singletonList("Wrong username or password"))
                .message("User not found").status(HttpStatus.NOT_FOUND).build();
    }
}
