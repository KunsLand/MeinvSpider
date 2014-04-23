package diao.si.downloader;

import java.io.File;

import diao.si.common.Configure;
import diao.si.common.ImageGroup4Downloader;

public class DownloadGroup extends Thread {

	private String filepath;

	private ImageGroup4Downloader imgs;

	public DownloadGroup(ImageGroup4Downloader imgs) {
		this.imgs = imgs;
		this.filepath = Configure.FILE_ROOT
				+ imgs.getImageGroup().getCategory() + "\\"
				+ imgs.getImageGroup().getName() + "\\";
	}

	public void run() {
		// threadCounter.increse();
		startDownloadImageGroup();
		// threadCounter.decrese();
	}

	public void startDownloadImageGroup() {
		int i = 0;
		for (String url : imgs.getImageUrls()) {
			Downloader downloader = new Downloader(filepath, i++ + "", url,
					Downloader.FORMAT_JPG);
			downloader.startDownload();
		}
		imgs.getImageGroup().setNum(i);
		File groupdir = new File(filepath);
		File[] pic_files = groupdir.listFiles();
		long size = 0;
		for (File pic_file : pic_files)
			size += pic_file.length();
		imgs.getImageGroup().setSize(size);
		System.out.println("COMPLETE GROUP: "
				+ imgs.getImageGroup().getCategory() + " \ttitle: "
				+ imgs.getImageGroup().getName() + " \tsize: "
				+ imgs.getImageGroup().getSize() + " \tnum: "
				+ imgs.getImageGroup().getNum());
	}
}
