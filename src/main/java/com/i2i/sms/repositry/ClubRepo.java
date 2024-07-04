package com.i2i.sms.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.sms.model.Club;

@Repository
public interface ClubRepo extends JpaRepository<Club,Integer> {
}
