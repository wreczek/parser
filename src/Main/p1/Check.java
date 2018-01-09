package p1;

public class Check {
    private static boolean writed = false;

    public static void set(){
        writed = true;
    }

    public static boolean isWrited(){
        return writed;
    }
}
