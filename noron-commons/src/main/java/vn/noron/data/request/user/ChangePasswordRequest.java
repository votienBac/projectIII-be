package vn.noron.data.request.user;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.constant.Constant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//@FieldsValueMatch(
//    field = "newPassword",
//    fieldMatch = "repeatNewPassword",
//    message = "New password and confirm password not match!"
//)
@Data
@Accessors(chain = true)
public class ChangePasswordRequest {
    private Long id;

    @NotEmpty(message = "current password cannot be empty")
    private String currentPassword;

    @Pattern(regexp = Constant.Pattern.PASSWORD_PATTERN, message = "Password is not correct format!")
    @NotEmpty(message = "new password cannot be empty")
    @Size(min = 8, max = 64, message = "Passwords are between 8 and 64 characters long")
    private String newPassword;

//    @NotEmpty(message = "repeat new password cannot be empty")
//    private String repeatNewPassword;
}
