package mk.ukim.finki.emtlab.service.application.impl;

import mk.ukim.finki.emtlab.dto.CreateBookDto;
import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.enumerations.Category;
import mk.ukim.finki.emtlab.service.application.BookApplicationService;
import mk.ukim.finki.emtlab.service.domain.AuthorService;
import mk.ukim.finki.emtlab.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    private final BookService bookService;
    private final AuthorService authorService;
 

    public BookApplicationServiceImpl(
            BookService bookService,
            AuthorService authorService
            
    ) {
        this.bookService = bookService;
        this.authorService = authorService;
      
    }

    @Override
    public Optional<DisplayBookDto> findById(Long id) {
        return bookService.findById(id).map(DisplayBookDto::from);
    }

    @Override
    public List<DisplayBookDto> findAll() {
        return bookService.findAll().stream().map(DisplayBookDto::from).toList();
    }

    @Override
    public Optional<DisplayBookDto> update(Long id, CreateBookDto createBookDto) {
        Optional<Author> author = authorService.findById(createBookDto.author());

        return bookService.update(id,
                        createBookDto.toBook(
                                author.orElse(null)
                        )
                )
                .map(DisplayBookDto::from);
    }

    @Override
    public Optional<DisplayBookDto> save(CreateBookDto createBookDto) {
        Optional<Author> author = authorService.findById(createBookDto.author());
        
        if (author.isPresent()) {
            return bookService.save(createBookDto.toBook(author.get()))
                    .map(DisplayBookDto::from);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        bookService.delete(id);
    }



}

