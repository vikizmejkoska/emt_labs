package mk.ukim.finki.emtlab.web;

import mk.ukim.finki.emtlab.dto.CreateAuthorDto;
import mk.ukim.finki.emtlab.dto.DisplayAuthorDto;
import mk.ukim.finki.emtlab.model.projections.AuthorProjection;
import mk.ukim.finki.emtlab.model.views.AuthorsPerCountryView;
import mk.ukim.finki.emtlab.service.application.AuthorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Controller", description = "API for managing authors")
public class AuthorController {

    private final AuthorApplicationService authorApplicationService;

    public AuthorController(AuthorApplicationService authorApplicationService) {
        this.authorApplicationService = authorApplicationService;
    }


    @GetMapping()
    @Operation(summary = "Get all authors", description = "Retrieves a list of all available authors")
    public ResponseEntity<List<DisplayAuthorDto>> findAll() {
        return ResponseEntity.ok(authorApplicationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by id", description = "Retrieve country by author")
    public ResponseEntity<DisplayAuthorDto> findById(@PathVariable Long id){
        return this.authorApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new author", description = "Create and store a new author")
    public ResponseEntity<DisplayAuthorDto> save(@RequestBody CreateAuthorDto createAuthorDto) {
//        return this.authorApplicationService.save(createAuthorDto)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());

        Optional<DisplayAuthorDto> displayAuthorDtoOptional = authorApplicationService.save(createAuthorDto);
        return displayAuthorDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit author", description = "Edit an existing author by id")
    public ResponseEntity<DisplayAuthorDto> update(@PathVariable Long id, @RequestBody CreateAuthorDto createAuthorDto) {
        return this.authorApplicationService.update(id, createAuthorDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete author", description = "Delete author by id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (authorApplicationService.findById(id).isPresent()) {
            authorApplicationService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<AuthorsPerCountryView>> findAuthorsPerCountry() {
        return ResponseEntity.ok(authorApplicationService.findAuthorsPerCountry());
    }

    @GetMapping("/names")
    public ResponseEntity<List<AuthorProjection>> findAllByNameAndSurname() {
        return ResponseEntity.ok(authorApplicationService.findAllByNameAndSurname());
    }
}
