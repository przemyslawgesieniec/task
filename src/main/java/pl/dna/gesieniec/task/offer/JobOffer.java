package pl.dna.gesieniec.task.offer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dna.gesieniec.task.offer.category.Category;
import pl.dna.gesieniec.task.user.User;

import java.sql.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobOffer {
    private Category category;
    private User user;
    private Date endDate;
    private Date startDate;
}
