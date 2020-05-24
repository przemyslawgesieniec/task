package pl.dna.gesieniec.task.offer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dna.gesieniec.task.offer.category.CategoryException;
import pl.dna.gesieniec.task.user.UserException;

import java.util.List;

@RestController
@RequestMapping("/jobOffers")
public class JobOfferController {

    private JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping
    public ResponseEntity<List<JobOffer>> getAllJobOffers(
            @RequestParam(value = "userName", required = false) final String userName,
            @RequestParam(value = "categoryName", required = false) final String categoryName) {
        List<JobOffer> allUsers = jobOfferService.getAllValidJobOffers(categoryName, userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUsers);
    }

    /**
     * basic authentication or even better HMAC with SpringSecurity SSL communication here
     * to allow create jobOffer in favour of given user...
     */
    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody final JobOfferDto jobOfferDto)
            throws JobOfferException, CategoryException, UserException {
        JobOffer jobOffer = jobOfferService.createJobOffer(jobOfferDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobOffer);
    }
}
