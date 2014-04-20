package diao.si.downloader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.imageio.ImageIO;

public class Downloader extends Thread {

	public final static int FORMAT_JPG = 1;

	public final static int FORMAT_AUTO = 0;

	private String dir;
	private String filename;
	private String remoteurl;
	private int format;

	public Downloader(String dir, String filename, String remoteurl, int format) {
		this.dir = dir;
		this.filename = filename;
		this.remoteurl = remoteurl;
		this.format = format;
	}

	public void run() {
		startDownload();
	}

	public void startDownload() {
		long size = 0;
		File path = new File(dir);
		if (!path.exists())
			path.mkdirs();
		String fileFormat = format == FORMAT_JPG ? "jpg" : remoteurl
				.substring(remoteurl.lastIndexOf(".") + 1);
		File file = new File(dir + filename + "." + fileFormat);
		if (file.exists() && file.length() > 0) {
			// read image file
			size = file.length();
			// System.out.println("File exists: " + file.getPath()
			// + "\t size(kb): " + size);
		} else {
			// create file
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// download image file
			boolean flag = false;
			long startTime = System.currentTimeMillis();
			do {
				try {
					BufferedImage image = readImage();
					long endTime = new Date().getTime();
					if (flag)
						System.out.println("it takes about "
								+ (endTime - startTime) / 1000
								+ " seconds to download " + remoteurl);
					flag = false;
					ImageIO.write(image, fileFormat, file);
					size = file.length();
					if (size == 0)
						System.out.println("File saved to: " + path.getPath()
								+ "\t File Length(kb): " + size);
				} catch (IOException e) {
					flag = true;
					pause();
					System.out.println(e.getMessage() + "\nRe-download: "
							+ remoteurl);
				}
			} while (flag);
		}
	}

	public static void main(String[] args) {
		new Downloader(
				"D:\\meinvPicture\\",
				"1-140405141220-51.jpg",
				"http://pic.tupian12345.com/picss/2014/allimg/140405/19494/1-140405141220-51.jpg",
				Downloader.FORMAT_JPG).start();
	}

	public BufferedImage readImage() throws IOException {
		System.out.println(remoteurl);
		URL url = new URL(remoteurl);
		URLConnection connection = url.openConnection();
		connection
				.addRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
		BufferedImage image = ImageIO.read(url);
		return image;
	}

	private void pause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
