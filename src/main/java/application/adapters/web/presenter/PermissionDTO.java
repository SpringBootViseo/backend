package application.adapters.web.presenter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PermissionDTO {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    DELIVERY_READ("delivery:read"),
    DELIVERY_UPDATE("delivery:update"),
    DELIVERY_CREATE("delivery:create"),
    DELIVERY_DELETE("delivery:delete"),
    PREPARATOR_READ("preparator:read"),
    PREPARATOR_UPDATE("preparator:update"),
    PREPARATOR_CREATE("preparator:create"),
    PREPARATOR_DELETE("preparator:delete"),
    CLIENT_READ("client:read"),
    CLIENT_UPDATE("client:update"),
    CLIENT_CREATE("client:create"),
    CLIENT_DELETE("client:delete");
    @Getter
    private final String permission;
}