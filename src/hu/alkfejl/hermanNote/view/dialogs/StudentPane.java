package hu.alkfejl.hermanNote.view.dialogs;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hu.alkfejl.hermanNote.view.HermanNoteGUI;

public class StudentPane extends JPanel implements ListSelectionListener {

	//static JTable table;
    private JSplitPane splitPane;
    
    private HermanNoteGUI gui;
    //private String[] imageNames = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
    //    "kathyCosmo", "lainesTongue", "left", "middle", "right", "stickerface"};
    public StudentPane(HermanNoteGUI gui) {
    	super();
        this.gui = gui;
 
        //Create the list of images and put it in a scroll pane.
        /* 
        list = new JList(imageNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
         */
        StudentSearchPanel sp = new StudentSearchPanel(gui);
        //sp.setVisible(true);
        JScrollPane searchPane = new JScrollPane(sp); 
        
        
        
        
//        List<Student> students = gui.getController().getStudents();
//        table = new JTable(new StudentTableModel(students));
        StudentTablePanel tp = new StudentTablePanel(gui);
        JScrollPane selectPane = new JScrollPane(tp);
        
        
        
 
        //Create a split pane with the two scroll panes in it.
        JSplitPane selectorPane = new JSplitPane();
        selectorPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        		searchPane, selectPane);
        selectorPane.setOneTouchExpandable(true);
        selectorPane.setDividerLocation(250);
        
        JScrollPane editPane = new JScrollPane();
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        		selectorPane, editPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);
       // splitPane.setResizeWeight(.001d);
 
        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(100, 50);
        searchPane.setMinimumSize(minimumSize);
        searchPane.setMinimumSize(minimumSize);
 
        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        //updateLabel(imageNames[list.getSelectedIndex()]);
        gui.setActualContent(splitPane);
    }
     
    //Listens to the list
//    public void valueChanged(ListSelectionEvent e) {
//        JList list = (JList)e.getSource();
//        updateLabel(imageNames[list.getSelectedIndex()]);
//    }
//     
    //Renders the selected image
//    protected void updateLabel (String name) {
//        ImageIcon icon = createImageIcon("images/" + name + ".gif");
//        picture.setIcon(icon);
//        if  (icon != null) {
//            picture.setText(null);
//        } else {
//            picture.setText("Image not found");
//        }
//    }
 
    //Used by SplitPaneDemo2
//    public JList getImageList() {
//        return list;
//    }
 
    public JSplitPane getSplitPane() {
        return splitPane;
    }

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
 
    
    /** Returns an ImageIcon, or null if the path was invalid. */
//    protected static ImageIcon createImageIcon(String path) {
//       java.net.URL imgURL = SplitPaneModel.class.getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
//    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
//    private static void createAndShowGUI(HermanNoteGUI gui) {
// 
//        //Create and set up the window.
//        JFrame frame = new JFrame("SplitPaneDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        SplitPaneModel splitPaneDemo = new SplitPaneModel(gui);
//        frame.getContentPane().add(splitPaneDemo.getSplitPane());
// 
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
 
//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI(gui);
//            }
//        });
//    }

}
