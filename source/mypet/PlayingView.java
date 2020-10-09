package mypet;

import controller.playing.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import model.playing.*;
import utilites.*;

import javafx.event.EventHandler;
import utilites.petproperties.MoodEnum;
import utilites.petproperties.NameEnum;
import utilites.petproperties.PropertyNameEnum;

import java.util.Date;
import java.util.Random;

public class PlayingView {

    class GrowthListener implements IObserver {
        @Override
        public void update(IObservable observable, Object o) {
            myPet.growthUp();
        }
    }

    class MoodListener implements IObserver {
        @Override
        public void update(IObservable observable, Object o) {
            Mood mood = (Mood) o;
            if (mood.getMood().equals(MoodEnum.BAD)){
                myPet.setBadMood();
            } else {
                myPet.setGoodMood();
            }
        }
    }

    class FruitListener implements ChangeListener {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            if (parentContainer.getBoundsInParent().intersects(berry.getBoundsInParent())) {

                playingPane.getChildren().remove(berry);

                controller.pressButton();

                bSelect.setDisable(false);

                parentContainer.layoutXProperty().removeListener(this);
                parentContainer.layoutXProperty().removeListener(this);

            };
        }
    }

    Main view;

    Mediator model;
    Controller controller;

    Scene playingScene;

    PetAnimation myPet;
    Group parentContainer = new Group();

    int basicStep = 80;
    Pane playingPane = new Pane();

    CreatorFood creatorFood;
    ImageView berry;

    Button bLeft;
    Button bRight;
    Button bUpwards;
    Button bDownwards;
    Button bSelect;


    PlayingView(Main view) {

        this.view = view;

        model = new Mediator(view.petData);

        controller = new Controller(this, model);

        myPet = new PetAnimation(view.petData, parentContainer);

        creatorFood = new CreatorFood(((NameEnum)view.petData.getProperty(PropertyNameEnum.NAME)).name().toLowerCase());

        //Добавить наблюдателей
        model.addObserver(Interests.MOOD, new MoodListener());
        model.addObserver(Interests.GROWTH, new GrowthListener());

        //Для сообхения о гибели питомца
        model.addObserver(controller);

        bLeft = new Button("Влево");
        bRight = new Button("Вправо");
        bUpwards = new Button("Вверх");
        bDownwards = new Button("Вниз");
        bSelect = new Button("Покормить");


        bLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPet.left((int)Math.max(0, parentContainer.getTranslateX() - basicStep));
            }
        });

        bRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPet.right((int)Math.min(parentContainer.getTranslateX() + basicStep, playingPane.getWidth() - parentContainer.getBoundsInParent().getWidth()));
            }
        });

        bUpwards.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPet.upwards((int)Math.max(0, parentContainer.getTranslateY() - basicStep));
            }
        });

        bDownwards.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               myPet.downwards((int)Math.min(parentContainer.getTranslateY() + basicStep, playingPane.getHeight() - parentContainer.getBoundsInParent().getHeight()));
            }
        });

        bSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                bSelect.setDisable(true);

                Random random = new Random((new Date()).getTime());

                berry = creatorFood.getFood();
                do {
                    berry.setTranslateX(random.nextInt((int)(playingPane.getWidth() - 50)));
                    berry.setTranslateY(random.nextInt((int)(playingPane.getHeight()- 50)));

                } while (berry.getBoundsInParent().intersects(parentContainer.getBoundsInParent()));

                FruitListener listener = new FruitListener();

                parentContainer.translateXProperty().addListener(listener);
                parentContainer.translateYProperty().addListener(listener);

                playingPane.getChildren().add(berry);

            }
        });

        initializeView();

    }


    void show() {

        view.primaryStage.setScene(playingScene);

        view.primaryStage.show();
    }

    public void disable() {

        //Сохранить данные о гибели питомца
        view.petData.setProperty(PropertyNameEnum.ISALIVE, false);
        view.petData.setProperty(PropertyNameEnum.SAVEDATE, (new Date()).getTime());

        try {

            view.petData.savePetData();

        } catch (Exception e) {

            //ошибка при сохранении
            e.printStackTrace();
        }

        view.createDisabledView();

        //изменить представление.
        view.disabledView.show();
    }


    private void initializeView() {
        //Создание
        //Настройка
        //Размещение

        VBox root = new VBox();
        root.setAlignment(Pos.BOTTOM_CENTER);
        playingScene = new Scene(root, 800,600);

        VBox.setVgrow(playingPane, Priority.ALWAYS);
        playingPane.getChildren().add(parentContainer);
        root.getChildren().add(playingPane);

        HBox buttonPanel = new HBox();
        buttonPanel.setMinHeight(50);
        buttonPanel.setFillHeight(true);
        HBox.setHgrow(bLeft, Priority.ALWAYS);
        bLeft.setMaxWidth(Double.MAX_VALUE);
        bLeft.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(bRight, Priority.ALWAYS);
        bRight.setMaxWidth(Double.MAX_VALUE);
        bRight.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(bUpwards, Priority.ALWAYS);
        bUpwards.setMaxWidth(Double.MAX_VALUE);
        bUpwards.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(bDownwards, Priority.ALWAYS);
        bDownwards.setMaxWidth(Double.MAX_VALUE);
        bDownwards.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(bSelect, Priority.ALWAYS);
        bSelect.setMaxWidth(Double.MAX_VALUE);
        bSelect.setMaxHeight(Double.MAX_VALUE);

        Image image = new Image(getClass().getResource("/pets/choosingview/playground.png").toExternalForm());

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

        playingPane.setBackground(background);

        buttonPanel.getChildren().addAll(bLeft, bRight, bUpwards, bDownwards, bSelect);
        root.getChildren().add(buttonPanel);

        parentContainer.setTranslateX(playingScene.getWidth()/2 - parentContainer.getBoundsInParent().getWidth()/2);
        parentContainer.setTranslateY(playingScene.getHeight() - buttonPanel.getMinHeight() - parentContainer.getBoundsInParent().getHeight());

    }


}
