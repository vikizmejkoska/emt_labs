package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.CreateAuthorDto;
import mk.ukim.finki.emtlab.dto.DisplayAuthorDto;
import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.domain.Country;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.model.projections.AuthorProjection;
import mk.ukim.finki.emtlab.model.views.AuthorsPerCountryView;
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
        //return authorService.findAll().stream().map(DisplayAuthorDto::from).toList();
        return  DisplayAuthorDto.from(authorService.findAll());
    }

    @Override
    public Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.country());

        if (country.isPresent()) {
            Author author = new Author(
                    createAuthorDto.name(),
                    createAuthorDto.surname(),
                    country.get()
            );
            return authorService.update(id, author).map(DisplayAuthorDto::from);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DisplayAuthorDto> save(CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.country());

        if (country.isPresent()) {
//            AuthorEntity author = new AuthorEntity(
//                    createAuthorDto.name(),
//                    createAuthorDto.surname(),
//                    country.get()
//            );
            return authorService.save(createAuthorDto.toAuthor(country.get())).map(DisplayAuthorDto::from);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        authorService.delete(id);
    }
    @Override
    public List<AuthorsPerCountryView> findAuthorsPerCountry() {
        return authorService.findAuthorsPerCountry();
    }

    @Override
    public List<AuthorProjection> findAllByNameAndSurname() {
        return authorService.findAllByNameAndSurname();
    }

}

