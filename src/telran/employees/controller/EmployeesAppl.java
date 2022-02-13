package telran.employees.controller;
import java.util.Arrays;
import java.util.HashSet;

import telran.employees.services.*;
import telran.view.*;
public class EmployeesAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		if (args.length < 1) {
			io.writeObjectLine("Usage - argument should contain configuration file name");
			return;
		}
		//Configuration file contains text like employeesDataFile=employees.data
		//Apply BufferedReader for reading configuration
		String fileName = getFileName(args[0]);
		EmployeesMethods employeesMethods = new EmployeesMethodsMapsImpl(fileName);
		employeesMethods.restore();
		HashSet<String> departments = new HashSet<>(Arrays.asList("QA", "Development", "HR", "Management"));
		Menu menu = new Menu("Employees Application", EmployeeActions.getActionItems(employeesMethods, departments));
		menu.perform(io);

	}

	private static String getFileName(String configFile) {
		//TODO
		return null;
	}

}
