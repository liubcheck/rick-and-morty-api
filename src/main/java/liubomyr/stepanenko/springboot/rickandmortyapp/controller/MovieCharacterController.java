package liubomyr.stepanenko.springboot.rickandmortyapp.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.CharacterResponseDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.service.MovieCharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @GetMapping("/random")
    @ApiOperation(value = "Get a random 'Rick and Morty' character")
    public CharacterResponseDto getRandom() {
        MovieCharacter randomCharacter = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toResponseDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "Get all 'Rick and Morty' characters whose names contain requested part")
    public List<CharacterResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart)
                .stream()
                .map(movieCharacterMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
