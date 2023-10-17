package liubomyr.stepanenko.springboot.rickandmortyapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiInfoDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Gender;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Status;
import liubomyr.stepanenko.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository movieCharacterRepository;
    @Mock
    private MovieCharacterMapper movieCharacterMapper;
    private ApiResponseDto apiResponseDto;

    @BeforeEach
    public void setUp() {
        createApiResponseDto();
    }

    @Test
    public void saveDtosToDB_Ok() {
        List<MovieCharacter> expected = createExpectedMovieCharacters();
        List<MovieCharacter> movieCharacters = Arrays.stream(apiResponseDto.getResults())
                .map(movieCharacterMapper::parseApiCharacterResponse)
                .toList();
        Mockito.when(movieCharacterRepository.saveAll(movieCharacters)).thenReturn(expected);
        List<MovieCharacter> actual = movieCharacterService.saveDtosToDB(apiResponseDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assertions.assertEquals(expected.get(i).getExternalId(),
                    actual.get(i).getExternalId());
            Assertions.assertEquals(expected.get(i).getName(), actual.get(i).getName());
            Assertions.assertEquals(expected.get(i).getStatus(), actual.get(i).getStatus());
            Assertions.assertEquals(expected.get(i).getGender(), actual.get(i).getGender());
        }
    }

    private void createApiResponseDto() {
        apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(createInfo());
        apiResponseDto.setResults(createResults());
    }

    private ApiInfoDto createInfo() {
        ApiInfoDto dto = new ApiInfoDto();
        dto.setCount(4);
        dto.setPages(5);
        dto.setPrev("1");
        dto.setNext("3");
        return dto;
    }

    private ApiCharacterDto[] createResults() {
        ApiCharacterDto dto1 = new ApiCharacterDto();
        dto1.setId(1L);
        dto1.setName("Alice Smith");
        dto1.setStatus(String.valueOf(Status.ALIVE));
        dto1.setGender(String.valueOf(Gender.FEMALE));

        ApiCharacterDto dto2 = new ApiCharacterDto();
        dto1.setId(2L);
        dto2.setName("Bob Brown");
        dto2.setStatus(String.valueOf(Status.UNKNOWN));
        dto2.setGender(String.valueOf(Gender.MALE));

        return new ApiCharacterDto[] {dto1, dto2};
    }

    private List<MovieCharacter> createExpectedMovieCharacters() {
        MovieCharacter character1 = new MovieCharacter();
        character1.setId(1L);
        character1.setExternalId(1L);
        character1.setName("Alice Smith");
        character1.setStatus(Status.ALIVE);
        character1.setGender(Gender.FEMALE);

        MovieCharacter character2 = new MovieCharacter();
        character2.setId(2L);
        character2.setExternalId(2L);
        character2.setName("Bob Brown");
        character2.setStatus(Status.UNKNOWN);
        character2.setGender(Gender.MALE);

        return List.of(character1, character2);
    }
}
