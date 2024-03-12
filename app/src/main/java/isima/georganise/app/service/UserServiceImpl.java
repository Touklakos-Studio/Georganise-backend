package isima.georganise.app.service;


import isima.georganise.app.entity.dao.User;
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
    public List<User> users() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> op = usersRepository.findById(id);
        return op.orElseThrow(NotFoundException::new);
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
    public void deleteUser(Long id) {
        Optional<User> u = usersRepository.findById(id);
        if(u.isEmpty()) throw new NotFoundException();
        usersRepository.deleteById(id);
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
            throw new NotFoundException();
        }
    }
}

