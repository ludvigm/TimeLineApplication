package application;

public class Field {
	private String text;
	public Field(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}
	public String toString(){
		return "'"+text+"'";
	}
}
