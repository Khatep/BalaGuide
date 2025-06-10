package kz.balaguide.education_center_module.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;

@Data
@Builder
public class MonthlyChildrenGrowthDTO {
    private YearMonth month;
    private Integer childrenCount;
}
