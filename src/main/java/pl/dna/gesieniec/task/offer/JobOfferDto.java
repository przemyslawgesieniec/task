package pl.dna.gesieniec.task.offer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobOfferDto {

    private String categoryName;
    private String userLogin;
    private Date endDate;

    /**
     * Really really bad! But. But I do not want to create custom json parser... Its a few minutes but this minutes adds up...
     * It should be just initialized on object parsing...
     */
    public Date getStartDate(){
        return new Date(System.currentTimeMillis());
    }
}
