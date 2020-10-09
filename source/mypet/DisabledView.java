 package mypet;

 import controller.disabling.Countdown;
 import javafx.scene.Scene;
 import javafx.scene.image.Image;
 import javafx.scene.layout.*;
 import utilites.petproperties.PropertyNameEnum;
 import java.util.Date;

 public class DisabledView {

     Main view;

     Scene disabledScene;

    public DisabledView(Main view) {

        //Расчитать дату, до которой игра недоступна.
        utilites.petproperties.RecomputedPet.recompute(view.petData);

        Date newGameDate = new Date((long)view.petData.getProperty(PropertyNameEnum.CONTINUEDATE));

        this.view = view;

        initializeView();

        //Запустить таймер
        Countdown countdown = new Countdown(this, newGameDate);

    }

    public void choosingView(){

        //Иициализировать следующее представление
        view.createChoosingView();

        //Перейти к выбору питомца
        view.choosingView.show();
    }

     void show(){

        view.primaryStage.setScene(disabledScene);

        view.primaryStage.show();
     }

     private void initializeView(){

         VBox field = new VBox();
         field.setMaxWidth(Double.MAX_VALUE);
         VBox.setVgrow(field, Priority.ALWAYS);
         Image image = new Image(getClass().getResource("/pets/choosingview/heaven.png").toExternalForm());

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

         //Scene
         disabledScene = new Scene(field, 900, 400);

     }
}