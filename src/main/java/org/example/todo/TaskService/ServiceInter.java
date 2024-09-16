package org.example.todo.TaskService;

import org.example.todo.Entity.Task;

import java.util.List;
import java.util.Optional;

public interface ServiceInter {
    public List<Task> getTasks(String currentUserEmail);
    public void AddTask(Task task,String currentUserEmail);
    public Optional<Task> findbyId(int id,String currentUserEmail);
    public void DeleteTask(int id,String currentUserEmail);
    public List<Task> findTaskbyStatus(String status,String currentUserEmail);
}
