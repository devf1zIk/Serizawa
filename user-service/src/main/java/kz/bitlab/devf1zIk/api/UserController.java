package kz.bitlab.devf1zIk.api;

import kz.bitlab.devf1zIk.dto.UserChangePasswordDto;
import kz.bitlab.devf1zIk.dto.UserCreateDto;
import kz.bitlab.devf1zIk.dto.UserDTO;
import kz.bitlab.devf1zIk.dto.UserSignInDto;
import kz.bitlab.devf1zIk.service.KeycloakService;
import kz.bitlab.devf1zIk.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping(value = "/create")
    public UserRepresentation createUser(@RequestBody UserCreateDto userCreateDto) {
        return keycloakService.createUser(userCreateDto);
    }

    @GetMapping(value = "/current-user-name")
    private String getCurrentUserName() {
        return UserUtils.getCurrentUserName();
    }

    @GetMapping(value = "/current-user")
    private UserDTO getCurrentUser() {
        return UserUtils.getCurrentUser();
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDto userSignInDto) {
        return keycloakService.SignIn(userSignInDto);
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDto) {
        String currentUserName = UserUtils.getCurrentUserName();
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify  User");
        }
        try {
            keycloakService.changePassword(currentUserName, userChangePasswordDto.getNewPassword());
            return ResponseEntity.ok("Password Changed Successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on changing password");
        }
    }
}
