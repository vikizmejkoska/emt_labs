package mk.ukim.finki.emtlab.service.impl;

import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.dto.CountryDto;
import mk.ukim.finki.emtlab.repository.CountryRepository;
import mk.ukim.finki.emtlab.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Optional<Country> save(CountryDto countryDto) {
        Country country = new Country();
        country.setName(countryDto.getName());
        country.setContinent(countryDto.getContinent());
        return Optional.of(countryRepository.save(country));
    }

    @Override
    public Optional<Country> update(Long id, CountryDto country) {
        return countryRepository.findById(id).map(existingCountry->{
            if(country.getName() != null){
                existingCountry.setName(country.getName());
            }
            if(country.getContinent() != null){
                existingCountry.setContinent(country.getContinent());
            }
            return countryRepository.save(existingCountry);
        });
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}
