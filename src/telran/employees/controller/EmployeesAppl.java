package telran.employees.controller;
import java.util.*;
import java.util.stream.Collectors;

import telran.employees.services.*;
import telran.view.*;
import java.io.*;
public class EmployeesAppl {
private static final String DEFAULT_FILE_NAME = "employees.data";
private static final Object DATA_FILE_PROPERTY = null;
static Map<String, String> properties;
static InputOutput io = new ConsoleInputOutput();
	public static void main(String[] args) {
		
		if (args.length < 1) {
			io.writeObjectLine("Usage - argument should contain configuration file name");
			return;
		}
		//Configuration file contains text like employeesDataFile=employees.data
		//Apply BufferedReader for reading configuration
		String fileName = getFileName(args[0]);
		if (fileName == null) {
			return;
		}
		EmployeesMethods employeesMethods = new EmployeesMethodsMapsImpl(fileName);
		employeesMethods.restore();
		HashSet<String> departments = new HashSet<>(Arrays.asList("QA", "Development", "HR", "Management"));
		Menu menu = new Menu("Employees Application", EmployeeActions.getActionItems(employeesMethods, departments));
		menu.perform(io);

	}

	private static String getFileName(String configFile) {
		
		if(properties == null) {
			
			try(BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
				properties = reader.lines().map(str -> str.split("[ =:]+"))
						.collect(Collectors.toMap(tokens -> tokens[0], tokens -> tokens[1]));
				
			} catch (Exception e) {
				io.writeObjectLine(e.getMessage());
				return null;
			}
		}
		return properties.getOrDefault(DATA_FILE_PROPERTY, DEFAULT_FILE_NAME);
	}

}
