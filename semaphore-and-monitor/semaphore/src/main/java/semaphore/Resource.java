package semaphore;

import java.util.concurrent.Semaphore;

class Resource {
	private static final int RESOURCE_COUNT = 100;
	private static int availableResources = RESOURCE_COUNT;
	private static Semaphore resourceSemaphore = new Semaphore(1);

	private static void log(String msg) {
		System.out.println("Recurso: " + msg);
	}

	public static void reserve(int requested) throws InsufficientResourcesException {
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

	public static void free(int requested) {
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

}
