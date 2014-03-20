package server.util;

import java.util.List;

public class Util {

    public static boolean isEmpty(List<?> list) {
        return (list == null || list.size() == 0);
    }

    public static <T> boolean isEmpty(T[] array) {
        return ((array == null) || (array.length) == 0);
    }
    
    public static int RandomUserNum(){
        long time =System.currentTimeMillis()/1000;
        return (int) time;
    }
}