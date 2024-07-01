package com.i2i.sms.dao;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.sms.exception.StudentException;
import com.i2i.sms.helper.HibernateConnection;
import com.i2i.sms.model.Grade;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
*
* Class implemented to store, retrieve and update the grade details.
* 
*/
@Repository
public class GradeDao {

    private static final Logger logger = LogManager.getLogger(GradeDao.class);
   /**
    * <p>
    * Get the grade with the given standard and section
    * </p>
    *
    * @param standard
    *        Grade of the student as Integer.
    * @param section
    *        Section of the grade as character.
    * @throws StudentException if unable to get the grade with given standard and section.
    * @return Grade which contains standard, section, count if present or else null
    */
    public Grade getGradeWithStandardAndSection(int standard, char section) {
        Grade grade = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Grade where standard = :standard and section = :section");
            query.setParameter("standard", standard);
            query.setParameter("section", section);
            grade = (Grade) query.uniqueResult();
            logger.debug("Get the grade if exist or else null {}", grade);
            return grade;
        } catch (Exception e) {
            throw new StudentException("Unable to get grade with standard " + standard + " and section" + section, e);
        } 
    }
    
   /**
    * <p>
    * Updates the grade count, either increased by one or decreased by one
    * </p>
    *
    * @param gradeId
    *        Grade Id of the grade passed that we want to update the count.
    * @param isIncrement
    *        It increases grade count by 1 if pass true.
    *        It decreases grade count by 1 if pass false.
    * @throws StudentException if unable to update the count of the grade.
    * @return Boolean value as true if it updates the count or else false
    *         Boolean value as false if we pass second parameter other than true or false
    */
    public boolean updateGradeCount(int gradeId, boolean isIncrement) {
        Transaction transaction = null; 
        try (Session session = HibernateConnection.getSessionFactory().openSession()) { 
            transaction = session.beginTransaction(); 
            Grade grade = session.get(Grade.class, gradeId);  
           
            if (isIncrement == true) {
                grade.setSectionCount(grade.getSectionCount()+1);
                logger.debug("Grade section Count Increased by one, Section count = {} where grade Id = {}", grade.getSectionCount(), gradeId);
            } else if (isIncrement == false) {
                grade.setSectionCount(grade.getSectionCount()-1);
                logger.debug("Grade section Count decreased by one, Section count = {} where grade Id = {}", grade.getSectionCount(), gradeId);
            } else {
                logger.debug("Illegal argument passed for isIncrement {}", isIncrement);
                return false;
            }
            session.update(grade);         
            transaction.commit();
            logger.info("Update the grade Count whose grade Id {}", gradeId);
            return true;
        } catch(Exception e) { 
            if (transaction != null || transaction.isActive()) {
                transaction.rollback();
            }
            throw new StudentException("Unable to update the count of grade with Id " + gradeId, e);
        } 
    }
    /**
     * <p>
     * Creates the grade with standard and section.
     * </p>
     *
     * @param grade
     *        Grade which contains the standard and section.
     * @throws StudentException if unable to create the grade.
     * @return Grade which contains standard and section
     */
    public Grade insertGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(grade);
            transaction.commit();
            logger.info("Insert the grade with Id {}", grade.getGradeId());
            return grade;
        } catch (Exception e) {
            if (null != transaction ) {
                transaction.rollback();
            }
            throw new StudentException("Unable to add the grade with standard " + grade.getStandard(), e);
        }
    }

}