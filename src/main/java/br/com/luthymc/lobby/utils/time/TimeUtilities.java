package br.com.luthymc.lobby.utils.time;

import lombok.val;

import java.util.Calendar;

public class TimeUtilities {

    public static String getDayPart() {
        val calendar = Calendar.getInstance();
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String part;

        if (timeOfDay < 12) {
            part = "um bom dia";
        }else if (timeOfDay < 21) {
            part = "uma boa tarde";
        } else {
            part = "uma boa noite";
        }

        return part;
    }

    public static String timeLeft(int time) {
        final StringBuilder sb = new StringBuilder();

        int hours;
        int minutes;
        int seconds;

        int aux = time;

        if (time >= 3600) {
            hours = time / 3600;
            aux -= hours * 3600;
            sb.append((hours == 1) ? (hours + " hora ")
                    : (hours + " horas "));
        }
        if (aux >= 60) {
            minutes = aux / 60;
            aux -= minutes * 60;
            sb.append((minutes == 1) ? (minutes + " minuto ")
                    : (minutes + " minutos "));
        }
        if (aux > 0) {
            seconds = aux;
            sb.append((seconds == 1) ? (seconds + " segundo")
                    : (seconds + " segundos"));
        }
        return sb.substring(0, sb.toString().length());
    }

}
