import com.webscraper.input.WebScraperCLI;

public class MainClass {

    public static void main(String[] args) {

        try {

            // Basic command line interface for demo
            WebScraperCLI webScraperCLI = new WebScraperCLI();
            webScraperCLI.runWebScraperCLI();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
