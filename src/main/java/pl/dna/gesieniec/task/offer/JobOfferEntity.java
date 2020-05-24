package pl.dna.gesieniec.task.offer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dna.gesieniec.task.offer.category.Category;
import pl.dna.gesieniec.task.offer.category.CategoryEntity;
import pl.dna.gesieniec.task.user.User;
import pl.dna.gesieniec.task.user.UserEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "JOB_OFFERS")
public class JobOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id")
    private CategoryEntity categoryEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private UserEntity userEntity;

    private Date startDate;
    private Date endDate;

    public JobOfferEntity(CategoryEntity categoryEntity, UserEntity userEntity, Date startDate, Date endDate) {
        this.categoryEntity = categoryEntity;
        this.userEntity = userEntity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JobOffer toSecureModel() {
        User user = userEntity.toSecureModel();
        Category category = categoryEntity.toModel();
        return new JobOffer(category, user, endDate, startDate);
    }
}
