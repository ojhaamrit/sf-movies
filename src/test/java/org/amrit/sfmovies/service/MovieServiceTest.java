package org.amrit.sfmovies.service;

import org.amrit.sfmovies.dto.Movie;
import org.amrit.sfmovies.dto.MovieDto;
import org.amrit.sfmovies.mapper.MovieMapper;
import org.amrit.sfmovies.repository.MovieRepository;
import org.amrit.sfmovies.service.impl.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceTest {

	@Mock
	MovieRepository movieRepo;

	@InjectMocks
	MovieServiceImpl movieServiceImpl;

	@Autowired
	MovieMapper mapper;

	MovieDto movieDTO = new MovieDto();
	Movie movie = new Movie();

	@BeforeEach
	public void init() {

		movieDTO.setId(1L);
		movieDTO.setTitle("180");
		movieDTO.setLat(37.790003);
		movieDTO.setLon(-122.399926);
		movieDTO.setLocations("555 Market St.");
		movieDTO.setDirector("Jayendra");
		movieDTO.setWriter("Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba");
		movieDTO.setReleasedYear("2011");
		movieDTO.setProductionCompany("SPI Cinemas");
		movieDTO.setActor1("Siddarth");
		movieDTO.setActor2("Nithya Menon");
		movieDTO.setActor3("Priya Anand");

		movie = mapper.toEntity(movieDTO);
	}

	@Test
	public void testSave() {
		Mockito.when(movieRepo.save(movie)).thenReturn(movie);
		movieServiceImpl.save(movie);
		Mockito.verify(movieRepo).save(Mockito.any(Movie.class));
	}

	@Test
	public void testUpdate() {
		Mockito.when(movieRepo.save(movie)).thenReturn(movie);
		movieServiceImpl.update(movie);
		Mockito.verify(movieRepo).save(Mockito.any(Movie.class));
	}

	@Test
	public void testFindAll() {
		List<Movie> movieList = new ArrayList<Movie>();
		movieList.add(movie);
		Mockito.when(movieRepo.findAll()).thenReturn(movieList);

		movieServiceImpl.findAll();

		Mockito.verify(movieRepo).findAll();
	}

	@Test
	public void testSaveAll() {
		List<Movie> movieList = new ArrayList<Movie>();
		movieList.add(movie);
		Mockito.when(movieRepo.saveAll(movieList)).thenReturn(movieList);

		movieServiceImpl.saveAll(movieList);

		Mockito.verify(movieRepo).saveAll(Mockito.any(List.class));
	}

	@Test
	public void testFindOne() {
		Long id = 1L;
		Mockito.when(movieRepo.findById(id)).thenReturn(Optional.of(movie));

		movieServiceImpl.findOne(id);
		Mockito.verify(movieRepo).findById(Mockito.any(Long.class));
	}

	@Test
	public void testSearch() {
		String title = "180";
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		Mockito.when(movieRepo.findAllByTitleContaining(title)).thenReturn(movieList);

		movieServiceImpl.search(title);

		Mockito.verify(movieRepo).findAllByTitleContaining(title);

	}

}
