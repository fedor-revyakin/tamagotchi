package utilites;


public class TinyTwister {

    public TinyTwister(int listSize) {
        this.bounds = listSize - 1;
    }

    private int bounds;
    private int currentItem;

    public int getCurrentItem() {
        return currentItem;
    }

    public int nextPet() {

        if (currentItem == bounds) {

            currentItem = 0;
        } else {

            currentItem += 1;
        }

        return currentItem;
    }


    public int previousPet() {

        if (currentItem == 0) {

            currentItem = bounds;
        } else {

            currentItem -= 1;
        }

        return currentItem;
    }
}
