package project_app;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import timetable_pane.TimeTablePane;
import timetable_pane.project_edit_and_add.AddEventWindow;
import timetable_pane.project_edit_and_add.AddTimelineWindow;
import timetable_pane.project_edit_and_add.EditEventWindow;
import timetable_pane.project_edit_and_add.EditTimelineWindow;
import timetable_pane.project_edit_and_add.RemoveEventWindow;
import application.Derby;
import application.Event;
import application.TimeLine;

/**
 * This is a public class that extends VBox. It holds important components
 * such as the scrollpane with TimeTablePane.
 * 
 * @see TimeTablePane.java
 * 
 * @author Project Group 3
 * @since 2015-05-26
 */
public class TimeLineView extends VBox {

	private ScrollPane sp = new ScrollPane();
	private TimeLine selectedTimeLine;

	private ObservableList<TimeLine> timeLines = FXCollections.observableArrayList();
	private final Derby db = new Derby();

	private String icon = "/fxml/images/timeline.png";

	private TimeTablePane timetable;

	private ObservableList<Event> events;
	private ObservableList<TimeTablePane> timetableData;

	private Alert alert;

	private ListView<TimeLine> listView;

	private void setSelectedTimeLine(TimeLine t) {
		this.selectedTimeLine = t;
	}

	private TimeLine getSelectedTimeLine() { return this.selectedTimeLine; }

	public TimeLineView() throws ClassNotFoundException, SQLException, ParseException
	{   
		try 
		{
			db.createDB();
			db.createTimeLineTable();

		} catch(SQLException e) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, the app is already running in background!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();

			Platform.exit();
		}

		// 		(Debug button, prints database timelines and the events contained)
		//		Button debugBtn = new Button("Print DataBase");
		//		debugBtn.setPrefSize(140, 20);

		Button createTimeLine = new Button("Create TimeLine");
		createTimeLine.setPrefSize(160,20);

		Button createEventBtn = new Button("Create Event");
		createEventBtn.setPrefSize(160,20);

		Button deleteTimeLineBtn = new Button("Delete TimeLine");
		deleteTimeLineBtn.setPrefSize(160, 20);

		Button deleteEventBtn = new Button("Delete Event");
		deleteEventBtn.setPrefSize(160, 20);

		Button editTimeLineBtn = new Button("Edit TimeLine");
		editTimeLineBtn.setPrefSize(160, 20);

		Button editEventBtn = new Button("Edit Event");
		editEventBtn.setPrefSize(160, 20);

		// Adding timelines from DB to listView
		timeLines = db.getTimeLines();
		listView = new ListView<TimeLine>(timeLines);
		listView.setPrefHeight(800);
		listView.setMinWidth(200);
		listView.setStyle("-fx-font-family: Segoe UI Semibold; -fx-font-size: 11pt;");

