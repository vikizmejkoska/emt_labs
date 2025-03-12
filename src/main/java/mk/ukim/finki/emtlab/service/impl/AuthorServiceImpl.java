package mk.ukim.finki.emtlab.service.impl;

import mk.ukim.finki.emtlab.model.Author;
import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.dto.AuthorDto;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.service.AuthorService;
import mk.ukim.finki.emtlab.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryService countryService;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> save(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());

        if (authorDto.getCountry() != null && countryService.findById(authorDto.getCountry().getId()).isPresent()) {
            author.setCountry(authorDto.getCountry());
        }
        return Optional.of(authorRepository.save(author));
    }

    @Override
    public Optional<Author> update(Long id, AuthorDto author) {
        return authorRepository.findById(id).map(existingAuthor -> {
            if(author.getName() != null){
                existingAuthor.setName(author.getName());
            }
            if(author.getSurname() != null){
                existingAuthor.setSurname(author.getSurname());
            }
            if (author.getCountry() != null) {
                Optional<Country> country = countryService.findById(author.getCountry().getId());
                country.ifPresent(existingAuthor::setCountry);
            }
            return authorRepository.save(existingAuthor);
        });
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
