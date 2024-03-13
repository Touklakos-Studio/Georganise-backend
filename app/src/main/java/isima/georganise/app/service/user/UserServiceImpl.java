package isima.georganise.app.service.user;


import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User createUser(UserCreationDTO user) {
        return usersRepository.saveAndFlush(new User(user));
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.delete(usersRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public User updateUser(Long id, UserUpdateDTO user) {
        User userToUpdate = usersRepository.findById(id).orElseThrow(NotFoundException::new);

        if (user.getNickname() != null)
            userToUpdate.setNickname(user.getNickname());
        if (user.getPassword() != null)
            userToUpdate.setPassword(user.getPassword());
        if (user.getEmail() != null)
            userToUpdate.setEmail(user.getEmail());

        return usersRepository.saveAndFlush(userToUpdate);
    }
}

