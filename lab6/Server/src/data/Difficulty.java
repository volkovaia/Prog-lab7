package data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The enum Difficulty.
 */
public enum Difficulty implements Serializable {
    /**
     * Easy difficulty.
     */
    EASY,
    /**
     * Normal difficulty.
     */
    NORMAL,
    /**
     * Insane difficulty.
     */
    INSANE;
    public static HashMap<Integer, String> putByNumber = new HashMap<>() {{
        put(1, "EASY");
        put(2, "NORMAL");
        put(3, "INSANE"); }};


    /**
     * Enter difficulty difficulty.
     *
     * @param scanner the scanner
     * @return the difficulty
     */

    /**
     * Enter difficulty from file difficulty.
     *
     * @param line the line
     * @return the difficulty
     */
    public static Difficulty enterDifficultyFromInt(String line) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (Difficulty difficulty1 : Difficulty.values()) {
                stringBuilder.append(difficulty1).append(" ");
            }
            int in = Integer.parseInt(line);
            return Difficulty.valueOf(Difficulty.putByNumber.get(in));

        } catch (NullPointerException e) {
            System.out.println("Нет такого уровня сложности!");
            return enterDifficultyFromInt(line);
        }
    }

    public static Difficulty enterDifficultyFromCorrectString(String line) {
        return Difficulty.valueOf(line);
    }
}