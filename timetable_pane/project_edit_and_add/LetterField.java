package timetable_pane.project_edit_and_add;

import javafx.scene.control.TextField;

public class LetterField {

	public TextField getLetterField() {

		return new TextField() {
			@Override public void replaceText(int start, int end, String text) {
				if (text.matches("[0-9]*")) {
					super.replaceText(start, end, text);
				}
			}
			@Override public void replaceSelection(String text) {
				if (text.matches("[0-9]*")) {
					super.replaceSelection(text);
				}
			}
		};
	}
}
