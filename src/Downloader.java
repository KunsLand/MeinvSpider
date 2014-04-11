import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Downloader extends Thread {
	private String dir;
	private String filename;
	private String remoteurl;
	private String invalidCharset = "[\\/:*?\"<>|]";

	public Downloader(String dir, String filename, String remoteurl) {
		this.dir = dir;
		this.filename = filename.replaceAll(invalidCharset, "").trim();
		this.remoteurl = remoteurl;
	}

	public void run() {
		startDownload();
	}

	public void startDownload() {
		boolean flag;
		long startTime = new Date().getTime();
		do {
			try {
				URL url = new URL(remoteurl);
				BufferedImage image = ImageIO.read(url);
				long endTime = new Date().getTime();
				System.out.println("it takes about " + (endTime - startTime)
						/ 1000 + " seconds to download " + remoteurl);
				flag = false;
				File path = new File(dir);
				path.mkdirs();
				File file = new File(dir + filename);
				file.createNewFile();
				ImageIO.write(image,
						remoteurl.substring(remoteurl.lastIndexOf(".") + 1),
						file);
				System.out.println("File saved to: " + path.getPath());
			} catch (IOException e) {
				flag = true;
				System.out.println(e.getMessage() + "\nRe-download: "
						+ remoteurl);
			}
		} while (flag);
	}

	public static void main(String[] args) {
		new Downloader(
				"D:\\meinvPicture\\",
				"1-140405141220-51.jpg",
				"http://pic.tupian12345.com/picss/2014/allimg/140405/19494/1-140405141220-51.jpg")
				.start();
	}
}
