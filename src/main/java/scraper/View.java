package scraper;

// Import AWT Graphics
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

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

        // Creating a Title
        Label title = new Label("Formula 1 Stats Viewer:");
        title.setFont(new Font("Sans Serif", Font.PLAIN, 32));

        // Description Text
        Label desc = new Label("An easy way to track historical & current race results");
        desc.setFont(new Font("Sans Serif", Font.PLAIN, 28));

        // Creating a Button
        Button seasons = new Button("Seasons");
        seasons.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        seasons.addActionListener(this);

        // Setting position in the frame
        int labelWidth = 370;
        title.setBounds((f.getWidth() / 2) - (labelWidth / 2), f.getHeight() / 10, labelWidth, 40);
        labelWidth = 650;
        desc.setBounds((f.getWidth() / 2) - (labelWidth / 2), f.getHeight() / 5, labelWidth, 40);
        seasons.setBounds((f.getWidth() / 2) - 120, (f.getHeight() / 3), 120, 40);

        // Adding into frame
        f.add(seasons);
        f.add(title);
        f.add(desc);
    }

    // Clear all objects on the frame
    public void clear() {
        f.removeAll();
        f.revalidate();
    }

    public void listSeasons() throws IOException {

        // Clear screen
        this.clear();

        // Add home button
        Button home = new Button("Home");
        home.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        home.addActionListener(this);

        // Setting position in frame
        home.setBounds(20, 40, 120, 40);

        // Adding into frame
        f.add(home);

        // Create grid for buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 10));
        mainPanel.setBounds(80, 100, f.getWidth() - 200, f.getHeight() - 200);
        f.add(mainPanel);


        // Get list of all seasons
        List<String> seasonsList = Scrape.scrapeSeasonsList();

        // Add buttons to grid
        for (String i : seasonsList) {
            Button b = new Button(i);
            b.setFont(new Font("Sans Serif", Font.PLAIN, 16));
            b.addActionListener(this);
            mainPanel.add(b);
        }

        mainPanel.revalidate();
        f.revalidate();
    }

    public void seasonDetails(String season) throws IOException {

        // Clear screen
        this.clear();

        // Add home button
        Button home = new Button("Home");
        home.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        home.addActionListener(this);

        // Seasons button
        Button seasons = new Button("Seasons");
        seasons.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        seasons.addActionListener(this);

        // Creating a Label
        Label title = new Label(season + " Formula 1 Season");
        title.setFont(new Font("Sans Serif", Font.PLAIN, 32));

        // Setting position in frame
        int labelWidth = 500;
        title.setBounds((f.getWidth() / 2) - (labelWidth / 2), f.getHeight() / 10, labelWidth, 30);
        home.setBounds(20, 40, 120, 40);
        seasons.setBounds(160, 40, 120, 40);

        // Adding into frame
        f.add(home);
        f.add(title);
        f.add(seasons);

        // Create grid for buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 5));
        mainPanel.setBounds(80, 250, f.getWidth() - 200, f.getHeight() - 400);
        f.add(mainPanel);


        // Get list of all races
        List<String> racesList = Scrape.scrapeRacesList(season);

        // Add buttons to grid
        for (String i : racesList) {
            Button b = new Button(i);
            b.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            b.addActionListener(this);
            b.setActionCommand(season + i);
            mainPanel.add(b);
        }

        mainPanel.revalidate();
        f.revalidate();
    }

    public void raceDetails(String season, String name) throws IOException {

        // Clear screen
        this.clear();

        // Get race details
        List<String> details = Scrape.scrapeRaceResults(season, name.toLowerCase().strip().replace(' ', '-'));
        System.out.println(details);

        // Add home button
        Button home = new Button("Home");
        home.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        home.addActionListener(this);

        // Seasons button
        Button seasons = new Button("Seasons");
        seasons.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        seasons.addActionListener(this);

        // Race Full Name
        Label title = new Label(details.get(0));
        title.setFont(new Font("Sans Serif", Font.PLAIN, 32));

        // Race winners
        Label winner = new Label("Race Winner: " + details.get(1));
        Label second = new Label("Second Place: " + details.get(2));
        Label third = new Label("Third Place: " + details.get(3));
        winner.setFont(new Font("Sans Serif", Font.PLAIN, 28));
        second.setFont(new Font("Sans Serif", Font.PLAIN, 28));
        third.setFont(new Font("Sans Serif", Font.PLAIN, 28));

        // Setting position in frame
        int labelWidth = f.getWidth();
        title.setBounds(20, f.getHeight() / 10, labelWidth, 30);
        home.setBounds(20, 40, 120, 40);
        seasons.setBounds(160, 40, 120, 40);
        winner.setBounds(20, 170, 400, 30);
        second.setBounds(20, 220, 400, 30);
        third.setBounds(20, 270, 400, 30);

        // Adding into frame
        f.add(home);
        f.add(title);
        f.add(seasons);
        f.add(winner);
        f.add(second);
        f.add(third);

        f.revalidate();
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
        else if (e.getActionCommand().equals("Home")) {
            try {
                this.mainDisplay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Click on a specific season
        else if (e.getActionCommand().matches("[0-9]+") && e.getActionCommand().length() == 4) {
            try {
                this.seasonDetails(e.getActionCommand());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Click on a specific race
        else if (e.getActionCommand().matches("^.*[0-9]+.*$") && e.getActionCommand().length() > 4) {
            try {
                System.out.println(e.getActionCommand());
                this.raceDetails(e.getActionCommand().substring(0, 4), e.getActionCommand().substring(4));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // If Button handler isn't defined
        else {
            System.out.println("No handler for action command: " + e.toString());
        }
    }
}