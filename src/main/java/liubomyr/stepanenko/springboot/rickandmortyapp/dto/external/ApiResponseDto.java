package liubomyr.stepanenko.springboot.rickandmortyapp.dto.external;

import lombok.Data;

@Data
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
