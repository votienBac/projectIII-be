package vn.noron.core.sercurity;

import java.util.List;

public interface AlertSecurityProvider {
    List<String> getRoles();

    boolean isAdmin();

    Long loggedUserId();

    boolean showLogInfo();
}
