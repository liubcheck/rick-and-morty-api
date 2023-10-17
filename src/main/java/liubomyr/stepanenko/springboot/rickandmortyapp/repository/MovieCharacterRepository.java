package liubomyr.stepanenko.springboot.rickandmortyapp.repository;

import java.util.List;
import java.util.Set;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    List<MovieCharacter> findAllByNameContains(String namePart);
}
