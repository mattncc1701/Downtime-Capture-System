import java.io.IOException;

import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DownTimeDisplaySystem {

	public static void main(String[] args) {
		
		
		
		// Create and display down time display
		DownTimeDisplayFrame displayFrame = new DownTimeDisplayFrame();
        displayFrame.setVisible(true);
        
        // Open simulation frame if simulation enabled
        SimulationFrame simFrame;
        if (DcsConstants.SIMULATION_ENABLED) {
        	simFrame = new SimulationFrame(displayFrame.events, displayFrame.eventListPanel);
        	
        	// Center dialog box in window
			int x = (displayFrame.getWidth()-simFrame.getWidth()) / 2;  
			int y = displayFrame.getHeight();  
			simFrame.setLocation(x, y);  
            simFrame.setVisible(true);
        } // if
          
        // Loop here and process I/O events, loop will exit
        // when DownTimeFrame is closed
        while (!displayFrame.closed()) {
        	if(displayFrame.dialog != null){
        		if(displayFrame.dialog.thirdComboBoxList != null && displayFrame.dialog.thirdComboBoxList.getSelectedItem().equals("") &&
        				displayFrame.dialog.buttonsExist){//if no choice selected in third box remove buttons
            			displayFrame.dialog.removeButtons();
        		}
        		if(displayFrame.dialog.firstComboBoxList.getSelectedItem().equals("") && 
        				displayFrame.dialog.secondBoxExists){//if first box changed back to no entry remove other boxes and buttons
            		if(displayFrame.dialog.thirdBoxExists){
            			displayFrame.dialog.removeThirdComboBox();
            			displayFrame.dialog.currentSecondBoxChoice = "";
            		}
        			displayFrame.dialog.removeSecondComboBox();
            		displayFrame.dialog.secondComboBoxList = null;
        			displayFrame.dialog.currentFirstBoxChoice = "";
        			if(displayFrame.dialog.buttonsExist)
        			displayFrame.dialog.removeButtons();
        		}
        		
        		if(displayFrame.dialog.secondBoxExists && displayFrame.dialog.secondComboBoxList.getSelectedItem().equals("") && 
        				displayFrame.dialog.thirdBoxExists){//remove third box if second box reverted to no entry
        			displayFrame.dialog.removeThirdComboBox();
        			displayFrame.dialog.currentSecondBoxChoice = "";
        			if(displayFrame.dialog.buttonsExist)
            			displayFrame.dialog.removeButtons();
        		}
        		
        		if(displayFrame.dialog.checkFirstComboBoxSelection() && displayFrame.dialog.checkChangeInFirstComboBox() &&
        				!displayFrame.dialog.currentFirstBoxChoice.equals("")){//first box changes from one entry to another that isn't the no entry
        			if(displayFrame.dialog.thirdBoxExists){
        				displayFrame.dialog.removeThirdComboBox();
        			}
        			displayFrame.dialog.removeSecondComboBox();
        			displayFrame.dialog.createSecondComboBox();
        			displayFrame.dialog.currentFirstBoxChoice = (String) displayFrame.dialog.firstComboBoxList.getSelectedItem();
        			displayFrame.dialog.currentSecondBoxChoice = "";
        			if(displayFrame.dialog.buttonsExist)
            			displayFrame.dialog.removeButtons();
        		}
        		
        		if(displayFrame.dialog.secondComboBoxList != null && displayFrame.dialog.checkSecondComboBoxSelection() && 
        				displayFrame.dialog.checkChangeInSecondComboBox() &&
        				!displayFrame.dialog.currentSecondBoxChoice.equals("")){//second box changes from one entry to another that isn't the no entry
        			displayFrame.dialog.removeThirdComboBox();
        			displayFrame.dialog.createThirdComboBox();
        			displayFrame.dialog.currentSecondBoxChoice = (String) displayFrame.dialog.secondComboBoxList.getSelectedItem();
        			if(displayFrame.dialog.buttonsExist)
            			displayFrame.dialog.removeButtons();
        		}
        		
        		if(displayFrame.dialog.currentFirstBoxChoice.equals("") && displayFrame.dialog.checkChangeInFirstComboBox()){//first box got an entry create second box
        			displayFrame.dialog.createSecondComboBox();
        			displayFrame.dialog.currentFirstBoxChoice = (String) displayFrame.dialog.firstComboBoxList.getSelectedItem();
        		}
        		if(displayFrame.dialog.secondComboBoxList != null && displayFrame.dialog.currentSecondBoxChoice != null && 
        				displayFrame.dialog.currentSecondBoxChoice.equals("") &&
        				displayFrame.dialog.checkChangeInSecondComboBox()){//second box went from no entry to an entry thus create third box
        			displayFrame.dialog.currentSecondBoxChoice = (String) displayFrame.dialog.secondComboBoxList.getSelectedItem();
        			displayFrame.dialog.createThirdComboBox();
        		}
        		
        		if(displayFrame.dialog.firstComboBoxList != null && displayFrame.dialog.secondComboBoxList != null && 
        				displayFrame.dialog.thirdComboBoxList != null && !displayFrame.dialog.currentFirstBoxChoice.equals("") && 
        				!displayFrame.dialog.currentSecondBoxChoice.equals("") && !displayFrame.dialog.thirdComboBoxList.getSelectedItem().equals("") &&
        				displayFrame.dialog.buttonsExist == false){//all entries created create buttons
        			displayFrame.dialog.createButtons();
        		}
        		if(displayFrame.dialog.removeEntry){//if ok button has been pressed then remove entry from the display
        			displayFrame.dialog.removeEntry = false;
        			displayFrame.events.removeEntry(displayFrame.currentSelectedItem);
        			displayFrame.eventListPanel.setEntries(displayFrame.events.getListData());
        			displayFrame.currentRow++;
        		}
        		if(displayFrame.backButtonPressed && displayFrame.dialog.lastTime != null &&  displayFrame.dialog.lastDate != null
        				&& displayFrame.dialog.lastMachine != null){
        			displayFrame.backButtonPressed = false;
        			displayFrame.events.restoreLastEntry(displayFrame.dialog.lastTime, displayFrame.dialog.lastDate, displayFrame.dialog.lastMachine);
        			displayFrame.eventListPanel.setEntries(displayFrame.events.getListData());
        			displayFrame.currentRow--;
        			try {
						displayFrame.writableSheet.addCell(new Label(0, displayFrame.currentRow, ""));
	        			displayFrame.writableSheet.addCell(new Label(1, displayFrame.currentRow, ""));
	        			displayFrame.writableSheet.addCell(new Label(2, displayFrame.currentRow, ""));
	        			displayFrame.writableSheet.addCell(new Label(3, displayFrame.currentRow, ""));
	        			displayFrame.writableSheet.addCell(new Label(4, displayFrame.currentRow, ""));
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			displayFrame.dialog.lastTime = null;
        			displayFrame.dialog.lastDate = null; 
        			displayFrame.dialog.lastMachine = null;
        		}
        		if(displayFrame.backButtonPressed && displayFrame.dialog.lastTime == null){
        			displayFrame.backButtonPressed = false;
        		}
        	}
            try {
            	// Check Rashberry PI I/O 
            	
            	// TBD add code to check Rashberry PI IO
            	
            	
            	// Delay to wait for next time to check I/O
            	// on Rashberry PI
				Thread.sleep(DcsConstants.IO_CHECK_WAIT);
				
				// Update simulation status is enabled
				if (DcsConstants.SIMULATION_ENABLED) {
					simFrame.updateSimulationStatus(); 
			    } // if
			} // try
            
            catch (InterruptedException e) {
				System.out.println("ERROR: " + e.getMessage());
			} // catch 
        }// while
		try {
			displayFrame.writableWorkbook.write();
			displayFrame.writableWorkbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Update simulation status is enabled
		if (DcsConstants.SIMULATION_ENABLED) {
			simFrame.dispose();
	    } // if
	} // main
} // GuiTest
