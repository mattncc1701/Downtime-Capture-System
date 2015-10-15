import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class ListPanel extends JPanel{
	
	protected static class CellRenderer extends JPanel implements ListCellRenderer{
		Vector<JLabel> columns;
		
		int numColumns;
		CellRenderer(int numColumns) {
			this.numColumns = numColumns;
			setLayout(new GridLayout(1, numColumns));
			columns = new Vector<JLabel>();
			
			for (int col=0; col<numColumns; col++) {
				columns.add(new JLabel());
				columns.get(col).setOpaque(true);
				add(columns.get(col));
				columns.get(col).setFont(new Font("Arial", Font.BOLD,20));
			} // for
			
		} // ctor

		public Component getListCellRendererComponent(
			JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

			for (int col=0; col<numColumns; col++) {
				columns.get(col).setText(((String[])value)[col]);
				
				if (isSelected) {
					columns.get(col).setBackground(list.getSelectionBackground());
					columns.get(col).setForeground(list.getSelectionForeground());
				} // else if
				
				else {
					columns.get(col).setBackground(list.getBackground());
					columns.get(col).setForeground(list.getForeground());
				} // else
			} // for
			
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			return this;
		} // getListCellRendererComponent
	} // CellRenderer
	
	protected int numColumns;
	protected JScrollPane scroll;
	protected JList list;
	protected String[][] columnData;
	
	public ListPanel(int numColumns) {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
				
		list = new JList();
		list.setCellRenderer(new CellRenderer(numColumns));	
		scroll = new JScrollPane(list,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane. HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.gridx = 0; layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(1, 1, 1, 1);
		layoutConstraints.anchor = GridBagConstraints.CENTER;
		layoutConstraints.weightx = 1.0; layoutConstraints.weighty = 1.0;	
		layout.setConstraints(scroll, layoutConstraints);
		add(scroll);
		this.numColumns = numColumns;		
	} // ctor
	
	public void setEntries(String[][] listEntries) {
		//first number is number of rows, second is number of columns
		columnData = listEntries;
				
		// Set list for JList
		list.setListData(columnData);
		
		clearSelection();
	} // setElements
	
	public int getSelectedEntry(){
		return list.getSelectedIndex();
	}
	
	public String getTimeAtEntry(int index){
		if(index == -1){
			return null;
		}
		else{
			return columnData[index][1];
		}
	}
	
	public String getDateAtEntry(int index){
		if(index == -1){
			return null;
		}
		else{
			return columnData[index][2];
		}
	}
	
	public String getMachineAtEntry(int index){
		if(index == -1){
			return null;
		}
		else{
			return columnData[index][0];
		}
	}
	
	public void clearSelection() {
		list.clearSelection();
	} // clearSelection
	
} // ListPanel