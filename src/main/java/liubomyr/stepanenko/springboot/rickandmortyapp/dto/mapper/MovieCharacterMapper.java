package liubomyr.stepanenko.springboot.rickandmortyapp.dto.mapper;

import liubomyr.stepanenko.springboot.rickandmortyapp.dto.CharacterResponseDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Gender;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponse(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        return movieCharacter;
    }

    public CharacterResponseDto toResponseDto(MovieCharacter movieCharacter) {
        CharacterResponseDto dto = new CharacterResponseDto();
        dto.setId(movieCharacter.getId());
        dto.setExternalId(movieCharacter.getExternalId());
        dto.setName(movieCharacter.getName());
        dto.setStatus(movieCharacter.getStatus());
        dto.setGender(movieCharacter.getGender());
        return dto;
    }
}
