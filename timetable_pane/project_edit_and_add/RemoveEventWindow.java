package timetable_pane.project_edit_and_add;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.scene.layout.HBox;
import timetable_pane.TimeTablePane;
import application.Event;
import application.TimeLine;

/**
 * This class represents a pop-up window.
 * 
 * @author Project Group 3
 * @since 2015-05-11
 */
public class RemoveEventWindow extends Stage {

	private ChoiceBox<Event> choicebox = new ChoiceBox<Event>();

	public Event selectedEvent;
	private Alert alert;

	private String icon = "/fxml/images/timeline.png";

	/**
	 * This is a constructor that has two parameters that will show all the potential events that
	 * can be removed in the TimeTablePane.
	 * 
	 * @param timeline
	 * @param events
	 * 
	 * @see TimeTablePane.java
	 */
	public RemoveEventWindow(TimeLine timeline, ObservableList<Event> events) 
	{
		initStyle(StageStyle.UTILITY);

		for (int i = 0; i < events.size(); i++) 
		{
			Event curr = events.get(i);			
			choicebox.getItems().add(curr);
		}
		choicebox.setMaxWidth(340);

		Label eventLabel = new Label("Event:");
		eventLabel.setStyle("-fx-font-size: 11pt; -fx-font-family: Segoe UI Semibold;");

		Label headerlabel = new Label("Event Manager");
		headerlabel.setStyle("-fx-font-size: 14pt; -fx-font-family: Segoe UI Light;");
		headerlabel.setPadding(new Insets(10,0,20,0));

		Button deleteEventBtn = new Button("Remove Event");
		deleteEventBtn.setPrefWidth(140);
		deleteEventBtn.setOnAction(buttonEvent -> {

			if (getSelectedEvent() == null)
			{
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You have to select an event you want to remove!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon ).toString()));

				alert.showAndWait();
			}
			else 
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Look, a Confirmation Dialog");
				alert.setContentText("Are you sure?");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource("/fxml/images/timeline.png").toString()));

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) 
				{
					selectedEvent = getSelectedEvent();
					close();
				} 
				else 
				{
					selectedEvent = null;
					alert.close();
				}
			}
		});

		VBox vbox99 = new VBox(10);
		vbox99.getChildren().addAll(headerlabel);

		VBox vbox0 = new VBox(10);
		vbox0.getChildren().addAll(eventLabel, choicebox);

		VBox vbox1 = new VBox(20);
		vbox1.getChildren().addAll(vbox99, vbox0);

		HBox root0 = new HBox();
		root0.getChildren().add(deleteEventBtn);
		root0.setPadding(new Insets(0,0,0,200));

		VBox root99 = new VBox(80);
		root99.getChildren().addAll(vbox1, root0);
		root99.setPadding(new Insets(20,20,20,20));

		root99.setStyle("-fx-background-color: white;");

		Scene scene = new Scene(root99,400,320);
		
		setScene(scene);
		setResizable(false);
	}

	/**
	 * This is a public method that will return a selected event to be removed.
	 * @return selectedEvent
	 */
	public Event getSelectedEvent() 
	{
		selectedEvent = choicebox.getSelectionModel().getSelectedItem();
		return selectedEvent;
	}
}
