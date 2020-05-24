package pl.dna.gesieniec.task.user;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") final Long id) throws UserException {
        User user = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final User user) throws UserException {
        User newUser = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newUser);
    }

    /**
     * basic authentication/authorization or even better HMAC with SpringSecurity SSL communication here
     * to allow delete user account
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable(value = "id") final Long id) throws UserException {
        //TODO add authorization with password and login
        userService.removeUserById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * basic authentication/authorization or even better HMAC with SpringSecurity SSL communication here
     * to allow delete user account
     */
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable(value = "id") final Long id,
                                          @RequestBody JsonPatch patch) throws UserException {
        //TODO add authorization with password and login
        User user = userService.patchUserById(id, patch);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}