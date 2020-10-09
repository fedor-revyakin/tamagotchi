package mypet;

import utilites.petproperties.PetData;
import utilites.petproperties.PropertyNameEnum;
import utilites.petproperties.RecomputedPet;

import java.util.Date;

public class Mypet {

    public static void main(String[] args) {

        App app = new App();

        app.play();
    }

}

class App {

    void play() {

        String[] args = new String[] { launchString() };

        Main.main(args);
    }

    private PetData petData;

    String launchString() {

        try {

            petData = new PetData();

        } catch (Exception e) {

            return "choosingview";

        }

        RecomputedPet.recompute(petData);

        if (!(boolean)petData.getProperty(PropertyNameEnum.ISALIVE)) {

            return delay();

        } else {

            return "playingview";
        }

    }

    private String delay(){

        Date today = new Date();

        Date startGameDate = new Date((long)petData.getProperty(PropertyNameEnum.CONTINUEDATE));

        if (today.after(startGameDate)) {

            return "choosingview";

        } else {

            return "disabledview";
        }
    }


}