package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);

	void writeString(String str);

	default void writeLine(Object obj)
	{
		writeString(obj.toString() + "\n");
	}

	default  <T> T readObject(String prompt, String error_prompt, Function<String, T> mapper)
	{
		boolean running;
		T res = null;
		do {
			running = false;
			try {
				String user_entered = readString(prompt);
				res = mapper.apply(user_entered);
			} catch (Exception e) {
				writeLine(error_prompt + ": " + e.getMessage());
				running = true;
			}
		} while (running);

		return res;
	}

	/**
	 * 
	 * @param prompt
	 * @param errorPrompt
	 * @return Integer number
	 */
	default Integer readInt(String prompt, String errorPrompt)
	{
		return readObject(prompt, errorPrompt, input -> Integer.parseInt(input));
	}

	default Long readLong(String prompt, String errorPrompt)
	{
		return readObject(prompt, errorPrompt, input -> Long.parseLong(input));
	}

	default Double readDouble(String prompt, String errorPrompt)
	{
		return readObject(prompt, errorPrompt, input -> Double.parseDouble(input));
	}

	default Double readNumberRange(String prompt, String errorPrompt, double min, double max)
	{
		double res = readDouble(prompt, errorPrompt);
		if (res < min || res > max) {
			throw new IllegalArgumentException(errorPrompt);
		}

		return res;
	}

	default String readStringPredicate(String prompt, String errorPrompt,
			Predicate<String> predicate)
	{
		String res = readString(prompt);
		if (!predicate.test(res)) {
			throw new IllegalArgumentException(errorPrompt);
		}

		return res;
	}

	default String readStringOptions(String prompt, String errorPrompt,
			HashSet<String> options)
	{
		String res = readString(prompt);
		if (!options.contains(res)) {
			throw new IllegalArgumentException(errorPrompt);
		}
		return res;
	}

	default LocalDate readIsoDate(String prompt, String errorPrompt)
	{
		return readObject(prompt, errorPrompt, input -> LocalDate.parse(input));
	}

	default LocalDate readIsoDateRange(String prompt, String errorPrompt, LocalDate from,
			LocalDate to)
	{
		if (to.isAfter(from)) {
			throw new IllegalArgumentException("Invalid date range: 'from' date must be before 'to' date");
		}
		LocalDate res = readIsoDate(prompt, "Illegal date format");
		if (!(res.isBefore(from) && res.isAfter(to))) {
			throw new IllegalArgumentException(errorPrompt);
		}

		return res;
	}
}
