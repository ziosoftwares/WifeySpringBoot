package com.zio.plan.controller;

import com.zio.plan.data.entity.Plan;
import com.zio.common.util.ZioException;
import com.zio.plan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Plan> makePlanForDays(@PathVariable Integer days) throws ZioException {
        if (days > 7) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(planService.makePlanForDays(days));
    }

    @PostMapping("publish")
    public ResponseEntity<Long> publishPlan(@RequestBody Plan plan) throws ZioException {
        return new ResponseEntity<>(planService.savePlan(plan), HttpStatus.CREATED);
    }
}
