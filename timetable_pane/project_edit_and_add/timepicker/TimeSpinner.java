package timetable_pane.project_edit_and_add.timepicker;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This is a class that extends VBox.
 * 
 * @author Project Group 3
 * @since 2015-05-26
 */
public class TimeSpinner extends VBox {
	
	private HourSpinner hourspin;
	private MinuteSpinner minspin;

	/**
	 * This is a constructor with a HourSpinner and a MinuteSpinner plus two labels for the sake
	 * of the layout.
	 */
	public TimeSpinner()
	{
		hourspin = new HourSpinner();
		minspin = new MinuteSpinner();
				
		Label label0 = new Label("Choose time:");
		label0.setStyle("-fx-font-size: 10pt; -fx-font-family: Segoe UI Semibold;");
		
		Label label1 = new Label(":");
		label1.setStyle("-fx-font-size: 10pt; -fx-font-family: Segoe UI Semibold;");
		
		HBox hbox = new HBox(5);
		hbox.getChildren().addAll(hourspin, label1, minspin);
		
		getChildren().addAll(label0, hbox);
	}

	/**
	 * This is a public method that returns the selected hour.
	 * @return hour
	 */
	public int getHour()
	{
		return hourspin.getHour();	
	}
	
	/**
	 * This is a public method that returns the selected minute.
	 * @return minute
	 */
	public int getMinute()
	{
		return minspin.getMinute();	
	}
	
	/**
	 * This is a public method that disables or enables the HourSpinner.
	 * @param b
	 */
	public void disableHourSpinner(boolean b)
	{
		hourspin.setDisable(b);
	}
	
	/**
	 * This is a public method that disables or enables the MinuteSpinner.
	 * @param b
	 */
	public void disableMinuteSpinner(boolean b)
	{
		minspin.setDisable(b);
	}	
}
