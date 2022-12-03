package vn.noron.data.request.user;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

import static vn.noron.data.constant.Constant.Pattern.*;

@Data
@Accessors(chain = true)

public class CreateUserRequest {
    @NotBlank(message = "Username is not empty")
    @Size(min = 8, max = 64, message = "username has length from 8 to 64 characters")
    @Pattern(regexp = USERNAME_PATTERN, message = "username contains only lowercase letters and numbers")
    private String username;

    @NotBlank(message = "Password is not empty")
    @Size(min = 8, max = 64, message = "password has length from 8 to 64 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "wrong password format")
    private String password;

////    @NotBlank(message = "FullName is not empty")
//    @Size(min = 1, max = 256, message = "fullName with at most 256 characters")
//    private String fullName;

//    @NotBlank(message = "Email is not empty")
    @Size(min = 1, max = 256, message = "email with at most 256 characters")
    @Email
    @Pattern(regexp = EMAIL_PATTERN,
            message = "Email invalidate")
    private String email;

//    @Pattern(regexp = "^\\d*$", message = "phoneNumber invalidate")
    @Size(min = 10, max = 20, message = "phoneNumber has length from 10 to 20 characters")
    private String phoneNumber;

    @Size(min = 1, max = 256, message = "avatarUrl has length from 8 to 64 characters")
    private String avatarUrl;

    @NotEmpty(message = "Role is not empty")
    private Set<String> roles;

}
