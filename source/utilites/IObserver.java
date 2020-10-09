package utilites;

public interface IObserver {

	//observable - посредник
	//Object o - изменяющийся объект
	public void update(IObservable observable, Object o);
}