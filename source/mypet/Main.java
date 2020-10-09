package mypet;

import javafx.application.Application;

import javafx.stage.Stage;
import utilites.petproperties.PetData;

import java.util.List;

public class Main extends Application {

    Stage primaryStage;

    PlayingView playingView;
    ChoosingView choosingView;
    DisabledView disabledView;

    PetData petData;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        List<String> args = getParameters().getRaw();

        switch (args.get(0)) {

            case "choosingview" : startChoosingView();
            break;

            case "playingview": startPlayingView();
            break;

            case "disabledview": startDisabledView();
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void startChoosingView() {
        createChoosingView();
        choosingView.show();
    }

    private void startPlayingView() {
        loadPetData();
        createPlayingView();
        playingView.show();
    }

    private void startDisabledView() {
        loadPetData();
        createDisabledView();
        disabledView.show();
    }

    private void loadPetData() {

        try {

            petData = new PetData();

        } catch (Exception e) {

            //Ошибка чтения файла
            e.printStackTrace();
        }
    }

    void createChoosingView() {choosingView = new ChoosingView(this);}
    void createPlayingView() {playingView = new PlayingView(this);}
    void createDisabledView() {disabledView = new DisabledView(this);}

    @Override
    public void stop() throws Exception {
        if (petData != null) {
            petData.savePetData();
        }

    }






}
