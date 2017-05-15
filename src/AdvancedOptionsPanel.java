import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class AdvancedOptionsPanel extends JPanel {

	
	private final JFrame frame;
	
	private final JLabel lblStartTime = new JLabel("Start Time");
	private final SpinnerModel startTimeModel = new SpinnerNumberModel(12, 0, 24, 1);
	private final JSpinner timeSpinner = new JSpinner(startTimeModel);
	
	private final JLabel lblDuration = new JLabel("Match Duration");
	private final SpinnerModel durationModel = new SpinnerNumberModel(12, 1, 30, 1);
	private final JSpinner durationSpinner = new JSpinner(durationModel);
	
	
	private final JPanel top = new JPanel();
	private final JPanel middle = new JPanel();
	private final JPanel bottom = new JPanel();
	
	
	public AdvancedOptionsPanel(){
		
		frame = new JFrame("Advanced Options");
		frame.setSize(new Dimension (200,200));
		
		
		
		
		top.add(lblStartTime);
		top.add(timeSpinner);
		
		middle.add(lblDuration);
		middle.add(durationSpinner);
		
		
		
		
		this.setLayout(new BorderLayout());
		this.add(top,  BorderLayout.NORTH);
		this.add(middle, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		
		frame.setContentPane(this);
		frame.add(this);
		
	}
	
	
	
}



