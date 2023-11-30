package semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

class InsufficientResourcesException extends Exception {
	InsufficientResourcesException(int requested, int available) {
		super("Insufficient resources. Requested=" + requested + ", got=" + available);
	}
}

class Process extends Thread {
	private static final int ONE_SECOND = 1000;
	private static final int RESOURCE_COUNT = 100;
	private static int availableResources = RESOURCE_COUNT;
	private static Semaphore resourceSemaphore = new Semaphore(1);

	private static int currentID = 0;
	private int processID;

	Process() {
		processID = currentID++;
		start();
	}

	private void log(String msg) {
		System.out.println("Thread " + processID + ": " + msg);
	}

	private void reserve(int requested) throws InsufficientResourcesException {
		log("reserve - Adquiriendo semáforo");

		try {
			resourceSemaphore.acquire();

			if (availableResources < requested) {
				throw new InsufficientResourcesException(requested, availableResources);
			}

			availableResources -= requested;
			log("reserve - " + availableResources + " restantes");
		} catch (InterruptedException e) {
			log(e.getMessage());
			e.printStackTrace();
		} finally {
			log("reserve - Liberando semáforo");
			resourceSemaphore.release();
		}

		log("reserve - Reservados " + requested + " recursos");
	}

	private void free(int requested) {
		log("free - Adquiriendo semáforo");

		try {
			resourceSemaphore.acquire();
			availableResources += requested;
			log("free - " + availableResources + " disponibles");
		} catch (InterruptedException e) {
			log(e.getMessage());
			e.printStackTrace();
		} finally {
			log("free - Liberando semáforo");
			resourceSemaphore.release();
		}

		log("free - Liberados " + requested + " recursos");
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
					reserve(requested);
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

				free(requested);
				done = true;
			}
		}
	}
}
