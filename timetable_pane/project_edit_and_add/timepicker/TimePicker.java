package timetable_pane.project_edit_and_add.timepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class extends VBox and it is used to receive the exact time of day.
 * (Seconds is not included since it does not fit the theme of project.)
 *
 * @author Project Group 3
 * @since 2015-05-11
 * 
 * @see TimeSpinner.java
 * @see http://code.makery.ch/blog/javafx-8-date-picker/
 */
public class TimePicker extends VBox {

	private SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

	private Date date;
	private DatePicker dp;

	private TimeSpinner timespin;

	//Get methods for editing
	public DatePicker getDP() {
		return this.dp;
	}

	public TimePicker(String startOrEnd)
	{
		if (startOrEnd.equals("START"))
		{
			Locale.setDefault(Locale.ENGLISH);

			dp = new DatePicker();
			dp.setPrefWidth(120);
			dp.setShowWeekNumbers(true);

			timespin = new TimeSpinner();

			Label startlabel = new Label("Start Date:");
			startlabel.setStyle("-fx-font-size: 10pt; -fx-font-family: Segoe UI Semibold;");
			startlabel.setPadding(new Insets(20,0,0,0));

			HBox hbox = new HBox();
			hbox.getChildren().addAll(dp);
			hbox.setPadding(new Insets(20,0,0,0));

			HBox hbox1 = new HBox(10);
			hbox1.getChildren().addAll(startlabel, hbox, timespin);
			hbox1.setPadding(new Insets(0,0,20,0));

			setSpacing(10);
			getChildren().add(hbox1);
		}
		else if (startOrEnd.equals("END"))
		{
			Locale.setDefault(Locale.ENGLISH);

			dp = new DatePicker();
			dp.setPrefWidth(120);
			dp.setShowWeekNumbers(true);

			timespin = new TimeSpinner();
				
			Label endlabel = new Label("End Date: ");
			endlabel.setStyle("-fx-font-size: 10pt; -fx-font-family: Segoe UI Semibold;");
			endlabel.setPadding(new Insets(20,0,0,0));
			
			HBox hbox = new HBox();
			hbox.getChildren().addAll(dp);
			hbox.setPadding(new Insets(20,0,0,0));

			HBox hbox1 = new HBox(10);
			hbox1.getChildren().addAll(endlabel, hbox, timespin);

			setSpacing(10);	
			getChildren().add(hbox1);
		}

	}

	/**
	 * This is a public method that returns the exact time and date.
	 * 
	 * @return date
	 */
	@SuppressWarnings("deprecation")
	public Date getDate()
	{
		String date0 = dp.getValue().toString();

		try 
		{
			if (timespin.isDisable())
			{
				date = df1.parse(date0);
			}
			else 
			{
				date = df1.parse(date0);
				date.setHours(timespin.getHour());
				date.setMinutes(timespin.getMinute());
			}	
			return date;

		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * This is a public method that returns the date from the DatePicker
	 * and not from the TimeSpinner.
	 * 
	 * @return date
	 * @throws ParseException
	 */
	public Date getDateOnly() throws ParseException
	{
		String date0 = dp.getValue().toString();
		date = df1.parse(date0);

		return date;
	}

	/**
	 * This is a public method that disables or enables the HourSpinner in TimeSpinner.
	 * @param b
	 */
	public void disableHourSpinner(boolean b)
	{
		timespin.disableHourSpinner(b);
	}

	/**
	 * This is a public method that disables or enables the MinuteSpinner in TimeSpinner.
	 * @param b
	 */
	public void disableMinuteSpinner(boolean b)
	{
		timespin.disableMinuteSpinner(b);
	}

	/**
	 * @return hour
	 */
	public int getHour()
	{
		return timespin.getHour();
	}

	/**
	 * @return minute
	 */
	public int getMinute() 
	{
		return timespin.getMinute();	
	}

	public void setValue(Date date) 
	{
		LocalDate localeDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

		this.dp.setValue(localeDate);
	}
}
