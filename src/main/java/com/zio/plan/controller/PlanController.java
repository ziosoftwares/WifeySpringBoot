package com.zio.plan.controller;

import com.zio.data.dto.DayPlanDTO;
import com.zio.util.ZioException;
import com.zio.plan.service.PlanService;
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

    @GetMapping("days/{days}")
    public ResponseEntity<List<DayPlanDTO>> makePlanForDays(Integer days) throws ZioException {
        return ResponseEntity.ok(planService.makePlanForDays(days).block());
    }

    @GetMapping("day")
    public ResponseEntity<DayPlanDTO> makeDayPlan() throws ZioException {
        return ResponseEntity.ok(planService.makePlanForDays(1).block().getFirst());
    }

}
