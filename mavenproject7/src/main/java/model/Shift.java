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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author N5537
 */
@Entity
@XmlRootElement
public class Shift implements Serializable {
    
    private Long shiftID;
    private User employee;
    private Date starttime;
    private Date endtime;
    
    public Shift(){}

    public Shift(User employee, Date starttime, Date endtime) {
        this.employee = employee;
        this.starttime = starttime;
        this.endtime = endtime;
    }
    
    @Id
    @GeneratedValue
    @Column(name="shiftID")
    @XmlElement
    public Long getShiftID() {
        return shiftID;
    }

    public void setShiftID(Long shiftID) {
        this.shiftID = shiftID;
    }
    
    @ManyToOne
    @JoinColumn(name = "employee",referencedColumnName = "username",nullable = false)
    @XmlElement
    public User getEmployee() {
        return employee;
    }
    
    public void setEmployee(User employee) {
        this.employee = employee;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="starttime",nullable = false)
    @XmlElement
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="endtime",nullable = false)
    @XmlElement
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.shiftID);
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
        final Shift other = (Shift) obj;
        if (!Objects.equals(this.shiftID, other.shiftID)) {
            return false;
        }
        return true;
    }
    
}
