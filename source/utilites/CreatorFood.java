package utilites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mypet.Main;

import java.util.Date;
import java.util.Random;

public class CreatorFood {

    private  ImageView[] food = new ImageView[9];

    private  Random random = new Random((new Date()).getTime());

    public CreatorFood(String source) {

        for (int i = 0; i < food.length; i++) {

            food[i] = new ImageView(new Image(Main.class.getResource("/food/" + source + "/" + i + ".png").toString(), 50,0,true,true));

        }

    }

    public ImageView getFood() {

        return food[random.nextInt(food.length)];
    }



}








/*

public class CreatorFood {

    private static ImageView[] food = new ImageView[9];

    private static String path;

    private static Random random = new Random((new Date()).getTime());

    static {
        for (int i = 0; i < food.length; i++) {

            food[i] = new ImageView(new Image(Main.class.getResource("/food/" + path + "/" + i + ".png").toString(), 50,0,true,true));

        }

    }

    public static ImageView getFood() {

        return food[random.nextInt(food.length)];
    }

    public static void setSource(String source){
        path = source;
    }
}

 */
