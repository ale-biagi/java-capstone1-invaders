package br.openhpi.capstone1.invaders.model;

import br.openhpi.capstone1.invaders.view.Observer;

public interface Observable {
	// every "observable" object (like game elements) must attach "observers" (views) to it...
	void attach(Observer observer);
	// ..., notify them whenever something relevant to be displayed changes...
	void notifyAllObservers();
	// ...and detach them for disposal
	void detachAllObservers();
}
