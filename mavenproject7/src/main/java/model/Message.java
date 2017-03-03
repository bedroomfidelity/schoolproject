/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class Message implements Serializable {
    private Long messageID;
    private String content;
    private User sender;
    private User receiver;
    private String file;
    private boolean read;
    private Date sentdate;
    
    public Message(){}

    public Message(String content, User sender, User receiver, boolean read, Date sentdate) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.read = read;
        this.sentdate = sentdate;
    }

    public Message(String content, User sender, User receiver, String file, boolean read, Date sentdate) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.file = file;
        this.read = read;
        this.sentdate = sentdate;
    }
    
    @Id
    @GeneratedValue
    @Column(name="messageID")
    @XmlElement
    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }
    
    
    @Lob
    @Size(max = 65535)
    @Column(name = "content",nullable = false)
    @XmlElement
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @ManyToOne
    @JoinColumn(name = "sender",referencedColumnName = "username",nullable = false)
    @XmlElement
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver",referencedColumnName = "username",nullable = false)
    @XmlElement
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Column(name = "file")
    @XmlElement
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    @Column(name = "isread",nullable=false)
    @XmlElement
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    @Column(name = "sentdate",nullable=false)
    @Temporal(TemporalType.TIME)
    @XmlElement
    public Date getSentdate() {
        return sentdate;
    }

    public void setSentdate(Date sentdate) {
        this.sentdate = sentdate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.messageID);
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
        final Message other = (Message) obj;
        if (!Objects.equals(this.messageID, other.messageID)) {
            return false;
        }
        return true;
    }
    
    
    
}
