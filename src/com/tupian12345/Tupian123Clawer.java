package com.tupian12345;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import diao.si.common.ImageGroup4Downloader;
import diao.si.common.ThreadPool;
import diao.si.common.WebClient;
import diao.si.downloader.DownloadGroup;

public class Tupian123Clawer {

	private String baseUrl = "http://www.tupian12345.com";

	public void execute(String typeurl) {
		String type = typeurl.substring(9, typeurl.lastIndexOf("/"));
		// first page
		String url = baseUrl + typeurl;
		System.out.println(url);
		Document doc = WebClient.getHtmlByUrl(url);
		Elements picGroup = doc.select("div.page1 > ul > li > a");
		clawPicGroups(picGroup, type);

		if (doc.select("div.sxye > ul > li > a").isEmpty())
			return;
		// other pages
		while (!doc.select("div.sxye > ul > li").last().select("a").isEmpty()) {
			url = baseUrl
					+ typeurl
					+ doc.select("div.sxye > ul > li").last()
							.previousElementSibling().select("a").attr("href");
			System.out.println(url);
			doc = WebClient.getHtmlByUrl(url);
			picGroup = doc.select("div.page1 > ul > li > a");
			clawPicGroups(picGroup, type);
		}
	}

	private void clawPicGroups(Elements picGroup, String type) {
		for (Element e : picGroup) {
			String url = baseUrl + e.attr("href");
			String title = e.attr("title");
			System.out.println(url + ": " + title);
			Document html = WebClient.getHtmlByUrl(url);

			ImageGroup4Downloader ig = new ImageGroup4Downloader();
			ig.getImageGroup().setName(title);
			ig.getImageGroup().setCategory(type);

			Elements pics = html.select("div.page-list > div > p > img");
			ig.addImageUrls(getPicUrls(pics));
			String prefix = url.substring(0, url.lastIndexOf("/") + 1);

			Elements pages_bar = html.select("div.dede_pages ul li");
			if (!pages_bar.isEmpty()) {
				int pages = Integer.parseInt(pages_bar.last()
						.previousElementSibling().select("a").last().ownText());
				System.out.println("total pages: " + pages);
				while (!html.select("div.dede_pages ul li a").last()
						.attr("href").equals("#")) {
					url = prefix
							+ html.select("div.dede_pages ul li a").last()
									.attr("href");
					System.out.println(url);
					html = WebClient.getHtmlByUrl(url);
					pics = html.select("div.page-list > div > p > img");
					ig.addImageUrls(getPicUrls(pics));
					// pause();
				}
			}

			// send this image group to download handler
			 ThreadPool.execute(new DownloadGroup(ig));
		}
	}

	private List<String> getPicUrls(Elements pics) {
		List<String> urlList = new ArrayList<String>();
		for (Element pic : pics) {
			String picurl = pic.attr("src");
			urlList.add(picurl);
		}
		return urlList;
	}
}