		// Method makes listview display the getName string from TimeLine.(instead of object id)
		listView.setCellFactory(new Callback<ListView<TimeLine>, ListCell<TimeLine>>() {
			public ListCell<TimeLine> call(ListView<TimeLine> param) {
				final ListCell<TimeLine> cell = new ListCell<TimeLine>() {
					@Override
					public void updateItem(TimeLine item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.getName());
						}
					}
				};
				return cell;
			}
		});

		// List with the TimeTablePane data
		timetableData = FXCollections.observableArrayList();

		// ListView Event
		listView.setOnMouseClicked(event -> {
			fakeEvent();
		});

		// Create timeline
		createTimeLine.setOnAction(createTimeline -> {
			AddTimelineWindow window = new AddTimelineWindow(timeLines);
			window.showAndWait();

			if (window.startdate != null) 
			{
				try 
				{
					timetable = new TimeTablePane(window.startdate, window.enddate, window.duration);

				} catch (Exception e) {
					e.printStackTrace();
				}

				timeLines.add(new TimeLine(window.name, window.startdate, window.enddate, window.duration));
				db.insertTimeLine(new TimeLine(window.name, window.startdate, window.enddate, window.duration));

				timetable.setSpacing(20);
				timetable.setPadding(new Insets(20,20,20,20));

				sp.setContent(timetable);
				sp.setMaxHeight(585);
				sp.setStyle("-fx-background: white;");
			}
		});

		// Create event
		createEventBtn.setOnAction(createEvent -> {
			if (selectedTimeLine != null)
			{
				AddEventWindow aew = new AddEventWindow(selectedTimeLine);
				aew.showAndWait();

				if (aew.start != null) 
				{
					try 
					{
						selectedTimeLine.addEvent(new Event(db.getCurrentID(selectedTimeLine), aew.name, aew.description, aew.start, aew.end, aew.duration));
						db.insertEvents(new Event(db.getCurrentID(selectedTimeLine), aew.name, aew.description, aew.start, aew.end, aew.duration), selectedTimeLine);

						timetable.addEvent(aew.start, aew.end, aew.name, aew.description);
						fakeEvent();
						
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
			else 
			{
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You first have to select in which timeline you want to add the event!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();	
			}
		});

		// Delete timeline
		deleteTimeLineBtn.setOnAction(event -> {
			try 
			{
				if (selectedTimeLine == null) 
				{
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You first have to select which timeline in the listview you want to delete!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();
				}
				else 
				{
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Look, a Confirmation Dialog");
					alert.setContentText("Are you sure?");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) 
					{
						for (int i = 0; i < events.size(); i++) 
						{
							db.deleteEvent(events.get(i), selectedTimeLine);
						}
						db.dropTable(getSelectedTimeLine().getName());

						timeLines.remove(getSelectedTimeLine());	
						timetable.getChildren().clear();
					} 
					else 
					{
						alert.close();
					}

				}			
			} catch (Exception e) {
				// Do nothing
			}
		});

		// Delete event button set on action
		deleteEventBtn.setOnAction(event -> {
			try 
			{
				deleteEvent();
				fakeEvent();

			} catch (Exception e) {

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You first have to select in which timeline the wished event is located!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();

			}
		});

		// Edit timeline button set on action
		editTimeLineBtn.setOnAction(event -> {
			try 
			{
				editTimeline();

			} catch (Exception e) {

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You first have to select which timeline in the listview you want to edit!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();	
			}
		});

		// Edit event button set on action
		editEventBtn.setOnAction(editEvent -> {
			try 
			{	
				if (events.size() == 0)
				{
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Ooops, there are no events that you can edit in this timeline!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

					alert.showAndWait();
				}
				else 
				{
					editEvent();
				}	

			} catch (Exception e) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("You first have to select a timeline!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}	          
		});

		//		debugBtn.setOnAction(event -> {
		//			try {
		//				db.print(db.query("TIMELINES"));
		//				try 
		//				{
		//					for (TimeLine t : db.getTimeLines()) 
		//					{
		//						System.out.println("TIMELINE: " + t.getName());
		//						db.print(db.query(t.getName()));
		//					}
		//				} catch (Exception e) {
		//					System.out.println("Parse ERROR");
		//					e.printStackTrace();
		//				}
		//			} catch(SQLException e) {
		//				System.out.println("couldnt print db");
		//			}
		//		});

		sp.setContent(timetable);
		sp.setMaxHeight(585);
		sp.setPrefWidth(1500);
		sp.setStyle("-fx-background: white;");

		MenuBar menuBar = new MenuBar();

		// File menu -> Exit
		Menu fileMenu = new Menu("File");
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		// File menu -> Delete all timelines
		MenuItem deleteAll = new MenuItem("Clear");
		deleteAll.setOnAction(event -> {
			try 
			{
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Look, a Confirmation Dialog");
				alert.setContentText("Are you sure?");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) 
				{
					db.clearDB();

					timeLines.clear();
					timetable.getChildren().clear();
				} 
				else 
				{
					alert.close();
				}	

			} catch (Exception e) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, you have to unselect the timeline in the listview!");

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

				alert.showAndWait();
			}
		});

		fileMenu.getItems().addAll(deleteAll, exitMenuItem);

		// Help menu -> About this project
		Menu helpMenu = new Menu("Help");
		MenuItem about = new MenuItem("About");
		helpMenu.getItems().add(about);
		menuBar.getMenus().addAll(fileMenu, helpMenu);

		HBox buttonBar = new HBox(10);
		buttonBar.getChildren().addAll(createTimeLine, createEventBtn, deleteTimeLineBtn, deleteEventBtn, editTimeLineBtn, editEventBtn);
		buttonBar.setPadding(new Insets(20,20,20,20));

		HBox listAndTimeBox = new HBox(10);
		listAndTimeBox.getChildren().addAll(listView, sp);
		listAndTimeBox.setPadding(new Insets(0,20,20,20));

		getChildren().addAll(menuBar, buttonBar, listAndTimeBox);

		setStyle("-fx-background-color: #36383E;");
		setPadding(new Insets(0,0,0,0));
	}

	/**
	 * This is a private method that deletes a selected event if not null.
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	private void deleteEvent() throws SQLException, ParseException, InterruptedException 
	{
		if (selectedTimeLine == null)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You first have to select in which timeline the wished event is located!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();
		}
		else if (events.size() == 0)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Ooops, there are no events that you can delete from this timeline!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();
		}
		else 
		{
			RemoveEventWindow rew = new RemoveEventWindow(selectedTimeLine, events);
			rew.showAndWait();

			if (rew.selectedEvent != null)
			{
				db.deleteEvent(rew.selectedEvent, selectedTimeLine);
				selectedTimeLine.removeEvent(rew.selectedEvent);
			}
			else
			{
				//User crosses down window, do nothing
				System.out.println("Manual exit");
			}		
		}
	}

	/**
	 * This is a private method that edits the timeline (not the events) if not null.
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	private void editTimeline() throws SQLException, ParseException, InterruptedException 
	{
		if (selectedTimeLine == null) 
		{	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You first have to select which timeline in the listview you want to edit!");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(icon).toString()));

			alert.showAndWait();
		}
		else 
		{
			EditTimelineWindow etw = new EditTimelineWindow(selectedTimeLine, events, selectedTimeLine.getDuration());
			etw.showAndWait();

			ObservableList<Event> obsTmp = events;

			if (etw.startdate != null)
			{
				if (etw.startdate != null)
				{
					for (int i = 0; i < events.size(); i++) 
					{
						db.deleteEvent(events.get(i), selectedTimeLine);
					}
					db.dropTable(getSelectedTimeLine().getName());

					timeLines.remove(getSelectedTimeLine());	
					timetable.getChildren().clear();

					String newName = etw.name;
					Date newStart = etw.startdate;
					Date newEnd = etw.enddate;
					String newDur = etw.duration;

					TimeLine newTimeline = new TimeLine(newName, newStart, newEnd, newDur);

					if (etw.startdate != null) 
					{
						timetable = new TimeTablePane(newStart, newEnd, etw.duration);

						timeLines.add(newTimeline);
						db.insertTimeLine(newTimeline);

						timetable.setSpacing(20);
						timetable.setPadding(new Insets(20,20,20,20));

						sp.setContent(timetable);
						sp.setMaxHeight(585);
						sp.setStyle("-fx-background: white;");
					}

					for (int i = 0; i < obsTmp.size(); i++) 
					{
						Event curr = obsTmp.get(i);

						newTimeline.addEvent(new Event(db.getCurrentID(newTimeline), curr.getTitle(), curr.getDescription(), curr.getStartDate(), curr.getEndDate(), curr.getDuration()));
						db.insertEvents(new Event(db.getCurrentID(newTimeline), curr.getTitle(), curr.getDescription(), curr.getStartDate(), curr.getEndDate(), curr.getDuration()), newTimeline);

						try 
						{
							timetable.addEvent(curr.getStartDate(), curr.getEndDate(), curr.getTitle(), curr.getDescription());

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					selectedTimeLine = newTimeline;
				}
				else
				{
					//User crosses down window, do nothing
					System.out.println("Manual exit");
				}
			}
		}	
	}

	/**
	 * This is a private method that edits an event if not null.
	 * 
	 * @param timetableData
	 * @throws InterruptedException
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	private void editEvent() throws InterruptedException, SQLException, ParseException 
	{	
		EditEventWindow eew = new EditEventWindow(selectedTimeLine, events);
		eew.showAndWait();

		if (eew.start != null)
		{
			Event event = new Event(db.getCurrentID(selectedTimeLine), eew.name, eew.description, eew.start, eew.end, selectedTimeLine.getDuration());
			selectedTimeLine.removeEvent(eew.selectedEvent);

			db.updateEvent(event, selectedTimeLine.getName(), eew.selectedEvent);
			selectedTimeLine.addEvent(event);
			fakeEvent();
		}
		else 
		{
			// User crosses down window, do nothing
			System.out.println("manual exit");
		}		
	}
	
	private void fakeEvent() 
	{
		// Clear scrollpane before loading a new timeline
		sp.getChildrenUnmodifiable().remove(timetableData);			

		setSelectedTimeLine(listView.getSelectionModel().getSelectedItem());			

		// Get events from timeline selected
		events = FXCollections.observableArrayList();
		String s = "";
		try
		{
			s = selectedTimeLine.getName();

			try 
			{
				timetable = new TimeTablePane(selectedTimeLine.getStart(), selectedTimeLine.getEnd(), selectedTimeLine.getDuration());

			} catch (Exception e) {
				e.printStackTrace();
			}
			timetable.setSpacing(20);
			timetable.setPadding(new Insets(20,20,20,20));

			sp.setContent(timetable);
			sp.setMaxHeight(585);
			sp.setStyle("-fx-background: white;");
		} catch (NullPointerException e) {
			// Do nothing if click on empty
		}

		try 
		{
			events = db.getList(s);

		} catch (Exception e) {
			//Do nothing
		}

		for (int i = 0; i < events.size(); i++) 
		{
			Event curr = events.get(i);
			timetableData.add(timetable.addEvent(curr.getStartDate(), curr.getEndDate(), curr.getTitle(), curr.getDescription()));
		}
	}
}
