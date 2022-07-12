package ru.myrating.application.web.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.myrating.application.web.websocket.dto.ReportDTO;

@Controller
public class ActivityService {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    @MessageMapping("/test")
    @SendTo("/topic/result")
    public ReportDTO sendTest(String string) {
        ReportDTO reportDTO = new ReportDTO(1L, "test");
        log.debug("Sending test tracking data {}", reportDTO);
        return reportDTO;
    }
}
