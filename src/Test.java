import org.json.JSONException;


public class Test {

	public static void main(String[] args) {
		try {
			new PicClawer().getImageGroup("http://www.95mm.com/slide-data/data/3350");
		} catch (StringIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
