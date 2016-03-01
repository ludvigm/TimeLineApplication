package timetable_pane;

import java.util.Date;

import javafx.scene.layout.VBox;

public class DateAndRangePane extends VBox {

	public DateAndRangePane(Date startdate, Date enddate, String duration) 
	{
		DateRangePane drp = new DateRangePane(startdate, enddate, duration);
		DateLinePane dlp = new DateLinePane(startdate, enddate, duration);
		
		getChildren().addAll(drp, dlp);
	}
}
