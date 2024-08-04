package commands;

public interface AcceptFloat {

    default boolean check(String arg) {
        try {
            Float.parseFloat(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
