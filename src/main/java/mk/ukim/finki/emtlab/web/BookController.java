package mk.ukim.finki.emtlab.web;

import mk.ukim.finki.emtlab.dto.CreateBookDto;
import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.service.application.BookApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mk.ukim.finki.emtlab.model.enumerations.Category;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "API for managing books")
public class BookController {
    private final BookApplicationService bookService;

    public BookController(BookApplicationService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public List<DisplayBookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Retrieve book by id")
    public ResponseEntity<DisplayBookDto> findById(@PathVariable Long id) {
        return bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new book", description = "Create and store a new book")
    public ResponseEntity<DisplayBookDto> save(@RequestBody CreateBookDto book) {
        return bookService.save(book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit book", description = "Edit existing book by id")
    public ResponseEntity<DisplayBookDto> update(@PathVariable Long id, @RequestBody CreateBookDto book) {
        return bookService.update(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete book", description = "Delete book by id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

//    @PostMapping("/markAsRented/{id}")
//    @Operation(summary = "Rent a book", description = "Decrease available copies when a book is rented")
//    public ResponseEntity<Void> markAsRented(@PathVariable Long id) {
//        if (bookService.findById(id).isPresent()) {
//            bookService.markAsRented(id);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @GetMapping("/search")
//    @Operation(summary = "Filter", description = "Filter by title, author and category")
//    public List<DisplayBookDto> searchBooks( @RequestParam(required = false) String title,
//                                   @RequestParam(required = false) String author,
//                                   @RequestParam(required = false) Category category) {
//        return bookService.searchBooks(title, author, category);
//    }

}


