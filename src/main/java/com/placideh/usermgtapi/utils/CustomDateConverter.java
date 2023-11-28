package com.placideh.usermgtapi.utils;

import com.placideh.usermgtapi.Dto.TaskDto;
import com.placideh.usermgtapi.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
@Component
public class CustomDateConverter {
    public static Task convertTaskDtoToSchedule(TaskDto taskDto){
        LocalTime startTime = LocalTime.parse(taskDto.getStartTime());
        LocalTime endTime = LocalTime.parse(taskDto.getEndTime());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        LocalDateTime startDate = LocalDate.parse(taskDto.getStartDate(), dateFormatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(taskDto.getEndDate(), dateFormatter).atStartOfDay();
        return Task.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
