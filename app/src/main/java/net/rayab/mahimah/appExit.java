package net.rayab.mahimah;

import android.app.Activity;
import android.content.Context;

/**
 * Created by MrSafavie on 4/18/2018.
 */

public class appExit {

    public static Context act1 = null;
    public static Context act2 = null;
    public static Context act3 = null;
    public static Context act4 = null;
    public static Context act5 = null;
    public static Context act6 = null;
    public static Context act7 = null;
    public static Context act8 = null;
    public static Context act9 = null;

    public static void ExitApp() {
        try {
            if (act1 != null) {
                ((Activity) act1).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act2 != null) {
                ((Activity) act2).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act3 != null) {
                ((Activity) act3).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act4 != null) {
                ((Activity) act4).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act5 != null) {
                ((Activity) act5).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act6 != null) {
                ((Activity) act6).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act7 != null) {
                ((Activity) act7).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act8 != null) {
                ((Activity) act8).finish();
            }
        } catch (Exception ignored) {
        }
        try {
            if (act9 != null) {
                ((Activity) act9).finish();
            }
        } catch (Exception ignored) {
        }
    }

}
