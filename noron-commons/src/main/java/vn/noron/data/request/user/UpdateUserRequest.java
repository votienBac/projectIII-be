package vn.noron.data.request.user;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.constant.Constant;

import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class UpdateUserRequest {
   private Long userId;
   @Pattern(regexp = Constant.Pattern.USERNAME_PATTERN, message = "Username is not correct format!")
   private String username;

   private String fullName;

   @Pattern(regexp = Constant.Pattern.EMAIL_PATTERN, message = "Email is not correct format!")
   private String email;

   private String phoneNumber;

   private String avatarUrl;
}