package liubomyr.stepanenko.springboot.rickandmortyapp.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper movieCharacterMapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @PostConstruct
    @Override
    @Scheduled(cron = "0 8 * * * ?")
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters method invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        saveDtosToDB(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDB(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.getById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    List<MovieCharacter> saveDtosToDB(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters =
                movieCharacterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.parseApiCharacterResponse(externalDtos.get(i)))
                .toList();
        return movieCharacterRepository.saveAll(charactersToSave);
    }
}
