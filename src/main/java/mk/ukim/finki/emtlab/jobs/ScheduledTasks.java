package mk.ukim.finki.emtlab.jobs;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emtlab.service.domain.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final BookService bookService;


    @Scheduled(cron = "0 * * * * *")
    public void refreshMaterializedView() {
        this.bookService.refreshMaterializedView();
    }


}



