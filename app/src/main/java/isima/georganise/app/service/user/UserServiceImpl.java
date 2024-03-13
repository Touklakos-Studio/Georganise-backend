package isima.georganise.app.service.user;


import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.exception.WrongPasswordException;
import isima.georganise.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<User> getAllUsers(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return usersRepository.findAll();
    }

    @Override
    public User getUserById(UUID authToken, Long id) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return usersRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID createUser(UserCreationDTO user) {
        return usersRepository.saveAndFlush(new User(user)).getAuthToken();
    }

    @Override
    public void deleteUser(UUID authToken, Long id) {
        User user = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!user.getUserId().equals(id))
            throw new UnauthorizedException(user.getNickname(), "delete user with id: " + id);

        usersRepository.delete(usersRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public User updateUser(UUID authToken, Long id, UserUpdateDTO user) {
        User loggedUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!loggedUser.getUserId().equals(id))
            throw new UnauthorizedException(loggedUser.getNickname(), "update user with id: " + id);

        User userToUpdate = usersRepository.findById(id).orElseThrow(NotFoundException::new);

        if (user.getNickname() != null)
            userToUpdate.setNickname(user.getNickname());
        if (user.getPassword() != null)
            userToUpdate.setPassword(user.getPassword());
        if (user.getEmail() != null)
            userToUpdate.setEmail(user.getEmail());

        return usersRepository.saveAndFlush(userToUpdate);
    }

    @Override
    public UUID login(UserLoginDTO user) {
        System.out.println(user.getEmail() + " is trying to login with: " + user.getPassword());
        User userToLogin = usersRepository.findByEmail(user.getEmail()).orElseThrow(NotFoundException::new);

        if (!userToLogin.getPassword().equals(user.getPassword()))
            throw new WrongPasswordException();

        userToLogin.setAuthToken(UUID.randomUUID());

        System.out.println(user.getEmail() + " has successfully logged in with token: " + userToLogin.getAuthToken());

        return usersRepository.saveAndFlush(userToLogin).getAuthToken();
    }

    @Override
    public void logout(UUID authToken) {
        System.out.println("Logging out user with token: " + authToken);
        User userToLogout = usersRepository.findByAuthToken(authToken).orElseThrow(NotFoundException::new);

        userToLogout.setAuthToken(null);
        System.out.println("User " + userToLogout + " has been successfully logged out");

        usersRepository.saveAndFlush(userToLogout);
    }
}
