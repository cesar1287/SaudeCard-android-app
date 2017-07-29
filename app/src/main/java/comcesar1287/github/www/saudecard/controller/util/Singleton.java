package comcesar1287.github.www.saudecard.controller.util;

/**
 * Created by César on 29/07/2017.
 */

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
