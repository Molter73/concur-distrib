package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientDisconnectedException extends Exception {
	ClientDisconnectedException(String msg) {
		super(msg);
	}
}

class InvalidInputException extends Exception {
	InvalidInputException(String msg) {
		super(msg);
	}
}

class Worker implements Runnable {
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public Worker(Socket s) throws IOException {
		socket = s;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
	}

	private enum Options {
		ECHO {
			@Override
			public String toString() {
				return "Eco";
			}
		},
		ROCK_PAPER_SCISSOR {
			@Override
			public String toString() {
				return "Piedra, papel o tijeras!";
			}
		},
		QUIT {
			@Override
			public String toString() {
				return "Salir";
			}
		},
	}

	private Options menu() throws IOException, InvalidInputException, ClientDisconnectedException {
		output.println("Seleccione una opción:");
		int count = 1;
		for (Options option : Options.values()) {
			output.println(count + ". " + option.toString());
			count++;
		}
		output.flush();

		String line = input.readLine();
		if (line == null) {
			throw new ClientDisconnectedException("Cliente desconectado");
		}

		int option;
		try {
			option = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Recibida opción no numérica");
		}

		switch (option) {
			case 1:
				return Options.ECHO;
			case 2:
				return Options.ROCK_PAPER_SCISSOR;
			case 3:
				return Options.QUIT;
			default:
				throw new InvalidInputException("Opción fuera de rango");
		}
	}

	@Override
	public void run() {
		System.out.println("Iniciando nuevo worker");
		Options option;
		while (socket.isConnected()) {
			try {
				option = menu();
			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (ClientDisconnectedException e) {
				System.out.println(e.getMessage());
				return;
			} catch (IOException e) {
				System.out.println("Error de IO");
				return;
			}

			Activity activity;

			switch (option) {
				case ECHO:
					activity = new Echo();
					break;
				case ROCK_PAPER_SCISSOR:
					activity = new RockPaperScissor();
					break;
				case QUIT:
					System.out.println("Cerrando worker");
					output.println("Tenga un buen día :)");
					output.flush();

					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					return;
				default:
					return;
			}

			runActivity(activity);
		}
	}

	private void runActivity(Activity activity) {
		String in;
		String out = activity.init();
		while (activity.getStatus()) {
			output.println(out);
			output.flush();

			try {
				in = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			out = activity.run(in);
		}
	}
}
