package pl.dna.gesieniec.task.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * I just wanted to skip creating model, DAO, DTO etc.
 * Just simple model for everything. Not always the best, but here it will do (just be careful) :)
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String login;
    private String password;
    private String name;
    private Date creationDate;

    public User(final Long id, final String name, final Date creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    public User(final String login, final String password, final String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public static void validate(final User user) throws UserException {
        /**
         * Standard field by field validation after patch and post request before saving into DB.
         * Check for password strength, login and username symbols (if they are allowed) etc...
         *
         * Depending on the taste (or agreement within the dev team)
         * this validation can be done on ServiceLayer, but I prefer it here.
         */
    }
}
