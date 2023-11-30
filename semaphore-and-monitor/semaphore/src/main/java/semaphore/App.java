package semaphore;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			new Process();
		}
	}
}
