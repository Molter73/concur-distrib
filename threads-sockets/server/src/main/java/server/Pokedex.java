package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

class Pokedex implements Activity {
	private boolean running = true;
	private static ArrayList<String> pokemons = null;

	private ArrayList<String> loadPokemon() throws URISyntaxException, IOException {
		InputStreamReader pokedexStream = new InputStreamReader(getClass().getResourceAsStream("/pokedex.txt"));
		BufferedReader reader = new BufferedReader(pokedexStream);
		ArrayList<String> p = new ArrayList<>();

		String drawing = "";
		String line;
		while ((line = reader.readLine()) != null) {
			if (!line.isEmpty()) {
				drawing += line + "\n";
				continue;
			}

			if (drawing.isEmpty()) {
				continue;
			}

			p.add(drawing);
			drawing = "";
		}

		reader.close();
		return p;
	}

	private synchronized String getPokemon(int n) {
		if (pokemons == null) {
			try {
				pokemons = loadPokemon();
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
				return e.getMessage();
			}
		}

		return pokemons.get(n);
	}

	@Override
	public String init() {
		return "Bienvenido a la PokèDex!\n0. Salir\n1-151. Imprimir pokemon";
	}

	@Override
	public String run(String input) {
		int n = Integer.parseInt(input);
		if (n == 0) {
			running = false;
			return "";
		}

		return getPokemon(n - 1);
	}

	@Override
	public boolean getStatus() {
		return running;
	}
}
