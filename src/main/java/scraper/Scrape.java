package scraper;

// Import WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAnchorElement;

import java.io.IOException;
import java.util.List;

public class Scrape {
    public static void scrapeRaceResults(String year, String raceName) throws IOException {

        // Set up webClient
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(10000);

        // Get list of links for provided year
        String yearURL = "https://www.formula1.com/en/results.html/" + year + "/races.html";
        HtmlPage yearPage = webClient.getPage(yearURL);
        List<HtmlUnorderedList> yearResults = yearPage.getByXPath("//ul[@class='resultsarchive-filter ResultFilterScrollable']");
        List<HtmlAnchor> links = yearResults.get(2).getByXPath("//a[@class='resultsarchive-filter-item-link FilterTrigger ']");

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
            return;
        }

        // Get race results
        String url = "https://www.formula1.com/" + finalLink;
        HtmlPage page = webClient.getPage(url);
        List<HtmlDivision> results = page.getByXPath("//div[@class='resultsarchive-col-right']");

        System.out.println(results.get(0).asXml());
        webClient.close();
    }
}
