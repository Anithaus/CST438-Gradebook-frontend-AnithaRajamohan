package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	/*
	 *  add a student to the system.With the input of the student email and name.
	 */
	@PostMapping("/student")
	@Transactional
	public Student addStudent(@RequestBody StudentDTO studentDTO) {
		System.out.println("/student called.");
		Student emailIdCheck = studentRepository.findByEmail(studentDTO.email);
		if(studentDTO != null && emailIdCheck == null){
			Student student = new Student();		
			student.setName(studentDTO.name);
			student.setEmail(studentDTO.email);
			Student saveStudent = studentRepository.save(student);
			return saveStudent;
		}else{
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "emailID is already exists ");
		}
		
	}
	
	/*
	 *  put a HOLD on a student's registration and release the hold
	 */
	
	@PutMapping("/student/{email}")
	@Transactional
	public void putHold( @PathVariable String email ) {
		System.out.println("/student/email called.");
		String holdReason ="Hold on regestration for delay on payment"; //reason for hold
		Student student = studentRepository.findByEmail(email);

		if(student != null) {
			
			//checking if StatusCode is equal to 0 then hold the student
			if(student.getStatusCode()  == 0) {			
				student.setStatus(holdReason);
				student.setStatusCode(1);
			}else {
				student.setStatus(null);
				student.setStatusCode(0);
			}
			
		}else{
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "emailID not found ");
		}
		
	}
}
