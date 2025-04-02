package mk.ukim.finki.emtlab.service.domain;

import mk.ukim.finki.emtlab.model.domain.Book;
import mk.ukim.finki.emtlab.model.domain.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    List<Book> listAllBooksInWishlist(Long id);
    Optional<Wishlist> addBookToWishlist(String username, Long bookId);
    Optional<Wishlist> findByUser(String username);
    void rentAllBooksInWishlist(String username);
    void  rentBook(String username, Long id);
}
