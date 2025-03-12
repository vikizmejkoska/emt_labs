package mk.ukim.finki.emtlab.model.dto;

import mk.ukim.finki.emtlab.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    String name;
    String surname;
    Country country;
}
