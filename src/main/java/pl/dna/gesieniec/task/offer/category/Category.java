package pl.dna.gesieniec.task.offer.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * It could also be an enum,
 * however in case of introducing the
 * new category redeployment would be necessary.
 * Therefore it is better to keep it as entity in the database
 * without exposed API to modify it, and if necessary modify the SQL
 * script/table or even create "ADMIN panel" for this purpose.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    private String value;
}
