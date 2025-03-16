package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> save(BookDto book);
    Optional<Book> update(Long id, BookDto book);
    void delete(Long id);
    void markAsRented(Long id);
    List<Book> searchBooks(String title, String author, Category category);
}
