package vn.noron.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AuthenticationRequest {
    @NotBlank(message = "username is not null")
    private String username;
    @NotBlank(message = "password is not null")
    private String password;
    @JsonProperty("g-recaptcha-response")
    private String captcha;
}