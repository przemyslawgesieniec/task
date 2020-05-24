package pl.dna.gesieniec.task.offer;

import org.springframework.stereotype.Service;
import pl.dna.gesieniec.task.offer.category.CategoryEntity;
import pl.dna.gesieniec.task.offer.category.CategoryException;
import pl.dna.gesieniec.task.offer.category.CategoryService;
import pl.dna.gesieniec.task.user.UserEntity;
import pl.dna.gesieniec.task.user.UserException;
import pl.dna.gesieniec.task.user.UserService;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class JobOfferService {

    public static final String END_DATE_CANNOT_BE_IN_THE_PAST = "End date cannot be in the past";
    private JobOfferRepository jobOfferRepository;
    private CategoryService categoryService;
    private UserService userService;

    public JobOfferService(final JobOfferRepository jobOfferRepository,
                           final CategoryService categoryService,
                           final UserService userService) {
        this.jobOfferRepository = jobOfferRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /**
     * Filtering should rather have been done on SQL query/JPA level....
     */
    public List<JobOffer> getAllValidJobOffers(final String categoryName, final String userName) {
        return jobOfferRepository.findAll()
                .stream()
                .map(JobOfferEntity::toSecureModel)
                .filter(jobOffer -> jobOffer.getEndDate().getTime() > System.currentTimeMillis())
                .filter(jobOffer -> byUserNameIfPresent.test(jobOffer, userName))
                .filter(jobOffer -> byCategoryNameIfPresent.test(jobOffer, categoryName))
                .collect(Collectors.toList());
    }

    /**
     * All the exceptions should be wrapped into JobOfferException...
     */
    public JobOffer createJobOffer(final JobOfferDto jobOfferDto) throws JobOfferException, UserException, CategoryException {

        if (jobOfferDto.getEndDate().getTime() < System.currentTimeMillis()) {
            throw new JobOfferException(END_DATE_CANNOT_BE_IN_THE_PAST);
        }

        CategoryEntity categoryEntity = categoryService.getCategoryEntityByCategory(jobOfferDto.getCategoryName());
        UserEntity userByLogin = userService.getUserByLogin(jobOfferDto.getUserLogin());

        JobOfferEntity jobOfferEntity = new JobOfferEntity(categoryEntity, userByLogin, jobOfferDto.getStartDate(), jobOfferDto.getEndDate());
        jobOfferRepository.save(jobOfferEntity);
        return jobOfferEntity.toSecureModel();
    }


    private static BiPredicate<JobOffer, String> byCategoryNameIfPresent = (jobOffer, categoryName) -> {
        if (categoryName != null) {
            return jobOffer.getCategory().getValue().equals(categoryName);
        }
        return true;
    };


    private static BiPredicate<JobOffer, String> byUserNameIfPresent = (jobOffer, userName) -> {
        if (userName != null) {
            return jobOffer.getUser().getName().equals(userName);
        }
        return true;
    };

}
