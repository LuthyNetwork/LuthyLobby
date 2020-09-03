package com.luthynetwork.lobby.utils.scoreboard;

public class TitleScroller {

    private static int i = 0;
    private static String current = "§e§lLUTHY";

    private final static String[] scroller = new String[]{"§e§lLUTHY", "§6§lL§e§lUTHY", "§f§lL§e§lUTHY", "§f§lL§6§lU§e§lTHY", "§f§lLU§e§lTHY", "§f§lLU§6§lT§e§lHY",
            "§f§lLUT§e§lHY", "§f§lLUT§6§lH§e§lY", "§f§lLUTH§e§lY", "§f§lLUTH§6§lY", "§f§lLUTHY", "§e§lLUTHY", "§f§lLUTHY"};

    public static void pass() {
        if (i == 50) {
            i = 0;
            return;
        }
        i++;
    }

    public static String get() {
        switch (i) {
            case 0:
            case 26:
                current = scroller[0];
                break;
            case 5:
                current = scroller[1];
                break;
            case 6:
                current = scroller[2];
                break;
            case 7:
                current = scroller[3];
                break;
            case 8:
                current = scroller[4];
                break;
            case 9:
                current = scroller[5];
                break;
            case 10:
                current = scroller[6];
                break;
            case 11:
                current = scroller[7];
                break;
            case 12:
                current = scroller[8];
                break;
            case 13:
                current = scroller[9];
                break;
            case 14:
                current = scroller[10];
                break;
            case 18:
                current = scroller[11];
                break;
            case 22:
                current = scroller[12];
                break;
        }

        return current;
    }

}
