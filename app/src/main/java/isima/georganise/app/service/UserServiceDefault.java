package isima.georganise.app.service;


import isima.georganise.app.entity.dao.User;
import isima.georganise.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceDefault implements UserService{

    @Autowired
    UsersRepository usersRepository;
    @Override
    public List<User> users() {
        return usersRepository.findAll();
    }

    @Override
    public User user(Long id) {
        Optional<User> op = usersRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }
        else {
            return null;
        }
    }

    @Override
    public User addUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public List<User> users(String name) {
        return usersRepository.findByName(name);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> u = usersRepository.findById(id);
        if (u.isPresent()) {
            // User found, delete it
            usersRepository.deleteById(id);
            return true;
        } else {
            // User not found
            return false;
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        // Check if the user exists
        Optional<User> optionalUser = usersRepository.findById(id);

        if (optionalUser.isPresent()) {
            // User found, update its properties
            User existingUser = optionalUser.get();
            if(user.getPassword() != null)
                existingUser.setPassword(user.getPassword());
            if(user.getEmail() != null)
                existingUser.setEmail(user.getEmail());
            if(user.getNickname() != null)
                existingUser.setNickname(user.getNickname());
            if(user.getImages() != null)
                existingUser.setImages(user.getImages());


            return usersRepository.save(existingUser);
        } else {
            // User not found
            return null;
        }
    }
}

