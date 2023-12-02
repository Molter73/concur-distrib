package monitor;

import java.util.Random;

public class Car extends Thread {
	private static Random random = new Random();
	private static int currentID = 0;
	private int id;
	private Status state;
	private BridgeMonitor monitor;

	Car(BridgeMonitor m) {
		state = random.nextBoolean() ? Status.GOING_NORTH : Status.GOING_SOUTH;
		id = ++currentID;
		monitor = m;
		start();
	}

	private void log(String msg) {
		System.out.println("Auto " + id + ": " + msg);
	}

	public void run() {
		log("Intentando cruzar " + state.toString());
		monitor.getOn(state);
		log("Cruzando");

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log("Saliendo del puente");
		monitor.getOff();
	}
}
