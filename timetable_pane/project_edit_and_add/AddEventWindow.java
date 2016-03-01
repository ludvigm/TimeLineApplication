package timetable_pane.project_edit_and_add;

import java.text.ParseException;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class AddEventWindow extends Stage {

	private TimePicker tp1, tp2;

	public Date startdate, enddate;
	public String name, description;

	private TextField nameField;
	private TextArea descriptionArea;

	public boolean checkBoxIsSelected;
	public Date start, end;

	public String duration;

	private Alert alert;

	private String icon = "/fxml/images/timeline.png";

	public AddEventWindow(TimeLine t) 
	{
		initStyle(StageStyle.UTILITY);

		Label nameLabel = new Label("Title:" );
		nameLabel.setStyle("-fx-font-size: 11pt; -fx-font-family: Segoe UI Semibold;");

		Label descriptionLabel = new Label("Description: ");
		descriptionLabel.setStyle("-fx-font-size: 11pt; -fx-font-family: Segoe UI Semibold;");

		Label headerlabel = new Label("Event Manager");
		headerlabel.setStyle("-fx-font-size: 14pt; -fx-font-family: Segoe UI Light;");
		headerlabel.setPadding(new Insets(10,0,20,0));
		
		Label l3 = new Label("Non-durational?");

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

		descriptionArea = new TextArea();
		descriptionArea.setMaxWidth(340);
		descriptionArea.setPrefRowCount(3);
		descriptionArea.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (descriptionArea.getText().length() >= 150) 
					{
						descriptionArea.setText(descriptionArea.getText().substring(0, 150));
					}
				}
			}
		});
		descriptionArea.setPromptText("150 characters or less");
		descriptionArea.setWrapText(true);

		tp1 = new TimePicker("START");
		tp2 = new TimePicker("END");

		Button b1 = new Button("Add Event");
		b1.setPrefWidth(140);
		b1.setOnAction(event -> {
			try 
			{
				errorHandleDatePicker(tp1, tp2, t);

			} catch (Exception e1) 
			{	
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, make sure that dates have been added to both date fields if it is not a non-durational event!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
		});

		CheckBox cb = new CheckBox();

		VBox nameBox = new VBox(10);
		nameBox.getChildren().addAll(nameLabel, nameField);
		nameBox.setPadding(new Insets(0,20,0,0));

		VBox hbox = new VBox(20);
		hbox.getChildren().addAll(tp1, tp2);
		hbox.setPadding(new Insets(20,20,20,0));

		cb.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (cb.isSelected()) 
				{			
					tp2.setDisable(true);
					checkBoxIsSelected = true;
				} 
				else 
				{
					tp2.setDisable(false);
					checkBoxIsSelected = false;
				}
			}
		});

		HBox root100 = new HBox();
		root100.getChildren().addAll(cb, l3);

		VBox descriptionBox = new VBox(10);
		descriptionBox.getChildren().addAll(descriptionLabel, descriptionArea);
		descriptionBox.setPadding(new Insets(0,20,0,0));

		HBox root0 = new HBox(85);
		root0.getChildren().addAll(root100, b1);
		
		VBox vbox = new VBox();
		vbox.getChildren().add(headerlabel);

		VBox root99 = new VBox(20);
		root99.getChildren().addAll(vbox, nameBox, descriptionBox, hbox, root0);
		root99.setPadding(new Insets(20,20,20,20));

		root99.setStyle("-fx-background-color: white;");

		Scene scene = new Scene(root99,400,550);

		setScene(scene);
		setResizable(false);
	}

	/**
	 * This is a private method that will prevent the user from submitting bad
	 * data that otherwise could the program to crash.
	 * 
	 * @param tp1
	 * @param tp2
	 * @param t
	 * @throws ParseException
	 */
	private void errorHandleDatePicker(TimePicker tp1, TimePicker tp2, TimeLine t) throws ParseException 
	{
		if (checkBoxIsSelected == true)
		{
			Date date1 = tp1.getDate();

			if (date1.before(t.getStart())|| date1.after(t.getEnd()))
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Out of timeline scope");
				alert.setHeaderText("Event date out of scope.");
				alert.setContentText("You cannot plan an event outside the scope of the timeline.");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (nameField.getText().isEmpty() || descriptionArea.getText().isEmpty()) 
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you cannot create an event with no title or description!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (tp1.getHour() < 0 || tp1.getHour() > 23)
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
			else if ((date1.after(t.getStart()) || date1.equals(t.getStart())) && (date1.before(t.getEnd()) || date1.equals(t.getEnd())) && !nameField.getText().isEmpty() && !descriptionArea.getText().isEmpty()) 
			{
				start = tp1.getDate();

				name = nameField.getText();
				description = descriptionArea.getText();

				duration = t.getDuration();

				close();	
			}
		}
		else
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
			else if ((date1.before(t.getStart()) && !date1.equals(t.getStart())) || (date1.after(t.getEnd()) || date2.before(t.getStart())) || (date2.after(t.getEnd()) && !date2.equals(t.getEnd()))) 
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Out of timeline scope");
				alert.setHeaderText("Event date out of scope.");
				alert.setContentText("You cannot plan an event outside the scope of the timeline.");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (nameField.getText().isEmpty() || descriptionArea.getText().isEmpty()) 
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you cannot create an event with no title or description!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
			else if (tp1.getHour() < 0 || tp1.getHour() > 23)
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
			else if ((date1.after(t.getStart()) || date1.equals(t.getStart())) || (date1.before(t.getEnd()) && date2.after(t.getStart())) || (date2.before(t.getEnd()) || date2.equals(t.getEnd())) && !nameField.getText().isEmpty() && !descriptionArea.getText().isEmpty())			
			{
				start = tp1.getDate();
				end = tp2.getDate();

				name = nameField.getText();
				description = descriptionArea.getText();

				duration = t.getDuration();

				close();	
			}
		}	
	}

	private TextField getLetterField() 
	{
		return new TextField() {
			@Override public void replaceText(int start, int end, String text) {
				if (text.matches("[a-ö]*") || text.matches("[A-Ö]*") || text.matches(" ") || text.matches("[0-9]*")) {
					super.replaceText(start, end, text);
				}
			}

			@Override public void replaceSelection(String text) {
				if (text.matches("[a-ö]*") || text.matches("[A-Ö]*") || text.matches(" ") || text.matches("[0-9]*")) {
					super.replaceSelection(text);
				}
			}
		};
	}
}