package kz.bitlab.devf1zIk.service;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import kz.bitlab.devf1zIk.dto.UserCreateDto;
import kz.bitlab.devf1zIk.dto.UserSignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client}")
    private String client;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(UserCreateDto user) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);

        newUser.setCredentials(List.of(credential));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            log.error("Error creating user");
            throw new RuntimeException("Error creating user");
        }

        List<UserRepresentation> seachUsers = keycloak.realm(realm).users().search(user.getUsername());
        return seachUsers.get(0);
    }

    public String SignIn(UserSignInDto userSignInDto) {
        String token = url + "/realms/"+realm+"/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type","password");
        formData.add("client_id",client);
        formData.add("client_secret",clientSecret);
        formData.add("username",userSignInDto.getUsername());
        formData.add("password",userSignInDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(token,new HttpEntity<>(formData,headers),Map.class);
        Map<String,Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error signing in");
            throw new RuntimeException("failed signing in");
        }
        return (String) responseBody.get("access_token");
    }

    public void changePassword(String username,String newPassword) {
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(username);

        if (users.isEmpty()) {
            log.error("User not found to change");
            throw new RuntimeException("User not found with username" + username);
        }

        UserRepresentation userRepresentation = users.get(0);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);
        keycloak
                .realm(realm)
                .users()
                .get(userRepresentation.getId())
                .resetPassword(credential);
        log.info("Password changed");
    }
}
