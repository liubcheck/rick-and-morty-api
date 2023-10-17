package liubomyr.stepanenko.springboot.rickandmortyapp.dto;

import liubomyr.stepanenko.springboot.rickandmortyapp.model.Gender;
import liubomyr.stepanenko.springboot.rickandmortyapp.model.Status;
import lombok.Data;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
