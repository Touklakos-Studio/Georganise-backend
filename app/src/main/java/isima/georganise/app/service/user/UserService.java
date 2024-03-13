package isima.georganise.app.service.user;

import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    public List<User> getAllUsers(UUID authToken);

    public User getUserById(UUID authToken, Long id);

    public UUID createUser(UserCreationDTO user);

    public void deleteUser(UUID authToken, Long id);

    public User updateUser(UUID authToken, Long id, UserUpdateDTO user);

    public UUID login(UserLoginDTO user);

    public void logout(UUID authToken);
}
