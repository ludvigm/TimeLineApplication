package application;

import java.util.ArrayList;
import java.util.Date;

public class TimeLine {
	
	//Fields
	private String name;
	private Date start;
	private Date end;
	private ArrayList<Event> eventList;
	private String duration;
	
	//Constructor
	public TimeLine() 
	{
		name = null;
		start = null;
		end = null;
		eventList = new ArrayList<Event>();
	}
		//constructor with parameters
	public TimeLine(String name, Date start, Date end, String duration) 
	{
		this.name = name;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.eventList = new ArrayList<Event>();
	}
	
	//Set&Get
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start2) {
		this.start = start2;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end2) {
		this.end = end2;
	}

	public ArrayList<Event> getEventList() {
		return eventList;
	}
	
	public String getDuration() {
		return duration;
	}
	
	//Methods
	
	public void addEvent(Event e) {
		this.eventList.add(e);
	}
	public void removeEvent(Event e) {
		this.eventList.remove(e);
	}
	public void addAllEvents(Event...args) {
		  for(Event arg : args) {
		   this.eventList.add(arg);
		  }
		 }
}

