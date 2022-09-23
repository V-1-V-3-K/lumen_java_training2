package com.training.manager;



import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.training.model.Employee;
import com.training.services.EmployeeService;

public class ApplicationManager {
	private static final Logger logger = LogManager.getRootLogger() ;
	private static EmployeeService service = new EmployeeService();
	public static void showMenu() {
		System.out.println("Please Select from the List:-");
		System.out.println("1.	Add employee details");
		System.out.println("2.	Get the List of employees by their firstName.");
		System.out.println("3.	Get the List of employees with FirstName and Phone Number");
		System.out.println("4.	Update the email and phoneNumber of a particular employee.");
		System.out.println("5.	Delete Details of a Particular employee by firstName");
		System.out.println("6.	Get a list of employees with their firstName and emailAddress  whose Birthday falls on the given date");
		System.out.println("7.	Get the list of employees with their firstName and phone Number whose Wedding Anniversary falls on the given date");
		System.out.println("8.	To Exit");
	}
	
	public static Employee addEmployeeInput() {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter Employee ID -> ");
		int id = sc.nextInt();
		
		sc.nextLine();
		System.out.print("Enter Employee's firstName -> ");
		String firstName = sc.nextLine() ;
		
		System.out.print("Enter Employee's lastName -> ");
		String lastName = sc.nextLine() ; 
		 
		 System.out.print("Enter Employee's address -> ");
		 String address = sc.nextLine() ; 
		 
		 System.out.print("Enter Employee's email -> ");
		 String email = sc.nextLine() ; 
		 
		 System.out.print("Enter Employee's phone number :");
		 Long phoneNumber = Long.parseLong(sc.nextLine()); 
		 
		 System.out.print("Enter Employee's date of birth in dd/mm/yyyy format: ");
		 String dateOfBirth = sc.nextLine() ;
		 String[] parts = dateOfBirth.split("/") ;
//		 System.out.println(parts.length);
		 LocalDate dateOfBirthActual = LocalDate.of(Integer.parseInt(parts[2]) , 
				 									Integer.parseInt(parts[1]) , 
				 									Integer.parseInt(parts[0])); 
		 
		 System.out.println("Enter Employee's wedding date in dd/mm/yyyy format or hit enter if unmarried: ");
		 LocalDate dateOfWeddingActual = null;
		 String dateOfWedding = sc.nextLine();
		 parts = dateOfWedding.split("/"); 
		 if(parts.length==1) {
			 dateOfWeddingActual = null;
		 }else {
			 dateOfWeddingActual= LocalDate.of(Integer.parseInt(parts[2]) , 
					  Integer.parseInt(parts[1]) , 
					   Integer.parseInt(parts[0]));
		 }
		 return new Employee(id, firstName, lastName, address, email, phoneNumber, dateOfBirthActual, dateOfWeddingActual);
	}
	
	public static String[] updateEmployeeInput(){
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the id of the employee that you want to modify: ");
    	int id = sc.nextInt();
    	String stringId = Integer.toString(id);
    	System.out.println();
    	
    	
    	System.out.print("Now enter New Mobile Number: ");
    	Long phoneNumber = sc.nextLong();
    	String stringPhoneNumber = Long.toString(phoneNumber);
    	System.out.println();
    	
    	sc.nextLine();
    	System.out.print("Enter New Email id: ");
    	String newEmail = sc.nextLine();
		System.out.println();
    	
		String[] detailList = {stringId,newEmail,stringPhoneNumber};
		
		return detailList;
		 
	}
	
	public static String deleteEmpByNameInput() {
		System.out.print("Please Enter the name of the Employee to be deleted: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.println();
		return name;
	}
	
	public static MonthDay getEmpByDateOfBirthInput() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Month Number: ") ; 
    	int monthOfTheYear = sc.nextInt() ; 
    	System.out.println();
    	
    	System.out.print("Entger Day number of that Month: ");
    	int dayOfTheMonth = sc.nextInt() ; 
    	System.out.println();
    	
    	return MonthDay.of(monthOfTheYear, dayOfTheMonth);
	}
	
	public static MonthDay getEmpByWeddingDateInput() {
		Scanner sc = new Scanner(System.in);
		MonthDay wedDay = null;
		
		System.out.println("Enter Month Number and 0 if not married: ") ; 
    	int monthOfTheYear = sc.nextInt() ; 
    	System.out.println();
    	
    	System.out.print("Entger Day number of that Month and 0 if not married: ");
    	int dayOfTheMonth = sc.nextInt() ; 
    	System.out.println();
    	
    	if(monthOfTheYear==0 || dayOfTheMonth==0) {
    		wedDay=null;
    	}
    	else {
    		wedDay = MonthDay.of(monthOfTheYear,dayOfTheMonth);
    	}
    	return wedDay;
	}
	
	
	
	public static boolean choiceBasedSwitch(int choice) {
		
		switch (choice) {
		case 1:
			logger.info("Choice = 1");
			Employee emp = addEmployeeInput();
			logger.info("Employee Added:= "+service.add(emp));
			return true;
		case 2:
			logger.info("Choice = 2");
			List<String> list = service.getNames();
			list.forEach(e-> logger.info(e));
			return true;
		case 3:
			logger.info("Choice = 3");
			Map<String,Long> map1 = service.getFirstNamePhoneNumber();
			Set<Map.Entry<String,Long>> item1 = map1.entrySet(); 
			item1.forEach(e-> logger.info(e.getKey() +" "+ e.getValue()) );
			return true;
		case 4:
			logger.info("Choice = 4");
			String[] paramList = updateEmployeeInput();
			logger.info("Employee updated:= "+service.updateEmailPhone(Integer.parseInt(paramList[0]),paramList[1], Long.parseLong(paramList[2])));
			return true;
		case 5:
			logger.info("Choice = 5");
			String name = deleteEmpByNameInput();
			logger.info("Employee deleted:= "+service.deleteByName(name));
			return true;
		case 6:
			logger.info("Choice = 6");
			MonthDay dateOfBirth = getEmpByDateOfBirthInput();
			Map<String,String> map = service.getFnameEmailDob(dateOfBirth);
			Set<Map.Entry<String,String>> item = map.entrySet(); 
			item.forEach(e-> logger.info(e.getKey() +" "+ e.getValue()) );
			return true;
		case 7:
			logger.info("Choice = 7");
			MonthDay wedDate = getEmpByWeddingDateInput();
			Map<String,Long> map2 = service.getFnamePhoneDob(wedDate);
			Set<Map.Entry<String,Long>> item2 = map2.entrySet(); 
			item2.forEach(e-> logger.info(e.getKey() +" "+ e.getValue()) );
			return true;
		case 8:
			logger.info("Choice = 8");
			return false;
		default:
			logger.info("Please Input a valid choice!!");
			System.out.println();
			return true;
		}
	}
	
	
	
}
