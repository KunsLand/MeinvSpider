package com.tupian12345;

public class Tupian123ClawerManager extends Thread {

	private String typeurl;

	public Tupian123ClawerManager(String typeurl) {
		this.typeurl = typeurl;
	}

	public void run() {
		new Tupian123Clawer().execute(typeurl);
	}

	public static void main(String[] args) {
//		String baseUrl = "http://www.tupian12345.com";
//		new Tupian123Clawer().execute("/HTM/pic/TuiGirl/");
//		new Tupian123Clawer().execute("/HTM/pic/hanguofengsuniang/");
//		new Tupian123Clawer().execute("/HTM/pic/plans/");
//		new Tupian123Clawer().execute("/HTM/pic/Ligui/");
//		new Tupian123Clawer().execute("/HTM/pic/rqstar/");
//		new Tupian123Clawer().execute("/HTM/pic/ROSI/");
//		new Tupian123Clawer().execute("/HTM/pic/4k/");
//		new Tupian123Clawer().execute("/HTM/pic/dgxz/");
//		new Tupian123Clawer().execute("/HTM/pic/Beautyleg/");
//		new Tupian123Clawer().execute("/HTM/pic/DGC/");
//		new Tupian123Clawer().execute("/HTM/pic/taboo/");
//		new Tupian123Clawer().execute("/HTM/pic/TopQueen/");
//		new Tupian123Clawer().execute("/HTM/pic/xiuren/");
//		new Tupian123Clawer().execute("/HTM/pic/Bombtv/");
		new Tupian123Clawer().execute("/HTM/pic/Sabranet/");
//		Document siteMapHtml = getHtmlByUrl(baseUrl + "/data/sitemap.html");
//
//		Elements taotu = siteMapHtml.select("ul.f6").first().select("li a");
//		new Tupian123Clawer().execute(taotu.first().attr("href"));
//		new Tupian123ClawerThread(taotu.first().attr("href")).start();
//		for (Element e : taotu)
//			new Tupian123ClawerThread(e.attr("href")).start();

		// Elements shipin = siteMapHtml.select("ul.f6").get(1).select("li a");
		// for (Element e : shipin)
		// System.out.println(baseUrl + e.attr("href") + " : " + e.ownText());
	}

}
