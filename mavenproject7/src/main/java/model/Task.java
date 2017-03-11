/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class Task implements Serializable {

    private Long taskID;
    private String taskname;
    private String description;
    private Date startdate;
    private Date deadline;
    private boolean done;
    private List<User> workers;
    private List<Comment> comments;
    private List<Notification> notifications;

    public Task() {
    }

    public Task(String taskname, String description, Date startdate, Date deadline, boolean done) {
        this.taskname = taskname;
        this.description = description;
        this.startdate = startdate;
        this.deadline = deadline;
        this.done = done;
    }

    @Id
    @GeneratedValue
    @Column(name = "taskID")
    @XmlElement
    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    @Column(name = "taskname", nullable = false)
    @XmlElement
    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    @Lob
    @Size(max = 65535)
    @Column(name = "description", nullable = false)
    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type = "timestamp")
    @Column(name = "startdate", nullable = false)
    @XmlElement
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type = "timestamp")
    @Column(name = "deadline", nullable = false)
    @XmlElement
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Column(name = "done", nullable = false)
    @XmlElement
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @ManyToMany(mappedBy = "username")
    @Cascade(CascadeType.ALL)
    @XmlTransient
    public List<User> getWorkers() {
        return workers;
    }

    public void setWorkers(List<User> workers) {
        this.workers = workers;
    }

    @OneToMany(mappedBy = "onTask")
    @Cascade(CascadeType.ALL)
    @XmlTransient
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "task")
    @XmlTransient
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.taskID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (!Objects.equals(this.taskID, other.taskID)) {
            return false;
        }
        return true;
    }

}
