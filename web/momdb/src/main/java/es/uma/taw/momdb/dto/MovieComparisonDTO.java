package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author - edugbau (Eduardo González)
 */
@Data
public class MovieComparisonDTO {
    private String metricName;
    private Object movieValue;
    private Object overallAverage;
    private Object genreAverage;
}
