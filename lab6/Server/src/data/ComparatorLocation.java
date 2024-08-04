package data;

import java.util.Comparator;

public class ComparatorLocation implements Comparator<LabWork> {
    @Override
    public int compare(LabWork o1, LabWork o2){
        if (o1.getCoordinates().getX() > o2.getCoordinates().getX()){
            return 1;
        } else if (o1.getCoordinates().getX() < o2.getCoordinates().getX()) {
            return -1;
        } else {
            return 0;
        }
    }
}
