package utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static void simulateUserInput(String message) {
        byte[] m = message.getBytes();
        try (ByteArrayInputStream mockInput = new ByteArrayInputStream(m)){
            System.setIn(mockInput);
        } catch (Exception e) {
        }
    }

    public static String getRegexMatch(String regex, String text) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(text);
        return m.group();
    }
}