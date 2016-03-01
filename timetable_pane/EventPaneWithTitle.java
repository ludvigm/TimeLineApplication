package timetable_pane;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * This class makes it possible for a title to be positioned above each EventPane
 * (graphical event).
 * 
 * @author Project Group 3
 * @since 2015-05-10
 */
public class EventPaneWithTitle extends Pane {

	private double positionLabel;

	/**
	 * This is a constructor with the parameters "middleValue" and "title". The
	 * "middleValue" is used for the calculation for the position of "title".
	 * 
	 * @param middleValue
	 * @param title
	 */
	public EventPaneWithTitle(double middleValue, String title) 
	{
		Label label = new Label(title);
		label.setStyle("-fx-font-family: Segoe UI Semibold; -fx-font-size: 13px; -fx-text-fill: #6F6F6F;");
		
		positionLabel = middleValue - (label.getWidth()/2)-50;
		
		label.setLayoutX(positionLabel);
		getChildren().add(label);
	}

}
