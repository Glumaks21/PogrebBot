package ua.glumaks.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class HashUtil {

    public String encode(String msg) {
        return new String(Base64.getEncoder().encode(msg.getBytes(StandardCharsets.UTF_8)));
    }

    public String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8)));
    }

}
