package com.zio.plan.repo;

import com.zio.plan.data.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepo extends JpaRepository<Plan, Long> {
}
