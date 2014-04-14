import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.imageio.ImageIO;

public class Downloader extends Thread {
	private String dir;
	private String filename;
	private String remoteurl;
	private ThreadCounter threadCounter;

	public Downloader(String dir, String filename, String remoteurl) {
		this.dir = dir;
		this.filename = filename;
		this.remoteurl = remoteurl;
	}

	public void run() {
		startDownload();
		if (threadCounter != null)
			threadCounter.decrese();
	}

	public void startDownload() {
		boolean flag = false;
		long startTime = new Date().getTime();
		do {
			try {
				pause();
				URL url = new URL(remoteurl);
				URLConnection connection = url.openConnection();
				connection
						.addRequestProperty(
								"User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
				BufferedImage image = ImageIO.read(url);
				long endTime = new Date().getTime();
				if (flag)
					System.out.println("it takes about "
							+ (endTime - startTime) / 1000
							+ " seconds to download " + remoteurl);
				flag = false;
				File path = new File(dir);
				path.mkdirs();
				File file = new File(dir + filename);
				file.createNewFile();
				ImageIO.write(image,
						remoteurl.substring(remoteurl.lastIndexOf(".") + 1),
						file);
				// System.out.println("File saved to: " + path.getPath());
			} catch (IOException e) {
				flag = true;
				System.out.println(e.getMessage() + "\nRe-download: "
						+ remoteurl);
			}
		} while (flag);
	}

	public void setThreadCounter(ThreadCounter threadCounter) {
		this.threadCounter = threadCounter;
		threadCounter.increse();
	}

	public static void main(String[] args) {
		new Downloader(
				"D:\\meinvPicture\\",
				"1-140405141220-51.jpg",
				"http://pic.tupian12345.com/picss/2014/allimg/140405/19494/1-140405141220-51.jpg")
				.start();
	}

	private void pause() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
