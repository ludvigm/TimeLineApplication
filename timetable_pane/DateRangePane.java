package timetable_pane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DateRangePane extends StackPane {

	private SimpleDateFormat df = new SimpleDateFormat("EEE d/M, yyyy", Locale.US);

	public DateRangePane(Date startdate, Date enddate, String duration)
	{
		Label label = new Label(df.format(startdate) + "    -    " + df.format(enddate));
		label.setStyle("-fx-font-size: 13px; -fx-font-family: Segoe UI Semibold; -fx-text-fill: #599A59; -fx-font-weight: 900;");
		getChildren().add(label);
		
		setStyle("-fx-background-color: #E6E6E6; -fx-padding: 10px; -fx-font-size: 14px; -fx-border-color: #B7B7B7; -fx-border-style: solid solid hidden solid;");

		setPrefWidth(getSize(startdate, enddate, duration)*DatePane.getDateWidth(duration));
	}

	private double getSize(Date startdate, Date enddate, String duration) 
	{
		double size = 0; 
		
		if (duration.equals("YEARS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			size = (double) (diff/(24*60*60*1000))/366;
		}
		else if (duration.equals("MONTHS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			size = (double) (diff/(24*60*60*1000))/31;
		}
		else if (duration.equals("DAYS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			size = (double) diff/(24*60*60*1000);
		}
		else if (duration.equals("HOURS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			size = (double) diff/(60*60*1000);
		}
		return size;
	}

}
