package mk.ukim.finki.emtlab.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.dto.WishlistDto;
import mk.ukim.finki.emtlab.service.application.WishlistApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist API", description = "Endpoints for managing wishlist")
public class WishlistController {
    private final WishlistApplicationService wishlistApplicationService;


    @GetMapping
    @Operation(summary = "Get all books in wishlist", description = "Retrieve all books in wishlist")
    public ResponseEntity<WishlistDto> getWishlist(HttpServletRequest req){
        String username = req.getRemoteUser();

        System.out.println("Username: " + username);

        if (username == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if the user is not authenticated
        }

        Optional<WishlistDto> wishlist = wishlistApplicationService.findByUser(username);
        return wishlist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add book to wishlist", description = "Add book to wishlist by book id")
    public ResponseEntity<WishlistDto> addBookToWishlist(@PathVariable Long id,
                                                         HttpServletRequest req){


        String username = req.getRemoteUser();
        if (username == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if the user is not authenticated
        }

        Optional<WishlistDto> updatedWishlist = wishlistApplicationService.addBookToWishlist(username, id);
        return updatedWishlist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/rent")
    @Operation(summary = "Rent all books in wishlist", description = "Rent all books in wishlist and reduce available copies")
    public ResponseEntity<String> rentAllBooks(HttpServletRequest req) {
        String username = req.getRemoteUser();

        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized!");
        }

        try {
            wishlistApplicationService.rentAllBooksInWishlist(username);
            return ResponseEntity.ok("All books rented successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/rent/{id}")
    @Operation(summary = "Rent a book from the wishlist", description = "Rent a specific book from the wishlist by book id")
    public ResponseEntity<WishlistDto> rentBookById(@PathVariable Long id, HttpServletRequest req) {
        String username = req.getRemoteUser();

        if (username == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if the user is not authenticated
        }

        try {
            // Call the service method to rent the book by ID
            wishlistApplicationService.rentBook(username, id);

            // Fetch the updated wishlist after renting the book
            Optional<WishlistDto> updatedWishlist = wishlistApplicationService.findByUser(username);

            // Return the updated wishlist or handle not found scenario
            return updatedWishlist.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());

        } catch (RuntimeException e) {
            // If there's an error (e.g., book not found, wishlist empty), return a bad request response with the error message
            return ResponseEntity.badRequest().build();
        }
    }


}


