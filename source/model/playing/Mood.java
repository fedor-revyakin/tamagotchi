package model.playing;

import javafx.application.Platform;
import utilites.*;
import utilites.petproperties.MoodEnum;
import java.util.TimerTask;
import java.util.Timer;

public class Mood implements IObservable {

	public Mood(MoodEnum mood) {

		this.mood = mood;

		//инциализировать таймер
		timerBadMood = new Timer(true);

		//Если питомец не ест хотя бы раз за 3 часа (10800000 мс), настроение ухудшается.
		timerBadMood.scheduleAtFixedRate(new MyTask(), 10800000, 10800000);

	}

	class MyTask extends TimerTask {
	//"Уменьшить" настроение питомца.
		@Override
		public void run() {
			reduce();
		}
	}

	private MoodEnum mood;

	private IObserver mediator;

	private Timer timerBadMood;



	public MoodEnum getMood() {
		return mood;
	}


	public void reduce() {

		switch (mood) {

			case BAD: {
				timerBadMood.cancel();
				mood = MoodEnum.DEAD;
			}
			break;

			case GOOD: mood = MoodEnum.BAD;
			break;

			case PERFECT: mood = MoodEnum.GOOD;
			break;

		}

		notifyObservers(Interests.MOOD);
	}


	public void increase() {

		timerBadMood.cancel();

		switch (mood) {

			case BAD: mood = MoodEnum.GOOD;
			break;

			case GOOD: mood = MoodEnum.PERFECT;
			break;

		}

		timerBadMood = new Timer(true);
		timerBadMood.scheduleAtFixedRate(new MyTask(), 10800000, 10800000);

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