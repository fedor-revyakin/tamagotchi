package controller.playing;

import controller.IController;
import model.playing.Mediator;
import mypet.PlayingView;
import utilites.IObservable;
import utilites.IObserver;

public class Controller implements IController, IObserver  {

    public Controller(PlayingView view, Mediator mediator){

        this.view = view;
        this.mediator = mediator;
    }

    PlayingView view;

    Mediator mediator;

    @Override
    public void pressButton() {
        mediator.eatFood();
    }

    @Override
    public void update(IObservable observable, Object o) {
        view.disable();
    }
}