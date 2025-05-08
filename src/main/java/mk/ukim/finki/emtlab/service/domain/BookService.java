package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.model.views.BooksPerAuthorView;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> save(Book book);
    Optional<Book> update(Long id, Book book);
    void delete(Long id);
  //  void markAsRented(Long id);
    List<Book> searchBooks(String title, String author, Category category);
    List<Book> findLatestBooks();

    void refreshMaterializedView();

    List<BooksPerAuthorView> findBooksPerAuthor();
}
