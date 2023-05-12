package application.adapters.web.presenter;

import application.domain.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;


import static application.adapters.web.presenter.PermissionDTO.*;

@RequiredArgsConstructor
public enum RoleDTO {
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
                    PermissionDTO.CLIENT_DELETE

            )
    );







    @Getter
    private final Set<PermissionDTO> permissions;
}