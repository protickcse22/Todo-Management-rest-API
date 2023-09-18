package com.protick.todomanagementrestapi.dto.todo;

public record TaskRequestDto(String title, String description, boolean isCompleted) {
}
