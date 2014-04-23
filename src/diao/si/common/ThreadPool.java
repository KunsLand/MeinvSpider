package diao.si.common;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private static ExecutorService executor;

	static {
		executor = Executors.newFixedThreadPool(40);
	}

	public static void execute(Runnable run) {
		executor.execute(run);
	}
}
