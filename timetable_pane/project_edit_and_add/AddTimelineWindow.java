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
import application.TimeLine;

/**
 * This class represents a pop-up window.
 * 
 * @author Project Group 3
 * @since 2015-05-11
 */
public class AddTimelineWindow extends Stage {

	private TimePicker tp1, tp2;

	public Date startdate, enddate;
	public String name;

	private ToggleButton tb1, tb2, tb3, tb4;
	private TextField nameField;

	public String duration;

	private String icon = "/fxml/images/timeline.png";

	private Alert alert;

	public AddTimelineWindow(ObservableList<TimeLine> timelines) 
	{ 
		initStyle(StageStyle.UTILITY);

		tp1 = new TimePicker("START");
		tp2 = new TimePicker("END");

		Label nameLabel = new Label("Title:" );
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
		
		checkToggleButtons();
		
		Button createTimelineBtn = new Button("Add TimeLine");	
		createTimelineBtn.setPrefWidth(140);
		createTimelineBtn.setOnAction(event -> {
			try 
			{
				errorHandleDatePicker(timelines);
				
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
		root0.getChildren().addAll(createTimelineBtn);
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
	 * Disables toggle-buttons whenever it is appropriate.
	 */
	private void checkToggleButtons() 
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

	/**
	 * This is a private method that will prevent the user from submitting bad
	 * data that could lead the program to a crash.
	 * @param timelines 
	 * @param timelines 
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	private void errorHandleDatePicker(ObservableList<TimeLine> timelines) throws ParseException 
	{
		Date date1 = tp1.getDate();
		Date date2 = tp2.getDate();

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
		else if (date2.after(date1) && !(tb1.isSelected() || tb2.isSelected() || tb3.isSelected() || tb4.isSelected()))
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, you have not selected a duration to the timeline!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();
		}
		else if (date2.after(date1) && (tb1.isSelected() || tb2.isSelected() || tb3.isSelected() || tb4.isSelected()) && nameField.getText().isEmpty())
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, you cannot create a nameless timeline!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();
		}
		else if (date2.after(date1) && (tb1.isSelected() || tb2.isSelected() || tb3.isSelected() || tb4.isSelected()) && !nameField.getText().isEmpty() && errorHandleName(timelines))
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
				if (tp1.getHour() < 0 || tp1.getHour() > 23)
				{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Look, an Error Dialog");
					alert.setContentText("Ooops, make sure that the hours are within a 24 hour time format!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();	
				}	
				else if (tp1.getMinute() < 0 || tp1.getMinute() > 59)
				{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Look, an Error Dialog");
					alert.setContentText("Ooops, make sure that the hours are within a 60 minute time format!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();
				}	
				else if (tp2.getHour() < 0 || tp2.getHour() > 23)
				{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Look, an Error Dialog");
					alert.setContentText("Ooops, make sure that the hours are within a 24 hour time format!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();
				}	
				else if (tp2.getMinute() < 0 || tp2.getMinute() > 59)
				{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Look, an Error Dialog");
					alert.setContentText("Ooops, make sure that the hours are within a 60 minute time format!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();	
				}	
				else 
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

	//some additional checks on name..
	private boolean errorHandleName(ObservableList<TimeLine> timelines) 
	{
		String name = nameField.getText();
		if (name.equals("null")) 
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Name null");
			alert.setContentText("You cannot name your timeline \"null\" ");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();

			return false;
		} 
		else if (alreadyExists(timelines, name)) 
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Already exists");
			alert.setContentText("There is already a timeline with that name.");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();

			return false;
		} 
		else 
		{
			return true;
		}
	}

	private boolean alreadyExists(ObservableList<TimeLine> timelines, String name) 
	{
		for (TimeLine t : timelines) 
		{
			if (t.getName().toLowerCase().equals(name.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}

	private TextField getLetterField() 
	{
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


