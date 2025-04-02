package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.CreateBookDto;
import mk.ukim.finki.emtlab.dto.DisplayBookDto;
import mk.ukim.finki.emtlab.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<DisplayBookDto> findAll();
    Optional<DisplayBookDto> findById(Long id);
    Optional<DisplayBookDto> save(CreateBookDto book);
    Optional<DisplayBookDto> update(Long id, CreateBookDto book);
    void delete(Long id);

}
