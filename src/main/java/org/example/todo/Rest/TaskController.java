
package org.example.todo.Rest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.todo.AuthService.AuthenticationService;
import org.example.todo.Entity.Task;
import org.example.todo.TaskService.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final Service service;

    @GetMapping("/Tasks")
    public List<Task> GetTasks(){
        String currentUserEmail = getCurrentUserEmail();
        return service.getTasks(currentUserEmail);
    }


    @PostMapping("/Add")
    public void addTask(@RequestBody Task task){
        task.setId(0);
        String currentUserEmail = getCurrentUserEmail();
        service.AddTask(task,currentUserEmail);
    }


    @GetMapping("/Task/{id}")
    public Optional<Task> findbyId(@PathVariable int id){
        String currentUserEmail = getCurrentUserEmail();
        return service.findbyId(id,currentUserEmail);
    }


    @DeleteMapping("/Delete/{id}")
    public void DeleteTask(@PathVariable int id){
        String currentUserEmail = getCurrentUserEmail();
        service.DeleteTask(id,currentUserEmail);
    };


    @GetMapping("Tasks/status/{status}")
    public List<Task> findTaskbyStatus(@PathVariable String status){
        String currentUserEmail = getCurrentUserEmail();
       return service.findTaskbyStatus(status,currentUserEmail);
    }



    //Helper Function to extract current user (Logged-in user)
    private String getCurrentUserEmail() {
        Object princeple= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (princeple instanceof UserDetails){
            return ((UserDetails) princeple).getUsername();
        }else {
            return princeple.toString();
        }
    }

}
