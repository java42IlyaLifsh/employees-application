package telran.view;
//IlyaL_HW36
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.dto.Employee;

class InputOutputTest {
InputOutput io = new ConsoleInputOutput();
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
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
	
	@Test 
	void readPredicateTest() {
		String str = io.readStringPredicate("enter any string containing exactly 3 symbols",
				"this is no string containing exactly 3 symbols", s -> s.length() == 3);
		assertEquals(3, str.length());
	}
	@Test
	void employeeBySeparateFields() {
		//TODO
		//enter ID by readLong method
		//enter Name by readStringPredicate (only letters with capital first letter)
		//enter birthdate by readDate 
		//enter salary by readInt(prompt, min, max)
		//enter department by readStringOption specifying possible departments
		
	}

}
