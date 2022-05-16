package scraper;

// Import WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAnchorElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrape {
    private static WebClient webClient;
    public static void setUpWebclient() {
        // Set up webClient
        webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(10000);
    }
    public static List<String> scrapeRaceResults(String year, String raceName) throws IOException {

        setUpWebclient();

        // Get list of links for provided year
        String yearURL = "https://www.formula1.com/en/results.html/" + year + "/races.html";
        HtmlPage yearPage = webClient.getPage(yearURL);
        List<HtmlUnorderedList> yearResults = yearPage.getByXPath("//ul[@class='resultsarchive-filter ResultFilterScrollable']");
        List<HtmlAnchor> links = yearResults.get(2).getByXPath(".//a[@class='resultsarchive-filter-item-link FilterTrigger ']");

        // Find link for specific race
        String finalLink = null;
        for (HtmlAnchor a : links) {
            String link = a.getHrefAttribute();
            if (link.contains(raceName)) {
                finalLink = link;
                break;
            }
        }

        if (finalLink == null) {
            System.out.println("Race/year combination doesn't exist!!");
            webClient.close();
            return null;
        }

        // Get race results
        String url = "https://www.formula1.com/" + finalLink;
        HtmlPage page = webClient.getPage(url);
        List<HtmlDivision> results = page.getByXPath("//div[@class='resultsarchive-col-right']");

        // Get names of winners
        List<HtmlHeading1> fullnames = page.getByXPath("//h1[@class='ResultsArchiveTitle']");
        String fullname = fullnames.get(0).getTextContent().strip();

        List<HtmlSpan> firsts = page.getByXPath("//span[@class='hide-for-tablet']");
        List<HtmlSpan> lasts = page.getByXPath("//span[@class='hide-for-mobile']");

        String winner = firsts.get(0).getTextContent().strip() + " " + lasts.get(0).getTextContent().strip();
        String second = firsts.get(1).getTextContent().strip() + " " + lasts.get(1).getTextContent().strip();
        String third = firsts.get(2).getTextContent().strip() + " " + lasts.get(2).getTextContent().strip();

        // Create list to return
        List<String> rets = new ArrayList<String>();
        rets.add(fullname);
        rets.add(winner);
        rets.add(second);
        rets.add(third);

        webClient.close();
        return rets;
    }

    public static List<String> scrapeSeasonsList() throws IOException {

        setUpWebclient();

        // Get list of seasons
        String yearURL = "https://www.formula1.com/en/results.html";
        HtmlPage yearPage = webClient.getPage(yearURL);
        List<HtmlUnorderedList> seasonsList = yearPage.getByXPath("//ul[@class='resultsarchive-filter ResultFilterScrollable']");
        List<HtmlSpan> seasons = seasonsList.get(0).getByXPath(".//span[@class='clip']");

        // Create list of text within span
        List<String> seasonsText = new ArrayList<String>();
        for (HtmlSpan a : seasons) {
            String year = a.getTextContent();
            seasonsText.add(year);
        }

        webClient.close();
        return seasonsText;
    }

    public static List<String> scrapeRacesList(String season) throws IOException {

        setUpWebclient();

        // Get list of races for provided year
        String yearURL = "https://www.formula1.com/en/results.html/" + season + "/races.html";
        HtmlPage yearPage = webClient.getPage(yearURL);
        List<HtmlUnorderedList> yearResults = yearPage.getByXPath("//ul[@class='resultsarchive-filter ResultFilterScrollable']");
        List<HtmlSpan> names = yearResults.get(2).getByXPath(".//span[@class='clip']");
        System.out.println(names.toString());

        // Create list of text within span
        List<String> raceNames = new ArrayList<String>();
        for (HtmlSpan a : names) {
            String name = a.getTextContent();
            raceNames.add(name);
        }
        // Remove "all" item
        raceNames.remove(0);

        webClient.close();
        return raceNames;
    }
}
