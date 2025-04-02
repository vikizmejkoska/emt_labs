package mk.ukim.finki.emtlab.dto;

import mk.ukim.finki.emtlab.model.domain.Wishlist;

import java.util.ArrayList;
import java.util.List;

public record WishlistDto (Long id,
                           DisplayUserDto user,
                           List<DisplayBookDto> books){
    public static WishlistDto from(Wishlist wishlist){
        return new WishlistDto(
                wishlist.getId(),
                DisplayUserDto.from(wishlist.getUser()),
                wishlist.getBooks() != null ? DisplayBookDto.from(wishlist.getBooks()) : new ArrayList<>()
        );
    }
}
