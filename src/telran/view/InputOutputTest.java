package telran.view;
//IlyaL_HW36
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.dto.Employee;

class InputOutputTest {
InputOutput io = new ConsoleInputOutput();
	@BeforeEach
	void setUp() throws Exception {
	}

//	@Test
	void employeeInputAsOneString() {
		Employee empl = io.readObject("Enter employee data as string <id>#<name>#<birthdate ISO>#<salary>#<department>",
				"Wrong format of employee data", InputOutputTest::toEmployeeFromStr);
		io.writeObjectLine(empl);
	}
	static Employee toEmployeeFromStr(String str) {
		String emplTokens[] = str.split("#");
		long id = Long.parseLong ( emplTokens[0] );
		String name = emplTokens[1];
		LocalDate birthDate = LocalDate.parse(emplTokens[2]);
		int salary = Integer.parseInt(emplTokens[3]);
		String department = emplTokens[4];
		return new Employee(id, name, birthDate, salary, department);
	}
	
//	@Test 
	void readPredicateTest() {
		String str = io.readStringPredicate("enter any string containing exactly 3 symbols",
				"this is no string containing exactly 3 symbols", s -> s.length() == 3);
		assertEquals(3, str.length());
	}
	@Test
	void employeeBySeparateFields() {
		//
		//enter ID by readLong method
		
		long id= io.readLong("Enter ID  \t");
		io.writeObjectLine("ID "+id + "\n");
		
		//enter Name by readStringPredicate (only letters with capital first letter)
		
		String name =io.readStringPredicate("enter name, onli laters, first capitalizd\t",
				"input format incorected", s->s.matches("[A-Z][a-z]*"));
		io.writeObjectLine("name "+name +  "\n");
		
		//enter birthdate by readDate 
		LocalDate birthDay=io.readDate("Enter birthday yyyy-MM-dd format \t");
		io.writeObjectLine("birthDay is " + birthDay+ "\n");
		
		String formatter = "yyyy MM dd";
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern(formatter);
		birthDay=io.readDate("Enter berthday using special format \t" + formatter, dtf);
		io.writeObjectLine("birthDay is " + birthDay+  "\n");
		
		//enter salary by readInt(prompt, min, max)
		int salary = io.readInt("enter salary \t", 10_000, 20_000);
		io.writeObjectLine("salary " +salary +  "\n");
		
		//enter department by readStringOption specifying possible departments
		
		String listOfDep[]= {"Dep1", "Dep2", "Dep3"};
		Set <String> deps=new HashSet<>(Arrays.asList(listOfDep));
		String dep=io.readStringOptions("enter one of departmens: \t" + Arrays.toString(listOfDep),
				deps);
		io.writeObjectLine("department " + dep + "\n");
		
					
	}

}
