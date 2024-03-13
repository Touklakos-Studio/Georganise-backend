package isima.georganise.app.service.user;

import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User createUser(UserCreationDTO user);

    public void deleteUser(Long id);

    public User updateUser(Long id, UserUpdateDTO user);

}
