package server;

interface Activity {
	String init();
	String run(String input);
	boolean getStatus();
}
