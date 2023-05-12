package application.adapters.security.entity;

import application.domain.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static application.adapters.security.entity.PermissionSec.*;

@RequiredArgsConstructor
public enum RoleSec {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_READ,
                    ADMIN_UPDATE)
    ),
    DELIVERY(
            Set.of(
                    DELIVERY_CREATE,
                    DELIVERY_READ,
                    DELIVERY_UPDATE,
                    DELIVERY_DELETE)
    ),
    PREPARATOR(
            Set.of(
                    PREPARATOR_CREATE,
                    PREPARATOR_READ,
                    PREPARATOR_UPDATE,
                    PREPARATOR_DELETE
            )
    ),
    CLIENT(
            Set.of(
                    CLIENT_CREATE,
                    CLIENT_READ,
                    CLIENT_UPDATE,
                    CLIENT_DELETE

            )
    );

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
    @Getter
    private final Set<PermissionSec> permissions;

}