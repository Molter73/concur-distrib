package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 8888)) {
			System.out.println("Iniciando cliente");

			Scanner stdin = new Scanner(System.in);
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			boolean running = true;

			while (running) {
				do {
					String line = null;
					try {
						line = input.readLine();
					} catch (Exception e) {
					}

					if (line == null) {
						System.out.println("Conexión cerrada por el servidor");
						running = false;
						break;
					}

					System.out.println(line);
				} while (input.ready());

				if (running) {
					System.out.print("> ");
					output.println(stdin.nextLine());
				}
			}

			stdin.close();
		}
	}
}
