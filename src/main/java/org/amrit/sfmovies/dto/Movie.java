package org.amrit.sfmovies.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "released_year")
    private String releasedYear;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "locations")
    private String locations;

    @Column(name = "display_location")
    private String displayLocation;

    @Column(name = "production_company")
    private String productionCompany;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actor_1")
    private String actor1;

    @Column(name = "actor_2")
    private String actor2;

    @Column(name = "actor_3")
    private String actor3;

}
