package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.CreateAuthorDto;
import mk.ukim.finki.emtlab.dto.DisplayAuthorDto;
import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.domain.Country;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.service.application.AuthorApplicationService;
import mk.ukim.finki.emtlab.service.application.BookApplicationService;
import mk.ukim.finki.emtlab.service.domain.AuthorService;
import mk.ukim.finki.emtlab.service.domain.BookService;
import mk.ukim.finki.emtlab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {
    private final AuthorService authorService;
    private final CountryService countryService;


    public AuthorApplicationServiceImpl(
            CountryService countryService,
            AuthorService authorService

    ) {
        this.countryService = countryService;
        this.authorService = authorService;

    }

    @Override
    public Optional<DisplayAuthorDto> findById(Long id) {
        return authorService.findById(id).map(DisplayAuthorDto::from);
    }

    @Override
    public List<DisplayAuthorDto> findAll() {
        return authorService.findAll().stream().map(DisplayAuthorDto::from).toList();
    }

    @Override
    public Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.country());

        return authorService.update(id,
                        createAuthorDto.toAuthor(
                                country.orElse(null)
                        )
                )
                .map(DisplayAuthorDto::from);
    }

    @Override
    public Optional<DisplayAuthorDto> save(CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.country());

        if (country.isPresent()) {
            return authorService.save(createAuthorDto.toAuthor(country.get()))
                    .map(DisplayAuthorDto::from);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        authorService.delete(id);
    }


}

