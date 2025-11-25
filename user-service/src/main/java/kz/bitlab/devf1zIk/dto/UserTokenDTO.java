package kz.bitlab.devf1zIk.dto;

import lombok.Data;

@Data
public class UserTokenDTO {

    private String token;
    private String refreshToken;
}
