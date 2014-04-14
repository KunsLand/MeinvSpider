import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownloadGroup implements ThreadCounter {

	private Executor executor = Executors.newFixedThreadPool(100);

	private String dir = "E:\\meinvPicture\\";

	private int threadCounter = 0;

	public synchronized void downloadImageGroup(ImageGroup imgs) {
		int i = 0;
		for (String url : imgs.getUrlList()) {
			Downloader downloader = new Downloader(dir + imgs.getType() + "\\"
					+ imgs.getName() + "\\", i++ + ".jpg", url);
			downloader.setThreadCounter(this);
			executor.execute(downloader);
		}
	}

	@Override
	public synchronized void increse() {
		this.threadCounter++;
		if (threadCounter % 69 == 0)
			System.out.println("Thread NUM: 70+");
		if (threadCounter < 5)
			System.out.println("Thread NUM: " + threadCounter);
	}

	@Override
	public synchronized void decrese() {
		this.threadCounter--;
		if (threadCounter % 69 == 0)
			System.out.println("Thread NUM: 70-");
		if (threadCounter < 5)
			System.out.println("Thread NUM: " + threadCounter);
	}

}
