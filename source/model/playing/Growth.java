package model.playing;

import java.util.TimerTask;
import java.util.Timer;

import javafx.application.Platform;
import utilites.*;
import utilites.petproperties.GrowthEnum;

public class Growth extends TimerTask implements IObservable {

	public Growth(GrowthEnum growth) {

		this.growth = growth;

		if (!growth.equals(GrowthEnum.BIG)) {

			//инциализировать таймер
			timerAgeIncrease = new Timer(true);

			timerAgeIncrease.scheduleAtFixedRate(this, 600000, 600000);
		}

	}

	private IObserver mediator;

	private GrowthEnum growth;

	private Timer timerAgeIncrease;

	@Override
	public void run(){

		notifyObservers(Interests.GROWTH);

	}

	public void increase() {

		switch (growth) {

			case LITTLE: growth = GrowthEnum.JUNIOR;
			break;

			case JUNIOR: growth = GrowthEnum.MIDDLE;
			break;

			case MIDDLE: {
				growth = GrowthEnum.BIG;
				timerAgeIncrease.cancel();
				break;
			}

		}
	}

	public GrowthEnum getGrowth() {
		return growth;
	}

	@Override
	public void notifyObservers(Object observerInterest) {
		Platform.runLater(() -> {
			mediator.update(this, observerInterest);
		});
	}

	@Override
	public void addObserver(IObserver o) {
		mediator = o;
	}


	@Override
	public void addObserver(Interests observerInterest, IObserver o) {}
	@Override
	public void notifyObservers(Interests observerInterest, Object o) {}

}