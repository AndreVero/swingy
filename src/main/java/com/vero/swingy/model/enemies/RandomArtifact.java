package com.vero.swingy.model.enemies;

import javax.swing.ImageIcon;

public class RandomArtifact {
	private int adding;
	private TypeOfArtifact type;
	private String name;
	private ImageIcon ii;
	
	public RandomArtifact(TypeOfArtifact type, String name) {
		String[] arr = name.split("-");
		try {
			this.ii = new ImageIcon("classes/" + arr[0] + ".png");
		}
		catch (Exception e){
			this.ii = null;
		}
		this.name = arr[0];
		this.type = type;
		this.adding = Integer.parseInt(arr[1]);
	}
	
	public int getAdding() {
		return adding;
	}

	public TypeOfArtifact getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public ImageIcon getIi() {
		return ii;
	}
}
