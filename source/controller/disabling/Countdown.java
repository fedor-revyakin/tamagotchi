package controller.disabling;

import javafx.application.Platform;
import mypet.DisabledView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown extends TimerTask {

    /*
    blastOff - время, оставшееся до старта новой игры.
     */

    public Countdown(DisabledView disabledView, Date blastOff) {

        this.disabledView = disabledView;

        countdown.schedule(this, blastOff);
    }

    DisabledView disabledView;

    Timer countdown = new Timer(true);

    @Override
    public void run() {

        Platform.runLater(() -> {

            countdown.cancel();

            disabledView.choosingView();
        });

    }
}
