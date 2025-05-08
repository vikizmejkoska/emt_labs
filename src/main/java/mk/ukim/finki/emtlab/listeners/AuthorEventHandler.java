package mk.ukim.finki.emtlab.listeners;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.events.AuthorEvent;
import mk.ukim.finki.emtlab.service.domain.AuthorService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorEventHandler {
    private final AuthorService authorService;


    @EventListener(AuthorEvent.class)
    public void refreshMaterializedView() {
        authorService.refreshMaterializedView();
    }
}
