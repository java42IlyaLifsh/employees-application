package telran.employees.services;
//IlyaL
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
		
		List<Employee> expected = Arrays.asList(empl1, empl2, employees.getEmployee(ID1+10000));
		assertIterableEquals(expected, employees.getEmployeesByDepartmentAndSalary(DEPARTMENT1, SALARY1, SALARY1));
	}

	@Test
	void testRemoveEmployee() {
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID1));
			assertEquals(ReturnCode.EMPLOYEE_NOT_FOUND, employees.removeEmployee(ID1));
			
			List<Employee> expected2 = Arrays.asList(empl2);
			assertIterableEquals(expected2, employees.getEmployeesBySalary(SALARY1, SALARY1));
			
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID2));
			assertEquals(ReturnCode.EMPLOYEE_NOT_FOUND, employees.removeEmployee(ID2));
			
			assertTrue(((List<Employee>) employees.getEmployeesBySalary(SALARY1, SALARY1)).isEmpty());
			
			List<Employee> expected3 = Arrays.asList(empl3);
			assertIterableEquals(expected3, employees.getEmployeesByDepartment(DEPARTMENT1));
			
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID3));
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID4));
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID5));
			assertEquals(ReturnCode.OK, employees.removeEmployee(ID6));
			
			assertTrue(((List<Employee>) employees.getEmployeesBySalary(SALARY1-100000, SALARY3+100000)).isEmpty());
			assertTrue(((List<Employee>) employees.getEmployeesByDepartment(DEPARTMENT1)).isEmpty());
			assertTrue(((List<Employee>) employees.getEmployeesByDepartment(DEPARTMENT2)).isEmpty());

			
	}


	@Test
	void testGetAllEmployees() {
		for (Employee employee : employees.getAllEmployees()) {
			assertTrue(employeesList.contains(employee));
		}
 	
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, employees.getEmployee(ID1));
		
		assertNull(employees.getEmployee(ID1+1000));
	}

	@Test
	void testGetEmployeesByAge() {
		
		
		assertTrue(((List<Employee>) employees.getEmployeesByAge(1, 400)).size()==6);

		//Variant 1 does not work, because the order of the elements does not match
		//		assertIterableEquals(employeesList, employees.getEmployeesByAge(1, 400));
		
		//Variant 2 is working
		Iterator<Employee> iteratorAge = employees.getEmployeesByAge(1, 400).iterator();
		while(iteratorAge.hasNext()) {
			Employee employee = iteratorAge.next();
			assertTrue(employeesList.contains(employee));
		}
		
//Variant 3 and tell me please, where did I go wrong? the compiler swears, but I did not understand why????????????????????		
//		assertIterableEquals(employeesList.sort(((e1, e2)->Long.compare(e1.id, e2.id))), 
//				((List<Employee>) employees.getEmployeesByAge(1, 400)).sort((q1,q2)->Long.compare(q1.id, q2.id))); 
	
	}

	@Test
	void testGetEmployeesBySalary() {
		assertTrue(((List<Employee>) employees.getEmployeesBySalary(5000, 8000)).size()>0);
		assertEquals(0, ((List<Employee>) employees.getEmployeesBySalary(2000, 4500)).size());
		List<Employee> expected = Arrays.asList(empl3, empl4);
		assertIterableEquals(expected, employees.getEmployeesBySalary(SALARY2, SALARY2));
		
	}

	@Test
	void testGetEmployeesByDepartment() {
		List<Employee> expected = Arrays.asList(empl1, empl2, empl3);
		assertIterableEquals(expected, employees.getEmployeesByDepartment(DEPARTMENT1));
	}

	@Test
	void testGetEmployeesByDepartmentAndSalary() {
		List<Employee> expected = Arrays.asList(empl1, empl2, empl3);
		assertIterableEquals(expected, employees.getEmployeesByDepartmentAndSalary(DEPARTMENT1, SALARY1, SALARY2));
	}

	@Test
	void testUpdateSalary() {
		assertEquals(ReturnCode.OK, employees.updateSalary(ID3, SALARY3));
		assertEquals(ReturnCode.EMPLOYEE_NOT_FOUND, employees.updateSalary(999, SALARY3));
		assertEquals(SALARY3, employees.getEmployee(ID3).salary);
		
		List<Employee> expected2 = Arrays.asList(empl4);
		assertIterableEquals(expected2, employees.getEmployeesBySalary(SALARY2, SALARY2));

	}

	@Test
	void testUpdateDepartment() {
		String newDepartment = "other department";
		assertEquals(ReturnCode.OK, employees.updateDepartment(ID1, newDepartment));
		assertEquals(ReturnCode.OK, employees.updateDepartment(ID2, newDepartment));
		assertEquals(ReturnCode.OK, employees.updateDepartment(ID3, newDepartment));
		assertTrue(((List<Employee>) employees.getEmployeesByDepartment(DEPARTMENT1)).isEmpty());
		
		
		assertEquals(ReturnCode.EMPLOYEE_NOT_FOUND, employees.updateDepartment(888, newDepartment));
		
		String superDepartment = "super department";
		assertEquals(ReturnCode.OK, employees.updateDepartment(ID6, superDepartment));
		assertEquals(superDepartment, employees.getEmployee(ID6).department);
		assertIterableEquals(Arrays.asList(employees.getEmployee(ID6)),
				employees.getEmployeesByDepartment(superDepartment));
		
		
	}

}
