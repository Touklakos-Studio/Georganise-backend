package isima.georganise.app.service;

import isima.georganise.app.entity.dao.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> users();
    public User user(Long id);
    public User addUser(User user);
    public List<User> users(String name);
    public boolean deleteUser(Long id);
    public User updateUser(Long id,User user);


}
