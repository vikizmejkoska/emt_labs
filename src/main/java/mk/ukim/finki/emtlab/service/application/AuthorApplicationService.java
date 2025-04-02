package mk.ukim.finki.emtlab.service.application;

import mk.ukim.finki.emtlab.dto.CreateAuthorDto;
import mk.ukim.finki.emtlab.dto.DisplayAuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorApplicationService {
    List<DisplayAuthorDto> findAll();
    Optional<DisplayAuthorDto> findById(Long id);
    Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto author);
    Optional<DisplayAuthorDto> save(CreateAuthorDto author);
    void delete(Long id);
}
