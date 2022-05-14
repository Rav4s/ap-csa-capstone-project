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
    private Frame f;
    public View() {
        height = 1024;
        width = 1920;

        // Creating the Frame
        f = new Frame();

        // Handling window close
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });

        // Setting frame size
        f.setSize(width, height);

        // Setting frame title
        f.setTitle("F1 Stats");

        // No layout
        f.setLayout(null);

        // Make frame visible
        f.setVisible(true);
    }

    public void mainDisplay() throws IOException {

        // Clear screen
        this.clear();

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
    }

    // Clear all objects on the frame
    public void clear() {
        f.removeAll();
    }

    public void listSeasons() throws IOException {

        // Clear screen
        this.clear();

        // Add home button
        Button home = new Button("Home");
        home.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        home.addActionListener(this);

        // Setting position in frame
        home.setBounds((width / 8), (height / 8), 120, 40);

        // Adding into frame
        f.add(home);

        //Scrape.scrapeRaceResults("2021", "bahrain");
    }

    // Handle Button clicks
    @Override
    public void actionPerformed(ActionEvent e) {

        // Seasons click
        if (e.getActionCommand().equals("Seasons")) {
            try {
                this.listSeasons();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Home click
        if (e.getActionCommand().equals("Home")) {
            try {
                this.mainDisplay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // If Button handler isn't defined
        else {
            System.out.println("No handler for action command: " + e.getActionCommand());
        }
    }
}