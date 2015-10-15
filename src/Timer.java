public class Timer {

	// Timer start time
	protected long startTime;

	public Timer() {
		startTime = 0;
	} // ctor

	public void start() {
        startTime = System.currentTimeMillis();
    } // start

    public String getElapsedTime() {
    	
    	if (startTime == 0)
    		return "0.0";
    	
    	Double newDouble  = (double) (System.currentTimeMillis() - startTime);
    	newDouble = ((newDouble / 1000));
    	String newString = newDouble.toString();
    	newString = newString.substring(0, newString.indexOf(".")+2);
        return newString; //returns in seconds
    } // getElapsedTime
    
    public void reset() {
    	startTime = 0;
    } // reset
} // Timer