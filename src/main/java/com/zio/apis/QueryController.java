package com.zio.apis;

import com.zio.data.dto.DayPlan;
import com.zio.data.dto.IdNameDTO;
import com.zio.service.PlanService;
import com.zio.service.QueryService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    QueryService queryService;

    @GetMapping("ingred/{name}")
    ResponseEntity<List<IdNameDTO>> getIngredsLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getIngredsLike(name));
    }

    @GetMapping("recipe/{name}")
    ResponseEntity<List<IdNameDTO>> getRecipeLike(@PathVariable String name) {
        return ResponseEntity.ok(queryService.getRecipeLike(name));
    }


}
