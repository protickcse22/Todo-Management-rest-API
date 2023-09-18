package com.protick.todomanagementrestapi.repository;

import com.protick.todomanagementrestapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByUsername(String username);
}
