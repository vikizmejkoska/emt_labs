package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.dto.CountryDto;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();
    Optional<Country> findById(Long id);
    Optional<Country> update(Long id, CountryDto country);
    Optional<Country> save(CountryDto country);
    void delete(Long id);
}