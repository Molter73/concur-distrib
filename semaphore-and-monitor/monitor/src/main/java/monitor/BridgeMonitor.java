package monitor;

public class BridgeMonitor {
	private Status state = Status.EMPTY;
	private int carsCrossing = 0;

	private void log(String msg) {
		System.out.println("BridgeMonitor: " + msg);
	}

	public synchronized void getOn(Status s) {
		log("Nuevo auto en el puente");

		// Esperamos que se esté cruzando en nuestro mismo sentido o
		// a que el puente esté vacío.
		while (state != s && state != Status.EMPTY) {
			log("Esperando que se libere el puente");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		log("Cruzando " + s.toString());

		// Cruzamos.
		if (state == Status.EMPTY) {
			state = s;
		}
		carsCrossing++;
		log("Autos cruzando el puente: " + carsCrossing);
	}

	public synchronized void getOff() {
		log("Auto saliendo del puente");

		carsCrossing--;
		if (carsCrossing == 0) {
			state = Status.EMPTY;
			notifyAll();
		}

		log("Autos cruzando el puente: " + carsCrossing);
	}
}
