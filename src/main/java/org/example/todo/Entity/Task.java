package org.example.todo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String Task;
    @Column
    private String Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", Task='" + Task + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
