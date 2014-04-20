package diao.si.common;
import java.util.ArrayList;
import java.util.List;

public class ImageGroup4Downloader {

	private ImageGroup ig = new ImageGroup();

	private List<String> urlList = new ArrayList<String>();

	public void addImageUrl(String imgUrl) {
		this.urlList.add(imgUrl);
	}

	public void addImageUrls(List<String> imgUrls) {
		this.urlList.addAll(imgUrls);
	}

	public void setImageUrls(List<String> imgUrls) {
		this.urlList = imgUrls;
	}

	public List<String> getImageUrls() {
		return urlList;
	}

	public ImageGroup getImageGroup() {
		return this.ig;
	}

}
