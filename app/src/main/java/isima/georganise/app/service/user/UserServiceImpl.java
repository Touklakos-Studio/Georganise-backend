package isima.georganise.app.service.user;


import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.*;
import isima.georganise.app.exception.*;
import isima.georganise.app.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Service class for managing users.
 * Implements the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull ImagesRepository imagesRepository;

    private final @NotNull PlacesRepository placesRepository;

    private final @NotNull TokensRepository tokensRepository;

    private final @NotNull TagsRepository tagsRepository;

    private final @NotNull PlacesTagsRepository placesTagsRepository;

    private final @NotNull Pattern pattern;

    /**
     * Constructor for the UserServiceImpl class.
     *
     * @param usersRepository The repository for users.
     * @param imagesRepository The repository for images.
     * @param placesRepository The repository for places.
     * @param tokensRepository The repository for tokens.
     * @param tagsRepository The repository for tags.
     * @param placesTagsRepository The repository for placesTags.
     */
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
        this.pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    /**
     * Retrieves all users associated with the given authentication token.
     *
     * @param authToken The authentication token.
     * @return A list of users.
     */
    @Override
    public @NotNull List<User> getAllUsers(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return usersRepository.findAll();
    }

    /**
     * Retrieves a user by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @return The user's nickname.
     */
    @Override
    public @NotNull GetUserNicknameDTO getUserById(UUID authToken, @NotNull Long id) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        User user = usersRepository.findById(id).orElseThrow(NotFoundException::new);

        return new GetUserNicknameDTO(user.getNickname());
    }

    /**
     * Creates a new user with the given user creation DTO.
     *
     * @param user The user creation DTO.
     * @return The authentication token of the created user.
     */
    @Override
    public UUID createUser(@NotNull UserCreationDTO user) {
        if (Objects.isNull(user.getNickname())) throw new IllegalArgumentException("Nickname must not be null");
        if (Objects.isNull(user.getPassword())) throw new IllegalArgumentException("Password must not be null");
        if (Objects.isNull(user.getEmail())) throw new IllegalArgumentException("Email must not be null");
        if (!pattern.matcher(user.getEmail()).matches()) throw new IllegalArgumentException("Email is not valid");

        if (usersRepository.findByEmail(user.getEmail()).isPresent()) throw new ConflictException("User with email: " + user.getEmail() + " already exists");
        if (usersRepository.findByNickname(user.getNickname()).isPresent()) throw new ConflictException("User with nickname: " + user.getNickname() + " already exists");

        User newUser = usersRepository.saveAndFlush(new User(user));

        TagCreationDTO tag = new TagCreationDTO();
        tag.setTitle("{" + newUser.getNickname() + "} real time");
        tag.setDescription("Real time positions of user: " + newUser.getNickname());

        tagsRepository.saveAndFlush(new Tag(tag, newUser.getUserId()));
        return newUser.getAuthToken();
    }

    /**
     * Deletes a user by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     */
    @Override
    public void deleteUser(UUID authToken, @NotNull Long id) {
        User user = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!user.getUserId().equals(id)) throw new UnauthorizedException(user.getNickname(), "delete user with id: " + id);

        List<Image> userImages = imagesRepository.findByUserId(user.getUserId());
        userImages.forEach(image -> placesRepository.findByImageId(image.getImageId()).forEach(place -> {
            place.setImageId(null);
            placesRepository.saveAndFlush(place);
        }));
        imagesRepository.deleteAll(userImages);

        tokensRepository.findByCreatorId(user.getUserId()).ifPresent(tokensRepository::deleteAll);
        tokensRepository.findByUserId(user.getUserId()).ifPresent(tokensRepository::deleteAll);

        List<Tag> userTags = tagsRepository.findByUserId(user.getUserId());
        userTags.forEach(tag -> placesTagsRepository.deleteAll(placesTagsRepository.findByTag_TagId(tag.getTagId())));
        tagsRepository.deleteAll(userTags);

        List<Place> userPlaces = placesRepository.findByUserId(user.getUserId());
        userPlaces.forEach(place -> placesTagsRepository.deleteAll(placesTagsRepository.findByPlace_PlaceId(place.getPlaceId())));
        placesRepository.deleteAll(userPlaces);

        usersRepository.delete(usersRepository.findById(id).orElseThrow(UnauthorizedException::new));
    }

    /**
     * Updates a user by its ID, the given authentication token, and user update DTO.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @param user The user update DTO.
     * @return The updated user.
     */
    @Override
    public @NotNull User updateUser(UUID authToken, @NotNull Long id, @NotNull UserUpdateDTO user) {
        User loggedUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!loggedUser.getUserId().equals(id)) throw new UnauthorizedException(loggedUser.getNickname(), "update user with id: " + id);

        User userToUpdate = usersRepository.findById(id).orElseThrow(NotFoundException::new);

        if (user.getNickname() != null) userToUpdate.setNickname(user.getNickname());
        if (user.getPassword() != null) userToUpdate.setPassword(user.getPassword());
        if (user.getEmail() != null) {
            if (!pattern.matcher(user.getEmail()).matches()) throw new IllegalArgumentException("Email is not valid");
            userToUpdate.setEmail(user.getEmail());
        }

        return usersRepository.saveAndFlush(userToUpdate);
    }

    /**
     * Logs in a user with the given user login DTO.
     *
     * @param user The user login DTO.
     * @return The authentication token of the logged-in user.
     */
    @Override
    public UUID login(@NotNull UserLoginDTO user) {
        User userToLogin = usersRepository.findByEmail(user.getEmail()).orElseThrow(NotFoundException::new);

        if (!userToLogin.getPassword().equals(user.getPassword())) throw new WrongPasswordException();

        userToLogin.setAuthToken(UUID.randomUUID());

        return usersRepository.saveAndFlush(userToLogin).getAuthToken();
    }

    /**
     * Logs out a user with the given authentication token.
     *
     * @param authToken The authentication token.
     */
    @Override
    public void logout(UUID authToken) {
        User userToLogout = usersRepository.findByAuthToken(authToken).orElseThrow(NotFoundException::new);

        userToLogout.setAuthToken(null);

        usersRepository.saveAndFlush(userToLogout);
    }

    /**
     * Retrieves the user associated with the given authentication token.
     *
     * @param authToken The authentication token.
     * @return The user.
     */
    @Override
    public User getMe(UUID authToken) {
        return usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
    }
}

