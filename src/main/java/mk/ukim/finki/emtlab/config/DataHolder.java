package mk.ukim.finki.emtlab.config;

import mk.ukim.finki.emtlab.model.Author;
import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.repository.CountryRepository;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;



@Component
public class DataHolder {
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final BookRepository bookRepository;

    public DataHolder(AuthorRepository authorRepository,
                           CountryRepository countryRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.bookRepository = bookRepository;
    }

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

    }
}
