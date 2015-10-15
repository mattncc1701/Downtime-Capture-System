import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListPanelTitles extends JPanel {

	Vector<JLabel> titles;
	protected int numTitles;
	
	public ListPanelTitles(int numTitles, String[] titles) {
		Box layout = Box.createHorizontalBox();
        layout.setAlignmentX(LEFT_ALIGNMENT);
		add(layout);
			
		this.titles = new Vector<JLabel>();		
		
		for (int col=0; col < numTitles; col++) {
			JLabel currentColumnLabel = new JLabel(titles[col]);
			currentColumnLabel.setFont(new Font("Arial", Font.BOLD, 30));
			this.titles.add(currentColumnLabel);
			this.titles.get(col).setOpaque(true);
			layout.add(this.titles.get(col));
			if (col == 0)
		        layout.add(Box.createRigidArea(new Dimension(190, 0)));
			if (col == 1)
				layout.add(Box.createRigidArea(new Dimension(255, 0)));
			if (col == 2)
				layout.add(Box.createRigidArea(new Dimension(240, 0)));
		} // for
		
		this.numTitles = numTitles;	
	} // ctor

} // ListPanel