package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
import ro.tuc.ds2020.dtos.LoginInformationDTO;
import ro.tuc.ds2020.services.LoginResponseService;
import ro.tuc.ds2020.services.UserService;

import java.util.UUID;

@RestController
@CrossOrigin("*")
public class LoginController {

    private final LoginResponseService loginResponseService;
    private final UserService userService;

    public LoginController(LoginResponseService loginResponseService, UserService userService) {
        this.loginResponseService = loginResponseService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginInformationDTO dto) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseService.login(dto));
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.logout(id));
    }
}
