package timetable_pane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.controlsfx.control.PopOver;

/**
 * This class includes the classes (@see) that are needed to make up the graphical timeline. 
 * The main function of this class is to make sure that events can be added to the timeline --
 * both non-durational and durational events.
 * 
 * @author Project Group 3
 * @since 2015-05-10
 * 
 * @see DateLinePane.java 
 * @see DatePane.java  
 * @see EventPane.java 
 * @see EventPaneWithTitle.java 
 * @see	DateLinePane.java
 */
public class TimeTablePane extends VBox {

	private Date startdate, enddate;
	private String duration;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private EventPane ep;
	private EventPaneWithTitle epwt;

	/**
	 * This is an empty constructor.
	 */
	public TimeTablePane() { }

	/**
	 * This constructor calls the class DateLinePane which returns the dates between
	 * start date and end date in form of a line of dates.
	 * 
	 * @param startdate
	 * @param enddate
	 * @param duration
	 * @throws InterruptedException 
	 * @throws ParseException 
	 */
	public TimeTablePane(Date startdate, Date enddate, String duration) throws ParseException, InterruptedException
	{		
		this.startdate = startdate;
		this.enddate = enddate;
		this.duration = duration;

		DateAndRangePane darp = new DateAndRangePane(startdate, enddate, duration);
		PopOver pop = new PopOver();
		pop.hide();
		getChildren().addAll(darp);	
		
		darp.setOnMousePressed(event -> {

            Label label0 = new Label("Start Date: " + df.format(startdate));
            label0.setStyle("-fx-font-size: 10pt;");
            label0.setMaxWidth(280);
            label0.setWrapText(true);
            
            Label label1 = new Label("End Date: " + df.format(enddate));
            label1.setStyle("-fx-font-size: 10pt;");
            label1.setMaxWidth(280);
            label1.setWrapText(true);
              
            VBox vbox0 = new VBox(5);
            vbox0.getChildren().addAll(label0, label1);
            
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
            	pop.show(darp);
                pop.setDetachable(false);  
            }
            
		});
	}

	/**
	 * This is a public method that adds an event to TimeTablePane. If the end date of 
	 * the event is null then a non-durational event will be added. The method calls - 
	 * if durational - the classes EventPane and EventPaneWithTitle.
	 * 
	 * @param startEvent
	 * @param endEvent
	 * @param title
	 * @param description
	 * 
	 * @return TimeTablePane
	 */
	public TimeTablePane addEvent(Date startEvent, Date endEvent, String title, String description)
	{	
		if (endEvent == null)
		{
			addNonDurationalEvent(startEvent, title, description);
		}
		else 
		{
			EventPane ep = new EventPane(startEvent, endEvent, startdate, enddate, duration, description);
			EventPaneWithTitle epwt = new EventPaneWithTitle(ep.getMiddleValue(), title);

			getChildren().addAll(epwt, ep);
		}
		return null;	
	}

	/**
	 * This is a private method that adds the non-durational event to the TimeTablePane. It calls
	 * the classes EventPane and EventPaneWithTitle.
	 * 
	 * @param startNonEvent
	 * @param title
	 * @param description
	 */
	private void addNonDurationalEvent(Date startNonEvent, String title, String description)
	{
		ep = new EventPane(startNonEvent, startdate, enddate, duration, description);
		epwt = new EventPaneWithTitle(ep.getMiddleValue()+15, title);
		
		getChildren().addAll(epwt,ep);
	}

	/**
	 * This is a public method that returns the start date of TimeTablePane.
	 * 
	 * @return startdate
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public Date getStartdate() throws ParseException, InterruptedException 
	{
		return startdate;
	}

	/**
	 * This is a public method that returns the end date of TimeTablePane.
	 * 
	 * @return enddate
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public Date getEnddate() throws ParseException, InterruptedException 
	{
		return enddate;
	}

	/**
	 * This is a method that returns the duration of the TimeTablePane. The duration
	 * can either be "YEARS", "MONTHS", "DAYS", or "HOURS". 
	 * 
	 * @return duration
	 */
	public String getDuration()
	{
		return duration;
	}
}