package monitor;

public class App {
	public static void main(String[] args) {
		BridgeMonitor monitor = new BridgeMonitor();

		// Hacemos cruzar a 100 autos a la vez
		for (int i = 0; i < 100; i++) {
			new Car(monitor);
		}
	}
}
