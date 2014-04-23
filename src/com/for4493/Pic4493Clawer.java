package com.for4493;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import diao.si.common.ImageGroup4Downloader;
import diao.si.common.ThreadPool;
import diao.si.common.WebClient;
import diao.si.downloader.DownloadGroup;

public class Pic4493Clawer extends Thread {

	private String category;

	private String baseurl = "http://www.4493.com/";

	public Pic4493Clawer(String category) {
		this.category = category;
	}

	public void run() {
		excute();
	}

	public void excute() {
		String url = baseurl + category;
		System.out.println(url);
		Document html = WebClient.getHtmlByUrl(url);
		Elements group_elements = html
				.select("div[class=all_lanmu ping] > ul > li");
		for (Element e : group_elements) {
			// claw each group
			clawEachGroup(e.select("a").first().attr("href"));
		}
		Elements page_bar = html.select("div.page1 > a");
		if (page_bar.isEmpty())
			return;
		while (page_bar.last().previousElementSibling().hasAttr("href")) {
			url = baseurl + page_bar.last().attr("href");
			html = WebClient.getHtmlByUrl(url);
			group_elements = html.select("div[class=all_lanmu ping] > ul > li");
			for (Element e : group_elements) {
				// claw each group
				clawEachGroup(e.select("a").first().attr("href"));
			}
			page_bar = html.select("div.page1 > a");
		}
	}

	private void clawEachGroup(String url) {
		System.out.println(url);
		
		String first_pic_url = url;
		String all_pic_url = url.replaceAll("/1.htm", ".htm");
		
		Document html = WebClient.getHtmlByUrl(first_pic_url);
		Element pic_info = html.select("span#picbox > a").first();
		String title = pic_info.attr("title");
		System.out.println(title);
		String pics_url_prefix = pic_info.select("img").first().attr("src");
		pics_url_prefix = pics_url_prefix.substring(0, pics_url_prefix.lastIndexOf("/"));

		System.out.println(all_pic_url);
		html = WebClient.getHtmlByUrl(all_pic_url);
		Elements pic_names = html.select("div.pic_show_list > ul > li > i > a > img");
//		System.out.println(pic_names);
		List<String> urlList = new ArrayList<String>();
		for(Element e: pic_names){
			String name = e.attr("src");
			String pic_url = pics_url_prefix+name.substring(name.lastIndexOf("/"));
			urlList.add(pic_url);
			System.out.println(pic_url);
		}
		ImageGroup4Downloader dg4d = new ImageGroup4Downloader();
		dg4d.getImageGroup().setCategory(category);
		dg4d.getImageGroup().setName(title);
		dg4d.addImageUrls(urlList);
		
		// downloader
//		ThreadPool.execute(new DownloadGroup(dg4d));
	}
	
	public static void main(String[] args) {
		new Pic4493Clawer("xingganmote").excute();
//		new Pic4493Clawer("mingxingxiezhen").excute();
//		new Pic4493Clawer("weimeixiezhen").excute();
//		new Pic4493Clawer("wangluomeinv").excute();
//		new Pic4493Clawer("tiyumeinv").excute();
//		new Pic4493Clawer("dongmanmeinv").excute();
//		new Pic4493Clawer("siwameitui").excute();
//		new Pic4493Clawer("gaoqingmeinv").excute();
//		new Pic4493Clawer("motemeinv").excute();
//		new Pic4493Clawer("yule").excute();
	}
}
