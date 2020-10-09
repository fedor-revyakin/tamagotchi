package mypet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.scene.image.ImageView;
import utilites.TinyTwister;
import utilites.petproperties.NameEnum;
import utilites.petproperties.PetData;
import utilites.petproperties.PropertyNameEnum;

import java.util.Date;


public class ChoosingView {

    Main view;

    TinyTwister tinyTwister;

    Button bNext;
    Button bPress;

    Scene choosingScene;

    NameEnum[] petNameList = NameEnum.values();

    double scale = 0.17;

    Group image_group;

    public ChoosingView(Main view) {

        this.view = view;

        image_group = new Group();

        ImageView visibleImageview = new ImageView(new Image(getClass().getResource("/pets/choosingview/"
                + petNameList[0].name().toLowerCase() + ".png").toExternalForm()));

        image_group.getChildren().add(0, visibleImageview);

        visibleImageview.setScaleX(scale);
        visibleImageview.setScaleY(scale);

        for (int i = 1; i < petNameList.length; i++) {
            ImageView iv = new ImageView(new Image(getClass().getResource("/pets/choosingview/"
                    + petNameList[i].name().toLowerCase() + ".png").toExternalForm()));

            image_group.getChildren().add(i, iv);
            iv.setScaleX(scale);
            iv.setScaleY(scale);
            iv.setVisible(false);
        }

        tinyTwister = new TinyTwister(image_group.getChildren().size());

        bNext = new Button("Нет");
        bPress = new Button("Да ");

        bNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                image_group.getChildren().get(tinyTwister.getCurrentItem()).setVisible(false);

                image_group.getChildren().get(tinyTwister.nextPet()).setVisible(true);
            }
        });


        bPress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int item = tinyTwister.getCurrentItem();

                startPlaying(petNameList[item]);
            }
        });

        initializeView();

    }




    void startPlaying(NameEnum petName) {

        //Создать и сохранить выбранного питомца.
        view.petData = new PetData(petName);
        view.petData.setProperty(PropertyNameEnum.SAVEDATE,new Date().getTime());

        try {

            view.petData.savePetData();

        } catch (Exception e) {
            //Выбранный питомец не сохранен.
            e.printStackTrace();
        }

        //Инициализация представления основной игры.
        view.createPlayingView();

        //начать игру
        view.playingView.show();
    }

    void show(){

        view.primaryStage.setScene(choosingScene);

        view.primaryStage.show();
    }

    private void initializeView(){

        VBox root = new VBox();

        //Нижняя часть экрана.
        HBox footer = new HBox();
        footer.setMinHeight(50);
        footer.setFillHeight(true);
        HBox.setHgrow(bNext, Priority.ALWAYS);
        bNext.setMaxWidth(Double.MAX_VALUE);
        bNext.setMaxHeight(Double.MAX_VALUE);

        HBox.setHgrow(bPress, Priority.ALWAYS);
        bPress.setMaxWidth(Double.MAX_VALUE);
        bPress.setMaxHeight(Double.MAX_VALUE);


        //Основная часть экрана
        VBox field = new VBox();
        field.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(field, Priority.ALWAYS);
        Image image = new Image(getClass().getResource("/pets/choosingview/background1.png").toExternalForm());

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO,
                false,
                false,
                true,true);

        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize));
        field.setBackground(background);

        field.setAlignment(Pos.BOTTOM_CENTER);
        field.getChildren().addAll(image_group);

        footer.getChildren().addAll(bNext, bPress);

        root.setAlignment(Pos.BOTTOM_CENTER);//размещать эл-ты в конт-ре по центру

        root.getChildren().addAll(field, footer); //Добавление элементов в корень

        //Scene
        choosingScene = new Scene(root, 800, 600);
    }

}