package es.uma.taw.momdb.dto;
//Autor: edugbau (Eduardo González)
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GenreAnalyticsDTO {
    private String genreName;
    private Double averageRevenue;
    private Double averageRuntime;
    private BigDecimal averagePopularity;
    private Double averageBudget;
    private Double averageVoteCount;
    private Long favoriteCount;
    private Long watchlistCount;
}

