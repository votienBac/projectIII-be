package com.noron.commons.data.response.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserInfoResponse {
    private String id;
    private String type;
    private String fullName;
    private String title;
    private String avatarUrl;
    private String avatar;
    private List<String> roles;
    private String username;
    private Boolean online;
    private String lastMessageStatus;
}
