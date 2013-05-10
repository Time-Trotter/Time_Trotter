package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Task extends Model {

    @Id
    public Long id;
    public String title;
    @ManyToOne
    public User assignedTo;
    @ManyToOne
    public Project project;

    public static Model.Finder<Long,Task> find = new Model.Finder(Long.class, Task.class);

    public static List<Task> findTodoInvolving(String user) {
       return find.fetch("project").where()
                .eq("project.members.email", user)
           .findList();
    }

    public static Task create(Task task, Long project) {
        task.project = Project.find.ref(project);
        task.save();
        return task;
    }
}