package co.com.juanjogv.lms.infrastructure.task;

import co.com.juanjogv.lms.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class OverdueNotificationTask {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 8-18 * * *")
    public void notifyOverdueBooks(){
        notificationService.notifyOverdueBooks();
    }
}
