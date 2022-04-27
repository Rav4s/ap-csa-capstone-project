import net.sourceforge.htmlunit.*;
import java.io.IOException;
import java.util.List;

public class Scraper {

    public static void scrape(String url) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        try {
            HtmlPage page = webClient.getPage(url);

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
}
