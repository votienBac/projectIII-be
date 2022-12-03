package vn.noron.data.constant;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }
}
