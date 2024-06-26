package com.i2i.sms.model;

import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private int id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "club_president")
    private String president;

    @Column(name = "club_website")
    private String website;

    @Column(name = "club_count")
    private int count;

    @ManyToMany(mappedBy = "clubs", fetch = FetchType.LAZY)
    private Set<Student> students;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    public Set<Student> getStudents() {
        return students;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\tClub Id: ").append(id)
                     .append("\n\t\tClub Name: ").append(name)
                     .append("\n\t\tClub President: ").append(president)
                     .append("\n\t\tClub website:").append(website)
                     .append("\n\t\tcount: ").append(count);
        return stringBuilder.toString();
    }
}