package org.amrit.sfmovies.resource;

import org.amrit.sfmovies.dto.Movie;
import org.amrit.sfmovies.dto.MovieDto;
import org.amrit.sfmovies.exceptions.BadRequestException;
import org.amrit.sfmovies.exceptions.ResourceNotFoundException;
import org.amrit.sfmovies.mapper.MovieMapper;
import org.amrit.sfmovies.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	MovieMapper mapper;

	@MockBean
	private MovieService movieService;

	MovieDto movieDTO = new MovieDto();
	Movie movie = new Movie();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
	public void testGet() throws Exception {
		Long id = 1L;
		when(movieService.findOne(id)).thenReturn(Optional.of(movie));

		mockMvc.perform(get("/api/movies/" + id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(Integer.valueOf(movieDTO.getId() + ""))))
				.andExpect(jsonPath("$.actor_1", is(movieDTO.getActor1())));

	}

	@Test
	public void testGetWithNullId() throws Exception {
		Long id = 0L;
		when(movieService.findOne(id)).thenThrow(new ResourceNotFoundException("NOT_FOUND"));

		mockMvc.perform(get("/api/movies/" + id))
				.andExpect(jsonPath("$.status", is("NOT_FOUND")));

	}

	@Test
	public void testCreate() throws Exception {
		String movieJSON = "{\"title\":\"180\",\"lat\":37.790003,\"lon\":-122.399926,\"locations\":\"555 Market St.\",\"director\":\"Jayendra\",\"writer\":\"Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba\",\"release_year\":\"2011\",\"production_company\":\"SPI Cinemas\",\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";

		when(movieService.save(Mockito.any(Movie.class))).thenReturn(movie);

		mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.id", is(1)));

	}

	@Test
	public void testCreateWithIdNotNull() throws Exception {
		String movieJSON = "{\"id\":\"1\",\"title\":\"180\","
				+ "\"lat\":37.790003,\"lon\":-122.399926,\"locations\":\"555 "
				+ "Market St.\",\"director\":\"Jayendra\",\"writer\":\"Umarji Anuradha, Jayendra, Aarthi Sriram, "
				+ "& Suba\",\"release_year\":\"2011\",\"production_company\":"
				+ "\"SPI Cinemas\",\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";
		when(movieService.save(Mockito.any(Movie.class)))
				.thenThrow(new BadRequestException("Id must be null to create movie."));

		mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andExpect(jsonPath("$.message", is("Id must be null to create movie.")));
	}

	@Test
	public void testCreateWithNullEntityAndEmptyLocation() throws Exception {
		String movieJSON = "{\"title\":\"180\",\"lat\":37.790003,\"lon\":-122.399926,"
				+ "\"locations\":\"\",\"writer\":\"Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba\""
				+ ",\"release_year\":\"2011\",\"production_company\":\"SPI Cinemas\",\"actor_1\":\"Siddarth\","
				+ "\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";
		when(movieService.save(movie)).thenThrow(new BadRequestException("Location cannot be found in the map."));

		mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andExpect(jsonPath("$.message", is("Location cannot be found in the map.")));

	}

	@Test
	public void testUpdate() throws Exception {
		Long id = 0L;
		String movieJSON = "{\"title\":\"180\",\"lat\":null,\"lon\":null,\"locations\":"
				+ "\"555 Market St.\",\"director\":\"Jayendra\",\"writer\":\"Umarji Anuradha,"
				+ " Jayendra, Aarthi Sriram, & Suba\",\"release_year\":\"2011\",\"production_company\":"
				+ "\"SPI Cinemas\",\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":"
				+ "\"Priya Anand\"}";

		when(movieService.update(movie))
				.thenThrow(new BadRequestException("ID is either null or doesn't match with url's."));

		mockMvc.perform(put("/api/movies/" + id).contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andExpect(jsonPath("$.message", is("ID is either null or doesn't match with url's.")));

	}

	@Test
	public void testUpdateWithEmptyLocation() throws Exception {
		Long id = 1L;
		String movieJSON = "{\"id\":1,"
				+ "\"title\":\"180\",\"lat\":null,\"lon\":null,"
				+ "\"locations\":\"\",\"director\":\"Jayendra\","
				+ "\"writer\":\"Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba"
				+ "\",\"release_year\":\"2011\",\"production_company\":\"SPI Cinemas"
				+ "\",\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";

		when(movieService.update(movie)).thenThrow(new BadRequestException("Location cannot be null or empty."));

		mockMvc.perform(put("/api/movies/" + id).contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andExpect(jsonPath("$.message", is("Location cannot be null or empty.")));

	}


	@Test
	public void testUpdateWithNullLatAndLong() throws Exception {
		Long id = 1L;
		String movieJSON = "{\"id\":\"1\",\"title\":"
				+ "\"180\",\"lat\":null,\"lon\":null,\"locations\":"
				+ "\"aaa\",\"director\":\"Jayendra\",\"writer\":"
				+ "\"Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba\","
				+ "\"release_year\":\"2011\",\"production_company\":\"SPI Cinemas\","
				+ "\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";

		when(movieService.update(movie)).thenThrow(new BadRequestException("Latitude and Longitude cannot be null."));

		mockMvc.perform(put("/api/movies/" + id).contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andDo(print())
				.andExpect(jsonPath("$.message", is("Latitude and Longitude cannot be null.")));

	}

	@Test
	public void testUpdateAll() throws Exception {
		Long id = 1L;
		String movieJSON = "{\"id\":1,\"title\":"
				+ "\"180\",\"lat\":127,\"lon\":129,\"locations\":"
				+ "\"aaa\",\"director\":\"Jayendra\",\"writer\":"
				+ "\"Umarji Anuradha, Jayendra, Aarthi Sriram, & Suba\","
				+ "\"release_year\":\"2011\",\"production_company\":\"SPI Cinemas\","
				+ "\"actor_1\":\"Siddarth\",\"actor_2\":\"Nithya Menon\",\"actor_3\":\"Priya Anand\"}";

		when(movieService.update(Mockito.any(Movie.class))).thenReturn(movie);

		mockMvc.perform(put("/api/movies/" + id).contentType(MediaType.APPLICATION_JSON_VALUE).content(movieJSON))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.id", is(1)));

	}

	@Test
	public void testSearch() throws Exception {
		String title = "180";
		mockMvc.perform(get("/api/movies/search").contentType(MediaType.APPLICATION_JSON_VALUE).param("keyword", title))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
