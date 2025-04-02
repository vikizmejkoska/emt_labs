package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.Country;

public record CreateCountryDto(
        String name,
        String continent) {
    public Country toCountry() {
        return new Country(name, continent);
    }
}