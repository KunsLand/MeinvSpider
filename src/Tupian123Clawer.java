import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tupian123Clawer {

	private static Logger logger = Logger.getLogger(Tupian123Clawer.class
			.getName());

	private String baseUrl = "http://www.tupian12345.com";

	public void start() {

		Document siteMapHtml = getHtmlByUrl(baseUrl + "/data/sitemap.html");

		Elements taotu = siteMapHtml.select("ul.f6").first().select("li a");
		for (Element e : taotu) {
			String typeurl = e.attr("href");
			String type = typeurl.substring(9, typeurl.lastIndexOf("/"));
			// first page
			String url = baseUrl + typeurl;
			System.out.println(url + " : " + e.ownText());
			Document doc = getHtmlByUrl(url);
			Elements picGroup = doc.select("div.page1 ul li a");
			clawPicGroups(picGroup, type);

			// other pages
			while (!doc.select("div.sxye ul li a").isEmpty()
					&& !doc.select("div.sxye ul li").last().select("a")
							.isEmpty()) {
				url = baseUrl
						+ typeurl
						+ doc.select("div.sxye ul li").last()
								.previousElementSibling().select("a")
								.attr("href");
				doc = getHtmlByUrl(url);
				picGroup = doc.select("div.page1 ul li a");
				clawPicGroups(picGroup, type);
			}
		}

		// Elements shipin = siteMapHtml.select("ul.f6").get(1).select("li a");
		// for (Element e : shipin)
		// System.out.println(baseUrl + e.attr("href") + " : " + e.ownText());
	}

	public static void main(String[] args) {
		new Tupian123Clawer().start();
	}

	public Document getHtmlByUrl(String url) {
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

	private void clawPicGroups(Elements picGroup, String type) {
		for (Element e : picGroup) {
			ImageGroup ig = new ImageGroup();
			String url = baseUrl + e.attr("href");
			String prefix = url.substring(0, url.lastIndexOf("/") + 1);
			Document html = getHtmlByUrl(url);
			Elements pics = html.select("div.page-list > div > p > img");
			String title = html.select("div.bcen div div.title h1").first()
					.ownText();
			ig.setName(title);
			ig.setType(type);
			System.out.println("type:\t "+type+"\nurl:\t " + url + "\ntitle:\t " + title);
			int i = 1;
			for (Element pic : pics)
				System.out.println(i++ + ":\t " + pic.attr("src"));

			// more than one page
			int pages = Integer.parseInt(html.select("div.dede_pages ul li")
					.last().previousElementSibling().select("a").last().ownText());
			System.out.println("total pages: " + pages);
			while (!html.select("div.dede_pages ul li a").last().attr("href")
					.equals("#")) {
				url = prefix
						+ html.select("div.dede_pages ul li a").last()
								.attr("href");
				html = getHtmlByUrl(url);
				pics = html.select("div.page-list > div > p > img");
				for (Element pic : pics)
					System.out.println(i++ + ":\t " + pic.attr("src"));
			}
		}
	}

}
