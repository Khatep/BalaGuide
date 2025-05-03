package kz.balaguide.common_module.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
public class Schedule extends AbstractEntity {
    private LocalTime startTime;
    //9:00

    private LocalTime endTime;
    //10:00

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    //M

    private String timeZone; //Asia/Almaty

}
