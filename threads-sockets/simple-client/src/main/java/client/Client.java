package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

class Client {
	private static final int DEFAULT_PORT = 8888;
	private static final String DEFAULT_ADDRESS = "localhost";

	private static void run(String address, int port) throws UnknownHostException, IOException {
		try (Socket socket = new Socket(address, port)) {
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

	public static void main(String[] args) throws UnknownHostException, IOException, ParseException {
		Options options = new Options();
		options.addOption("p", "port", true, "Puerto utilizado por el servidor. Default: 8888");
		options.addOption("a", "address", true, "Dirección del servidor. Default: localhost");

		CommandLineParser parser = new DefaultParser();
		CommandLine cli = parser.parse(options, args);

		int port = DEFAULT_PORT;
		if (cli.hasOption('p')) {
			port = Integer.parseInt(cli.getOptionValue('p'));
		}

		String address = DEFAULT_ADDRESS;
		if (cli.hasOption('a')) {
			address = cli.getOptionValue('a');
		}

		run(address, port);
	}
}
