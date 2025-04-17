package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.NotificationResponseDto;
import org.fatec.esportiva.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApiController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(){
        List<NotificationResponseDto> notificationResponseDtos = notificationService.getNotifications();
        return ResponseEntity.ok(notificationResponseDtos);
    }

    @PatchMapping("/viewed")
    public  ResponseEntity<?> patchNotifications(@RequestBody List<Long> ids){
        notificationService.markAsViewed(ids);
        return ResponseEntity.ok().build();
    }
}
