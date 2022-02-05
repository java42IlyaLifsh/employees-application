package telran.view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
//IlyaL_HW36
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);
	void writeObject(Object obj);
	default void writeObjectLine(Object obj) {
		writeObject(obj.toString() + "\n");
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		
		while(true) {
			String string = readString(prompt);
			try {
				R result = mapper.apply(string);
				return result;
			} catch (Exception e) {
				writeObjectLine(errorPrompt);
			}
		}
		
	}

	default String readStringPredicate(String prompt, String errorMessage, Predicate<String> predicate) {
		
		return readObject(prompt, errorMessage, str -> {
			if (predicate.test(str)) {
				return str;
			}
			throw new IllegalArgumentException();
		});
	}
	//write all default methods from UML schema 
	//1
	default Integer readInt(String prompt) {
		try {
			return readObject (prompt, "Input digits only   ", str->{
				return Integer.parseInt(str);
			});
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	//2
	default Integer readInt (String prompt, int min, int max) {
		String errorMessage = String.format("Please digits onli in range  %d ---%d   ",  min, max);
		try {
			return readObject(prompt, errorMessage, str-> {
				int value = Integer.parseInt(str);
				if (value < min || value > max) throw new IllegalArgumentException();
				return value;
			});
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	//3
	default Long readLong(String prompt) {
		try {
			return readObject (prompt, "Input digits only   ", str->{
				return Long.parseLong(str);
			});
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	//4
	default String readStringOptions(String prompt, Set<String>options) {
		return readStringPredicate(prompt, "Out of set   ", str-> {
			return options.contains(str);
		});
	}
	
	//5
	default LocalDate readDate (String prompt) {
		String formater = "yyyy-MM-dd";
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern(formater);
		return readDate(prompt  ,  dtf);
		}
	
	//6
	default LocalDate readDate(String prompt, DateTimeFormatter formatter) {
	try {
		return readObject (prompt, "incorect data format   ", str -> {
			return LocalDate.parse(str, formatter);
		});
	} catch (DateTimeParseException e) {
		throw new IllegalArgumentException();
		}	
	}
}
