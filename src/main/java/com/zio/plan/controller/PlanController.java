package com.zio.plan.controller;

import com.zio.plan.data.DayPlanDTO;
import com.zio.plan.data.DayPlanFull;
import com.zio.plan.data.PlanDTO;
import com.zio.plan.data.PlanFull;
import com.zio.plan.data.entity.Plan;
import com.zio.common.util.ZioException;
import com.zio.plan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("days/{days}")
    public ResponseEntity<List<DayPlanFull>> makePlanForDays(@PathVariable Integer days) throws ZioException {
        if (days > 7) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(planService.makePlanForDays(days));
    }

    @PostMapping("publish")
    public ResponseEntity<Void> publishPlan(@RequestParam("title") String title, @RequestBody List<DayPlanDTO> dayPlans) throws ZioException {
        planService.savePlan(dayPlans, title);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<PlanDTO>> getAllPlans() throws ZioException {
        return new ResponseEntity<>(planService.getAllPlans(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlanFull> getPlan(@PathVariable Long id) throws ZioException {
        return new ResponseEntity<>(planService.getPlan(id), HttpStatus.OK);
    }

}
