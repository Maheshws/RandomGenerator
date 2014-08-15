package ws.mahesh.apps.randomgenerator.utils;

import java.util.Random;

/**
 * Created by Mahesh on 8/10/2014.
 */
public class PassGenerator {

    static Random rnd = new Random();

    public String randomString(int len, String AB) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public int randomLen() {
        int x;
        do {
            x = rnd.nextInt(20);
        } while (x < 3);
        return x;
    }
}
