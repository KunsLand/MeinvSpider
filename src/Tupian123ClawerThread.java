import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tupian123ClawerThread extends Thread {

	private static Logger logger = Logger.getLogger(Tupian123ClawerThread.class
			.getName());

	private String typeurl;

	public Tupian123ClawerThread(String typeurl) {
		this.typeurl = typeurl;
	}

	public void run() {
		new Tupian123Clawer().execute(typeurl);
	}

	public static void main(String[] args) {
		String baseUrl = "http://www.tupian12345.com";
		Document siteMapHtml = getHtmlByUrl(baseUrl + "/data/sitemap.html");

		Elements taotu = siteMapHtml.select("ul.f6").first().select("li a");
		new Tupian123Clawer().execute(taotu.first().attr("href"));
//		new Tupian123ClawerThread(taotu.first().attr("href")).start();
//		for (Element e : taotu)
//			new Tupian123ClawerThread(e.attr("href")).start();

		// Elements shipin = siteMapHtml.select("ul.f6").get(1).select("li a");
		// for (Element e : shipin)
		// System.out.println(baseUrl + e.attr("href") + " : " + e.ownText());
	}

	public static Document getHtmlByUrl(String url) {
		logger.info("\nRetrieving: " + url);
		Document doc = null;
		while (doc == null) {
			try {
				doc = Jsoup.connect(url).timeout(60 * 1000).get();
			} catch (IOException e) {
				logger.info(e.getMessage() + "\nRetry...");
			}
		}
		return doc;
	}

}
