package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class Project extends Model {

    @Id
    public Long id;
    public String companyId;
    public String projectName;
    public Date startDate;
    public Date endDate;
    public Boolean	billable;
    public Boolean defaultProject;
    public Boolean active;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<User> members = new ArrayList<User>();

    public Project(String companyId, String projectName, Date startDate, Date endDate, boolean billable, boolean defaultProject, boolean active, User owner) {
    	this.companyId = companyId;
    	this.projectName = projectName;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.billable = billable;
    	this.defaultProject = defaultProject;
    	this.active = active;
        this.members.add(owner);
    }
    
    /**
     * Query projects.
     */
    public static Model.Finder<Long,Project> find = new Model.Finder(Long.class, Project.class);

    /**
     * Create a new project.
     */
    public static Project create(String companyId, String projectName, Date startDate, Date endDate, boolean billable, boolean defaultProject, boolean active, String owner) {
        Project project = new Project(companyId, projectName, startDate, endDate, billable, defaultProject, active, User.findByEmail(owner));
        project.save();
        project.saveManyToManyAssociations("members");
        return project;
    }
    
    /**
     * Rename a project
     */
    public static String rename(Long projectId, String newName) {
        Project project = find.ref(projectId);
        project.projectName = newName;
        project.update();
        return newName;
    }

    /**
     * Retrieve project for user
     */
    public static List<Project> findInvolving(String user) {
        return find.where()
            .eq("members.email", user)
            .findList();
    }
    
    /**
     * Retrieve project for Company
     */
    public static List<Project> findInvolvingByCompany(String companyId) {
        return find.where()
            .eq("companyId", companyId)
            .findList();
    }
    
    /**
     * Add a member to this project
     */
    public static void addMember(Long project, String user) {
        Project p = Project.find.setId(project).fetch("members", "email").findUnique();
        p.members.add(
            User.find.ref(user)
        );
        p.saveManyToManyAssociations("members");
    }
    
    /**
     * Remove a member from this project
     */
    public static void removeMember(Long project, String user) {
        Project p = Project.find.setId(project).fetch("members", "email").findUnique();
        p.members.remove(
            User.find.ref(user)
        );
        p.saveManyToManyAssociations("members");
    }
    
    /**
     * Check if a user is a member of this project
     */
    public static boolean isMember(Long project, String user) {
        return find.where()
            .eq("members.email", user)
            .eq("id", project)
            .findRowCount() > 0;
    } 
}