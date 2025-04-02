package mk.ukim.finki.emtlab.service.domain.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.domain.Wishlist;
import mk.ukim.finki.emtlab.model.exceptions.BookUnavailableException;
import mk.ukim.finki.emtlab.repository.WishlistRepository;
import mk.ukim.finki.emtlab.service.domain.BookService;
import mk.ukim.finki.emtlab.service.domain.UserService;
import mk.ukim.finki.emtlab.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public List<Book> listAllBooksInWishlist(Long id) {
        if (wishlistRepository.findById(id).isEmpty()){
            throw new RuntimeException("Wishlist id empty!");
        }
        return wishlistRepository.findById(id).get().getBooks();
    }

    @Override
    public Optional<Wishlist> addBookToWishlist(String username, Long bookId) {
        User user = userService.findByUsername(username);

        // Find existing wishlist or create a new one if not found
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(user, new ArrayList<>())));

        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

        // Prevent adding duplicate books
        if (wishlist.getBooks().stream().anyMatch(b -> b.getId().equals(bookId))) {
            throw new RuntimeException("Book is already in wishlist!");
        }

        if (book.getAvailableCopies() > 0) {
            // book.setAvailableCopies(book.getAvailableCopies() - 1);
            book.setWishlist(wishlist); // Ensure the book references the wishlist
            wishlist.getBooks().add(book);

            wishlistRepository.save(wishlist); // Persist changes
            return Optional.of(wishlist);
        } else {
            throw new BookUnavailableException(book.getId());
        }
    }



    @Override
    public Optional<Wishlist> findByUser(String username) {
        User user = userService.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found!");
        }
        return wishlistRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void rentAllBooksInWishlist(String username) {
        User user = userService.findByUsername(username);
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user!"));

        if (wishlist.getBooks().isEmpty()) {
            throw new RuntimeException("Wishlist is empty!");
        }

        List<Book> rentedBooks = new ArrayList<>();

        for (Book book : wishlist.getBooks()) {
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                rentedBooks.add(book);
            } else {
                throw new RuntimeException("Book '" + book.getName() + "' is not available for rent!");
            }
        }

        wishlist.getBooks().clear();
        wishlistRepository.save(wishlist);
    }

    @Override
    public void rentBook(String username, Long id) {
        User user = userService.findByUsername(username);
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user!"));

        if (wishlist.getBooks().isEmpty()) {
            throw new RuntimeException("Wishlist is empty!");
        }

        Book book = bookService.findById(id).orElseThrow(()->new RuntimeException("Book not found!"));
        if(book.getAvailableCopies() > 0){
            book.setAvailableCopies(book.getAvailableCopies()-1);
        }
        wishlistRepository.save(wishlist);
    }
}
