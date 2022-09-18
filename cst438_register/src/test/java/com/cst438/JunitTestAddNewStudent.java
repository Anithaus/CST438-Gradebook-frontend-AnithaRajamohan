package com.cst438;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.controller.StudentController;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;




@ContextConfiguration(classes = { StudentController.class, Student.class, StudentDTO.class, StudentRepository.class})
@WebMvcTest

public class JunitTestAddNewStudent {
	public static final String TEST_STUDENT_EMAIL = "test1@csumb.edu";
	public static final String TEST_STUDENT_NAME = "test1";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	StudentRepository studentRepository;
	
	@Test
	public void checkAddStudent()throws Exception{

		
		MockHttpServletResponse	response;
		
		StudentDTO studDTO = new StudentDTO ();
		studDTO.email=TEST_STUDENT_EMAIL;
		studDTO.name=TEST_STUDENT_NAME;

		// then do an http post request with body of student as JSON
		response = mvc.perform(
						MockMvcRequestBuilders
					      .post("/student")
					      .content(asJsonString(studDTO))
					      .contentType(MediaType.APPLICATION_JSON)
					      .accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		
		//checking if email is already there it must return statues=400
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		verify(studentRepository).save(any(Student.class));
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student")
			      .content(asJsonString(student))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(400, response.getStatus());

	}
	
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
