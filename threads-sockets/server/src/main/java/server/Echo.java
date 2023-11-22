package server;

class Echo implements Activity {
	boolean running = true;

	@Override
	public String init() {
		return "Servicio de eco.\nIngrese Salir para regresar al menú";
	}

	@Override
	public String run(String input) {
		if (input.equals("Salir")) {
			running = false;
			return "";
		}
		return input;
	}

	@Override
	public boolean getStatus() {
		return running;
	}
}
