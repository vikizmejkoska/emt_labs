package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.dto.WishlistDto;

import java.util.List;
import java.util.Optional;

public interface WishlistApplicationService {
    List<DisplayBookDto> listAllBooksInWishlist(Long id);
    Optional<WishlistDto> findByUser(String username);
    Optional<WishlistDto> addBookToWishlist(String username, Long bookId);
    void rentAllBooksInWishlist(String username);
    void  rentBook(String username, Long id);
}
