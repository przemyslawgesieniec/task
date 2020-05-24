package pl.dna.gesieniec.task.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Setter
// Setters for entities are 'meh' but here, we do not have to care that much about integrity (no complex/associated entities inside).
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "USERS")
public class UserEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;
    private String password;
    private String name;
    private Date creationDate;

    public UserEntity(final String login, final String password, final String name, final Date creationDate) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.creationDate = creationDate;
    }

    public User toSecureModel() {
        /**
         * exposing the DB ID is not my favourite practice, but I'll do it here for simplicity...
         * usually I would rather keep and expose additional ID like UUID.
         */
        return new User(id, name, creationDate);
    }

    User toModel() {
        return new User(id, login, password, name, creationDate);
    }
}
