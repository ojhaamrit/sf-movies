package org.amrit.sfmovies.repository;

import org.amrit.sfmovies.dto.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByTitleContaining(String title);

}
