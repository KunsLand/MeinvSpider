import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PicClawer {
	// private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-HH");
	static Logger logger = Logger.getLogger(PicClawer.class.getName());
	private DataHandler handler;
	private DownloadGroup dg;

	public PicClawer() {
		handler = new DataHandler();
		dg = new DownloadGroup();
	}

	public void task(String category_url) {
		String category_name = category_url.substring(category_url
				.lastIndexOf("/") + 1);
		logger.info("Category:\t " + category_name + ":\t " + category_url);

		// each type page
		Document doc2 = getHtmlByUrl(category_url);
		Document current_page;
		Elements pages_bar;
		for (current_page = doc2, pages_bar = current_page
				.select("div.pages > ul > li"); !pages_bar.last().select("a")
				.first().attr("href").equals("#"); current_page = getHtmlByUrl(category_url
				+ "/" + pages_bar.last().select("a").first().attr("href")), pages_bar = current_page
				.select("div.pages > ul > li")) {
			int current_page_num = Integer.valueOf(pages_bar
					.select("li.pagecur > a").first().ownText());
			logger.info("Now we are in " + category_name + ": Page"
					+ current_page_num);

			// picture-groups in this type page
			Elements piclists = current_page.select("div.preview");

			// each picture-group
			for (Element pic_group : piclists) {
				String url = pic_group.select("a").attr("href");
				// Document doc3 = getHtmlByUrl(url);
				String picgroupid = url.substring(url.lastIndexOf('/') + 1,
						url.length() - 5);
				System.out.println(picgroupid);
				String letters = picgroupid.replaceAll("([0-9])", "");
				for (int j = 0; j < letters.length(); j++)
					picgroupid = picgroupid.replace(
							letters.subSequence(j, j + 1),
							(int) letters.charAt(j) + "");
				System.out.println(picgroupid);
				picgroupid = picgroupid.substring(4, picgroupid.length() - 4);
				ImageGroup imgrp = null;
				for (int k = 0; imgrp == null && k < 5; k++) {
					try {
						imgrp = getImageGroup("http://www.95mm.com/slide-data/data/"
								+ picgroupid);
						imgrp.setType(category_name);
						// handler.add(imgrp);
						dg.downloadImageGroup(imgrp);
						// System.out.println(imgrp);
					} catch (JSONException e) {
						// e.printStackTrace();
						// logger.info("");
						logger.info(e.getMessage());
						try {
							System.in.read();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}

	public ImageGroup getImageGroup(String ajaxurl) throws JSONException,
			StringIndexOutOfBoundsException {
		ImageGroup images = new ImageGroup();
		Document doc4 = getHtmlByUrl(ajaxurl);
		String ajaxstring = StringEscapeUtils.unescapeHtml(doc4.select("body")
				.html());
		JSONObject ajaxdata = new JSONObject(ajaxstring.substring(ajaxstring
				.indexOf("{")));
		String imgTitle = ajaxdata.getJSONObject("slide").getString("title");
		images.setName(imgTitle);

		JSONArray imgJsonArray = ajaxdata.getJSONArray("images");
		images.setUrlList(getImgUrls(imgJsonArray));
		return images;
	}

	public List<String> getImgUrls(JSONArray imgJsonArray) {
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < imgJsonArray.length(); i++) {
			try {
				String url = imgJsonArray.getJSONObject(i).getString(
						"image_url");
				logger.info((i + 1) + ":\t " + url);
				urls.add(url);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return urls;
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

	public JSONObject getPicgroupListAjaxData(String page) {
		try {
			return new JSONObject(page).getJSONObject("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
