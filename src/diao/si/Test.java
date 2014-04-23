package diao.si;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Test {
	public static void main(String[] args) {
		new Test().test2();
	}
	
	public void test2(){
		String url = "http://www.tupian12345.com/HTM/pic/Sabranet/";
		Document doc;
		try {
			URL u = new URL(url);
			URLConnection con = u.openConnection();
			con.setConnectTimeout(10*1000);
			con.setReadTimeout(60*1000);
			doc = Jsoup.parse(con.getInputStream(), "gbk", url);
			Element e = doc.select("div.page1 > ul > li > a").get(1);
			String title = e.attr("title");
			System.out.println(title);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test1(){
		String hello = "{\"name\":\"[Beautyleg]2006.12.23 No.110 X\u0027mas 95 pics\",\"url\":\"/Beautyleg/109/\",\"num\":96,\"category\":\"Beautyleg\",\"size\":21039929,\"isserial\":true}";
		try {
			JSONObject json = new JSONObject(hello);
			System.out.println(json.get("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
