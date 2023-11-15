package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
		ECHO,
		QUIT,
		CLIENT_DISCONNECTED,
		INVALID,
	}

	private Options menu() throws IOException {
		output.println("Seleccione una opción:");
		output.println("1. Eco");
		output.println("2. Salir");
		output.flush();

		String line = input.readLine();
		if (line == null) {
			System.out.println("Cliente desconectado");
			return Options.CLIENT_DISCONNECTED;
		}

		int option;
		try {
			option = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			System.out.println("Recibida opción no numérica");
			return Options.INVALID;
		}

		switch (option) {
			case 1:
				return Options.ECHO;
			case 2:
				return Options.QUIT;
			default:
				return Options.INVALID;
		}
	}

	@Override
	public void run() {
		System.out.println("Iniciando nuevo worker");
		Options option;
		while (socket.isConnected()) {
			try {
				option = menu();
			} catch (IOException e) {
				System.out.println("Error de IO");
				return;
			}

			switch (option) {
				case ECHO:
					output.println("TODO");
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
				case CLIENT_DISCONNECTED:
					System.out.println("Conexión terminada por el cliente");
					return;
				case INVALID:
					output.println("Opción inválida");
					break;
			}
		}
	}
}
