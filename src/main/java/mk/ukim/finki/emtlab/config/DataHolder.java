package mk.ukim.finki.emtlab.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.domain.Country;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.model.enumerations.Role;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.repository.CountryRepository;
import mk.ukim.finki.emtlab.repository.UserRepository;
import mk.ukim.finki.emtlab.service.domain.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.time.LocalDate;


@Component
@AllArgsConstructor
public class DataHolder {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final BookRepository bookRepository;
    private final UserService userService;



//  @PostConstruct
    public void init(){
        Country country1 = new Country("England", "Europe");
        countryRepository.save(country1);
        Country country2 = new Country("Russia", "Europe");
        countryRepository.save(country2);

        Author author1 = new Author("Jane", "Austen", country1);
        authorRepository.save(author1);
        Author author2 = new Author("Leo", "Tolstoy", country2);
        authorRepository.save(author2);

        bookRepository.save(new Book("Pride and Prejudice", Category.DRAMA, author1, 5, LocalDate.now()));
        bookRepository.save(new Book("Anna Karenina", Category.NOVEL, author2, 10,LocalDate.now()));

        userService.save("user", "user", "user", "user", "user", Role.ROLE_USER);
        userService.save("librarian", "librarian", "librarian", "librarian", "librarian", Role.ROLE_LIBRARIAN);

    }

}
