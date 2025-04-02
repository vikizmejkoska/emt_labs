package mk.ukim.finki.emtlab.service.application.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.dto.WishlistDto;
import mk.ukim.finki.emtlab.service.application.WishlistApplicationService;
import mk.ukim.finki.emtlab.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistApplicationServiceImpl implements WishlistApplicationService {
    private final WishlistService wishlistService;


    @Override
    public List<DisplayBookDto> listAllBooksInWishlist(Long id) {
        return DisplayBookDto.from(wishlistService.listAllBooksInWishlist(id));
    }

    @Override
    public Optional<WishlistDto> findByUser(String username) {
        return wishlistService.findByUser(username).map(WishlistDto::from);
    }

    @Override
    public Optional<WishlistDto> addBookToWishlist(String username, Long bookId) {
        return wishlistService.addBookToWishlist(username,bookId).map(WishlistDto::from);
    }

    @Override
    public void rentAllBooksInWishlist(String username) {
        wishlistService.rentAllBooksInWishlist(username);
    }

    @Override
    public void rentBook(String username, Long id) {
        wishlistService.rentBook(username, id);
    }
}
