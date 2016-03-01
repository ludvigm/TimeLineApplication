package application;

import java.util.Date;

public class Event {

	//Fields
	protected String title = null;
	protected String description = null;
	protected Date startDate = null;
	protected Date endDate = null;
	
	private int ID;
	
	private String duration;

	// Construct with set ID
	public Event(int ID, String title, String description, Date start, Date end, String duration) 
	{
		this.ID = ID; 
		this.title = title; 
		this.description = description; 
		this.startDate = start; 
		this.endDate = end;
		this.duration = duration;
	}

	public Event(int ID, String title, String description, Date start, String duration) 
	{
		this.ID = ID; 
		this.title = title; 
		this.description = description; 
		this.startDate = start;
		this.duration = duration;
	}

	public int getID() { return ID; }
	//set&Get
	public String getTitle() { return this.title; }
	public void setTitle(String t) { this.title = t; }

	public String getDescription() { return this.description; }
	public void setDescription(String d) { this.description = d; }

	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate; }

	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; }

	public String getDuration() { return description; }
	public void setDuration(String duration) { this.duration = duration; }


	@Override
	public String toString()
	{
		if (endDate != null)
		{
			return title + ", " + description + ", " + startDate.toString() + ", " + endDate.toString() + ", " + duration;
		}
		else
		{
			return title + ", " + description + ", " + startDate.toString() + ", " + null + ", " + duration;
		}
	}
}

//package lud;
//
//import java.util.Calendar;
//
//public class Event {
//	
//	//Fields
//	protected String title = null;
//	protected String description = null;
//	protected Calendar startDate = null;
//	protected Calendar endDate = null;
//	//Constructor
//	public Event(String title, String description, Calendar start, Calendar end) {
//		this.title = title;
//		this.description = description;
//		this.startDate = start;
//		this.endDate = end;
//	}
//	public Event(String title, String description, Calendar start) {
//		this.title = title;
//		this.description = description;
//		this.startDate = start;
//		this.endDate = null;
//	}
//			
//	//set&Get
//	public String getTitle() {return this.title;}
//	public void setTitle(String t) {this.title = t;}
//	
//	public String getDescription() {return this.description;}
//	public void setDescription(String d) {this.description = d;}
//
//	public Calendar getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Calendar startDate) {
//		this.startDate = startDate;
//	}
//
//	public Calendar getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Calendar endDate) {
//		this.endDate = endDate;
//	}
//	public String toString(){
//		if(endDate !=null){
//			return title + ", " +description +", " + startDate.getTime() + ", " + endDate.getTime();
//		}else{
//			return title + ", " +description +", " + startDate.getTime();
// 
//		}
//	}
//
//}
