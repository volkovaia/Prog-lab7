package data;

import lombok.Data;

import java.io.Serializable;
import java.util.Scanner;

/**
 * The type Coordinates.
 */
@Data
public class Coordinates implements Serializable {
    private int x;

    private Float y;

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates(int x, Float y) {


            try {
                if (y == null) {
                    throw new IllegalArgumentException("Строка не может быть пустой");
                }

                this.y = y;
                this.x = x;

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

    /**
     * Enter coordinate x int.
     *
     * @param scanner the scanner
     * @return the int
     */
    public static int enterCoordinateX(Scanner scanner) {
        System.out.println("Введите coordinate X: ");
        try {
            int in = Integer.parseInt(scanner.nextLine());
            return in;
        }catch (NumberFormatException e){
            System.out.println("Координата X должна быть типа int. Попробуйте снова!");
            return enterCoordinateX(scanner);
        }
    }

    /**
     * Enter coordinate x from file int.
     *
     * @param line the line
     * @return the int
     */
    public static int enterCoordinateXFromFile(String line) {
        try {
            int in = Integer.parseInt(line);
            return in;
        }catch (NumberFormatException e){
            System.out.println("Координата X должна быть типа int. Попробуйте снова!");
            return enterCoordinateXFromFile(line);
        }
    }

    /**
     * Enter coordinate y float.
     *
     * @param scanner the scanner
     * @return the float
     */
    public static Float enterCoordinateY(Scanner scanner) {
        System.out.println("Введите coordinate Y: ");
        try {
            Float in = Float.parseFloat(scanner.nextLine());
            if (in == null) {
                System.out.println("Координата Y не может быть пустой");
                return enterCoordinateY(scanner);
            } else {
                return in;
            }
        }catch (NumberFormatException e){
            System.out.println("Координата Y должна быть типа Float. Попробуйте снова!");
            return enterCoordinateY(scanner);
        }
    }

    /**
     * Enter coordinate y from file float.
     *
     * @param line the line
     * @return the float
     */
    public static Float enterCoordinateYFromFile(String line) {
        try {

            //Float in = Float.parseFloat(line);
            Float in = Float.valueOf(line);


            if (in == null) {
                System.out.println("Координата Y не может быть пустой");
                return enterCoordinateYFromFile(line);
            } else {
                return in;
            }
        }catch (NumberFormatException e){
            System.out.println("Координата Y должна быть типа Float. Попробуйте снова!");
            return enterCoordinateYFromFile(line);
        }
    }
    public String toString() {
        return "{ \"x\": " + x + ", \"y\": " + y + " }";
    }
}
