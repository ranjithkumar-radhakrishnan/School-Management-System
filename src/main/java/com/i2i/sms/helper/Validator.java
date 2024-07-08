package com.i2i.sms.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.i2i.sms.dto.AddressRequestDto;
import com.i2i.sms.dto.UpdateGradeDto;
import com.i2i.sms.dto.UpdateStudentDto;
import com.i2i.sms.utils.CommonUtil;
import com.i2i.sms.utils.DateUtil;

@Component
public class Validator {
    /**
     * <p>
     * Validates the Student information such as name, mark, dob
     * Student information are gathered by in the form of updateStudentDto
     * </p>
     *
     * @param updateStudentDto {@link UpdateStudentDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    public ResponseEntity<?> validRequest(UpdateStudentDto updateStudentDto) {
        if (CommonUtil.isValidString(updateStudentDto.getName())) {
            if (CommonUtil.isValidRangeOfNumber(updateStudentDto.getMark(), 0, 100)) {
                if (null != DateUtil.validateDateFormat(updateStudentDto.getDob().toString())) {
                    int age = DateUtil.getDifferenceBetweenDateByYears(updateStudentDto.getDob(), null);
                    if (CommonUtil.isValidRangeOfNumber(age, 6, 17)) {
                        return validateAddressRequest(updateStudentDto);
                    } else {
                        return new ResponseEntity<>("Student age not valid for the grade, Invalid age: " + age, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Invalid Date provided, it should be in format yyyy-MM-dd", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Invalid Mark range, it should be between 0 to 100", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid Student Name " + updateStudentDto.getName(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * <p>
     * Validates the Address of the student
     * Student information are gathered by in the form of updateStudentDto
     * </p>
     *
     * @param updateStudentDto {@link UpdateStudentDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    public ResponseEntity<?> validateAddressRequest(UpdateStudentDto updateStudentDto) {
        AddressRequestDto addressRequestDto = updateStudentDto.getAddress();
        if (CommonUtil.isValidString(addressRequestDto.getStreet())) {
            if (CommonUtil.isValidString(addressRequestDto.getCity())) {
                if (CommonUtil.isValidString(addressRequestDto.getState())) {
                    if (CommonUtil.isValidNumber(Integer.toString(addressRequestDto.getPincode()))) {
                        return validateGradeRequest(updateStudentDto.getGrade());
                    } else {
                        return new ResponseEntity<>("Invalid Pincode " + addressRequestDto.getPincode(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Invalid State Name " + addressRequestDto.getState(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Invalid City name: " + addressRequestDto.getCity(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid Street name: " + addressRequestDto.getStreet(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * <p>
     * Validates the grade of the student
     * Grade information are gathered by in the form of updateGradeDto
     * </p>
     *
     * @param updateGradeDto {@link UpdateGradeDto}
     * @return ResponseEntity as null if validated or else send Http bad request
     */
    public ResponseEntity<?> validateGradeRequest(UpdateGradeDto updateGradeDto) {
        if (CommonUtil.isValidRangeOfNumber(updateGradeDto.getStandard(), 1, 12)) {
            if (CommonUtil.isUppercaseCharacter(updateGradeDto.getSection())) {
                return null;
            } else {
                return new ResponseEntity<>("Invalid Section, it should be uppercase character: " + updateGradeDto.getSection(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid Standard, it should be range from 1 to 12: " + updateGradeDto.getStandard(), HttpStatus.BAD_REQUEST);
        }
    }
}
