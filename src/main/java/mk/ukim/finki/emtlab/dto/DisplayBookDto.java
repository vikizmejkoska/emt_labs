package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.Author;
import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.enumerations.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayBookDto(Long Id,
                             String name,
                             Category category,
                             Long author,
                             Integer availableCopies,
                             LocalDate publishedDate) {

    public static DisplayBookDto from(Book book) {
        return new DisplayBookDto(
                book.getId(),
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAvailableCopies(),
                book.getPublishedDate()
        );
    }

    public static List<DisplayBookDto> from(List<Book> books) {
        return books.stream().map(DisplayBookDto::from).collect(Collectors.toList());
    }

    public Book toBook(Author author) {
        return new Book(name, category, author, availableCopies, publishedDate);
    }
}
