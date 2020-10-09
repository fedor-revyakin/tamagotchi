package model.playing;

import javafx.application.Platform;
import utilites.IObservable;
import utilites.IObserver;
import utilites.Interests;
import utilites.petproperties.GrowthEnum;
import utilites.petproperties.MoodEnum;
import utilites.petproperties.PetData;
import utilites.petproperties.PropertyNameEnum;

import java.util.Hashtable;


public class Mediator implements IObserver, IObservable {

    public Mediator(PetData petData) {

        this.petData = petData;
        this.growth = new Growth((GrowthEnum) petData.getProperty(PropertyNameEnum.GROWTH));
        this.mood = new Mood((MoodEnum) petData.getProperty(PropertyNameEnum.MOOD));

        growth.addObserver((IObserver)this);
        mood.addObserver((IObserver)this);



    }

    PetData petData;

    private Growth growth;
    private Mood mood;

    //оповестить контроллер о гибели питомца
    private IObserver controller;

    private Hashtable<Interests, IObserver> observerList = new Hashtable<Interests, IObserver>();


    @Override
    public void update(IObservable observable, Object o) {

        Interests interests = (Interests)o;

        switch (interests) {

            case GROWTH: changeGrowth();
            break;

            case MOOD: changeMood();
            break;

        }
    }

    public void eatFood(){

        mood.increase();

        //Изменение настроения питомца после еды
        notifyObservers(Interests.MOOD, mood);
    }

    private void changeMood(){

        if (mood.getMood().equals(MoodEnum.DEAD)) {

            notifyObservers(false);

        } else {

            notifyObservers(Interests.MOOD, mood);

            petData.setProperty(PropertyNameEnum.MOOD, mood.getMood());
        }
    }

    private void changeGrowth(){

        if (mood.getMood().equals(MoodEnum.GOOD) || mood.getMood().equals(MoodEnum.PERFECT)) {

            growth.increase();

            notifyObservers(Interests.GROWTH, growth);

            petData.setProperty(PropertyNameEnum.GROWTH, growth.getGrowth());

        }
    }


    @Override
    public synchronized void notifyObservers(Object isAlive)  {

        Platform.runLater(() -> {

            controller.update((IObservable) this, isAlive);

            });
    }

    @Override
    public synchronized void notifyObservers(Interests observerInterest, Object o) {

        Platform.runLater(() -> {

            IObserver ob = observerList.get(observerInterest);

            ob.update(this, o);

        });
    }

    @Override
    public void addObserver(IObserver o) {
        controller = o;
    }

    @Override
    public void addObserver(Interests observerInterest, IObserver o) {

        observerList.put(observerInterest, o);
    }

}

