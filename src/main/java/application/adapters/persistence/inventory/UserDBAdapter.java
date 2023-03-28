package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.UserEntity;
import application.domain.User;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDBAdapter implements UserPort {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone());
        UserEntity userEntitySaved = userRepository.save(userEntity);
        return new User(userEntitySaved.getId(), userEntitySaved.getFullname(), userEntitySaved.getEmail(), userEntitySaved.getNumberPhone());
    }
}
