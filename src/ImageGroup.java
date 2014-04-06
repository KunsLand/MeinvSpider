import java.util.ArrayList;
import java.util.List;

public class ImageGroup {

	private String name;
	private String type;
	private List<String> urlList = new ArrayList<String>();

	// public ImageGroup(String name , List<String> urlList){
	// this .name = name;
	// this.urlList = urlList;
	// }

	public void addUrl(String url) {
		urlList.add(url);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public void addUrlList(List<String> urlList) {
		this.urlList.addAll(urlList);
	}

}
