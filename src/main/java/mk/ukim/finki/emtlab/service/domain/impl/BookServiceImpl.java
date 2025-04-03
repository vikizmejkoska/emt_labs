package mk.ukim.finki.emtlab.service.domain.impl;

import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.service.domain.AuthorService;
import mk.ukim.finki.emtlab.service.domain.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;


    @Override
    public List<Book> findAll() {
        return bookRepository.findAllActive();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findByIdActive(id);
    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getAuthor() != null && authorService.findById(book.getAuthor().getId()).isPresent()){
            return Optional.of(
                    bookRepository.save(new Book(book.getName(),book.getCategory(),
                            authorService.findById(book.getAuthor().getId()).get(),
                            book.getAvailableCopies(),book.getPublishedDate() )));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id).map( existingBook -> {
        if (book.getName()!=null){
            existingBook.setName(book.getName());
        }
        if (book.getCategory()!=null){
            existingBook.setCategory(book.getCategory());
        }
        if (book.getAuthor()!=null && authorService.findById(book.getAuthor().getId()).isPresent() ){
            existingBook.setAuthor(authorService.findById(book.getAuthor().getId()).get());
        }
        if (book.getAvailableCopies()!=null){
            existingBook.setAvailableCopies(book.getAvailableCopies());
        }
        return bookRepository.save(existingBook);
                } );
    }

    @Override
    public void delete(Long id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setDeleted(true);
            bookRepository.save(book);
        });
    }

//    @Override
//    public void markAsRented(Long id) {
//        bookRepository.findById(id).ifPresent(book -> {
//            if (book.getAvailableCopies() > 0) {
//                book.setAvailableCopies(book.getAvailableCopies() - 1);
//                bookRepository.save(book);
//            }
//        });
//    }

    @Override
    public List<Book> searchBooks(String title, String author, Category category) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> !book.isDeleted())  // Ensuring soft-deleted books are ignored
                .filter(book -> title == null || book.getName().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author == null || book.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> category == null || book.getCategory() == category)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findLatestBooks() {
        return bookRepository.findTop10ByOrderByPublishedDateDesc(PageRequest.of(0, 10));
    }
}
