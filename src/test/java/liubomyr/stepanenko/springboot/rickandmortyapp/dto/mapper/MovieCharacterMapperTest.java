package liubomyr.stepanenko.springboot.rickandmortyapp.dto.mapper;

import liubomyr.stepanenko.springboot.rickandmortyapp.dto.CharacterResponseDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Gender;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieCharacterMapperTest {
    private static final Long ID = 1L;
    private static final String NAME = "Alice Smith";
    private static final Status STATUS = Status.ALIVE;
    private static final Gender GENDER = Gender.FEMALE;
    private MovieCharacterMapper movieCharacterMapper;
    private ApiCharacterDto[] apiCharacterDtos;
    private MovieCharacter movieCharacter;

    @BeforeEach
    public void setUp() {
        movieCharacterMapper = new MovieCharacterMapper();
        apiCharacterDtos = createApiCharacterDtos();
        movieCharacter = createMovieCharacter();
    }

    @Test
    public void parseApiCharacterDto_Ok() {
        MovieCharacter actual = movieCharacterMapper.parseApiCharacterResponse(apiCharacterDtos[0]);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(movieCharacter.getExternalId(), actual.getExternalId());
        Assertions.assertEquals(movieCharacter.getName(), actual.getName());
        Assertions.assertEquals(movieCharacter.getStatus(), actual.getStatus());
        Assertions.assertEquals(movieCharacter.getGender(), actual.getGender());
    }

    @Test
    public void toResponseDto_Ok() {
        CharacterResponseDto actual = movieCharacterMapper.toResponseDto(movieCharacter);
        CharacterResponseDto expected = createCharacterResponseDto();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getExternalId(), actual.getExternalId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getStatus(), actual.getStatus());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
    }

    private ApiCharacterDto[] createApiCharacterDtos() {
        ApiCharacterDto dto1 = new ApiCharacterDto();
        dto1.setId(ID);
        dto1.setName(NAME);
        dto1.setStatus(String.valueOf(STATUS));
        dto1.setGender(String.valueOf(GENDER));
        return new ApiCharacterDto[] {dto1};
    }

    private MovieCharacter createMovieCharacter() {
        MovieCharacter character = new MovieCharacter();
        character.setId(ID);
        character.setExternalId(ID);
        character.setName(NAME);
        character.setStatus(STATUS);
        character.setGender(GENDER);
        return character;
    }

    private CharacterResponseDto createCharacterResponseDto() {
        CharacterResponseDto dto = new CharacterResponseDto();
        dto.setId(ID);
        dto.setExternalId(ID);
        dto.setName(NAME);
        dto.setStatus(STATUS);
        dto.setGender(GENDER);
        return dto;
    }
}
