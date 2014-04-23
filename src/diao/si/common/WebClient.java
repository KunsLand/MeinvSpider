package diao.si.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebClient {
	private static Logger logger = Logger.getLogger(WebClient.class.getName());

	public static Document getHtmlByUrl(String url) {
		Document doc = null;
		while (doc == null) {
			try {
				URLConnection con = getURLConnection(url);
				doc = Jsoup.parse(con.getInputStream(), "gbk", url);
			} catch (IOException e) {
				logger.info(e.getMessage() + "\tRetry: " + url);
				pause();
			}
		}
		return doc;
	}

	public static BufferedImage readImage(String remoteurl) {
		BufferedImage image = null;
		boolean flag = false;
		long startTime = System.currentTimeMillis();
		do {
			try {
				URLConnection connection = getURLConnection(remoteurl);
				image = ImageIO.read(connection.getInputStream());
				if (flag)
					System.out.println("it takes about "
							+ (System.currentTimeMillis() - startTime) / 1000
							+ " seconds to download " + remoteurl);
			} catch (MalformedURLException e) {
				System.out.println(e.getMessage());
				flag = true;
				pause();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				flag = true;
				pause();
			}
		} while (image == null);
		return image;
	}
	
	public static URLConnection getURLConnection(String remoteurl) throws IOException{
		URL url = new URL(remoteurl);
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
						+ " Chrome/34.0.1847.116 Safari/537.36");
		connection.setConnectTimeout(10 * 1000);
		connection.setReadTimeout(60 * 1000);
		return connection;
	}

	public static void pause() {
		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
