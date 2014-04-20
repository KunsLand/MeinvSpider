package cc.aimm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import diao.si.common.ImageGroup4Downloader;
import diao.si.common.WebClient;
import diao.si.downloader.DownloadGroup;

public class PicClawer extends Thread {
	static Logger logger = Logger.getLogger(PicClawer.class.getName());
	private String invalidCharset = "[\\\\/:*?\"<>|]";
	private String category_url;

	public PicClawer(String category_url) {
		this.category_url = category_url;
	}

	public void run() {
		task();
	}

	public void task() {
		String category_name = category_url.substring(category_url
				.lastIndexOf("/") + 1);
		logger.info("Category:\t " + category_name + ":\t " + category_url);

		// each type page
		Document doc2 = WebClient.getHtmlByUrl(category_url);
		Document current_page = doc2;
		Elements pages_bar;
		while (true) {
			pages_bar = current_page.select("div.pages > ul > li");
			int current_page_num = Integer.valueOf(pages_bar
					.select("li.pagecur > a").first().ownText());
			logger.info("Now we are in " + category_name + ": Page"
					+ current_page_num);

			// picture-groups in this type page
			Elements piclists = current_page.select("div.preview");
			parseImageGroups(piclists, category_name);

			String nextPageUrl = pages_bar.last().select("a").first()
					.attr("href");
			if (!nextPageUrl.equals("#"))
				current_page = WebClient.getHtmlByUrl(category_url + "/" + nextPageUrl);
			else
				break;
		}

		System.out.println("Category: " + category_name + " finished");
	}

	public void parseImageGroups(Elements piclists, String category) {
		int i = 0;
		for (Element pic_group : piclists) {
			String url = pic_group.select("a").attr("href");
			if (!url.endsWith(".html"))
				continue; // exception when get 5th image group from
							// "qingchun" on page 24.
			String picgroupid = url.substring(url.lastIndexOf('/') + 1,
					url.length() - 5);
			// System.out.println(picgroupid);
			String letters = picgroupid.replaceAll("([0-9])", "");
			for (int j = 0; j < letters.length(); j++)
				picgroupid = picgroupid.replace(letters.subSequence(j, j + 1),
						(int) letters.charAt(j) + "");
			// System.out.println(picgroupid);
			picgroupid = picgroupid.substring(4, picgroupid.length() - 4);
			ImageGroup4Downloader imgrp = null;
			for (int k = 0; imgrp == null && k < 5; k++) {
				String ajaxurl = "http://www.95mm.com/slide-data/data/"
						+ picgroupid;
				try {
					imgrp = getImageGroup(ajaxurl);
					imgrp.getImageGroup().setCategory(category);
					imgrp.getImageGroup().setUrl("/" + category + "/" + i++);
					imgrp.getImageGroup().setIsserial(false);
					DownloadGroup dg = new DownloadGroup(imgrp);
					dg.start();
				} catch (JSONException e) {
					logger.info(e.getMessage());
					logger.info("Retry " + ajaxurl);
				}
			}
		}
	}

	public ImageGroup4Downloader getImageGroup(String ajaxurl)
			throws JSONException, StringIndexOutOfBoundsException {
		ImageGroup4Downloader ig4d = new ImageGroup4Downloader();
		Document doc4 = WebClient.getHtmlByUrl(ajaxurl);
		String ajaxstring = StringEscapeUtils.unescapeHtml(doc4.select("body")
				.html());

		// invalid title will cause JSONObject Exception
		String prefix = ajaxstring.substring(ajaxstring.indexOf("{"),
				ajaxstring.indexOf("title") + 8);
		String imgTitle = ajaxstring.substring(ajaxstring.indexOf("title") + 8,
				ajaxstring.indexOf("createtime") - 4);
		imgTitle = imgTitle.replaceAll(invalidCharset, "").trim();
		ig4d.getImageGroup().setName(imgTitle);

		ajaxstring = prefix + imgTitle
				+ ajaxstring.substring(ajaxstring.indexOf("createtime") - 4);
		JSONObject ajaxdata = new JSONObject("{ "
				+ ajaxstring.substring(ajaxstring.indexOf("images") - 1,
						ajaxstring.lastIndexOf("next_album") - 3) + "}");

		JSONArray imgJsonArray = ajaxdata.getJSONArray("images");
		ig4d.setImageUrls(getImgUrls(imgJsonArray));
		ig4d.getImageGroup().setNum(ig4d.getImageUrls().size());
		return ig4d;
	}

	public List<String> getImgUrls(JSONArray imgJsonArray) {
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < imgJsonArray.length(); i++) {
			try {
				String url = imgJsonArray.getJSONObject(i).getString(
						"image_url");
				urls.add(url);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return urls;
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
