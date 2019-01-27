package kasper.android.pulseframework.utils;

import android.content.Context;

public class GraphicsHelper {

    private static Context context;

    public static void setup(Context context) {
        GraphicsHelper.context = context;
    }

    public static int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(float dp) {
        return (int)(dp * context.getResources().getDisplayMetrics().density);
    }
}
