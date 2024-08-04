package data;

import java.util.Comparator;

public class ComparatorLabwork implements Comparator<LabWork> {
    @Override
    public int compare(LabWork o1, LabWork o2){
        if (o1.getAveragePoint() > o2.getAveragePoint()){
            return 1;
        } else if (o1.getAveragePoint() < o2.getAveragePoint()) {
            return -1;
        } else {
            return 0;
        }
    }
}
