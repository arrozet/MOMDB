package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author Artur797 (Artur Vargas - 92.3%), projectGeorge (Jorge Repullo - 7.7%)
 */
@Data
public class ReviewDTO {
    private Integer movieId;
    private Integer userId;
    private String username;
    private String content;
    private Double rating;
    private String movieTitle;
} 