package com.placideh.usermgtapi.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NotNull
public class TaskDto {
    @NotBlank(message = "title can not be blank")
    @NotEmpty(message = "title must be entered")
    private String title;
    @NotBlank(message = "StartTime can not be blank")
    @NotEmpty(message = "StartTime source must be entered")
    private String startTime;

    @NotBlank(message = "EndTime can not be blank")
    @NotEmpty(message = "EndTime source must be entered")

    private String endTime;
    @NotBlank(message = "startDate can not be blank")
    @NotEmpty(message = "startDate must be entered")
    private String startDate;

    @NotBlank(message = "endDate can not be blank")
    @NotEmpty(message = "endDate must be entered")
    private String endDate;

    @NotBlank(message = "priority can not be blank")
    @NotEmpty(message = "priority must be entered")
    private String priority;

    @NotBlank(message = "Attachments can not be blank")
    @NotEmpty(message = "Attachments must be entered")
    private String attachments;
}
