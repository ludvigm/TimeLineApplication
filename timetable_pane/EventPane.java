package timetable_pane;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.controlsfx.control.PopOver;

/**
 * This class makes it possible for a graphical event to be placed within 
 * TimeTablePane. It has two constructors (with parameters) which both have two
 * ways of displaying EventPane in TimeTablePane. One represents non-durative 
 * event and the other one represents a durative event.
 * 
 * @author Project Group 3
 * @since 2015-05-10
 */
public class EventPane extends Pane {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private double timeSpan = 0;

	private Date startdate, enddate;

	private Date timetableStart, timetableEnd;
	private Date startNonEvent;

	private String duration;

	private double posX;
	private double firstXValue;
	private double lastXValueOfRect;
	private double lastXValue;
	private double middleXValue;

	public String description;

	/**
	 * This is an empty constructor.
	 */
	public EventPane() {}

	/**
	 * This constructor calls the methods that are necessary for displaying a
	 * durational event within TimeTablePane.
	 * 
	 * @param startdate
	 * @param enddate
	 * @param timetableStart
	 * @param timetableEnd
	 * @param duration
	 * @param description
	 */
	public EventPane(Date startdate, Date enddate, Date timetableStart, Date timetableEnd, String duration, String description)
	{
		this.startdate = startdate;
		this.enddate = enddate;

		// Duration can either be "YEARS", "MONTHS", "DAYS", or "HOURS"
		this.duration = duration;
		this.description = description;

		this.setTimetableStart(timetableStart);
		this.setTimetableEnd(timetableEnd);

		setStyle("-fx-background-radius: 10px; -fx-padding: 10;");

		getTimeSpan();
		getEventPane();
	}

	/**
	 * This constructor calls the methods that are necessary for displaying a
	 * non-durational event within TimeTablePane.
	 * 
	 * @param startNonEvent
	 * @param timetableStart
	 * @param timetableEnd
	 * @param duration
	 * @param description
	 */
	public EventPane(Date startNonEvent, Date timetableStart, Date timetableEnd, String duration, String description) 
	{
		this.startNonEvent = startNonEvent;

		this.setTimetableStart(timetableStart);
		this.setTimetableEnd(timetableEnd);

		// Duration can either be "YEARS", "MONTHS", "DAYS", or "HOURS"
		this.duration = duration;
		this.description = description;

		getNonEventPane();
	}

	/**
	 * This is a private method with help of the private method "positionEvent" that calculates 
	 * the position of the durational event.
	 */
	private void getEventPane() 
	{
		lastXValueOfRect = timeSpan*DatePane.dateWidth;
		Rectangle rect = new Rectangle(lastXValueOfRect, 20, Color.GREENYELLOW);

		posX = positionEvent(timetableStart, startdate, duration);
		firstXValue = posX*DatePane.dateWidth;

		rect.setLayoutX(firstXValue);
		getChildren().add(rect);
		PopOver pop = new PopOver();
		rect.setOnMousePressed(event -> {

			String start = df.format(startdate);
			String end = df.format(enddate);

			Label label0 = new Label("Start Date: " + start);
			label0.setStyle("-fx-font-size: 10pt;");
			label0.setMaxWidth(280);
			label0.setWrapText(true);

			Label label1 = new Label("End Date: " + end);
			label1.setStyle("-fx-font-size: 10pt;");
			label1.setMaxWidth(280);
			label1.setWrapText(true);

			Label label2 = new Label("Description: " + getDescription());
			label2.setStyle("-fx-font-size: 10pt;");
			label2.setMaxWidth(280);
			label2.setWrapText(true);

			Label label3 = new Label("Event " + getTimeLeft());
			label3.setStyle("-fx-font-size: 10pt;");
			label3.setMaxWidth(280);
			label3.setWrapText(true);

			VBox vbox0 = new VBox(5);
			vbox0.getChildren().addAll(label0, label1, label2, label3);

			Button cancelBtn = new Button("Close");
			cancelBtn.setOnMouseClicked(mouse -> {
				pop.hide();
			});

			VBox vbox1 = new VBox();
			vbox1.getChildren().add(cancelBtn);
			vbox1.setPadding(new Insets(0,0,0,200));

			VBox vbox = new VBox(20);
			vbox.getChildren().addAll(vbox0, vbox1);
			vbox.setPadding(new Insets(20,20,20,20));    

			pop.setContentNode(vbox);

			if(!pop.isShowing()) {
				pop.show(rect);
				pop.setDetachable(false);  
			} 
		});
	}

