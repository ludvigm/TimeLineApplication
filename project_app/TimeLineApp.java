package project_app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This is the main controller of the application.
 * 
 * @see TimeLineView.java
 * 
 * @author Project Group 3
 * @since 2015-05-26
 */
public class TimeLineApp extends Application {

	private String projectIcon = "/fxml/images/timeline.png";

	private Stage thestage;
	private Scene scene1, scene2;

	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException, ParseException {

		thestage = primaryStage;

		Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/WelcomePage.fxml"));		
		TimeLineView root2 = new TimeLineView();

		primaryStage.getIcons().add(new Image(projectIcon));
		primaryStage.setResizable(false);

		scene1 = new Scene(root1,1000,650);
		scene2 = new Scene(root2,1000,650);

		scene2.getStylesheets().add(this.getClass().getResource("/fxml/stylesheet/DarkTheme.css").toExternalForm());
		
		primaryStage.setScene(scene1);
		primaryStage.show();

		playTimeLineAnimation();
	}

	/**
	 * Displays scene for 4 seconds and then displays a new page (TimeLineView).
	 */
	private void playTimeLineAnimation() 
	{
		Timeline timeline = new Timeline();

		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(4),
						new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {	
						thestage.setScene(scene2);	
					}
				}));
		timeline.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
