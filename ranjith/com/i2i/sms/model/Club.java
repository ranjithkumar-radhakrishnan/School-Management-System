package com.i2i.sms.model;

import java.util.Set;
import java.util.HashSet;

import com.i2i.sms.model.Student;
/**
* This class represents a club which contains the information such as name, president, website, count.
*/
public class Club {

    private int id;
    private String name;
    private String president;
    private String website;
    private int count;
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