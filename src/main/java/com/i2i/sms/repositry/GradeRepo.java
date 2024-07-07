package com.i2i.sms.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.sms.model.Grade;

@Repository
public interface GradeRepo extends JpaRepository<Grade, String> {
    Grade findByStandardAndSection(int standard, Character section);
}
