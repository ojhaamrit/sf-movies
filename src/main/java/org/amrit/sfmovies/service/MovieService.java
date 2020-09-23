package org.amrit.sfmovies.service;

import org.amrit.sfmovies.dto.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie save(Movie t);

    List<Movie> saveAll(List<Movie> t);

    Movie update(Movie t);

    List<Movie> findAll();

    Page<Movie> findAll(Pageable pageable);

    Optional<Movie> findOne(Long id);

    List<Movie> search(String title);

    void delete(Movie t);

    void delete(Long id);

    boolean existsById(Long id);

}
