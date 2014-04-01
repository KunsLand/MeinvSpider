import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPool {

	private static Executor executor;

	static {
		executor = Executors.newFixedThreadPool(20);
	}

	public static void execute(Runnable run) {
		executor.execute(run);
	}
}
