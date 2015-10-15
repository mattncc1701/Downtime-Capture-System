import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class EventDatabase {

	// TBD, temporary
	String[][] columnData;
    
	int rowcnt = 100;
	
	String time;
	// List of events
	protected Vector<DownTimeEvent> events;
	
	public EventDatabase() {
		
		// Temp for compliation
		events = new Vector<DownTimeEvent>();	
		
		columnData = new String[rowcnt][3];
	} // ctor
	
	public void newTimeIncident(String newTime){
		time = newTime;
	}
	
	public void loadUncompletedEvents() { 

		int i = 0;
		while(i < columnData.length && columnData[i][0] != null){
			i++;
		}
		if(i >= columnData.length){
			String[][] tempStringArray =  new String[rowcnt][3];
			for(int j = 0;  j < columnData.length; j++){
				tempStringArray[j][0] = columnData[j][0];
				tempStringArray[j][1] = columnData[j][1];
				tempStringArray[j][2] = columnData[j][2];
			}
			rowcnt = rowcnt*2;
			columnData = new String[rowcnt][3];
			for(int j = 0;  j < tempStringArray.length; j++){
				columnData[j][0] = tempStringArray[j][0];
				columnData[j][1] = tempStringArray[j][1];
				columnData[j][2] = tempStringArray[j][2];
			}

		}
		columnData[i][0] = "Machine" + i;
		columnData[i][1] = time;
		Calendar c = new GregorianCalendar();
		columnData[i][2] = ((Date)c.getTime()).toString();
	
	} // loadUncompletedEvents
	
	public void removeEntry(int index){
		int i = 0;
		for(int j = 0;  j < columnData.length; j++){
			if(j == index){
				i++;
			}
			if(i < columnData.length){
				columnData[j][0] = columnData[i][0];
				columnData[j][1] = columnData[i][1];
				columnData[j][2] = columnData[i][2];
			}
			if(i == columnData.length && columnData[j][0] != null){
				columnData[j][0] = null;
				columnData[j][1] = null;
				columnData[j][2] = null;
			}
			i++;
		}
	}
	
	public void restoreLastEntry(String time, String date, String machine){
		int i = 0;
		while(i < columnData.length && columnData[i][0] != null){
			i++;
		}
		if(i >= columnData.length){
			String[][] tempStringArray =  new String[rowcnt][3];
			for(int j = 0;  j < columnData.length; j++){
				tempStringArray[j][0] = columnData[j][0];
				tempStringArray[j][1] = columnData[j][1];
				tempStringArray[j][2] = columnData[j][2];
			}
			rowcnt = rowcnt*2;
			columnData = new String[rowcnt][3];
			for(int j = 0;  j < tempStringArray.length; j++){
				columnData[j][0] = tempStringArray[j][0];
				columnData[j][1] = tempStringArray[j][1];
				columnData[j][2] = tempStringArray[j][2];
			}
		}
		columnData[i][0] = machine;
		columnData[i][1] = time;
		columnData[i][2] = date;
	}
	
	public String[][] getListData() {
		return columnData;	
	} // getListData
	
    public Vector<DownTimeEvent> getEventList() {
		return events;
	} // getEventList
	
	
} // ReasonDatabase
