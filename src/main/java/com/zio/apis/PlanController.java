package com.zio.apis;

import com.zio.util.ZioException;
import com.zio.data.dto.DayPlan;
import com.zio.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("plan")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("{days}/{userId}")
    public ResponseEntity<List<DayPlan>> makePlanForDays(Integer days, Long userId) throws ZioException {
        return ResponseEntity.ok(planService.makePlanForDays(days, userId));
    }
}
