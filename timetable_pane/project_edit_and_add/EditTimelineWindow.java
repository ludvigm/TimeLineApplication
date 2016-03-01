package timetable_pane.project_edit_and_add;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.scene.layout.HBox;
import timetable_pane.project_edit_and_add.timepicker.TimePicker;
import application.Event;
import application.TimeLine;

/**
 * This class represents a pop-up window.
 * 
 * @author Project Group 3
 * @since 2015-05-11
 */
public class EditTimelineWindow extends Stage {

	private TimePicker tp1, tp2;
	public Date startdate, enddate;
	public String name;

	private ToggleButton tb1, tb2, tb3, tb4;
	private TextField nameField;

	public String duration;

	public Event selectedEvent;
	private Alert alert;
	private String icon = "/fxml/images/timeline.png";

	/**
	 * This is a constructor with three parameters.
	 * 
	 * @param timeline
	 * @param events
	 * @param duration
	 */
	public EditTimelineWindow(TimeLine timeline, ObservableList<Event> events, String duration) 
	{
		initStyle(StageStyle.UTILITY);
		this.duration = duration;

		tp1 = new TimePicker("START");
		tp2 = new TimePicker("END");

		tp1.setValue(timeline.getStart());
		tp2.setValue(timeline.getEnd());

		Label nameLabel = new Label("Title: " );
		nameLabel.setStyle("-fx-font-size: 11pt; -fx-font-family: Segoe UI Semibold;");

		nameField = getLetterField();
		nameField.setMaxWidth(340);
		nameField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (nameField.getText().length() >= 50) 
					{
						nameField.setText(nameField.getText().substring(0, 50));
					}
				}
			}
		});
		nameField.setPromptText("50 characters or less");


		Label headerlabel = new Label("TimeLine Manager");
		headerlabel.setStyle("-fx-font-size: 14pt; -fx-font-family: Segoe UI Light;");
		headerlabel.setPadding(new Insets(10,0,20,0));

		Label l3 = new Label("Duration");
		l3.setStyle("-fx-font-size: 11pt; -fx-font-family: Segoe UI Semibold;");

		ToggleGroup group = new ToggleGroup();

		tb1 = new ToggleButton("Years");
		tb1.setPrefWidth(85);
		tb1.setToggleGroup(group);
		tb1.setStyle("-fx-background-radius: 0;");

		tb2 = new ToggleButton("Months");
		tb2.setPrefWidth(85);
		tb2.setToggleGroup(group);
		tb2.setStyle("-fx-background-radius: 0;");

		tb3 = new ToggleButton("Days");
		tb3.setPrefWidth(85);
		tb3.setToggleGroup(group);
		tb3.setStyle("-fx-background-radius: 0;");

		tb4 = new ToggleButton("Hours");
		tb4.setPrefWidth(85);
		tb4.setToggleGroup(group);
		tb4.setStyle("-fx-background-radius: 0;");

		checkToggleButtons(events);

		Button b1 = new Button("Edit Timeline");
		b1.setPrefWidth(140);
		b1.setOnAction(event -> {
			try 
			{
				errorHandleDatePicker(timeline, events);

			} catch (Exception e1) {	

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, make sure that valid data have been added to all fields!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
		});

		HBox btnBox = new HBox();
		btnBox.getChildren().addAll(tb1, tb2, tb3, tb4);

		VBox datebox = new VBox();
		datebox.getChildren().addAll(tp1, tp2);
		datebox.setPadding(new Insets(20,20,20,0));

		VBox nameBox = new VBox(10);
		nameBox.getChildren().addAll(nameLabel, nameField);

		HBox root0 = new HBox(10);
		root0.getChildren().addAll(b1);
		root0.setPadding(new Insets(0,0,0,195));

		VBox vbox = new VBox(20);
		vbox.getChildren().addAll(headerlabel, nameBox, l3, btnBox);

		VBox root99 = new VBox(30);
		root99.getChildren().addAll(vbox, datebox, root0);
		root99.setPadding(new Insets(20,20,20,20));

		root99.setStyle("-fx-background-color: white;");

		Scene scene = new Scene(root99,400,530);

		setScene(scene);
		setResizable(false);
	}

	/**
	 * This is a private method that will disable toggle-buttons depending on the
	 * variable duration, which leads to a better error-handling. 
	 * @param events 
	 */
	private void checkToggleButtons(ObservableList<Event> events) 
	{
		if (events.size() != 0)
		{
			if (duration.equals("YEARS"))
			{
				tb2.setDisable(true);
				tb3.setDisable(true);
				tb4.setDisable(true);

				tb1.setSelected(true);

				tp1.disableHourSpinner(true);
				tp1.disableMinuteSpinner(true);

				tp2.disableHourSpinner(true);
				tp2.disableMinuteSpinner(true);
			}
			else if (duration.equals("MONTHS"))
			{
				tb1.setDisable(true);
				tb3.setDisable(true);
				tb4.setDisable(true);

				tb2.setSelected(true);

				tp1.disableHourSpinner(true);
				tp1.disableMinuteSpinner(true);

				tp2.disableHourSpinner(true);
				tp2.disableMinuteSpinner(true);
			}
			else if (duration.equals("DAYS"))
			{
				tb1.setDisable(true);
				tb2.setDisable(true);
				tb4.setDisable(true);

				tb3.setSelected(true);

				tp1.disableHourSpinner(true);
				tp1.disableMinuteSpinner(true);

				tp2.disableHourSpinner(true);
				tp2.disableMinuteSpinner(true);
			}
			else if (duration.equals("HOURS"))
			{
				tb1.setDisable(true);
				tb2.setDisable(true);
				tb3.setDisable(true);
				tb4.setSelected(true);

				tp1.disableHourSpinner(false);
				tp1.disableMinuteSpinner(false);

				tp2.disableHourSpinner(false);
				tp2.disableMinuteSpinner(true);
			}
		}
		else 
		{
			tb1.setOnAction(event -> {

				if (tb1.isSelected())
				{
					tp1.disableHourSpinner(true);
					tp1.disableMinuteSpinner(true);

					tp2.disableHourSpinner(true);
					tp2.disableMinuteSpinner(true);
				}
				else 
				{
					tp1.disableHourSpinner(false);
					tp1.disableMinuteSpinner(false);

					tp2.disableHourSpinner(false);
					tp2.disableMinuteSpinner(false);
				}
			});

			tb2.setOnAction(event -> {

				if (tb2.isSelected())
				{
					tp1.disableHourSpinner(true);
					tp1.disableMinuteSpinner(true);

					tp2.disableHourSpinner(true);
					tp2.disableMinuteSpinner(true);
				}
				else 
				{
					tp1.disableHourSpinner(false);
					tp1.disableMinuteSpinner(false);

					tp2.disableHourSpinner(false);
					tp2.disableMinuteSpinner(false);
				}
			});

			tb3.setOnAction(event -> {

				if (tb3.isSelected())
				{
					tp1.disableHourSpinner(true);
					tp1.disableMinuteSpinner(true);

					tp2.disableHourSpinner(true);
					tp2.disableMinuteSpinner(true);
				}
				else 
				{
					tp1.disableHourSpinner(false);
					tp1.disableMinuteSpinner(false);

					tp2.disableHourSpinner(false);
					tp2.disableMinuteSpinner(false);
				}
			});

			tb4.setOnAction(event -> {

				if (tb4.isSelected())
				{
					tp1.disableHourSpinner(false);
					tp1.disableMinuteSpinner(true);

					tp2.disableHourSpinner(false);
					tp2.disableMinuteSpinner(true);
				}
				else 
				{
					tp1.disableHourSpinner(false);
					tp1.disableMinuteSpinner(false);

					tp2.disableHourSpinner(false);
					tp2.disableMinuteSpinner(false);
				}
			});
		}
	}

	/**
	 * This is a private method that will basically prevent the user to enter
	 * bad data.
	 * 
	 * @param timeline
	 * @param events
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	private void errorHandleDatePicker(TimeLine timeline, ObservableList<Event> events) throws ParseException 
	{
		Date date1 = tp1.getDate();
		Date date2 = tp2.getDate();

		Date earliestStartDate;
		Date latestEndDate;

		if (events.size() == 0)
		{
			if (date2.before(date1))
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, make sure that the first date comes before the second date!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (nameField.getText().isEmpty())
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you have not given a name to the timeline!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (date2.after(date1) && !nameField.getText().isEmpty())
			{
				Calendar cal = new GregorianCalendar();

				if (tb1.isSelected()) 
				{
					cal.set(date1.getYear()+1900, 0, 1, 0, 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, 11, 31, 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "YEARS";
					close();
				}
				else if (tb2.isSelected())
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), 1, 0, 0);
					startdate = cal.getTime();

					cal.set(Calendar.MONTH-1, date2.getMonth());
					cal.set(date2.getYear()+1900, date2.getMonth(), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "MONTHS";
					close();
				}
				else if (tb3.isSelected())
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), 0, 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "DAYS";
					close();
				}
				else if (tb4.isSelected())
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), date1.getHours(), 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), date2.getHours(), 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "HOURS";
					close();

				}	
			}
		}
		else 
		{
			earliestStartDate = returnEarliestStartDate(events);
			latestEndDate = returnLatestEndDate(events);

			Calendar cal = new GregorianCalendar();

			if (timeline.getDuration().equals("YEARS")) 
			{
				cal.set(date1.getYear()+1900, 0, 1, 0, 0);
				date1 = cal.getTime();

				cal.set(date2.getYear()+1900, 11, 31, 23, 59);
				date2 = cal.getTime();
			}
			else if (timeline.getDuration().equals("MONTHS"))
			{
				cal.set(date1.getYear()+1900, date1.getMonth(), 1, 0, 0);
				date1 = cal.getTime();

				cal.set(Calendar.MONTH-1, date2.getMonth());
				cal.set(date2.getYear()+1900, date2.getMonth(), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59);
				date2 = cal.getTime();
			}
			else if (timeline.getDuration().equals("DAYS"))
			{
				cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), 0, 0);
				date1 = cal.getTime();

				cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), 23, 59);
				date2 = cal.getTime();
			}
			else if (timeline.getDuration().equals("HOURS"))
			{
				cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), date1.getHours(), 0);
				date1 = cal.getTime();

				cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), date2.getHours(), 59);
				date2 = cal.getTime();
			}	
			
			if (date2.before(date1))
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, make sure that the first date comes before the second date!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if ((date1.after(earliestStartDate) && !date1.equals(earliestStartDate)) || (date2.before(latestEndDate) && !date2.equals(latestEndDate)))
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, there are events scheduled at this point of time!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (nameField.getText().isEmpty())
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you have not given a name to the timeline!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (!(tb1.isSelected() || tb2.isSelected() || tb3.isSelected() || tb4.isSelected()))
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you have not selected a duration to the timeline!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else 
			{
				if (timeline.getDuration().equals("YEARS")) 
				{
					cal.set(date1.getYear()+1900, 0, 1, 0, 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, 11, 31, 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "YEARS";
					close();
				}
				else if (timeline.getDuration().equals("MONTHS"))
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), 1, 0, 0);
					startdate = cal.getTime();

					cal.set(Calendar.MONTH-1, date2.getMonth());
					cal.set(date2.getYear()+1900, date2.getMonth(), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "MONTHS";
					close();
				}
				else if (timeline.getDuration().equals("DAYS"))
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), 0, 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), 23, 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "DAYS";
					close();
				}
				else if (timeline.getDuration().equals("HOURS"))
				{
					cal.set(date1.getYear()+1900, date1.getMonth(), date1.getDate(), date1.getHours(), 0);
					startdate = cal.getTime();

					cal.set(date2.getYear()+1900, date2.getMonth(), date2.getDate(), date2.getHours(), 59);
					enddate = cal.getTime();

					name = nameField.getText();

					duration = "HOURS";
					close();
				}	
			}
		}	
	}

	/**
	 * This is a private method that will return the earliest start date in 
	 * the observable list events, in order to prevent the user to enter a value outside
	 * the timeline's start- and end date.
	 * 
	 * @param events
	 * @return earliestStartDate
	 */
	private Date returnEarliestStartDate(ObservableList<Event> events) 
	{
		Date earliestStartDate;

		if (events.get(0).getEndDate() == null)
		{
			earliestStartDate = events.get(0).getStartDate();
		}
		else 
		{
			earliestStartDate = events.get(0).getStartDate();
		}
		
		if (events.size() == 1 && events.get(0).getStartDate() != null)
		{
			return earliestStartDate = events.get(0).getStartDate();
		}
		else if (events.size() == 1 && events.get(0).getStartDate() == null)
		{
			return earliestStartDate = events.get(0).getStartDate();
		}
		else 
		{
			for (int i = 0; i < events.size(); i++)
			{
				if (events.get(i).getStartDate().before(earliestStartDate)) 
				{
					earliestStartDate = events.get(i).getStartDate();	
				}
			}
		}
		return earliestStartDate;	
	}

	/**
	 * This is a private method that will return the latest end date in 
	 * the observable list events, in order to prevent the user to enter a value outside
	 * the timeline's start- and end date.
	 * 
	 * @param events
	 * @return latestEndDate
	 */
	private Date returnLatestEndDate(ObservableList<Event> events) 
	{
		Date latestEndDate;

		if (events.get(0).getEndDate() == null)
		{
			latestEndDate = events.get(0).getStartDate();
		}
		else 
		{
			latestEndDate = events.get(0).getEndDate();
		}
		
		if (events.size() == 1 && events.get(0).getEndDate() != null)
		{
			return latestEndDate = events.get(0).getEndDate();
		}
		else if (events.size() == 1 && events.get(0).getEndDate() == null)
		{
			return latestEndDate = events.get(0).getStartDate();
		}
		else 
		{
			for (int i = 0; i < events.size(); i++)
			{
				if (events.get(i).getEndDate() != null) 
				{
					if (events.get(i).getEndDate().after(latestEndDate))
					{
						latestEndDate = events.get(i).getEndDate();	
					}
				}
				else if (events.get(i).getEndDate() == null) // In case the end-date is null
				{
					if (events.get(i).getStartDate().after(latestEndDate)) 
					{
						latestEndDate = events.get(i).getStartDate();	
					}
				}
			}
		}
		return latestEndDate;	
	}

	private TextField getLetterField() {

		return new TextField() {
			@Override public void replaceText(int start, int end, String text) {
				if (text.matches("[a-ö]*") || text.matches("[A-Ö]*")) {
					super.replaceText(start, end, text);
				}
			}

			@Override public void replaceSelection(String text) {
				if (text.matches("[a-ö]*") || text.matches("[A-Ö]*")) {
					super.replaceSelection(text);
				}
			}
		};
	}

}