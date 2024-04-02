package org.bolyuk.bktgbotlib;

public class Debug {
    static boolean onAir=false;
    public static void setDebug(boolean value){
        onAir=value;
    }

    public static void d(Object o){
        System.out.println(o);
    }

}
