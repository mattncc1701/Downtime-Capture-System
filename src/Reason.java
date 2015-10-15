import java.util.Vector;

public class Reason {
	
	// Text for reason 
	protected String text;
	protected Vector<Reason> reasonRefinements;
	
	public Reason(String text) {
		this.text = text;
	} // ctor
	
	public String getText() {
		return text;
	} // getText
	
    public void addRefinement(Reason reasonRefinement) {
    	reasonRefinements.add(reasonRefinement);
    } // addRefinement
    
    public Vector<Reason> getReasonRefinements() {    	
	    return reasonRefinements;
    } // reasonRefinements
    
} // Reason