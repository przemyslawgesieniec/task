package pl.dna.gesieniec.task.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * skipping the service interface since its trivial case and
 * I do not see the need for creating contract just to
 * provide one and only one implementation (YAGNI)
 */
@Service
public class UserService {

    public static final String USER_NOT_EXISTS = "User with id %s do not exist";
    public static final String USER_NOT_EXISTS_LOGIN = "User with login %s do not exist";
    public static final String CANNOT_REMOVE_NON_EXISTING_USER = "Cannot remove non existing user";
    public static final String CANNOT_PATCH_THE_USER_WITH_PROVIDED_DATA = "Cannot patch the user with provided data";
    public static final String CREATION_DATE_MODIFICATION_DISALLOWED = "Modification of the creation date is not allowed";
    public static final String MODIFICATION_OF_USER_ID_IS_NOT_ALLOWED = "Modification of user id is not allowed";
    public static final String USER_WITH_THIS_LOGIN_ALREADY_EXISTS = "User with this login already exists";

    private UserRepository userRepository;
    private ObjectMapper objectMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserEntity::toSecureModel)
                .collect(Collectors.toList());
    }

    User getUserById(final Long id) throws UserException {
        return getUserEntityById(id, USER_NOT_EXISTS).toSecureModel();
    }

    public UserEntity getUserByLogin(final String login) throws UserException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserException(String.format(USER_NOT_EXISTS_LOGIN, login)));
    }

    User createUser(User user) throws UserException {
        User.validate(user);
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserException(USER_WITH_THIS_LOGIN_ALREADY_EXISTS);
        }
        UserEntity userEntity = new UserEntity(user.getLogin(),
                passwordEncoder.encode(user.getPassword()),
                user.getName(),
                user.getCreationDate());
        userRepository.save(userEntity);
        return userEntity.toSecureModel();
    }

    /**
     * here it would be nice to verify password and login of the user,
     * which should be provided with the request (HMAC, or at least Basic authentication)
     */
    void removeUserById(final Long id) throws UserException {
        UserEntity userEntity = getUserEntityById(id, CANNOT_REMOVE_NON_EXISTING_USER);
        userRepository.delete(userEntity);
    }

    private UserEntity getUserEntityById(final Long id, final String message) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(String.format(message, id)));
    }

    /**
     * I do not like JsonPatch that much, because restricting the operations is pretty hard,
     * however it is fast for prototyping like this. In real case scenario I would prefer something tailored
     * just for necessary/required operations.
     *
     * here it would be nice to verify password and login of the user,
     * which should be provided with the request (HMAC, or at least Basic authentication)
     */
    User patchUserById(final Long id, final JsonPatch patch) throws UserException {
        UserEntity userEntity = getUserEntityById(id, USER_NOT_EXISTS);
        try {
            /**
             * I know that it's a bad practice to create invalid objects, but I have done it in order not to do this task all day long.
             * Hey, at least on the model object not directly on the entity :)
             */
            User patchedUser = applyPatchToUser(patch, userEntity.toModel());
            updateUserEntity(userEntity, patchedUser);
            userRepository.save(userEntity);
            return userEntity.toSecureModel();
        } catch (JsonPatchException | JsonProcessingException e) {
            //Log.info(e);
            throw new UserException(CANNOT_PATCH_THE_USER_WITH_PROVIDED_DATA);
        }
    }

    private User applyPatchToUser(final JsonPatch patch, final User user)
            throws JsonPatchException, JsonProcessingException {
        JsonNode jsonNode = objectMapper.convertValue(user, JsonNode.class);
        final JsonNode patched = patch.apply(jsonNode);
        return objectMapper.treeToValue(patched, User.class);
    }

    private void updateUserEntity(final UserEntity userToUpdate, final User patchedUser) throws UserException {
        User.validate(patchedUser);
        User userToUpdateModel = userToUpdate.toModel();
        if (!userToUpdateModel.getCreationDate().equals(patchedUser.getCreationDate())) {
            throw new UserException(CREATION_DATE_MODIFICATION_DISALLOWED);
        }
        if (!userToUpdateModel.getId().equals(patchedUser.getId())) {
            throw new UserException(MODIFICATION_OF_USER_ID_IS_NOT_ALLOWED);
        }
        userToUpdate.setLogin(patchedUser.getLogin());
        userToUpdate.setPassword(patchedUser.getPassword());
        userToUpdate.setName(patchedUser.getName());
    }
}
