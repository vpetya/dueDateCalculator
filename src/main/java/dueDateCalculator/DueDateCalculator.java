package dueDateCalculator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class DueDateCalculator {

	/**
	 * Calculates the due date of the submitted issue Handles every day from Monday
	 * to Friday an 8 hour working day (9AM-5PM)
	 * 
	 * @param submitDate
	 *            - submission of the issue
	 * @param turnAroundTime
	 *            - defined in hours
	 * @return
	 * @throws IllegalArgumentException
	 *             if the submitDate is not durig working hour or the turnarouund
	 *             time is a negative number
	 */
	public static LocalDateTime calculateDueDate(LocalDateTime submitDate, int turnAroundTime) {

		validateTurnAroundTime(turnAroundTime);
		validateSubmitDate(submitDate);

		int daysToAdd = calculateDaysToAdd(submitDate, turnAroundTime);

		LocalDateTime dayShifted = submitDate.plusDays(daysToAdd);

		LocalDateTime hourShifted;
		int minuteOfDay = submitDate.get(ChronoField.MINUTE_OF_DAY);
		// if the remaining hours will reach 5pm
		if ((minuteOfDay + (turnAroundTime % 8) * 60) > (17 * 60)) {
			// The dayshifted is already ahead of time, because we added a plus full day
			// so we go back with (a day - 16 hour non working hours - the remaining working
			// hours)
			hourShifted = dayShifted.minusHours(24 - 16 - (turnAroundTime % 8));
		} else {
			hourShifted = dayShifted.plusHours(turnAroundTime % 8);
		}
		return hourShifted;
	}

	private static int calculateDaysToAdd(LocalDateTime submitDate, int turnAroundTime) {
		int minuteOfDay = submitDate.get(ChronoField.MINUTE_OF_DAY);

		// Add one day for every 8 hour
		int workingDaysToAdd = turnAroundTime / 8;
		// Add one more day, if the remaining hours will reach 5pm
		if ((minuteOfDay + (turnAroundTime % 8) * 60) > (17 * 60)) {
			workingDaysToAdd++;
		}
		// Every five working day add 7 calendar day
		int calendarDaysToAdd = (workingDaysToAdd / 5) * 7;
		if ((submitDate.get(ChronoField.DAY_OF_WEEK) + workingDaysToAdd % 5) > 5) {
			// shift with an other weekend
			calendarDaysToAdd += (workingDaysToAdd % 5) + 2;
		} else {
			calendarDaysToAdd += workingDaysToAdd % 5;
		}
		return calendarDaysToAdd;
	}

	private static void validateSubmitDate(LocalDateTime submitDate) {
		// must be between Monday-Friday
		int day = submitDate.get(ChronoField.DAY_OF_WEEK);
		if (day > 5) {
			throw new IllegalArgumentException("Submit date must be on working day");
		}

		// must be between 9am-5pm inclusive
		int minuteOfDay = submitDate.get(ChronoField.MINUTE_OF_DAY);
		if (minuteOfDay < (9 * 60) || minuteOfDay > (17 * 60)) {
			throw new IllegalArgumentException("Submit date must be between 9am-5pm inclusive");
		}
	}

	private static void validateTurnAroundTime(int turnAroundTime) {
		if (turnAroundTime < 0) {
			throw new IllegalArgumentException("Turn around time must be non negative");
		}

	}

}
