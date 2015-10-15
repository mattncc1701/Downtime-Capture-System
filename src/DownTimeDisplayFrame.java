import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DownTimeDisplayFrame extends JFrame 
    implements WindowListener, ActionListener {

	// For compilation
	private static final long serialVersionUID = 1L;
	
	// Tracks if the window is closed
	protected boolean windowClosed;
	
	// Frame controls
	//JTable eventTable;
	ListPanel eventListPanel;
	JButton selectReasonButton; 
	JButton createNewFileButton;
	JButton backButton;
	
	boolean backButtonPressed = false;
	
	int currentSelectedItem;
	
	int currentRow = 1;

	Calendar c = new GregorianCalendar();
	File exlFile;
	WritableWorkbook writableWorkbook;
	WritableSheet writableSheet;
	
	// Database
	ReasonDatabase reasons;
	EventDatabase events;
	SelectReasonDialog dialog;
	
	public DownTimeDisplayFrame() {
		super("Down Time");
		
		String newDate = ((Date)c.getTime()).toString();
		newDate = newDate.replaceAll(" ", "_");
		newDate = newDate.replaceAll(":", "_");
		exlFile = new File("DCS_Data_"+ newDate +".xls");
	    try {
	 	    writableWorkbook= Workbook
	 		    .createWorkbook(exlFile);
	        writableSheet = writableWorkbook.createSheet(
	 		    "Sheet1", 0);
	     	     
			writableSheet.addCell(new Label(0, 0, "Time-Down"));
			writableSheet.addCell(new Label(1, 0, "Who Stopped"));
			writableSheet.addCell(new Label(2, 0, "Machine Part"));
			writableSheet.addCell(new Label(3, 0, "Reason"));
			writableSheet.addCell(new Label(4,0, "Date"));
	    } catch (IOException e1) {
	 	   // TODO Auto-generated catch block
		    e1.printStackTrace();
	    }
		 catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setMinimumSize(new Dimension(
			DcsConstants.DOWN_TIME_DISPLAY_FRAME_WIDTH, 
			DcsConstants.DOWN_TIME_DISPLAY_FRAME_HEIGHT));
		setResizable(false);	

		// Main vertical layout manager
		Box mainLayout = Box.createVerticalBox();
		add(mainLayout);
				
		String[] titles = {"Machine", "Time", "Date"}; 
		ListPanelTitles itemsPanel = new ListPanelTitles(3, titles);
		Border border = BorderFactory.createEmptyBorder(20, 0, 0, 20);	
		itemsPanel.setBorder(border);
		mainLayout.add(itemsPanel);   
				
		// TBD load existing events 
		events = new EventDatabase();
				
		// Create broader around scrolling table pane
		eventListPanel = new ListPanel(3);
		border = BorderFactory.createEmptyBorder(0, 20, 20, 20);	
		eventListPanel.setBorder(border);
		mainLayout.add(eventListPanel);
		
        // Button layout manager
        Box buttonLayout = Box.createHorizontalBox();
        border = BorderFactory.createEmptyBorder(0, 0, 0, 20);	
        buttonLayout.setBorder(border);
        //buttonLayout.setAlignmentX(RIGHT_ALIGNMENT);
        
    	// Add select reason button
     	selectReasonButton = new JButton("Select Reason");
     	selectReasonButton.addActionListener(this);
     	selectReasonButton.setFont(new Font("Arial", Font.BOLD, 20));
     	buttonLayout.add(selectReasonButton);
     	buttonLayout.add(Box.createRigidArea(new Dimension(10, 0))); // Add margin between buttons
     	
    	// Add Load Options button
     	createNewFileButton = new JButton("Create New File");
     	createNewFileButton.addActionListener(this);
     	createNewFileButton.setFont(new Font("Arial", Font.BOLD, 20));
     	buttonLayout.add(createNewFileButton);
     	buttonLayout.add(Box.createRigidArea(new Dimension(490, 0)));
     	
     	//Add Back button
     	backButton = new JButton("Back");
     	backButton.addActionListener(this);
     	backButton.setFont(new Font("Arial", Font.BOLD, 20));
     	buttonLayout.add(backButton);
     	
     	mainLayout.add(buttonLayout);
     	mainLayout.add(Box.createRigidArea(new Dimension(0, 20))); // Add bottom margin
        addWindowListener(this);
		windowClosed = false;
	} // ctor
	
	// This listener is call when a button event occurs
	@Override
	public void actionPerformed(ActionEvent event) {
		
		
		// Check if select reason button pressed
		if (event.getSource() == selectReasonButton && eventListPanel.getSelectedEntry() > -1) {
		    // Display select reason dialog
			currentSelectedItem = eventListPanel.getSelectedEntry();
			dialog = new SelectReasonDialog(this, eventListPanel, currentRow, writableSheet);
			
			// Center dialog box in window
			int x = (getWidth()-dialog.getWidth()) / 2;  
			int y = (getHeight()-dialog.getHeight()) / 2;  
	    	dialog.setLocation(x, y);     

		    dialog.setModalityType(
		       JDialog.ModalityType.DOCUMENT_MODAL);
		    dialog.setVisible(true);
			
			// TBD code to handle selected reason
		    
		    eventListPanel.clearSelection();
		} // if
		
		//Check if back button pressed
		if(event.getSource() == backButton){
			backButtonPressed = true;
		}
		
		// Check if reload options button pressed
	    if (event.getSource() == createNewFileButton) {
	    	currentRow = 1;
			try {
				writableWorkbook.write();
				writableWorkbook.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c = new GregorianCalendar();
			String newDate = ((Date)c.getTime()).toString();
			newDate = newDate.replaceAll(" ", "_");
			newDate = newDate.replaceAll(":", "_");
			exlFile = new File("DCS_Data_"+ newDate +".xls");
		    try { 
		 	    writableWorkbook= Workbook
		 		    .createWorkbook(exlFile);
		        writableSheet = writableWorkbook.createSheet(
		 		    "Sheet1", 0);
				writableSheet.addCell(new Label(0, 0, "Time-Down"));
				writableSheet.addCell(new Label(1, 0, "Who Stopped"));
				writableSheet.addCell(new Label(2, 0, "Machine Part"));
				writableSheet.addCell(new Label(3, 0, "Reason"));
				writableSheet.addCell(new Label(4,0, "Date"));
		    } catch (IOException e1) {
		 	   // TODO Auto-generated catch block
			    e1.printStackTrace();
		    }
			 catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} // if
	} // actionPerformed

	public boolean closed() {
		return windowClosed;
	} // closed
	
	@Override
	public void windowClosed(WindowEvent e) {
        windowClosed = true;
    } // windowClosed

	@Override
	public void windowActivated(WindowEvent arg0) {
	} // windowActivated

	@Override
	public void windowClosing(WindowEvent arg0) {	
		  // Finish closing the window
		  dispose();
	} // windowClosing

	@Override
	public void windowDeactivated(WindowEvent arg0) {	
	} // windowDeactivated

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	} // windowDeiconified

	@Override
	public void windowIconified(WindowEvent arg0) {
	} // windowIconified

	@Override
	public void windowOpened(WindowEvent arg0) {
	} // windowOpened

} // DownTimeFrame
