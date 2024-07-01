package com.i2i.sms.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.model.Club;
import com.i2i.sms.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
*
*This class implemented to store, collect, search and remove the club details.
*
*/
@Repository
public class ClubDao {

    private static final Logger logger = LogManager.getLogger(ClubDao.class);
   /**
    * <p>
    * Add the club details.
    * </p>
    *
    * @param club
    *        club details which contains club Id, name, president, website, count.
    * @throws StudentException if unable to add the club details.
    * @return Boolean value as true if it adds the club detail
    */
    public boolean insertClub(Club club) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            transaction = session.beginTransaction(); 
            session.save(club);
            transaction.commit();
            logger.info("Club added successfully of club Id {}", club.getId());
            return true;
        } catch (Exception e) { 
            if (transaction != null || transaction.isActive()) {
               transaction.rollback();
            }
            throw new StudentException("Unable to add the club details with club Id " + club.getId(), e);
        } 
    }
 
   /**
    *
    * <p>
    * It gets club if it already exist
    * </p>
    *
    * @param clubName
    *        Name of the club.
    * @throws StudentException if unable get the club.
    * @return Club if it already exist or else null 
    */
    public Club getClubIfAlreadyExist(String clubName) {
        Club club = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {  
            Query query = session.createQuery("from Club where name = :clubName");
            query.setParameter("clubName", clubName);
            club = (Club)query.uniqueResult(); 
            return club;
        } catch (Exception e) { 
            throw new StudentException("Unable to get the club with name " + clubName, e);
        } 
    }

   /**
    *
    * <p>
    * It gets all the clubs which contains the information such as club id, name, president, website, count.
    * </p>
    *
    * @throws StudentException if unable to display the club details
    * @return List of clubs which contains the information such as club id, name, president, website, count if club presents
              Or else null
    */
    public List<Club> fetchAllClubs() {
        List<Club> clubs = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Club");
            clubs = query.list();
            return clubs;
        } catch (Exception e) { 
            throw new StudentException("Unable to display the club details", e);
        } 
    }

   /**
    * <p>
    * It displays all the students of particular club with student roll No, name, mark, dob.
    * </p>
    *
    * @param clubId
    *        Id of the club passed as integer.
    * @return Set of students which contains the information such as roll No, name, mark, dob if students present
              Or else null
    */
    public Set<Student> displayAllStudentsOfClub(int clubId) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            Club club = session.get(Club.class, clubId);
            return club.getStudents();
        } catch(Exception e) { 
            throw new StudentException("Unable to display the students of club with id " + clubId, e);
        } 
    }

   /**
    *
    * <p>
    * Updates the club count, either increased by one or decreased by one
    * </p>
    *
    * @param clubId
    *        Club Id of the grade passed that we want to update the count.
    * @param isIncrement
    *        It increases grade count by 1 if pass 1.
    *        It decreases grade count by 1 if pass 0.
    * @throws StudentException if unable to update the count of the club.
    * @return Boolean value as true if it updates the count of club or else false
    *         Boolean value as false if we pass second parameter other than 0 or 1
    */
    public boolean updateClubCount(int clubId, boolean isIncrement) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            transaction = session.beginTransaction(); 
            Club club = session.get(Club.class, clubId);  
           
            if (isIncrement == true) {
                club.setCount(club.getCount() + 1);
                logger.debug("Club Count Increased by one, count = {} where club Id = {}", club.getCount(), club.getId());
            } else if (isIncrement == false) {
                club.setCount(club.getCount() - 1);
                logger.debug("Club Count decreased by one, count = {} where club Id = {}", club.getCount(), club.getId());
            } else {
                logger.debug("Illegal argument passed for isIncrement {}", isIncrement);
                return false;
            }
            session.update(club);         
            transaction.commit();
            logger.info("Update the club count whose club Id {} ", clubId);
            return true;
        } catch (Exception e) { 
            if(transaction != null || transaction.isActive()) {
               transaction.rollback();
            }
            throw new StudentException("Unable to update the club count with id " + clubId, e);
        } 
    }

    /**
    *
    * <p>
    * It gets Club with given club Id.
    * </p>
    *
    * @param clubId
    *        Club Id passed as integer.
    * @throws StudentException if unable to get the club with the given Id.
    * @return Club which contains the information such as Id, name, oresident, website, count
    */
    public Club getClubById(int clubId) {
        Club club = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            club = session.get(Club.class, clubId);
            return club;
        } catch (Exception e) { 
            throw new StudentException("Unable to get the club with id " + clubId, e);
        } 
    }
}