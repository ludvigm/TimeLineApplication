package timetable_pane.project_edit_and_add.timepicker;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * This is a custom class that extends the spinner class. 
 * 
 * @author Project Group 3
 * @since 2015-05-26
 */
public class HourSpinner extends Spinner<Integer> {

	public HourSpinner()
	{		
		SpinnerValueFactory<Integer> svf = new
				SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1); 
		
		setValueFactory(svf);
		setPrefWidth(60);
		
		setEditable(true);
	}

	/**
	 * This is a public method that returns the selected value.
	 * 
	 * @return hour
	 */
	public int getHour()
	{
		return getValue();
	}
}