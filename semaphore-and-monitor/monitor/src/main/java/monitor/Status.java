package monitor;

public enum Status {
	EMPTY,
	GOING_SOUTH {
		@Override
		public String toString() {
			return "Norte-Sur";
		}
	},
	GOING_NORTH {
		@Override
		public String toString() {
			return "Sur-Norte";
		}
	},
}