	/**
	 * This is a private method with help of the private method "positionNonEvent" that calculates 
	 * the position of the non-durational event.
	 */
	private void getNonEventPane() 
	{
		lastXValueOfRect = 20;
		Rectangle rect = new Rectangle(lastXValueOfRect,20, Color.CORNFLOWERBLUE);
		rect.setRotate(45);

		posX = positionNonEvent(timetableStart, startNonEvent, duration);
		firstXValue = posX*DatePane.dateWidth -10;

		rect.setOnMousePressed(event -> {

			PopOver pop = new PopOver();

			String start = df.format(startNonEvent);

			Label label0 = new Label("Start Date: " + start);
			label0.setStyle("-fx-font-size: 10pt;");
			label0.setMaxWidth(280);
			label0.setWrapText(true);

			Label label1 = new Label("Description: " + getDescription());
			label1.setStyle("-fx-font-size: 10pt;");
			label1.setMaxWidth(280);
			label1.setWrapText(true);

			Label label2 = new Label("Event " + getTimeLeft());
			label2.setStyle("-fx-font-size: 10pt;");
			label2.setMaxWidth(280);
			label2.setWrapText(true);	

			VBox vbox0 = new VBox(5);
			vbox0.getChildren().addAll(label0, label1, label2);

			Button cancelBtn = new Button("Close");
			cancelBtn.setOnMouseClicked(mouse -> {
				pop.hide();
			});

			VBox vbox1 = new VBox();
			vbox1.getChildren().add(cancelBtn);
			vbox1.setPadding(new Insets(0,0,0,200));

			VBox vbox = new VBox(20);
			vbox.getChildren().addAll(vbox0, vbox1);
			vbox.setPadding(new Insets(20,20,20,20));    

			pop.setContentNode(vbox);

			pop.show(rect);
			pop.setDetachable(false);  
		});

		rect.setLayoutX(firstXValue);
		getChildren().add(rect);
	}

	/**
	 * This is a private method that will calculate the time left to the selected event.
	 * @return stringLeft
	 */
	private String getTimeLeft() 
	{
		Calendar cal = new GregorianCalendar();
		Date todaysDate = cal.getTime();

		String stringLeft = "";

		if (startNonEvent != null)
		{
			if (startNonEvent.before(todaysDate))
			{
				stringLeft = "has already occured";
			}
			else 
			{
				long diff =  startNonEvent.getTime() - todaysDate.getTime();
				int daysLeft = (int) (diff/(24*60*60*1000))/365;

				if (daysLeft > 0)
				{
					stringLeft = "starts in " + daysLeft + " years";
				}
				else if (daysLeft == 0)
				{
					daysLeft = (int) diff/(24*60*60*1000);

					if (daysLeft > 0)
					{
						stringLeft = "starts in " + daysLeft + " days";
					}
					else if (daysLeft == 0)
					{
						daysLeft = (int) diff/(60*60*1000);

						if (daysLeft > 0)
						{
							stringLeft = "starts in " + daysLeft + " hours";
						}
						else if (daysLeft == 0)
						{
							daysLeft = (int) diff/(60*1000);

							if (daysLeft > 0)
							{
								stringLeft = "starts in " + daysLeft + " minutes";
							}
							else if (daysLeft == 0)
							{
								daysLeft = (int) diff/(1000);

								if (daysLeft > 0)
								{
									stringLeft = "starts in " + daysLeft + " seconds";
								}
								else 
								{
									stringLeft = "is ongoing";
								}
							}
						}
					}
				}
			}
		}
		else 
		{
			if (startdate.before(todaysDate) && enddate.before(todaysDate))
			{
				stringLeft = "has already occured";
			}
			else if ((startdate.before(todaysDate) || startdate.equals(todaysDate)) && (enddate.after(todaysDate) || enddate.equals(todaysDate)))
			{
				stringLeft = "is ongoing";
			}
			else 
			{
				long diff =  startdate.getTime() - todaysDate.getTime();
				int daysLeft = (int) (diff/(24*60*60*1000))/365;

				if (daysLeft > 0)
				{
					stringLeft = "starts in " + daysLeft + " years";
				}
				else if (daysLeft == 0)
				{
					daysLeft = (int) diff/(24*60*60*1000);

					if (daysLeft > 0)
					{
						stringLeft = "starts in " + daysLeft + " days";
					}
					else if (daysLeft == 0)
					{
						daysLeft = (int) diff/(60*60*1000);

						if (daysLeft > 0)
						{
							stringLeft = "starts in " + daysLeft + " hours";
						}
						else if (daysLeft == 0)
						{
							daysLeft = (int) diff/(60*1000);

							if (daysLeft > 0)
							{
								stringLeft = "starts in " + daysLeft + " minutes";
							}
							else if (daysLeft == 0)
							{
								daysLeft = (int) diff/(1000);

								if (daysLeft > 0)
								{
									stringLeft = "starts in " + daysLeft + " seconds";
								}
								else 
								{
									stringLeft = "is ongoing";
								}
							}
						}
					}
				}
			}
		}
		return stringLeft;
	}


