package pl.dna.gesieniec.task.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.dna.gesieniec.task.offer.JobOffer;
import pl.dna.gesieniec.task.offer.JobOfferEntity;
import pl.dna.gesieniec.task.offer.JobOfferException;
import pl.dna.gesieniec.task.offer.category.Category;
import pl.dna.gesieniec.task.offer.category.CategoryException;
import pl.dna.gesieniec.task.user.UserException;

@ControllerAdvice
public class ValidationControllerAdvice {
    @ExceptionHandler({UserException.class})
    public ResponseEntity<String> handleUserException(final UserException e) {
        //TODO add logger for the exception

        /**
         * usually I Would use full error description like this
         * *
         * {
         *    "timestamp":1461621047967,
         *    "status":404,
         *    "error":"Not Found",
         *    "exception":"UserException",
         *    "message":"User not found",
         *    "path":"/users"
         * }
         *
         *  Usually by wrapping with generic API exception and care about hiding
         *  confidential internal errors
         */
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({JobOfferException.class})
    public ResponseEntity<String> handleJobOfferException(final JobOfferException e) {
        //TODO add logger for the exception
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({CategoryException.class})
    public ResponseEntity<String> handleCategoryException(final CategoryException e) {
        //TODO add logger for the exception
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
