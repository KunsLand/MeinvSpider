package diao.si.common;
public class ImageGroup {

	private String name;
	private String url;
	private int num;
	private String category;
	private long size;
	private boolean isserial;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isIsserial() {
		return isserial;
	}

	public void setIsserial(boolean isserial) {
		this.isserial = isserial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String type) {
		this.category = type;
	}
}
