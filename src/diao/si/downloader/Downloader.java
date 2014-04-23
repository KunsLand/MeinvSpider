package diao.si.downloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import diao.si.common.WebClient;

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
		File path = new File(dir);
		if (!path.exists())
			path.mkdirs();
		String fileFormat = format == FORMAT_JPG ? "jpg" : remoteurl
				.substring(remoteurl.lastIndexOf(".") + 1);
		File file = new File(dir + filename + "." + fileFormat);
		if (!(file.exists() && file.length() > 0)) {
			// create file
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// download image file
			BufferedImage image = WebClient.readImage(remoteurl);
			try {
				ImageIO.write(image, fileFormat, file);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new Downloader(
				"D:\\meinvPicture\\",
				"1-140405141220-51.jpg",
				"http://pic.tupian12345.com/picss/2014/allimg/140405/19494/1-140405141220-51.jpg",
				Downloader.FORMAT_JPG).start();
	}

}
