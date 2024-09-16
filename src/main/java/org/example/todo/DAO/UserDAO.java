package org.example.todo.DAO;

import org.example.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User,Integer> {

    @Query(value = "select * from User t where t.email= :email ",nativeQuery = true)
    public Optional<User> findByEmail(@Param("email") String email);



}
