package net.avatar.realms.spigot.bending.abilities;

public enum AbilityState {

	None 		(0),
	CannotStart	(1),
	CanStart 	(2),
	Preparing 	(3), 
	Prepared 	(4),
	Progressing	(5),
	Ending		(6),
	Ended		(7),
	Removed		(8);

	private int step;

	AbilityState (int step) {
		this.step = step;
	}

	public boolean isBefore (AbilityState state) {
		if (this.step < state.step) {
			return true;
		}
		return false;
	}
}