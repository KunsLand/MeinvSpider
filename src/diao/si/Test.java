package diao.si;

import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static void main(String[] args) {
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
