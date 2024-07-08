package com.i2i.sms.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
* This class represents a club which contains the information such as name, president, website, count.
*/
@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "club_id")
    private String id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "club_president")
    private String president;

    @Column(name = "club_website")
    private String website;

    @ManyToMany(mappedBy = "clubs", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
  
    public void setPresident(String president) {
        this.president = president;
    }
    public String getPresident() {
        return president;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public String getWebsite() {
        return website;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    public Set<Student> getStudents() {
        return students;
    }

}