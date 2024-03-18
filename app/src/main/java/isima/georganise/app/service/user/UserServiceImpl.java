package isima.georganise.app.service.user;


import isima.georganise.app.entity.dao.*;
import isima.georganise.app.entity.dto.GetUserNicknameDTO;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import isima.georganise.app.exception.*;
import isima.georganise.app.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull ImagesRepository imagesRepository;

    private final @NotNull PlacesRepository placesRepository;

    private final @NotNull TokensRepository tokensRepository;

    private final @NotNull TagsRepository tagsRepository;

    private final @NotNull PlacesTagsRepository placesTagsRepository;

    @Autowired
    public UserServiceImpl(@NotNull UsersRepository usersRepository, @NotNull ImagesRepository imagesRepository, @NotNull PlacesRepository placesRepository, @NotNull TokensRepository tokensRepository, @NotNull TagsRepository tagsRepository, @NotNull PlacesTagsRepository placesTagsRepository) {
        Assert.notNull(usersRepository, "Users repository must not be null");
        Assert.notNull(imagesRepository, "Images repository must not be null");
        Assert.notNull(placesRepository, "Places repository must not be null");
        Assert.notNull(tokensRepository, "Tokens repository must not be null");
        Assert.notNull(tagsRepository, "Tags repository must not be null");
        Assert.notNull(placesTagsRepository, "PlacesTags repository must not be null");
        this.usersRepository = usersRepository;
        this.imagesRepository = imagesRepository;
        this.placesRepository = placesRepository;
        this.tokensRepository = tokensRepository;
        this.tagsRepository = tagsRepository;
        this.placesTagsRepository = placesTagsRepository;
    }

    @Override
    public @NotNull List<User> getAllUsers(UUID authToken) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());

        List<User> users = usersRepository.findAll();
        System.out.println("\tfetched " + users.size() + " users");
        return users;
    }

    @Override
    public GetUserNicknameDTO getUserById(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());

        GetUserNicknameDTO dto = new GetUserNicknameDTO();
        User user = usersRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched user: " + user);
        dto.setNickname(user.getNickname());

        return dto;
    }

    @Override
    public UUID createUser(@NotNull UserCreationDTO user) {
        if (Objects.isNull(user.getNickname())) throw new IllegalArgumentException("Nickname must not be null");
        if (Objects.isNull(user.getPassword())) throw new IllegalArgumentException("Password must not be null");
        if (Objects.isNull(user.getEmail())) throw new IllegalArgumentException("Email must not be null");

        Optional<User> existingUser = usersRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("\tUser: " + user + " already exists");
            throw new ConflictException("User with email: " + user.getEmail() + " already exists");
        }
        existingUser = usersRepository.findByNickname(user.getNickname());
        if (existingUser.isPresent()) {
            System.out.println("\tUser: " + user + " already exists");
            throw new ConflictException("User with nickname: " + user.getNickname() + " already exists");
        }

        User newUser = usersRepository.saveAndFlush(new User(user));
        System.out.println("\tUser: " + newUser + " has been successfully created");
        return newUser.getAuthToken();
    }

    @Override
    public void deleteUser(UUID authToken, @NotNull Long id) {
        User user = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + user.getUserId());

        if (!user.getUserId().equals(id)) throw new UnauthorizedException(user.getNickname(), "delete user with id: " + id);

        List<Image> userImages = imagesRepository.findByUserId(user.getUserId());
        System.out.println("\tdeleting " + userImages.size() + " images");
        for (Image image : userImages) {
            List<Place> imagePlaces = placesRepository.findByImageId(image.getImageId());
            System.out.println("\t\tupdating " + imagePlaces.size() + " places for image: " + image.getImageId());
            for (Place place : imagePlaces) {
                place.setImageId(null);
                placesRepository.saveAndFlush(place);
                System.out.println("\t\tplace: " + place.getPlaceId() + " has been successfully updated");
            }
        }
        imagesRepository.deleteAll(userImages);
        System.out.println("\t" + userImages.size() + " images have been successfully deleted");

        tokensRepository.findByCreatorId(user.getUserId()).ifPresent(p -> {
                System.out.println("\tdeleting " + p.spliterator().getExactSizeIfKnown() + " created tokens");
                tokensRepository.deleteAll(p);
        });
        tokensRepository.findByUserId(user.getUserId()).ifPresent(p -> {
                System.out.println("\tdeleting " + p.spliterator().getExactSizeIfKnown() + " used tokens");
                tokensRepository.deleteAll(p);
        });

        List<Tag> userTags = tagsRepository.findByUserId(user.getUserId());
        System.out.println("\tdeleting " + userTags.size() + " tags");
        userTags.forEach(tag -> {
            Iterable<PlaceTag> placeTags = placesTagsRepository.findByTag_TagId(tag.getTagId());
            System.out.println("\t\tdeleting " + placeTags.spliterator().getExactSizeIfKnown() + " places tags");
            placesTagsRepository.deleteAll(placeTags);
        });
        tagsRepository.deleteAll(userTags);
        System.out.println("\t" + userTags.size() + " tags have been successfully deleted");

        List<Place> userPlaces = placesRepository.findByUserId(user.getUserId());
        System.out.println("\tdeleting " + userPlaces.size() + " places");
        userPlaces.forEach(place -> {
            Iterable<PlaceTag> placeTags = placesTagsRepository.findByPlace_PlaceId(place.getPlaceId());
            System.out.println("\t\tdeleting " + placeTags.spliterator().getExactSizeIfKnown() + " places tags");
            placesTagsRepository.deleteAll(placeTags);
        });
        placesRepository.deleteAll(userPlaces);
        System.out.println("\t" + userPlaces.size() + " places have been successfully deleted");

        usersRepository.delete(usersRepository.findById(id).orElseThrow(UnauthorizedException::new));
        System.out.println("\tUser: " + id + " has been successfully deleted");
    }

    @Override
    public @NotNull User updateUser(UUID authToken, @NotNull Long id, @NotNull UserUpdateDTO user) {
        User loggedUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + loggedUser.getUserId());

        if (!loggedUser.getUserId().equals(id))
            throw new UnauthorizedException(loggedUser.getNickname(), "update user with id: " + id);

        User userToUpdate = usersRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched user: " + userToUpdate);

        if (user.getNickname() != null) {
            System.out.println("\tupdating nickname to: " + user.getNickname() + " from: " + userToUpdate.getNickname());
            userToUpdate.setNickname(user.getNickname());
        }
        if (user.getPassword() != null) {
            System.out.println("\tupdating password to: " + user.getPassword() + " from: " + userToUpdate.getPassword());
            userToUpdate.setPassword(user.getPassword());
        }
        if (user.getEmail() != null) {
            System.out.println("\tupdating email to: " + user.getEmail() + " from: " + userToUpdate.getEmail());
            userToUpdate.setEmail(user.getEmail());
        }

        User updatedUser = usersRepository.saveAndFlush(userToUpdate);
        System.out.println("\tUser: " + updatedUser + " has been successfully updated");

        return updatedUser;
    }

    @Override
    public UUID login(@NotNull UserLoginDTO user) {
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

    @Override
    public User getMe(UUID authToken) {
        User user = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\tfetched user: " + user);
        return user;
    }
}

