package scraper;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String... args) throws IOException {
        String link = "https://www.formula1.com/en/results.html/2022/races/1124/bahrain/race-result.html";
        String year = "2022";
        String raceName = "bahrain";
        Scrape.scrapeRaceResults(year, raceName);
        Scrape.scrapeRaceResults("2021", "france");
        Scrape.scrapeRaceResults("2012", "monaco");
        Scrape.scrapeRaceResults("2019", "spain");
    }
}