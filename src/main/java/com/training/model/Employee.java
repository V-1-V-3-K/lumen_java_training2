package com.training.model;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	int employeeId;
	String firstName;
	String lastName;
	String address;
	String email;
	long phoneNumber;
	LocalDate dateOfBirth;
	LocalDate weddingDate;
}
