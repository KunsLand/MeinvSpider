package diao.si.downloader;
import java.io.File;

import diao.si.common.Configure;
import diao.si.common.ImageGroup4Downloader;

public class DownloadGroup extends Thread {

	private String filepath;

	private ImageGroup4Downloader imgs;


	public DownloadGroup(ImageGroup4Downloader imgs) {
		this.imgs = imgs;
		this.filepath = Configure.FILE_ROOT + imgs.getImageGroup().getCategory() + "\\"
				+ imgs.getImageGroup().getName() + "\\";
	}

	public void run() {
//		threadCounter.increse();
		startDownloadImageGroup();
//		threadCounter.decrese();
	}

	public void startDownloadImageGroup() {
		int i = 0;
		for (String url : imgs.getImageUrls()) {
			Downloader downloader = new Downloader(filepath, i++ + "", url, Downloader.FORMAT_JPG);
			downloader.startDownload();
		}
		File groupdir = new File(filepath);
		long size = groupdir.length();
		imgs.getImageGroup().setSize(size);
	}
}
