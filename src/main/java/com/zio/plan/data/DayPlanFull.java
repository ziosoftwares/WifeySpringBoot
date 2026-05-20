package com.zio.plan.data;

import com.zio.common.data.GeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayPlanFull {
    private Integer day;
    private List<GeneralDTO>
            breakfast = new ArrayList<>(),
            lunch = new ArrayList<>(),
            dinner = new ArrayList<>();  //recipe ids


}
