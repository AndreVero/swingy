package com.vero.swingy.view.window;

import com.vero.swingy.controller.Controler;

public interface Window{
	
	public int	getVis();
	public void setVis();
	public void regObserver(Controler ob);
	public void setSize(int width, int height);
	public void win();
}
