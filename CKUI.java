	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import javax.swing.ButtonGroup;
	import javax.swing.JCheckBoxMenuItem;
	import javax.swing.JFrame;
	import javax.swing.JMenu;
	import javax.swing.JMenuBar;
	import javax.swing.JMenuItem;
	import javax.swing.JRadioButtonMenuItem;
	 
	public class CKUI extends JFrame {
	     
	    public CKUI() {
	         
	        setTitle("Menu Example");
	        setSize(150, 150);
	         
	        // Creates a menubar for a JFrame
	        JMenuBar menuBar = new JMenuBar();
	         
	        // Add the menubar to the frame
	        setJMenuBar(menuBar);
	         
	        // Define and add two drop down menu to the menubar
	        JMenu fileMenu = new JMenu("File");
	        JMenu editMenu = new JMenu("Edit");
	        menuBar.add(fileMenu);
	        menuBar.add(editMenu);
	         
	        // Create and add simple menu item to one of the drop down menu
	        JMenuItem newAction = new JMenuItem("New Database");
	        JMenuItem openAction = new JMenuItem("Open Database");
	        JMenuItem exitAction = new JMenuItem("Exit");
	        JMenuItem nEntAction = new JMenuItem("New Entry");
	        JMenuItem dEntAction = new JMenuItem("Delete Entry");
	        JMenuItem viewAction = new JMenuItem("View Details");
	         

	        fileMenu.add(newAction);
	        fileMenu.add(openAction);
	        fileMenu.addSeparator();
	        fileMenu.add(exitAction);
	        editMenu.add(nEntAction);
	        editMenu.add(dEntAction);
	        editMenu.add(viewAction);
	        newAction.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	                System.out.println("You have clicked on the new action");
	            }
	        });
	    }
	    
	    
	    
	    public static void main(String[] args) {
	        CKUI me = new CKUI();
	        me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        me.setVisible(true);
	    }
	}

