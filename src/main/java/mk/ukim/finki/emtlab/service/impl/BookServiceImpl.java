package mk.ukim.finki.emtlab.service.impl;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.service.AuthorService;
import mk.ukim.finki.emtlab.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(BookDto book) {
        if (book.getAuthor() != null && authorService.findById(book.getAuthor()).isPresent()){
            return Optional.of(
                    bookRepository.save(new Book(book.getName(),book.getCategory(),
                            authorService.findById(book.getAuthor()).get(),
                            book.getAvailableCopies() )));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Long id, BookDto book) {
        return bookRepository.findById(id).map( existingBook -> {
        if (book.getName()!=null){
            existingBook.setName(book.getName());
        }
        if (book.getCategory()!=null){
            existingBook.setCategory(book.getCategory());
        }
        if (book.getAuthor()!=null && authorService.findById(book.getAuthor()).isPresent() ){
            existingBook.setAuthor(authorService.findById(book.getAuthor()).get());
        }
        if (book.getAvailableCopies()!=null){
            existingBook.setAvailableCopies(book.getAvailableCopies());
        }
        return bookRepository.save(existingBook);
                } );
    }

    @Override
    public void delete(Long id) {
      bookRepository.deleteById(id);
    }

    @Override
    public void markAsRented(Long id) {
        bookRepository.findById(id).ifPresent(book -> {
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);
            }
        });
    }
}
