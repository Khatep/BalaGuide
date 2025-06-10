package kz.balaguide.education_center_module.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;

@Data
@Builder
public class MonthlyRevenueDTO {
    private YearMonth month;
    private Double revenue;
}

