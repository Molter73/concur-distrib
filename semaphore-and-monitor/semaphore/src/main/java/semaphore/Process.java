package semaphore;

import java.util.Random;

class InsufficientResourcesException extends Exception {
	InsufficientResourcesException(int requested, int available) {
		super("Insufficient resources. Requested=" + requested + ", got=" + available);
	}
}

class Process extends Thread {
	private static final int ONE_SECOND = 1000;
	private static final int RESOURCE_COUNT = 100;

	private static int currentID = 0;
	private int processID;

	Process() {
		processID = currentID++;
		start();
	}

	private void log(String msg) {
		System.out.println("Thread " + processID + ": " + msg);
	}

	@Override
	public void run() {
		Random rand = new Random();

		while (true) {
			boolean done = false;
			int requested = rand.nextInt(RESOURCE_COUNT / 3) + 1;
			log("Adquiriendo " + requested + " recursos");
			while (!done) {
				try {
					Resource.reserve(requested);
				} catch (InsufficientResourcesException e) {
					log(e.getMessage());
					log("Esperando para reintentar...");

					try {
						Thread.sleep(ONE_SECOND);
					} catch (InterruptedException e1) {
						log("Interrupted!!");
					}

					continue;
				}

				log("Esperando " + requested + " décimas de segundo");
				try {
					Thread.sleep(requested * 100);
				} catch (InterruptedException e) {
					log("Interrupted!!");
				}

				Resource.free(requested);
				done = true;
			}
		}
	}
}
