import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownloadGroup {

	private Executor executor = Executors.newFixedThreadPool(20);

	private String dir = "E:\\meinvPicture\\";

	public void downloadImageGroup(ImageGroup imgs) {
		int i = 0;
		for (String url : imgs.getUrlList())
			executor.execute(new Downloader(dir + imgs.getType() + "\\"+imgs.getName()+"\\", i++
					+ ".jpg", url));
	}

}
