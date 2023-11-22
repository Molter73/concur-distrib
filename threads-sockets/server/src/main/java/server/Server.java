package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class Server {
	private static final int DEFAULT_PORT = 8888;

	/**
	 * Entrypoint for the server.
	 * <p>
	 * This method will parse CLI arguments, open a socket on the configured
	 * port and wait for connections on it. Once a connection is made, the new
	 * peer is handled by a new {@link Worker} thread.
	 *
	 * @param args CLI arguments provided to the server.
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.apache.commons.cli.ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException, org.apache.commons.cli.ParseException {
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
