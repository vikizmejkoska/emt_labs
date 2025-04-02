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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;



@Component
@AllArgsConstructor
public class DataHolder {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @PostConstruct
    public void init(){
        Country country1 = new Country("England", "Europe");
        countryRepository.save(country1);
        Country country2 = new Country("Russia", "Europe");
        countryRepository.save(country2);

        Author author1 = new Author("Jane", "Austen", country1);
        authorRepository.save(author1);
        Author author2 = new Author("Leo", "Tolstoy", country2);
        authorRepository.save(author2);

        bookRepository.save(new Book("Pride and Prejudice", Category.DRAMA, author1, 5));
        bookRepository.save(new Book("Anna Karenina", Category.NOVEL, author2, 10));

        userRepository.save(new User(
                "user",
                passwordEncoder.encode("user"),
                "User",
                "User",
                Role.ROLE_USER
        ));

        userRepository.save(new User(
                "librarian",
                passwordEncoder.encode("librarian"),
                "Librarian",
                "Librarian",
                Role.ROLE_LIBRARIAN
        ));

    }

}
