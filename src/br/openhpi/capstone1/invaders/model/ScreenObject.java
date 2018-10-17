package br.openhpi.capstone1.invaders.model;


import java.util.ArrayList;
import java.util.List;

import br.openhpi.capstone1.invaders.view.Observer;

public abstract class ScreenObject implements Observable, Drawable {

	// this is the base abstract class for every screen object (element)
	// every element in the screen is observable by an "observer" (view) but cannot be moved along the screen
	
	// every element has a position in the screen, a size and a color code
	public ScreenPosition screenPosition;
	public Size size;
	public RGBColor color;

	protected List<Observer> observers = new ArrayList<Observer>();
	
	public ScreenObject(ScreenPosition screenPosition, Size size, RGBColor color) {
		this.screenPosition = screenPosition;
		this.size = size;
		this.color = color;
	}
	
	public ScreenObject(ScreenPosition screenPosition, Size size) {
		this(screenPosition, size, new RGBColor());
	}
	
	@Override
	public void attach(Observer observer) {
		// attach an observer (view)
		observers.add(observer);
	}

	@Override
	public void notifyAllObservers() {
		// notify all attached observers to update their view state
		for(Observer observer : observers) {
			observer.update();
		}
	}
	
	@Override
	public void detachAllObservers() {
		// remove and clean all attached observers to update their view state
		while(observers.size() > 0) {
			observers.set(0, null);
			observers.remove(0);
		}
	}
	
	@Override
	public void draw() {
		// drawing is a standard behavior of every screen element
		notifyAllObservers();
	}
}
