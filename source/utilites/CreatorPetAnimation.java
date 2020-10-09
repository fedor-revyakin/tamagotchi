package utilites;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import mypet.Main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class CreatorPetAnimation {

    public static Timeline createTimeLine(String path, int picCount, Group container) {

        /*
        Читать в директории path файл значений параметров:
        -cycle_count : int
        -action_duration
        -pause : int - если пауза не нужна, указать то же значение, что для action_duration
         */
        Hashtable<String, String> properties = new Hashtable<String, String>();

        //String directory = path.replace("/", File.separator);

        try {

            String s = "";

            Scanner scanner = new Scanner(Main.class.getResourceAsStream(path + "properties.txt"));

            while (scanner.hasNextLine()) {

                s = scanner.nextLine();

                properties.put(s.split(" ")[0], s.split(" ")[1]);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        List<ImageView> listImage = new ArrayList<ImageView>(picCount);

        for (int i = 0; i < picCount; i++) {
            ImageView iv = new ImageView(new Image(Main.class.getResource(path + i + ".png").toString()));
            listImage.add(iv);
        }

        //Create a group node
        container.getChildren().add(listImage.get(0));

        //Animate for Base Action
        Timeline result = new Timeline();
        if (properties.get("cycle_count").equals("Timeline.INDEFINITE")) {

            result.setCycleCount(Timeline.INDEFINITE);

        } else {

            result.setCycleCount(Integer.valueOf(properties.get("cycle_count")));
        }


        //Add images into the timeline
        //Пауза для моргания.
        int pauseBlink = Integer.valueOf(properties.get("pause"));
        result.getKeyFrames().add(createKeyFrame(pauseBlink, container, listImage.get(1)));

        int currentDuration = pauseBlink + Integer.valueOf(properties.get("action_duration"));
        for (int i = 2; i < picCount; i++) {

            result.getKeyFrames().add(createKeyFrame(currentDuration, container, listImage.get(i)));

            currentDuration += Integer.valueOf(properties.get("action_duration"));

        }

        return result;
    }


    public static TranslateTransition createTransition(String path) {

        /*
        Читать в директории "/petName/move" файл значений параметров:
        -cycle_count : int
        -duration : int
         */
        Hashtable<String, String> properties = new Hashtable<String, String>();

        try {
            String s = "";

            Scanner scanner = new Scanner(Main.class.getResourceAsStream(path + "/move.txt"));

            while (scanner.hasNextLine()) {

                s = scanner.nextLine();

                properties.put(s.split(" ")[0], s.split(" ")[1]);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        TranslateTransition moveAnimate = new TranslateTransition(Duration.millis(Integer.valueOf(properties.get("duration"))));

        moveAnimate.setCycleCount(Integer.valueOf(properties.get("cycle_count")));

        return moveAnimate;

    }


    private static KeyFrame createKeyFrame(int actionDuration, Group picContainer, ImageView iv) {

        return new KeyFrame(Duration.millis(actionDuration), (ActionEvent event) -> {
            picContainer.getChildren().setAll(iv);
        });
    }
}
