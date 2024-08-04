package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public interface AcceptString {
    default boolean check(String arg) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arg));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
