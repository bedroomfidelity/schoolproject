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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.*;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class User implements Serializable {

    private Long userID;
    private String username;
    private String password;
    private String title;
    private String email;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String address;
    private String image;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
    private List<Shift> shifts;
    private List<Task> tasks;
    private List<Notification> notifications;

    public User() {
    }

    public User(String username, String password, String title, String email, String phoneNumber,
            String firstname, String lastname, String image) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
    }

    public User(String username, String password, String title, String email, String phoneNumber,
            String firstname, String lastname, String address, String image) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.image = image;
    }

    @Id
    @GeneratedValue
    @Column(name = "userID")
    @XmlElement
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "username", nullable = false, unique = true)
    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "title", nullable = false)
    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "email", nullable = false, unique = true)
    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone", nullable = false)
    @XmlElement
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "firstname", nullable = false)
    @XmlElement
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", nullable = false)
    @XmlElement
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "address")
    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToMany(mappedBy = "user")
    @XmlTransient
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @OneToMany(mappedBy = "sender")
    @Cascade({CascadeType.SAVE_UPDATE})
    @XmlTransient
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    @OneToMany(mappedBy = "receiver")
    @Cascade({CascadeType.SAVE_UPDATE})
    @XmlTransient
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @OneToMany(mappedBy = "shiftID")
    @Cascade({CascadeType.ALL})
    @XmlTransient
    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    @ManyToMany
    @JoinTable(name = "user_task", joinColumns = {
        @JoinColumn(name = "worker", referencedColumnName = "username")}, inverseJoinColumns = {
        @JoinColumn(name = "task", referencedColumnName = "taskID")})
    @Cascade({CascadeType.ALL})
    @XmlTransient
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.userID);
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
        final User other = (User) obj;
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        return true;
    }

}
