package com.cst438.domain;

public class StudentDTO {
	public String name;
	public String email;
	public int statusCode;
	public String status;
	public int student_id;
	
	@Override
	public String toString() {
		return "StudentDTO [student_id=" + student_id + ", name=" + name + ", email=" + email + ", statusCode="
				+ statusCode + ", status=" + status + "]";
	}

}
