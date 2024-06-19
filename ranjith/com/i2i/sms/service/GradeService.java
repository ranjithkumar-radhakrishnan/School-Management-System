package com.i2i.sms.service;

import com.i2i.sms.dao.GradeDao;
import com.i2i.sms.model.Grade;

/**
*
* Class implemented to store, retrieve and update the grade details.
* 
*/

public class GradeService {

    private GradeDao gradeDao = new GradeDao();
    
   /**
    * <p>
    * Get the grade detail with given standard and section.
    * </p>
    *
    * @param standard
    *        grade of student as int.
    * @param section
    *        section of student as character
    * @throws StudentException if the grade not found with given standard and section.
    * @return Grade detail if present or else null
    */
    public Grade getGradeWithStandardAndSection(int standard, char section) {
        return gradeDao.getGradeWithStandardAndSection(standard, section);
    }

   /**
    * <p>
    * Updates the grade count, either increased by one or decreased by one
    * </p>
    *
    * @param gradeId
    *        Grade Id of the grade passed that we want to update the count.
    * @param isIncrement
    *        It increases grade count by 1 if we pass boolean as true.
    *        It decreases grade count by 1 if we pass boolean as false.
    * @throws StudentException if unable to update the count of the grade.
    * @return Boolean value as true if it updates the count or else false
    *         Boolean value as false if we pass second parameter other than true or false
    */
    public boolean updateCountOfGrade(int gradeId, boolean isIncrement) {
        return gradeDao.updateGradeCount(gradeId, isIncrement);
    }
}