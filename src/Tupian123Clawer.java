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

	private DataHandler handler = new DataHandler();

	private String dir = "E:\\meinvPicture\\";

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

	public void execute(String typeurl) {
		String type = typeurl.substring(9, typeurl.lastIndexOf("/"));
		// first page
		String url = baseUrl + typeurl;
		Document doc = getHtmlByUrl(url);
		Elements picGroup = doc.select("div.page1 ul li a");
		clawPicGroups(picGroup, type);

		// other pages
		while (!doc.select("div.sxye ul li a").isEmpty()
				&& !doc.select("div.sxye ul li").last().select("a").isEmpty()) {
			url = baseUrl
					+ typeurl
					+ doc.select("div.sxye ul li").last()
							.previousElementSibling().select("a").attr("href");
			doc = getHtmlByUrl(url);
			picGroup = doc.select("div.page1 ul li a");
			clawPicGroups(picGroup, type);
		}
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
			System.out.println("type:\t " + type + "\nurl:\t " + url
					+ "\ntitle:\t " + title);
			int i = 0;
			for (Element pic : pics) {
				String picurl = pic.attr("src");
				ig.addUrl(picurl);
				System.out.println(++i + ":\t " + picurl);
				new Downloader(dir + type + "\\" + title + "\\", i + ".jpg",
						picurl).startDownload();
			}
//			pause();

			// more than one page
			int pages = Integer.parseInt(html.select("div.dede_pages ul li")
					.last().previousElementSibling().select("a").last()
					.ownText());
			System.out.println("total pages: " + pages);
			while (!html.select("div.dede_pages ul li a").last().attr("href")
					.equals("#")) {
				url = prefix
						+ html.select("div.dede_pages ul li a").last()
								.attr("href");
				html = getHtmlByUrl(url);
				pics = html.select("div.page-list > div > p > img");
				for (Element pic : pics) {
					String picurl = pic.attr("src");
					ig.addUrl(picurl);
					System.out.println(++i + ":\t " + picurl);
					new Downloader(dir + type + "\\" + title + "\\",
							i + ".jpg", picurl).startDownload();
				}
//				pause();
			}

			// send this image group to download handler
			// handler.add(ig);
		}
	}

	private void pause() {
		try {
			Thread.sleep(1000 * 25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