	/**
	 * This is a private method that calculates the time between start date and end date, 
	 * which then is used to calculate the length of each EventPane.
	 *  
	 * @return timeSpan
	 */
	private double getTimeSpan()
	{
		if (duration.equals("YEARS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			timeSpan = (double) (diff/(24*60*60*1000))/365;

			return timeSpan;
		}
		else if (duration.equals("MONTHS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			timeSpan = (double) (diff/(24*60*60*1000))/30;

			return timeSpan;
		}
		else if (duration.equals("DAYS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			timeSpan = (double) diff/(24*60*60*1000);

			return timeSpan;
		}
		else if (duration.equals("HOURS"))
		{
			long diff = enddate.getTime() - startdate.getTime();
			timeSpan = (double) diff/(60*60*1000);

			return timeSpan;
		}
		return -99;
	}

	/**
	 * This is a private method that calculates where each EventPane (durational event)
	 * will be displayed within TimeTablePane.
	 * 
	 * @param startT -- start date of TimeTablePane
	 * @param startE -- start date of EventPane
	 * @param duration
	 * 
	 * @return diffDays
	 */
	private double positionEvent(Date startT, Date startE, String duration) 
	{
		if (duration.equals("YEARS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) (diff/(24*60*60*1000))/365;

			return diffDays;
		}
		else if (duration.equals("MONTHS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) (diff/(24*60*60*1000))/30;

			return diffDays;
		}
		else if (duration.equals("DAYS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) diff/(24*60*60*1000);

			return diffDays;
		}
		else if (duration.equals("HOURS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) diff/(60*60*1000);

			return diffDays;
		}
		return -99;
	}

	/**
	 * This is a private method that calculates where each EventPane (non-durational event) 
	 * will be displayed within TimeTablePane.
	 * 
	 * @param startT -- start date of TimeTablePane
	 * @param startE -- start date of EventPane
	 * @param duration
	 * 
	 * @return diffDays
	 */
	private double positionNonEvent(Date startT, Date startE, String duration) 
	{
		if (duration.equals("YEARS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) (diff/(24*60*60*1000))/365;

			return diffDays;
		}
		else if (duration.equals("MONTHS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) (diff/(24*60*60*1000))/30;

			return diffDays;
		}
		else if (duration.equals("DAYS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) diff/(24*60*60*1000);

			return diffDays;
		}
		else if (duration.equals("HOURS"))
		{
			long diff = startE.getTime() - startT.getTime();
			double diffDays = (double) diff/(60*60*1000);

			return diffDays;
		}
		return -99;
	}

	/**
	 * This is a method the returns the middle value of the EventPane.
	 * 
	 * @return middleXValue
	 */
	public double getMiddleValue() 
	{
		lastXValue = firstXValue + lastXValueOfRect;
		middleXValue = (firstXValue+lastXValue)/2;

		return middleXValue;
	}

	// Getters and Setters
	public Date getStartdate() { return startdate; }
	public void setStartdate(Date startdate) { this.startdate = startdate; }

	public Date getEnddate() { return enddate; }
	public void setEnddate(Date enddate) { this.enddate = enddate; }

	public Date getTimetableStart() { return timetableStart; }
	public void setTimetableStart(Date timetableStart) { this.timetableStart = timetableStart; }

	public Date getTimetableEnd() { return timetableEnd; }
	public void setTimetableEnd(Date timetableEnd) { this.timetableEnd = timetableEnd; }

	public Date getStartNonEvent() { return startNonEvent; }
	public void setStartNonEvent(Date startNonEvent) { this.startNonEvent = startNonEvent; }

	private String getDescription() { return description; }
}

