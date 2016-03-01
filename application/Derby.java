package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Derby {
	public final String DRIVER;
	public final String JDBC_URL;
	
	static Connection connection;

	public Derby() {
		DRIVER = "org.apache.derby.jdbc.EmbeddedDriver"; 
		JDBC_URL = "jdbc:derby:zadb;create=true";

		connection = null;
	}
	public void createTimeLineTable(){
		try 
		{
			connection.createStatement().execute("create table TIMELINES(Title varchar(50), StartDate varchar(16), EndDate varchar (16), Duration varchar(160))");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void createEventsTable(String name) 
	{
		try 
		{
			connection.createStatement().execute("create table " + name + "(ID varchar(10), Title varchar(50), Description varchar(160), StartDate varchar(16), EndDate varchar(16), Duration varchar(160))");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertTimeLine(TimeLine timeline)
	{
		String query = "(" + new Field(timeline.getName()) + ", " + new Field(dateToString(timeline.getStart())) + ", " + new Field(dateToString(timeline.getEnd())) + ", " + new Field(timeline.getDuration()) + ")";
		System.out.println(query);
		try { 
			//questions marks mean they are anonymous
			connection.createStatement().execute("Insert into TIMELINES values " + query);
			createEventsTable(timeline.getName());

			for (Event e : timeline.getEventList())
			{
				insertEvents(e, timeline);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}

	public void createDB() throws SQLException, ClassNotFoundException
	{
		Class.forName(DRIVER);
		connection = DriverManager.getConnection(JDBC_URL);
		//createTimeLineTable();
	}

	@SuppressWarnings("unused")
	public ObservableList<TimeLine> getTimeLines() throws SQLException, ParseException 
	{
		ResultSet set = query("TIMELINES");
		ObservableList<TimeLine> timeLines = FXCollections.observableArrayList();
		String title;
		Time startDate;
		Time endDate;
		String duration;

		int columns = set.getMetaData().getColumnCount();
		while(set.next()) 
		{
			title = set.getString(1);
			startDate = getDate(set.getString(2));
			endDate = getDate(set.getString(3));
			duration = set.getString(4);
			timeLines.add(new TimeLine(title, startDate, endDate, duration));
		}
		return timeLines;
	}

	public ObservableList<Event> getList(String table) throws SQLException, ParseException
	{
		ResultSet set = query(table);
		ObservableList<Event> eventList = FXCollections.observableArrayList();
		int ID;
		String title;
		String description;
		Time startDate;
		Time endDate;
		String duration;

		Event event;
		set.getMetaData().getColumnCount();

		while (set.next())
		{
			ID = Integer.parseInt(set.getString(1));
			title = set.getString(2);
			description = set.getString(3);
			startDate = getDate(set.getString(4));
			duration = set.getString(6);

			try
			{
				endDate = getDate(set.getString(5));
				event = new Event(ID, title, description, startDate, endDate, duration);

			} catch(Exception e) {
				event = new Event(ID, title, description, startDate, duration);
			}
			eventList.add(event);
		}
		return eventList;
	}

	public void insertEvents(Event event, TimeLine selectedTimeLine)
	{
		String query = "(" + new Field(String.valueOf(event.getID())) + ", " + new Field(event.title) + ", " + new Field(event.getDescription()) + ", " + new Field(dateToString(event.getStartDate())) + ", " + new Field(dateToString(event.getEndDate())) + ", " + new Field(event.getDuration()) + ")";

		System.out.println(query);
		try 
		{
			connection.createStatement().execute("Insert into " + selectedTimeLine.getName() + " values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String dateToString(java.util.Date date) 
	{	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString;

		if (date == null)
		{
			dateString = null;
		}
		else 
		{
			dateString = df.format(date);
		}
		return dateString;
	}

	private static Time getDate(String text) throws ParseException
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar cal = new GregorianCalendar();
		cal.setTime(df1.parse(text));

		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);    
		java.sql.Time sqlDate = new java.sql.Time(cal.getTime().getTime());

		return sqlDate;
	}

	public ResultSet query(String table) throws SQLException
	{
		String query = "select * from " +  table;
		return connection.createStatement().executeQuery(query);
	}

	public void print(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

		int columns = resultSetMetaData.getColumnCount();
		//		System.out.println(columns);

		for (int i = 1; i<= columns; i++) 
		{
			System.out.format("%20s", resultSetMetaData.getColumnName(i) + "  |  ");

		}
		while (resultSet.next()) 
		{
			System.out.println();
			for (int i = 1; i <= columns; i++) 
			{
				System.out.format("%20s", resultSet.getString(i) + "  |  ");
			}
		}
		System.out.println();
	}

	// ALSO DELETES CONTENT
	public void dropTable(String name) throws SQLException 
	{
		//Remove table itself
		connection.createStatement().execute("DELETE FROM TIMELINES WHERE title='" + name + "'");

		System.out.println(name + " dropped");
	}

	public void dropTableContent(String name) throws SQLException
	{
		// Empty table of content
		connection.createStatement().execute("TRUNCATE TABLE " + name);
	}

	public void deleteEvent(Event event, TimeLine t) throws SQLException 
	{
		connection.createStatement().execute("DELETE FROM " + t.getName() + " WHERE ID=" + new Field(String.valueOf(event.getID())));
		System.out.println(event.getTitle() + " deleted");
	}

	public void updateTimeLine(TimeLine timeline, TimeLine t) throws SQLException, ClassNotFoundException
	{
		connection.createStatement().execute("UPDATE TIMELINES SET title=" + new Field(timeline.getName()) + ", STARTDATE=" +  new Field(dateToString(timeline.getStart())) + ", ENDDATE=" + new Field(dateToString(timeline.getEnd())) + ", DURATION=" + new Field(timeline.getDuration()) + " WHERE title=" + new Field(t.getName()));

		System.out.println("Timeline '" + t.getName() + "' updated -> " + timeline.getName());
	}

	/**
	 * This is a public method that updates a single event.
	 * 
	 * @param eventTitle
	 * @param t
	 * @throws SQLException
	 */
	public void updateEvent(Event event, String table, Event oldEvent) throws SQLException
	{
		try 
		{ 
			connection.createStatement().execute("UPDATE " + table + " SET title=" + new Field(event.title) + ", DESCRIPTION=" + new Field(event.getDescription()) + ", STARTDATE=" + new Field(dateToString(event.startDate)) + ", ENDDATE=" + new Field(dateToString(event.endDate)) + ", DURATION=" + new Field(event.getDuration()) + " WHERE ID=" + new Field(String.valueOf(oldEvent.getID())));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Event '" + oldEvent.getTitle() + "' updated");
	}

	public void clearDB() throws SQLException, ParseException
	{		
		for (TimeLine t : this.getTimeLines()) 
		{	
			dropTableContent(t.getName());
			connection.createStatement().execute("DELETE FROM TIMELINES WHERE title='" + t.getName() + "'");

			System.out.println("Dropped table: " + t.getName());	
		}
	}

	public int getCurrentID(TimeLine selectedTimeLine) throws SQLException, ParseException
	{
		ObservableList<Event> events = getList(selectedTimeLine.getName());

		int biggestID;

		if (events.size() == 0)
		{
			return 1;
		}
		else if (events.size() == 1)
		{
			biggestID = events.get(0).getID() + 1;
			return biggestID;
		}
		else 
		{
			biggestID = events.get(0).getID();

			for (int i = 0; i < events.size(); i++)
			{
				if (events.get(i).getID() > biggestID)
				{
					biggestID = events.get(i).getID();
				}
			}
			biggestID = biggestID + 1;
			return biggestID;
		}
	}

	//	@SuppressWarnings("deprecation")
	//	public void main(String[] args) throws ClassNotFoundException, SQLException{
	//		createDB();
	//		Date date = new Date(2014,9,4);
	//		
	//		System.out.println(date);
	//		Event f = new Event("title", "Description", date);
	//		Event e = new Event("title", "Description", date,date);
	//		insert(f);
	//		insert(e);
	//		print(query());
	//		
	//		
	//	}
}
