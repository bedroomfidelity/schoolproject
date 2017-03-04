/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class Notification implements Serializable {

    private Long notificationID;
    private User user;
    private boolean viewed;
    private String type;
    private Task task;
    private Message message;
    private Comment comment;
    private Shift shift;

    public Notification(User user, Task task, String type) {
        this.user = user;
        this.task = task;
        this.viewed = false;
        this.type = type;
    }

    public Notification(User user, Message message, String type) {
        this.user = user;
        this.message = message;
        this.viewed = false;
        this.type = type;
    }

    public Notification(User user, Comment comment, String type) {
        this.user = user;
        this.comment = comment;
        this.viewed = false;
        this.type = type;
    }

    public Notification(User user, Shift shift, String type) {
        this.user = user;
        this.shift = shift;
        this.viewed = false;
        this.type = type;
    }
    
    public Notification(User user, String type) {
        this.user = user;
        this.viewed = false;
        this.type = type;
    }
    
    @Id
    @GeneratedValue
    @XmlElement
    @Column(name = "notificationID")
    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username", nullable = false)
    @XmlElement
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlElement
    @Column(name = "viewed")
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @ManyToOne
    @JoinColumn(name = "task", referencedColumnName = "taskID", nullable = true)
    @XmlElement
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @OneToOne
    @JoinColumn(name = "message", referencedColumnName = "messageID", nullable = true)
    @XmlElement
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @OneToOne
    @JoinColumn(name = "comment", referencedColumnName = "commentID", nullable = true)
    @XmlElement
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "shiftID", nullable = true)
    @XmlElement
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Column(name = "type", nullable = false)
    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.notificationID);
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
        final Notification other = (Notification) obj;
        if (!Objects.equals(this.notificationID, other.notificationID)) {
            return false;
        }
        return true;
    }

}
