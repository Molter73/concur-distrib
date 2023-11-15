package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		try (ServerSocket listener = new ServerSocket(8888)) {
			System.out.println("Servidor iniciado");

			while (true) {
				Socket socket = listener.accept();
				new Thread(new Worker(socket)).start();
			}
		}
	}
}
