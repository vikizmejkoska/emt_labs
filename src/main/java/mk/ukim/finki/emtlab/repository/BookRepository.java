package mk.ukim.finki.emtlab.repository;

import mk.ukim.finki.emtlab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    // Fetch only non-deleted books
    @Query("SELECT b FROM Book b WHERE b.deleted = false")
    List<Book> findAllActive();

    // Find by ID only if not deleted
    @Query("SELECT b FROM Book b WHERE b.id = :id AND b.deleted = false")
    Optional<Book> findByIdActive(Long id);
}
