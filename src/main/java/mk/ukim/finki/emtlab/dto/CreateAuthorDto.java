package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.domain.Country;


public record CreateAuthorDto(
        String name,
        String surname,
        Long country) {

    public Author toAuthor(Country country) {
        return new Author(name, surname, country);
    }


}