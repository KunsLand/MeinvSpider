package diao.si.common;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebClient {
	private static Logger logger = Logger.getLogger(WebClient.class.getName());

	public static Document getHtmlByUrl(String url) {
		Document doc = null;
		while (doc == null) {
			try {
				doc = Jsoup.connect(url).timeout(60 * 1000).get();
			} catch (IOException e) {
				logger.info(e.getMessage() + "\tRetry...");
				pause();
			}
		}
		return doc;
	}

	public static void pause() {
		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
