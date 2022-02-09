package telran.employees.controller;
import java.util.Arrays;
import java.util.HashSet;

import telran.employees.services.*;
import telran.view.*;
public class EmployeesAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		EmployeesMethods employeesMethods = new EmployeesMethodsMapsImpl();
		HashSet<String> departments = new HashSet<>(Arrays.asList("QA", "Development", "HR", "Management"));
		Menu menu = new Menu("Employees Application", EmployeeActions.getActionItems(employeesMethods, departments));
		menu.perform(io);

	}

}
