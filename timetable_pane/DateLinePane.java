package timetable_pane;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.scene.layout.HBox;

/**
 * This class stacks all DatePanes horizontally, in form of a date-line.
 * 
 * @author Project Group 3
 * @since 2015-05-10
 * 
 * @see DatePane.java
 */
public class DateLinePane extends HBox {

	private int numberOfDates;
	private Date enddate;

	private String duration;

	/**
	 * This is a constructor that will call one of four methods depending on the parameter
	 * duration. If the duration is "YEARS" it will call the method showAllYears, and so forth.
	 * 
	 * @param startdate
	 * @param enddate
	 * @param duration
	 * 
	 * @see DatePane.java
	 */
	public DateLinePane(Date startdate, Date enddate, String duration)
	{
		this.enddate = enddate;
		this.duration = duration;

		if (duration.equals("YEARS"))
		{
			showAllYears(startdate, enddate);
			setFillHeight(true);
		}
		else if (duration.equals("MONTHS"))
		{
			showAllMonths(startdate, enddate);
			setFillHeight(true);
		}
		else if (duration.equals("DAYS"))
		{
			showAllDates(startdate, enddate);
			setFillHeight(true);
		}
		else if (duration.equals("HOURS"))
		{
			showAllHours(startdate, enddate);
			setFillHeight(true);
		}
	}

	/**
	 * This is a private method that will call the constructor DatePane. With 
	 * help of the increment of the variable "numberOfDates" the DatePane will
	 * be assigned one of two colors. 
	 * 
	 * @param startdate
	 * @param enddate
	 * 
	 * @see DatePane.java
	 */
	private void showAllYears(Date startdate, Date enddate) 
	{
		Calendar cal = new GregorianCalendar();
		Date todaysDate = cal.getTime();
		cal.setTime(startdate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		numberOfDates = 0;

		while (cal.getTime().before(enddate))
		{	
			if (cal.get(Calendar.YEAR)%2 == 0 && !sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #C7D4E0; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");
				
				cal.add(Calendar.YEAR, 1);
				numberOfDates++;
			}
			else if (sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #FF8080; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.YEAR, 1);
				numberOfDates++;
			}
			else if (cal.get(Calendar.YEAR)%2 != 0)
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #799AB5; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.YEAR, 1);
				numberOfDates++;
			}		
		}	
	}

	/**
	 * This is a private method that will call the constructor DatePane. With 
	 * help of the increment of the variable "numberOfDates" the DatePane will
	 * be assigned one of two colors. 
	 * 
	 * @param startdate
	 * @param enddate
	 * 
	 * @see DatePane.java
	 */
	private void showAllMonths(Date startdate, Date enddate) 
	{
		Calendar cal = new GregorianCalendar();
		Date todaysDate = cal.getTime();
		cal.setTime(startdate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		numberOfDates = 0;

		while (cal.getTime().before(enddate))
		{	
			if (cal.get(Calendar.MONTH)%2 == 0 && !sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #C7D4E0; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");
				
				cal.add(Calendar.MONTH, 1);
				numberOfDates++;
			}
			else if (sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #FF8080; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.MONTH, 1);
				numberOfDates++;
			}
			else if (cal.get(Calendar.MONTH)%2 != 0)
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #799AB5; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.MONTH, 1);
				numberOfDates++;
			}		
		}	
	}

	/**
	 * This is a private method that will call the constructor DatePane. With 
	 * help of the increment of the variable "numberOfDates" the DatePane will
	 * be assigned one of two colors. 
	 * 
	 * @param startdate
	 * @param enddate
	 * 
	 * @see DatePane.java
	 */
	private void showAllDates(Date startdate, Date enddate) 
	{
		Calendar cal = new GregorianCalendar();
		Date todaysDate = cal.getTime();
		cal.setTime(startdate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		numberOfDates = 0;

		while (cal.getTime().before(enddate))
		{	
			if (cal.get(Calendar.WEEK_OF_YEAR)%2 == 0 && !sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #C7D4E0; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.DATE, 1);
				numberOfDates++;
			}
			else if (cal.get(Calendar.WEEK_OF_YEAR)%2 != 0 && !sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #799AB5; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.DATE, 1);
				numberOfDates++;
			}	
			else if (sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #FF8080; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.DATE, 1);
				numberOfDates++;
			}	
		}
	}	

	/**
	 * This is a private method that will call the constructor DatePane. With 
	 * help of the increment of the variable "numberOfDates" the DatePane will
	 * be assigned one of two colors. 
	 * 
	 * @param startdate
	 * @param enddate
	 * 
	 * @see DatePane.java
	 */
	private void showAllHours(Date startdate, Date enddate) 
	{
		Calendar cal = new GregorianCalendar();
		Date todaysDate = cal.getTime();
		cal.setTime(startdate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

		numberOfDates = 0;

		while (cal.getTime().before(enddate))
		{	
			if (cal.get(Calendar.HOUR_OF_DAY)%2 == 0 && !sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #C7D4E0; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.HOUR_OF_DAY, 1);
				numberOfDates++;
			}
			else if (sdf.format(todaysDate).equals(sdf.format(cal.getTime())))
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #FF8080; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.HOUR_OF_DAY, 1);
				numberOfDates++;
			}
			else if (cal.get(Calendar.HOUR_OF_DAY)%2 != 0)
			{
				DatePane dp = new DatePane(cal.getTime(), duration);

				getChildren().add(dp);
				dp.setStyle("-fx-background-color: #799AB5; -fx-font-size: 14px; -fx-border-color: #B7B7B7;");

				cal.add(Calendar.HOUR_OF_DAY, 1);
				numberOfDates++;
			}		
		}
	}

	public int getNumberOfDates() { return numberOfDates; }

	/**
	 * This is public method that returns the last date of DateLinePane. 
	 * @return enddate
	 */
	public Date getEndDate() 
	{ 
		return enddate; 
	}
}

