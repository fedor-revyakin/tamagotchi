package utilites;

import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import utilites.petproperties.GrowthEnum;
import utilites.petproperties.NameEnum;
import utilites.petproperties.PetData;
import utilites.petproperties.PropertyNameEnum;


public class PetAnimation {

        PetData petData;

        Group parentContainer;
        TranslateTransition moveAction;

        Group goodMoodLeft = new Group();
        Group badMoodLeft = new Group();
        Timeline goodMoodAnimationLeft = new Timeline();
        Timeline badMoodAnimationLeft = new Timeline();

        Group goodMoodRight = new Group();
        Group badMoodRight = new Group();
        Timeline goodMoodAnimationRight = new Timeline();
        Timeline badMoodAnimationRight = new Timeline();


        Group leftRun = new Group();
        Group leftWalk = new Group();
        Timeline leftRunAnimation = new Timeline();
        Timeline leftWalkAnimation = new Timeline();
        ParallelTransition leftRunParallel = new ParallelTransition();
        ParallelTransition leftWalkParallel = new ParallelTransition();//moveAction, leftWalkAnimation


        Group rightRun = new Group();
        Group rightWalk = new Group();
        Timeline rightRunAnimation = new Timeline();
        Timeline rightWalkAnimation = new Timeline();
        ParallelTransition rightRunParallel = new ParallelTransition();//moveAction, rightRunAnimation
        ParallelTransition rightWalkParallel = new ParallelTransition();//moveAction, rightWalkAnimation


        Timeline currentState;

        Scale currentScale;

        boolean isGoodMood = true;

        class LeftListener implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent event) {
                if (isGoodMood) {

                    parentContainer.getChildren().setAll(goodMoodLeft);
                    currentState = goodMoodAnimationLeft;

                } else {

                    parentContainer.getChildren().setAll(badMoodLeft);
                    currentState = badMoodAnimationLeft;
                }
            }
        }

        class RightListener implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent event) {
                if (isGoodMood) {

                    parentContainer.getChildren().setAll(goodMoodRight);
                    currentState = goodMoodAnimationRight;

                } else {

                    parentContainer.getChildren().setAll(badMoodRight);
                    currentState = badMoodAnimationRight;
                }
            }
        }


        public PetAnimation(PetData petData, Group parentContainer) {

            this.petData = petData;

            //Получение имени питомца из перечисления utilites.petproperties.Name
            String petName = ((NameEnum)petData.getProperty(PropertyNameEnum.NAME)).name().toLowerCase();

            this.parentContainer = parentContainer;

            moveAction = CreatorPetAnimation.createTransition("/pets/playingview/" + petName);
            moveAction.setNode(parentContainer);

            //Инициализация начального состояния
            goodMoodAnimationLeft = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/fun_blink_left/", 8, goodMoodLeft);//+
            parentContainer.getChildren().setAll(goodMoodLeft);

            //
            badMoodAnimationLeft = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/bad_blink_left/", 8, badMoodLeft);//+

            //
            goodMoodAnimationRight = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/fun_blink_right/", 8, goodMoodRight);//+

            //
            badMoodAnimationRight = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/bad_blink_right/", 8, badMoodRight);//+

            //Инициализация перемещения влево
            leftRunAnimation = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/left/", 14, leftRun);//+
            leftRunParallel.getChildren().addAll(moveAction, leftRunAnimation);

            //Инициализация перемещения вправо
            rightRunAnimation = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/right/", 14, rightRun);//+
            rightRunParallel.getChildren().addAll(moveAction, rightRunAnimation);

            //Инициализация перемещения вверх-вниз влево
            leftWalkAnimation = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/updown_left/", 14, leftWalk);
            leftWalkParallel.getChildren().addAll(moveAction, leftWalkAnimation);

            //Инициализация перемещения вверх-вниз вправо
            rightWalkAnimation = CreatorPetAnimation.createTimeLine("/pets/playingview/" + petName + "/updown_right/", 14, rightWalk);
            rightWalkParallel.getChildren().addAll(moveAction, rightWalkAnimation);

            currentState = goodMoodAnimationLeft;


            //Добавление наблюдателей
            LeftListener leftListener = new LeftListener();
            leftRunParallel.setOnFinished(leftListener);
            leftWalkParallel.setOnFinished(leftListener);
            RightListener rightListener = new RightListener();
            rightRunParallel.setOnFinished(rightListener);
            rightWalkParallel.setOnFinished(rightListener);

            currentState.play();

            badMoodAnimationLeft.play();
            goodMoodAnimationRight.play();
            badMoodAnimationRight.play();

            currentScale = Transform.scale(0.11,0.11, parentContainer.getTranslateX(), parentContainer.getTranslateY());

            parentContainer.getTransforms().add(currentScale);

            //Восстановить рост питомца
            int pos = ((GrowthEnum)petData.getProperty(PropertyNameEnum.GROWTH)).ordinal();
            for (int i = 0; i < pos; i++) {
                growthUp();
            }

        }

        public void left(int toX){
            moveAction.setToX(toX);
            parentContainer.getChildren().setAll(leftRun);
            leftRunParallel.play();
        }

        public void right(int toX){
            moveAction.setToX(toX);
            parentContainer.getChildren().setAll(rightRun);
            rightRunParallel.play();
        }

        public void upwards(int toY){

            moveAction.setToY(toY);

            if (currentState == goodMoodAnimationLeft || currentState == badMoodAnimationLeft) {
                parentContainer.getChildren().setAll(leftWalk);
                leftWalkParallel.play();
            } else {
                parentContainer.getChildren().setAll(rightWalk);
                rightWalkParallel.play();
            }

        }

        public void downwards(int toY){

            moveAction.setToY(toY);

            if (currentState == goodMoodAnimationLeft || currentState == badMoodAnimationLeft) {
                parentContainer.getChildren().setAll(leftWalk);
                leftWalkParallel.play();
            } else {
                parentContainer.getChildren().setAll(rightWalk);
                rightWalkParallel.play();
            }
        }

        public void setGoodMood(){

            if (isGoodMood) return;

            isGoodMood = true;

            if (currentState == badMoodAnimationLeft) {

                currentState = goodMoodAnimationLeft;
                parentContainer.getChildren().setAll(goodMoodLeft);

            } else {

                currentState = goodMoodAnimationRight;
                parentContainer.getChildren().setAll(goodMoodRight);

            }
        }

        public void setBadMood(){

            if (!isGoodMood) return;

            isGoodMood = false;

            if (currentState == goodMoodAnimationLeft) {

                currentState = badMoodAnimationLeft;
                parentContainer.getChildren().setAll(badMoodLeft);

            } else {

                currentState = badMoodAnimationRight;
                parentContainer.getChildren().setAll(badMoodRight);

            }

        }

        public void growthUp() {

            currentScale = Transform.scale(1.2,1.2, parentContainer.getTranslateX(), parentContainer.getTranslateY());

            parentContainer.getTransforms().add(currentScale);

        }

}
