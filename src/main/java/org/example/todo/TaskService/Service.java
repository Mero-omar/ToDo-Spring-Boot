package org.example.todo.TaskService;

import lombok.RequiredArgsConstructor;
import org.example.todo.CustomException.TaskNotFoundException;
import org.example.todo.DAO.TaskDAO;
import org.example.todo.DAO.UserDAO;
import org.example.todo.Entity.Task;
import org.example.todo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service implements ServiceInter{

   private final TaskDAO taskDAO;
   private final UserDAO userDAO;




    @Override
    public List<Task> getTasks(String currentUserEmail) {


//        userDAO.findByEmail("");
        User user=userDAO.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        Long user_id=user.getId();
        List<Task>AllTasks=taskDAO.findAllByUser(user_id);
        if(AllTasks.isEmpty()) {
            throw new TaskNotFoundException("There is no Tasks for this mail ");
        }
        CreateFile("AllTasks.txt",AllTasks);
        return AllTasks;
    }

    @Override
    public void AddTask(Task task,String currentUserEmail) {
        User user=userDAO.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        task.setUser(user);
        taskDAO.save(task);
    }


    @Override
    public Optional<Task> findbyId(int id,String currentUserEmail){
        User user=userDAO.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        Optional<Task> resTask=taskDAO.findAllByTaskIdAndUserId(id,user.getId());
        if(resTask.isEmpty()) {
            throw new TaskNotFoundException("There is no Tasks for this mail ");
        }
        CreateFile("IDTask.txt",resTask);
        return resTask;
    }


    @Override
    public void DeleteTask(int id,String currentUserEmail ){
        Optional<Task>restask=findbyId(id,currentUserEmail);
        if(restask.isEmpty()){
            throw new TaskNotFoundException("There is no Task With id "+id+" To delete");
        }

        taskDAO.deleteById(id);

    }


    @Override
    public List<Task> findTaskbyStatus(String status,String currentUserEmail){
        User user=userDAO.findByEmail(currentUserEmail).orElseThrow(() ->new UsernameNotFoundException("There is no user with mail"+currentUserEmail) );
        List<Task>resTask=taskDAO.findAllTasksByStatus(status,user.getId());
        if(resTask.isEmpty()) {
            throw new TaskNotFoundException("There is no Tasks for this mail ");
        }
        CreateFile("StatusTask.txt",resTask);
        return resTask;
    }



    //General Function to Create file and write
    public <T> void CreateFile(String Filename,T Value){
        try (BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(Filename,true) )){
            bufferedWriter.write(String.valueOf(Value));
            bufferedWriter.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
