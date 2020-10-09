package utilites;

public interface IObservable {

public void notifyObservers(Object observerInterest);

public void notifyObservers(Interests observerInterest, Object o);

public void addObserver(IObserver o);

public void addObserver(Interests observerInterest, IObserver o);

}