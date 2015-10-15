import java.util.Vector;

public class DownTimeEvent {
	
	// Text for reason 
	protected String machine;
	protected String time;
	protected String date;
	protected Vector<String> reasons;
	
	public DownTimeEvent(String machine, String time, String date) {
		this.machine = machine;
		this.time = time;
		this.date = date;
	} // ctor
	
	public String getMachine() {
		
		return machine;
	} // getMachine
	
    public String getTime() {
		
		return time;
	} // getTime
	
    public String getDate() {
	
	    return date;
    } // getDate

    public void setReasons(Vector<String> reasons) {
    	
    } // setReasons
    
    public Vector<String> getReasons() {
    	
	    return reasons;
    } // getReasons
    
} // Reason