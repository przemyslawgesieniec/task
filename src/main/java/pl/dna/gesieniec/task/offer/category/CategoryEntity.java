package pl.dna.gesieniec.task.offer.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "CATEGORIES")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public CategoryEntity(final String name) {
        this.name = name;
    }

    public Category toModel(){
        return new Category(name);
    }
}
