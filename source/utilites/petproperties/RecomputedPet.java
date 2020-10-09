package utilites.petproperties;

import java.util.Date;

public class RecomputedPet {

    //Интервал времени, в течение которого игра недоступна после гибели питомца (1 мин)
    private final static long DISABLETIME = 60000;

    public static void recompute(PetData petData){

        if (!(boolean)petData.getProperty(PropertyNameEnum.ISALIVE)) {

            long continueDate = (long)petData.getProperty(PropertyNameEnum.SAVEDATE) + DISABLETIME;

            petData.setProperty(PropertyNameEnum.CONTINUEDATE, continueDate);

            return;
        }

        //Интервал времени ухудщения состояния питомца при выключенной игре (свойство PropertyName.MOOD)
        long timeSpan = 10800000;

        Date date = new Date();
        //Рассчитать в миллисекундах время, прошедшее
        //с момента сохранения игры до настоящего времени.
        long sleepTimeInterval = date.getTime() - ((long)petData.getProperty(PropertyNameEnum.SAVEDATE));

        //Состояние питомца при сохранении
        int oldState = ((MoodEnum)petData.getProperty(PropertyNameEnum.MOOD)).ordinal();

        //Доступные состояния
        MoodEnum[] avaliableState = MoodEnum.values();

        //Рассчитать новое состояние питомца
        long newState = oldState + sleepTimeInterval/timeSpan;

        if (newState < avaliableState.length) {

            int indexNewState = (int)newState;

            petData.setProperty(PropertyNameEnum.MOOD,avaliableState[indexNewState]);

        } else {

            petData.setProperty(PropertyNameEnum.ISALIVE, false);
        }

    }
}