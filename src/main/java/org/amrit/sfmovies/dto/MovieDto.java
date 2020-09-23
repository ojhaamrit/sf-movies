package org.amrit.sfmovies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Movie", description = "Detail about the Movie")
public class MovieDto {

    @ApiModelProperty(notes = "The unique identifier of an object. It is autogenerated.")
    private Long id;

    @ApiModelProperty(notes = "Title of the movie", required = true)
    private String title;

    @ApiModelProperty(notes = "Released Year of the movie")
    @JsonProperty("release_year")
    private String releasedYear;

    @ApiModelProperty(notes = "Latitude of the address where the movie was filmed.")
    private Double lat;

    @ApiModelProperty(notes = "Latitude of the address where the movie was filmed.")
    private Double lon;

    @ApiModelProperty(notes = "Address where the movie was filmed.", required = true)
    private String locations;

    @ApiModelProperty(notes = "Address (from map) where the movie was filmed.", required = true)
    private String displayLocation;

    @ApiModelProperty(notes = "Company that produced the movie.")
    @JsonProperty("production_company")
    private String productionCompany;

    private String director;

    private String writer;

    @JsonProperty("actor_1")
    private String actor1;

    @JsonProperty("actor_2")
    private String actor2;

    @JsonProperty("actor_3")
    private String actor3;

}