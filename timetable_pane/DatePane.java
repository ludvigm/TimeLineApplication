package timetable_pane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * This class represents one date unit -- a graphical box which includes date
 * information about the specific box.
 * 
 * @author Project Group 3
 * @since 2015-05-10
 */
public class DatePane extends StackPane {

	private SimpleDateFormat df1 = new SimpleDateFormat("EEE", Locale.US);
	private SimpleDateFormat df2 = new SimpleDateFormat("MMM", Locale.US);
	private SimpleDateFormat df3 = new SimpleDateFormat("h:mm a", Locale.US);
	private SimpleDateFormat df4 = new SimpleDateFormat("yyyy", Locale.US);
	private SimpleDateFormat df5 = new SimpleDateFormat("d/M", Locale.US);
	
	private Date date;
	
	public static double dateWidth;

	/**
	 * This is a constructor calls a method, depending which duration the DatePane
	 * has, that will display a graphical box including the date.
	 * 
	 * @param date
	 * @param duration
	 */
	public DatePane(Date date, String duration)
	{	
		this.date = date;
		
		if (duration.equals("HOURS"))
		{
			getHourPane();
		}
		else if (duration.equals("DAYS"))
		{
			getDatePane();
		}
		else if (duration.equals("YEARS"))
		{
			getYearPane();
		}
		else if (duration.equals("MONTHS"))
		{
			getMonthPane();
		}
	}

	/**
	 * This is a private method that will add an extra bit of information to DatePane, namely
	 * what year it has.
	 * @param dateWidth2 
	 */
	private void getYearPane() 
	{
		Label label = new Label(df4.format(date).toString());
		label.setStyle("-fx-font-size: 13px; -fx-font-family: Segoe UI Semibold; -fx-text-fill: white; -fx-font-weight: 900;");
		
		getChildren().add(label);
		setPrefSize(getDateWidth("YEARS"),40);
	}
	
	/**
	 * This is a private method that will add an extra bit of information to DatePane, namely
	 * what month it has.
	 */
	private void getMonthPane()
	{
		Label label = new Label(df2.format(date).toString());
		label.setStyle("-fx-font-size: 13px; -fx-font-family: Segoe UI Semibold; -fx-text-fill: white; -fx-font-weight: 900;");
		
		getChildren().add(label);
		setPrefSize(getDateWidth("MONTHS"),40);
	}

	/**
	 * This is a private method that adds a DatePane-box with information about it.
	 */
	private void getDatePane() 
	{
		Label label = new Label(df5.format(date) + "\n" + df1.format(date).toString());
		label.setStyle("-fx-font-size: 13px; -fx-font-family: Segoe UI Semibold; -fx-text-fill: white; -fx-font-weight: 900;");
		
		getChildren().add(label);
		setPrefSize(getDateWidth("DAYS"),80);
	}
	
	/**
	 * This is a private method that will add an extra bit of information to DatePane, namely
	 * what hour it has.
	 */
	private void getHourPane() 
	{
		Label label = new Label(df3.format(date).toString());
		label.setStyle("-fx-font-size: 13px; -fx-font-family: Segoe UI Semibold; -fx-text-fill: white; -fx-font-weight: 900;");
		
		getChildren().add(label);
		setPrefSize(getDateWidth("HOURS"),40);
	}
	
	// Getters
	public double getStartX() { return getLayoutX(); }
	public double getEndX() { return getLayoutX()+dateWidth; }
	
	public Date getDate() { return date; }
	
	public static double getDateWidth(String duration)
	{
		if (duration.equals("HOURS"))
		{
			dateWidth = 70;
		}
		else if (duration.equals("DAYS"))
		{
			dateWidth = 55;
		}
		else if (duration.equals("YEARS"))
		{
			dateWidth = 60;
		}
		else if (duration.equals("MONTHS"))
		{
			dateWidth = 70;
		}
		return dateWidth;
	}
}
