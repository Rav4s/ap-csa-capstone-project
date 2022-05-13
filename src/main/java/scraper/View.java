package scraper;

// Import AWT Graphics
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View {

    private int height;
    private int width;
    public View() {
        height = 1024;
        width = 1920;

        // Creating the Frame
        Frame f = new Frame();

        // Handling window close
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });

        // creating a Label
        Label l = new Label("Employee id:");

        // creating a Button
        Button b = new Button("Submit");

        // setting position of above components in the frame
        l.setBounds(20, 80, 80, 30);
        b.setBounds(100, 100, 80, 30);

        // adding components into frame
        f.add(b);
        f.add(l);

        // frame size 300 width and 300 height
        f.setSize(width, height);

        // setting the title of frame
        f.setTitle("F1 Stats");

        // no layout
        f.setLayout(null);

        // setting visibility of frame
        f.setVisible(true);
    }

    public void mainDisplay() {
        System.out.println("Main Display Opened");
    }
}
