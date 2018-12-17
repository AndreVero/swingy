package com.vero.swingy.model.enemies;

import java.util.Random;

public class Artifact {
	private static Artifact instance;
	private Random r;
	private final String art[] = {
		"Little elixir of power-2",
		"Middle elixir of power-4",
		"Big elixir of power-6",
		
		"Little elixir of protection-2",
		"Middle elixir of protection-4",
		"Big elixir of protection-6",
		
		"Life elixir-75",
		"Super Life elixir-125",
		"Mega Life elixir-175"
	};
	int simple[] = {0,3,6};
	int middle[] = {1,4,7};
	int big[] = {2,5,8};
	
	private Artifact() {
		r = new Random();
	}
	
	public static Artifact getArtifact() {
		if (instance == null) {
			instance = new Artifact();
			return instance;
		}
		return instance;
	}
	
	public RandomArtifact getBonuses(int i) {
		int res = r.nextInt(3);
		if (i == 1)
			res = simple[res];
		else if (i == 2)
			res = middle[res];
		else
			res = big[res];
		TypeOfArtifact type;
		if (res < 3)
			type = TypeOfArtifact.ATTACK;
		else if (res > 5)
			type = TypeOfArtifact.HP;
		else
			type = TypeOfArtifact.DEFENCE;
		return (new RandomArtifact(type, art[res]));
	}
}
