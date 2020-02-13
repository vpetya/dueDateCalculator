package dueDateCalculator;

import java.time.LocalDateTime;

import org.junit.Test;

import org.junit.Assert;

public class DueDateCalculatorTest {

	/**
	 * Issue can be finished instantly
	 */
	@Test
	public void instantTest() {
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 9, 1), 0);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 13, 9, 1), calculatedDueDate);
	}

	/**
	 * Issue can be finished on the same day
	 */
	@Test
	public void sameDayTest() {
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 9, 1), 5);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 13, 14, 1), calculatedDueDate);
	}

	/**
	 * Issue can be finished on the next day
	 */
	@Test
	public void nextDayTest() {
		// Thursday-> Friday
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 14, 1), 5);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 14, 11, 1), calculatedDueDate);
	}

	/**
	 * Issue can be finished on the same week
	 */
	@Test
	public void sameWeekTest() {
		// Monday -> Thursday
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 10, 14, 1), 21);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 13, 11, 1), calculatedDueDate);
	}

	/**
	 * Issue can be finished on the next week
	 */
	@Test
	public void nextWeekTest() {
		// Thursday -> Monday
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 14, 1), 13);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 17, 11, 1), calculatedDueDate);
	}

	/**
	 * Issue can be finished in a few weeks
	 */
	@Test
	public void fewWeekTest() {
		// Thursday -> Monday
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 14, 1), 93);
		Assert.assertEquals(LocalDateTime.of(2020, 3, 2, 11, 1), calculatedDueDate);
	}

	/**
	 * Issue submitted on 5PM
	 */
	@Test
	public void fivePMTest() {
		LocalDateTime calculatedDueDate = DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 17, 0), 1);
		Assert.assertEquals(LocalDateTime.of(2020, 2, 14, 10, 0), calculatedDueDate);
	}

	/**
	 * negative turnaround
	 */
	@Test(expected = IllegalArgumentException.class)
	public void invalidTurnAroundTest() {
		DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 9, 1), -1);
	}

	/**
	 * Issue submitted on weekend
	 */
	@Test(expected = IllegalArgumentException.class)
	public void invalidSubmitDayTest() {
		// saturday
		DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 15, 9, 1), 1);
	}

	/**
	 * Issue submitted outside working hours
	 */
	@Test(expected = IllegalArgumentException.class)
	public void invalidSubmitTimeTest() {
		DueDateCalculator.calculateDueDate(LocalDateTime.of(2020, 2, 13, 17, 1), 1);
	}
}
