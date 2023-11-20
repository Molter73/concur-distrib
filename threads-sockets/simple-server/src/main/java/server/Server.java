package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Server {
	private static final int DEFAULT_PORT = 8888;

	public static void main(String[] args) throws IOException, ParseException {
		Options options = new Options();
		options.addOption("p", "port", true, "Puerto utilizado por el servidor. Default: 8888");

		CommandLineParser parser = new DefaultParser();
		CommandLine cli = parser.parse(options, args);

		int port = DEFAULT_PORT;
		if (cli.hasOption('p')) {
			port = Integer.parseInt(cli.getOptionValue('p'));
		}

		try (ServerSocket listener = new ServerSocket(port)) {
			System.out.println("Servidor iniciado.");
			System.out.println("Escuchando en puerto " + port);

			while (true) {
				Socket socket = listener.accept();
				new Thread(new Worker(socket)).start();
			}
		}
	}
}
