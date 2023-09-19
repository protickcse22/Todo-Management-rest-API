package com.protick.todomanagementrestapi.dto.todo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskResponseDto {
    private Integer id;
    private String title;
    private String description;
    private boolean isCompleted;
}
