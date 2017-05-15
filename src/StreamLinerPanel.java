import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class StreamLinerPanel extends JPanel{

	
	//Swing Components
	private final JLabel explanation;
	private final JFrame frame;
	private final JTextField inputLocation;
	private final JTextField outputLocation;
	private final JButton btnInputLocation;
	private final JButton btnOutputLocation;
	private final JButton btnRun;
	private final JButton btnReadMe;
	private final JFileChooser fc;
	
	private final JPanel top;
	private final JPanel middle;
	private final JPanel bottom;
	
	
	private File source;
	private File destination;
	
	public StreamLinerPanel (){
		
		frame = new JFrame("StreamLiner 1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setSize(400, 200);

		
		top = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();
		this.setLayout(new BorderLayout());

		
		
		explanation = new JLabel("Welcome to StreamLiner! Created by Tristan Bunyan");
		top.add(explanation);
		
		
		fc = new JFileChooser();
		
		inputLocation = new JTextField();
		inputLocation.setColumns(15);
		inputLocation.setEnabled(false);
		btnInputLocation = new JButton("Select Input file");
		btnInputLocation.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				fc.resetChoosableFileFilters();
				fc.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
					           return true;
					       } else {
					           String filename = f.getName().toLowerCase();
					           return filename.endsWith(".csv");
					       }
					}
					@Override
					public String getDescription() {
						return "CSV Files (*.csv)";
					}
					
				});
				
				fc.setDialogTitle("Locate the CSV pools data file");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showOpenDialog(StreamLinerPanel.this.frame);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					source = fc.getSelectedFile();
					inputLocation.setText(source.getAbsolutePath());
				}
			}
			
		});
		middle.add(inputLocation);
		middle.add(btnInputLocation);
		
		outputLocation = new JTextField();
		outputLocation.setColumns(15);
		outputLocation.setEnabled(false);
		btnOutputLocation = new JButton("Select output location");
		btnOutputLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fc.resetChoosableFileFilters();
				fc.setDialogTitle("Select the output directory");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(StreamLinerPanel.this.frame);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					destination = fc.getSelectedFile();
					outputLocation.setText(destination.getAbsolutePath());
				}
			}
			
		});
		middle.add(outputLocation);
		middle.add(btnOutputLocation);
		
		
		btnRun = new JButton("Create Schedules!");
		btnRun.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				
				createTournament();
				
			}
		});
		
		btnReadMe = new JButton("README");
		btnReadMe.addActionListener(new ActionListener(){
			

			public void actionPerformed(ActionEvent arg0) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(new URI("https://github.com/TristanBunyan/StreamLiner"));
			        } catch (Exception e) {
			        	JOptionPane.showMessageDialog(frame, "Unable to open README in your browser. Navigate to https://github.com/TristanBunyan/StreamLiner", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
				
			}
		});
		
		bottom.add(btnRun);
		bottom.add(btnReadMe);
		
		
		this.add(top, BorderLayout.NORTH);
		this.add(middle,  BorderLayout.CENTER);
		this.add(bottom,  BorderLayout.SOUTH);
		
		frame.setVisible(true);
		this.setVisible(true);
		repaint();
		frame.repaint();
		
		
		
		
//		new Tournament();
	}
	private void createTournament(){
		
		if (source != null && destination != null) {
			try {
				new Tournament(source, destination);
			} catch (HeadersException e) {
				
				JOptionPane.showMessageDialog(frame, "CSV file does not appear to be formatted correctly. See README for details", "Error", JOptionPane.ERROR_MESSAGE);
				return;
				
			} catch (FileNotFoundException e2){
				JOptionPane.showMessageDialog(frame, "File could not be found", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}catch (IOException e3){
				JOptionPane.showMessageDialog(frame, "IO Error, is .csv file you selected currently in use?", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			
			JOptionPane.showMessageDialog(frame, "Schedules created at " + destination.getAbsolutePath() + "\n press OK to close program");
			frame.dispose();
			System.exit(0);
		}else{
			JOptionPane.showMessageDialog(frame, "Please select input & output locations", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 public static void main(String[] args){
	 
	 new StreamLinerPanel();
	 
	
	  	 
	 
 }
	
}
