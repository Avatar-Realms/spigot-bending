package net.bendercraft.spigot.bending.abilities;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BendingPlayerData {
	private UUID player;
	private List<BendingElement> bendings;
	private List<BendingAffinity> affinities;
	private Map<String, Map<Integer, String>> decks;
	private String currentDeck;
	private List<BendingPerk> perks;
	private List<String> abilities;

	public UUID getPlayer() {
		return this.player;
	}

	public void setPlayer(UUID player) {
		this.player = player;
	}

	public List<BendingElement> getBendings() {
		return this.bendings;
	}

	public void setBendings(List<BendingElement> bending) {
		this.bendings = bending;
	}

	public Map<String, Map<Integer, String>> getDecks() {
		return this.decks;
	}

	public void setDecks(Map<String, Map<Integer, String>> slotAbilities) {
		this.decks = slotAbilities;
	}

	public List<BendingAffinity> getAffinities() {
		return this.affinities;
	}

	public void setAffinities(List<BendingAffinity> affinities) {
		this.affinities = affinities;
	}

	public String getCurrentDeck() {
		return this.currentDeck;
	}

	public void setCurrentDeck(String currentDeck) {
		this.currentDeck = currentDeck;
	}

	public List<BendingPerk> getPerks() {
		return perks;
	}

	public void setPerks(List<BendingPerk> perks) {
		this.perks = perks;
	}

	public List<String> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<String> abilities) {
		this.abilities = abilities;
	}
}
