package es.uma.taw.momdb.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Integer movieId;
    private Integer userId;
    private String username;
    private String content;
    private Double rating;
    private String movieTitle;
} 