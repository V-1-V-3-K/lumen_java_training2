package com.training;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.training.manager.ApplicationManager;
import com.training.model.Employee;
import com.training.services.EmployeeService;

public class App 
{
	
	
    public static void main( String[] args )
    {
    	
    	Logger logger = LogManager.getRootLogger() ;
    	Scanner sc = new Scanner(System.in); 
    	logger.info("Hello!");
        logger.info("---------------------------------");
        boolean runApp = true;
    	
        while(runApp) {
    		ApplicationManager.showMenu();
    		System.out.print("Enter your Choice: ");
    		int choice = sc.nextInt();
    		runApp = ApplicationManager.choiceBasedSwitch(choice);
//    		System.out.print("Hit Enter to continue..");
//    		sc.nextLine();
    		
    	}
    	logger.info("Shutting down.....");
    	sc.close();
    }
}
