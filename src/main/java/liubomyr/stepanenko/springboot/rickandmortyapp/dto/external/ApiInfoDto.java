package liubomyr.stepanenko.springboot.rickandmortyapp.dto.external;

import lombok.Data;

@Data
public class ApiInfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
