package org.example.todo.DAO;

import org.example.todo.Entity.Task;
import org.example.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskDAO extends JpaRepository<Task,Integer> {
    @Query(value = "SELECT * from Task t WHERE t.status=:status and t.user_id=:userid",nativeQuery = true)
    public List<Task> findAllTasksByStatus(@Param("status") String status,@Param("userid")long userid);

    @Query(value ="SELECT * from Task t WHERE t.user_id=:user_id",nativeQuery = true )
    public List<Task>findAllByUser(@Param("user_id") Long user_id);

    @Query(value ="SELECT * from Task t WHERE t.user_id=:userid and t.id=:taskid",nativeQuery = true)
    public Optional<Task> findAllByTaskIdAndUserId(@Param("taskid") int id,@Param("userid") long userid);
}
