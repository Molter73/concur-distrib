package server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class RockPaperScissor implements Activity {
	private String menuOutput;
	private boolean running = true;

	enum Choices {
		ROCK {
			@Override
			public String toString() {
				return "Piedra!";
			}

			public int toInt() {
				return 0;
			}
		},
		PAPER {
			@Override
			public String toString() {
				return "Papel!";
			}

			public int toInt() {
				return 1;
			}
		},
		SCISSOR {
			@Override
			public String toString() {
				return "Tijeras!";
			}

			public int toInt() {
				return 2;
			}
		},
	}

	private static final List<Choices> VALUES = Collections.unmodifiableList(Arrays.asList(Choices.values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	private static final int[][] OUTCOME = {
			{ 0, -1, 1 },
			{ 1, 0, -1 },
			{ -1, 1, 0 },
	};

	private String menu() {
		if (menuOutput == null) {
			menuOutput = "";
			int count = 1;
			for (Choices choice : Choices.values()) {
				menuOutput += count + ". " + choice.toString() + "\n";
				count++;
			}

			menuOutput += count + ". Salir\n";
		}

		return menuOutput;
	}

	@Override
	public String init() {
		return "Bienvenido a Piedra, Papel o Tijeras!\n" + menu();
	}

	private String game(String input) {
		int userPick = Integer.parseInt(input) - 1;
		if (userPick == 3) {
			running = false;
			return "";
		}

		if (userPick < 0 || userPick > 2) {
			return "Opción invalida";
		}

		int serverPick = RANDOM.nextInt(SIZE);
		int outcome = OUTCOME[userPick][serverPick];

		String result;

		switch (outcome) {
			case 0:
				result = "Empate!";
				break;
			case 1:
				result = "Victoria!";
				break;
			case -1:
				result = "Perdiste...";
				break;
			default:
				return "Unreachable";
		}

		return "Elegiste '" + VALUES.get(userPick).toString() + "'\n" +
				"Servidor elige '" + VALUES.get(serverPick).toString() + "'\n" + result;
	}

	@Override
	public String run(String input) {
		return game(input) + "\n" + menu();
	}

	@Override
	public boolean getStatus() {
		return running;
	}
}
