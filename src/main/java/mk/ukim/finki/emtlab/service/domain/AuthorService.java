package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.projections.AuthorProjection;
import mk.ukim.finki.emtlab.model.views.AuthorsPerCountryView;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> update(Long id, Author author);
    Optional<Author> save(Author author);
    void delete(Long id);

    void refreshMaterializedView();

    List<AuthorsPerCountryView> findAuthorsPerCountry();

    List<AuthorProjection> findAllByNameAndSurname();
}
