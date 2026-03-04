package com.zio.apis;

import com.zio.data.dto.DayPlanDTO;
import com.zio.util.ZioException;
import com.zio.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("plan")
/***
 * Special controller where methods use sophisticated resources
 * restricted to only small user base
 */
public class PlanController {

    @Autowired
    private PlanService planService;

    private Long userId = 1L;

    @GetMapping("days/{days}")
    public ResponseEntity<List<DayPlanDTO>> makePlanForDays(Integer days) throws ZioException {
        userId = (Long) getRequestingUser();
        return ResponseEntity.ok(planService.makePlanForDays(days, userId).block());
    }

    @GetMapping("day")
    public ResponseEntity<DayPlanDTO> makeDayPlan() throws ZioException {
        userId = (Long) getRequestingUser();
        return ResponseEntity.ok(planService.makeDayPlan(userId).block());
    }

    private Object getRequestingUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }
}
