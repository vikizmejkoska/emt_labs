package mk.ukim.finki.emtlab.service.domain.impl;

import mk.ukim.finki.emtlab.events.AuthorEvent;
import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.projections.AuthorProjection;
import mk.ukim.finki.emtlab.model.views.AuthorsPerCountryView;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.repository.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emtlab.service.domain.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> save(Author author) {
        Author authorEntity = authorRepository.save(author);
        AuthorEvent authorEvent = new AuthorEvent(authorEntity);
        applicationEventPublisher.publishEvent(authorEvent);
        return Optional.of(authorEntity);
    }

    @Override
    public Optional<Author> update(Long id, Author author) {
        Optional<Author> authorEntity = authorRepository.findById(id);

        if (authorEntity.isPresent()) {

            if (author.getName() != null) {
                authorEntity.get().setName(author.getName());
            }

            if (author.getSurname() != null) {
                authorEntity.get().setSurname(author.getSurname());
            }

            if (author.getCountry() != null) {
                authorEntity.get().setCountry(author.getCountry());
            }

            authorRepository.save(authorEntity.get());
            AuthorEvent authorEvent = new AuthorEvent(authorEntity);
            applicationEventPublisher.publishEvent(authorEvent);
            return authorEntity;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
        AuthorEvent authorEvent = new AuthorEvent(null);
        applicationEventPublisher.publishEvent(authorEvent);
    }

    @Override
    public void refreshMaterializedView() {
        authorsPerCountryViewRepository.refreshMaterializedView();
    }

    @Override
    public List<AuthorsPerCountryView> findAuthorsPerCountry() {
        return authorsPerCountryViewRepository.findAll();
    }

    @Override
    public List<AuthorProjection> findAllByNameAndSurname() {
        return authorRepository.findAllByNameAndSurname();
    }
}
