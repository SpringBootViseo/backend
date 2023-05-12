package application.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static application.domain.Permission.*;

@RequiredArgsConstructor
public enum Role {
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
    @Getter
    private final Set<Permission> permissions;
}
