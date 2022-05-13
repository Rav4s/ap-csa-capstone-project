package scraper;

// Import AWT Graphics
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class View implements ActionListener {

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

        // Creating a Label
        Label title = new Label("Formula 1 Stats Viewer:");
        title.setFont(new Font("Sans Serif", Font.PLAIN, 32));

        // Creating a Button
        Button seasons = new Button("Seasons");
        seasons.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        seasons.addActionListener(this);

        // Setting position in the frame
        int labelWidth = 150;
        title.setBounds((width / 2) - (labelWidth / 2), height / 10, labelWidth, 30);
        seasons.setBounds((width / 4), (height / 4), 120, 40);

        // Adding into frame
        f.add(seasons);
        f.add(title);

        // Setting frame size
        f.setSize(width, height);

        // Setting frame title
        f.setTitle("F1 Stats");

        // No layout
        f.setLayout(null);

        // Make frame visible
        f.setVisible(true);
    }

    public void mainDisplay() {
        System.out.println("Main Display Opened");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Scrape.scrapeRaceResults("2021", "bahrain");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}