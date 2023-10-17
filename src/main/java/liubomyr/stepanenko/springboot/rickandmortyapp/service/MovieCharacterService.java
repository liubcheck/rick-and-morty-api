package liubomyr.stepanenko.springboot.rickandmortyapp.service;

import java.util.List;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
