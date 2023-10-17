package liubomyr.stepanenko.springboot.rickandmortyapp.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Gender;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Status;
import liubomyr.stepanenko.springboot.rickandmortyapp.service.MovieCharacterService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MovieCharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovieCharacterService movieCharacterService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getRandom_Ok() {
        MovieCharacter mockCharacter = createMovieCharacter();
        Mockito.when(movieCharacterService.getRandomCharacter()).thenReturn(mockCharacter);
        RestAssuredMockMvc.when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(mockCharacter.getId().intValue()))
                .body("name", Matchers.equalTo(mockCharacter.getName()))
                .body("status", Matchers.equalTo(mockCharacter.getStatus().name()))
                .body("gender", Matchers.equalTo(mockCharacter.getGender().name()));
    }

    @Test
    public void findAllByName_Ok() {
        List<MovieCharacter> mockCharacters = createMovieCharacterList();
        Mockito.when(movieCharacterService.findAllByNameContains("Br"))
                .thenReturn(List.of(mockCharacters.get(1), mockCharacters.get(2)));
        RestAssuredMockMvc.when()
                .get("/movie-characters/by-name?name=Br")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(mockCharacters.get(1).getId().intValue()))
                .body("[0].name", Matchers.equalTo(mockCharacters.get(1).getName()))
                .body("[0].status", Matchers.equalTo(mockCharacters.get(1).getStatus().name()))
                .body("[0].gender", Matchers.equalTo(mockCharacters.get(1).getGender().name()))
                .body("[1].id", Matchers.equalTo(mockCharacters.get(2).getId().intValue()))
                .body("[1].name", Matchers.equalTo(mockCharacters.get(2).getName()))
                .body("[1].status", Matchers.equalTo(mockCharacters.get(2).getStatus().name()))
                .body("[1].gender", Matchers.equalTo(mockCharacters.get(2).getGender().name()));
    }

    private MovieCharacter createMovieCharacter() {
        MovieCharacter character = new MovieCharacter();
        character.setId(1L);
        character.setExternalId(1L);
        character.setName("Alice Smith");
        character.setStatus(Status.ALIVE);
        character.setGender(Gender.FEMALE);
        return character;
    }

    private List<MovieCharacter> createMovieCharacterList() {
        MovieCharacter character1 = createMovieCharacter();

        MovieCharacter character2 = new MovieCharacter();
        character2.setId(2L);
        character2.setExternalId(2L);
        character2.setName("Bruce Lee");
        character2.setStatus(Status.DEAD);
        character2.setGender(Gender.MALE);

        MovieCharacter character3 = new MovieCharacter();
        character3.setId(3L);
        character3.setExternalId(3L);
        character3.setName("Brian");
        character3.setStatus(Status.UNKNOWN);
        character3.setGender(Gender.UNKNOWN);

        return List.of(character1, character2, character3);
    }
}