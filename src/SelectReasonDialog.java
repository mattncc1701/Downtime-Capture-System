
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings("serial")
class SelectReasonDialog extends JDialog
     implements ActionListener {
	
	Workbook readableWorkbook;
    Sheet readableSheet;
    Box mainLayout;
	
    Component newRigidArea = Box.createRigidArea(new Dimension(0, 20));
    
    TreeNode[] head;
    
	ListPanel newListPanel;
	boolean removeEntry = false;

	int currentRow = 1;
	WritableSheet writeableSheet;
	
	// Ok and Cancel buttons
    Box buttonLayout;
	protected JButton okButton;
	protected JButton cancelButton;
	
	JComboBox firstComboBoxList;
	String currentFirstBoxChoice = ""; 
	boolean secondBoxExists = false;
	boolean secondBoxAlreadyCreated = false;
	JComboBox secondComboBoxList;
	String currentSecondBoxChoice = ""; 
	boolean thirdBoxExists = false;
	JComboBox thirdComboBoxList;
	boolean buttonsExist = false;
	
	String lastTime = null;
	String lastDate = null;
	String lastMachine = null;

    public SelectReasonDialog(Frame parent, ListPanel newListPanel, int currentRow, WritableSheet writableSheet) {
        super(parent, true);

        this.writeableSheet = writableSheet;
		this.currentRow = currentRow;
        this.newListPanel = newListPanel;
        
        try {
			readableWorkbook =  Workbook.getWorkbook(new File("C:/Users/ML/Desktop/programming assignments/TestCodeForDCSRead/format.xls"));
			readableSheet = readableWorkbook.getSheet(0);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        head = new TreeNode[readableSheet.getColumn(0).length];
        loadOptions();
        
        // Setup window to specific size and to not be resizable
     	setMinimumSize(new Dimension(
     		DcsConstants.SELECT_REASON_DIALOG_WIDTH, 
     		DcsConstants.SELECT_REASON_DIALOG_HEIGHT));
     	setResizable(false);
        setTitle("Select Reason");

        //Handle window closing correctly.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main vertical layout manager
     	mainLayout = Box.createVerticalBox();
        mainLayout.add(Box.createRigidArea(new Dimension(0, 10))); // Add top margin
     	add(mainLayout);

     	// TBD add combo boxes for reason selections here...
     	int i = 0;
     	while(head[i] != null){
     		i++;
     	}
     	String[] firstComboBoxStrings = new String[i + 1];
     	i = 0;
     	firstComboBoxStrings[0] = "";
     	while(head[i] != null){
 			firstComboBoxStrings[i + 1] = (String) head[i].getValue();	
     		i++;
     	}

        firstComboBoxList = new JComboBox(firstComboBoxStrings);
        firstComboBoxList.setFont(new Font("Arial", Font.BOLD, 20));
     	mainLayout.add(firstComboBoxList);
     	mainLayout.setBorder(BorderFactory.createEmptyBorder(10, 10, 330, 10));

    } // ctor
    
    public boolean checkChangeInFirstComboBox(){
    	if(currentFirstBoxChoice.equals(firstComboBoxList.getSelectedItem())){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkFirstComboBoxSelection(){
    	if(firstComboBoxList.getSelectedItem() == ""){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public void createSecondComboBox(){
    	secondBoxExists = true;
     	int i = 0;
     	while(head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i] != null){
     		i++;
     	}
     	String[] secondComboBoxStrings = new String[i + 1];
     	i = 0;
     	secondComboBoxStrings[0] = "";
     	while(head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i] != null){
 			secondComboBoxStrings[i + 1] = (String) head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i].getValue();	
     		i++;
     	}
    	
    	secondComboBoxList = new JComboBox(secondComboBoxStrings);
        secondComboBoxList.setFont(new Font("Arial", Font.BOLD, 20));
        if(secondBoxAlreadyCreated == false){
        	mainLayout.add(Box.createRigidArea(new Dimension(0, 20)));
        	secondBoxAlreadyCreated = true;
        }
     	mainLayout.setBorder(BorderFactory.createEmptyBorder(10, 10, 250, 10));
     	mainLayout.add(secondComboBoxList);
    }
    
    public void removeSecondComboBox(){
    	secondBoxExists = false;
    	mainLayout.remove(secondComboBoxList);
    	mainLayout.setBorder(BorderFactory.createEmptyBorder(10, 10, 330, 10));
    	mainLayout.validate();
    }
    public void updateSecondBoxChoice(){
    	currentSecondBoxChoice = (String) secondComboBoxList.getSelectedItem();
    }
    
    public boolean checkChangeInSecondComboBox(){
    	if(currentSecondBoxChoice == null || secondComboBoxList == null || currentSecondBoxChoice.equals(secondComboBoxList.getSelectedItem())){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public boolean checkSecondComboBoxSelection(){
    	if(secondComboBoxList == null || secondComboBoxList.getSelectedItem() == ""){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public void createThirdComboBox(){
    	thirdBoxExists = true;
     	int i = 0;
     	while(head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[secondComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i] != null){
     		i++;
     	}
     	String[] thirdComboBoxStrings = new String[i + 1];
     	i = 0;
     	thirdComboBoxStrings[0] = "";
     	while(head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[secondComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i]  != null){
 			thirdComboBoxStrings[i + 1] = (String) head[firstComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[secondComboBoxList.getSelectedIndex() - 1].getTreeNodeArray()[i] .getValue();	
     		i++;
     	}
    	
    	thirdComboBoxList = new JComboBox(thirdComboBoxStrings);
        thirdComboBoxList.setFont(new Font("Arial", Font.BOLD, 20));
    	mainLayout.add(newRigidArea);
     	mainLayout.setBorder(BorderFactory.createEmptyBorder(10, 10, 250, 10));
     	mainLayout.add(thirdComboBoxList);
     	mainLayout.validate();
    }
    
    public void removeThirdComboBox(){
    	thirdBoxExists = false;
    	mainLayout.remove(thirdComboBoxList);
    	mainLayout.remove(newRigidArea);
    	mainLayout.setBorder(BorderFactory.createEmptyBorder(10, 10, 330, 10));
    	mainLayout.validate();
    }
    public void createButtons(){
    	buttonsExist = true;
     	// Button layout manager
     	buttonLayout = Box.createHorizontalBox();
     	buttonLayout.setAlignmentX(LEFT_ALIGNMENT);

     	// Add start event button
     	buttonLayout.setBorder(BorderFactory.createEmptyBorder(30, 0, 80, 0));
     	buttonLayout.add(Box.createRigidArea(new Dimension(10, 10))); // Add left margin
     	okButton = new JButton("Ok");
     	okButton.setFont(new Font("Arial", Font.BOLD, 30));
     	okButton.addActionListener(this);
     	okButton.setEnabled(true); // Default to enabled
     	buttonLayout.add(okButton);

     	// Add stop event button
     	buttonLayout.add(Box.createRigidArea(new Dimension(10, 10))); // Add middle margin
     	cancelButton = new JButton("Cancel");
     	cancelButton.setFont(new Font("Arial", Font.BOLD, 30));
     	cancelButton.addActionListener(this);
     	cancelButton.setEnabled(true); // Default to disabled
     	buttonLayout.add(cancelButton);
     	mainLayout.add(buttonLayout);
     	mainLayout.validate();
    }
    
    public void removeButtons(){
    	buttonsExist = false;
    	mainLayout.remove(buttonLayout);
    	mainLayout.validate();
    }
    
    public void loadOptions(){
        for(int i = 1; i < readableSheet.getColumn(0).length; i++){
			recBuild(i, 0, head, readableSheet);
		}
    }
    
	public static TreeNode[] recBuild(int row, int column, TreeNode[] newNode, Sheet readableSheet){
		if(column > readableSheet.getRow(row).length - 1){
			return newNode;
		}
		else{
			int index = isInList(row, column, newNode, readableSheet);
			if(index == -1){
				int position = 0;
				while(position < newNode.length - 1 && newNode[position] != null){
					position++;
				} 
				newNode[position] = new TreeNode(readableSheet.getCell(column,row).getContents());
				newNode[position].setNext(new TreeNode[readableSheet.getColumn(0).length]);
				return recBuild(row, column+1, newNode[position].getTreeNodeArray(), readableSheet);
			}
			else{
				return recBuild(row, column+1, newNode[index].getTreeNodeArray(), readableSheet);
			}
		}
		
	}
	
	private static int isInList(int row, int column, TreeNode[] newNode, Sheet readableSheet){
		for(int i = 0; i < newNode.length; i++){
			if(newNode[i] != null && newNode[i].getValue().equals(readableSheet.getCell(column, row).getContents())){
				return i;
			}
		}
		return -1;
	}
 

	// Handle all events generated by various controls on the screen
	@Override
	public void actionPerformed(ActionEvent event) {
		
		// Check if start button pressed
		if (event.getSource() == okButton) {
			Label labelWS;
			Label labelMP;
			Label labelR;
			Label labelTime;
			Label labelDate;
			labelTime = new Label(0, currentRow, newListPanel.getTimeAtEntry(newListPanel.getSelectedEntry()));
			labelWS = new Label(1, currentRow,(String) firstComboBoxList.getSelectedItem());
			labelMP = new Label(2, currentRow, (String) secondComboBoxList.getSelectedItem());
			labelR = new Label(3, currentRow, (String) thirdComboBoxList.getSelectedItem());
			labelDate = new Label(4, currentRow, newListPanel.getDateAtEntry(newListPanel.getSelectedEntry()));
			try {
				writeableSheet.addCell(labelTime);
				writeableSheet.addCell(labelWS);
				writeableSheet.addCell(labelMP);
				writeableSheet.addCell(labelR);
				writeableSheet.addCell(labelDate);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastDate =  newListPanel.getDateAtEntry(newListPanel.getSelectedEntry());
			lastTime = newListPanel.getTimeAtEntry(newListPanel.getSelectedEntry());
			lastMachine = newListPanel.getMachineAtEntry(newListPanel.getSelectedEntry());
			
			removeEntry = true;
			clearAndHide();
		} // if
		
		// Check if cancel button pressed
		else if (event.getSource() == cancelButton) {
			
			// TBD code to about selections
			
			// Close window
			clearAndHide();
		} // else if
		
	} // actionPerformed	


    public void clearAndHide() {
        setVisible(false);
    } // clearAndHide
} // SelectReasonDialog
