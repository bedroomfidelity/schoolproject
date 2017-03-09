/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class Comment implements Serializable {

    private Long commentID;
    private String comment;
    private User commenter;
    private Task onTask;
    private Notification notifications;

    public Comment() {
    }

    public Comment(String comment, User commenter, Task onTask) {
        this.comment = comment;
        this.commenter = commenter;
        this.onTask = onTask;
    }

    @Id
    @GeneratedValue
    @Column(name = "commentID")
    @XmlElement
    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    @Lob
    @Size(max = 65535)
    @Column(name = "comment", nullable = false)
    @XmlElement
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "commenter", referencedColumnName = "username", nullable = false)
    @XmlElement
    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    @ManyToOne
    @JoinColumn(name = "onTask", referencedColumnName = "taskID", nullable = false)
    @XmlElement
    public Task getOnTask() {
        return onTask;
    }

    public void setOnTask(Task onTask) {
        this.onTask = onTask;
    }

    @OneToOne(mappedBy = "comment")
    @XmlTransient
    public Notification getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification notifications) {
        this.notifications = notifications;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.commentID);
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
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.commentID, other.commentID)) {
            return false;
        }
        return true;
    }

}
