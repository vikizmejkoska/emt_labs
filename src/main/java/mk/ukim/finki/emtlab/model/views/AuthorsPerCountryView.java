package mk.ukim.finki.emtlab.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Immutable
@Entity
@Subselect("select * from public.authors_per_country")
public class AuthorsPerCountryView {

    @Id
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "num_authors")
    private Integer numAuthors;

    public AuthorsPerCountryView() {
    }

    public AuthorsPerCountryView(Long countryId, Integer numAuthors) {
        this.countryId = countryId;
        this.numAuthors = numAuthors;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Integer getNumAuthors() {
        return numAuthors;
    }

    public void setNumAuthors(Integer numAuthors) {
        this.numAuthors = numAuthors;
    }
}
