package telran.employees.services;
//IlyaL
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.employees.dto.*;
import java.util.*;
class EmployeesMethodsTest {
private static final long ID1 = 123;
private static final String NAME = "name";
private static final LocalDate BIRTHDATE1 = LocalDate.of(2000, 1, 1);
private static final int SALARY1 = 5000;
private static final String DEPARTMENT1 = "department1";
private static final long ID2 = 124;
private static final long ID3 = 125;
private static final long ID4 = 126;
private static final long ID5 = 127;
private static final long ID6 = 128;
private static final LocalDate BIRTHDATE2 = LocalDate.of(1995, 1, 1);;
private static final LocalDate BIRTHDATE3 = LocalDate.of(1997, 1, 1);;
private static final LocalDate BIRTHDATE4 = LocalDate.of(1970, 1, 1);;
private static final LocalDate BIRTHDATE5 = LocalDate.of(1971, 1, 1);;
private static final LocalDate BIRTHDATE6 = LocalDate.of(1980, 1, 1);
private static final String DEPARTMENT2 = "department2";
private static final int SALARY2 = 6000;
private static final int SALARY3 = 1000;;
EmployeesMethods employees; 
Employee empl1 = new Employee(ID1, NAME, BIRTHDATE1, SALARY1, DEPARTMENT1);
Employee empl2 = new Employee(ID2, NAME, BIRTHDATE2, SALARY1, DEPARTMENT1);
Employee empl3 = new Employee(ID3, NAME, BIRTHDATE3, SALARY2, DEPARTMENT1);
Employee empl4 = new Employee(ID4, NAME, BIRTHDATE4, SALARY2, DEPARTMENT2);
Employee empl5 = new Employee(ID5, NAME, BIRTHDATE5, SALARY3, DEPARTMENT2);
Employee empl6 = new Employee(ID6, NAME, BIRTHDATE6, SALARY3, DEPARTMENT2);
List<Employee> employeesList = Arrays.asList(empl1,empl2,empl3,empl4,empl5,empl6);
	@BeforeEach
	void setUp() throws Exception {
		employees = new EmployeesMethodsMapsImpl();
		employeesList.forEach(employees::addEmployee);
		
	}

	@Test
	void testAddEmployee() {
		assertEquals(ReturnCode.EMPLOYEE_ALREADY_EXISTS, employees.addEmployee(empl1));
		assertEquals(ReturnCode.OK, 
				employees.addEmployee(new Employee(ID1 + 10000, NAME, BIRTHDATE1, SALARY1, DEPARTMENT1)));
	}

	@Test
	void testRemoveEmployee() {
		//TODO
	}

	@Test
	void testGetAllEmployees() {
		//TODO
	}

	@Test
	void testGetEmployee() {
		//TODO
	}

	@Test
	void testGetEmployeesByAge() {
		//TODO
	}

	@Test
	void testGetEmployeesBySalary() {
		//TODO
	}

	@Test
	void testGetEmployeesByDepartment() {
		List<Employee> expected = Arrays.asList(empl1, empl2, empl3);
		assertIterableEquals(expected, employees.getEmployeesByDepartment(DEPARTMENT1));
	}

	@Test
	void testGetEmployeesByDepartmentAndSalary() {
		//TODO
	}

	@Test
	void testUpdateSalary() {
		//TODO
	}

	@Test
	void testUpdateDepartment() {
		//TODO
	}

}
