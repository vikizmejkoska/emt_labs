package mk.ukim.finki.emtlab.repository;

import mk.ukim.finki.emtlab.model.domain.User;
import mk.ukim.finki.emtlab.model.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);

}
