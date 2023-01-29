package puzzles.water;

import puzzles.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WaterConfig implements Configuration{
    private final int amount;
    private final int[] buckets;
    private int[] currBucks;

    public WaterConfig(int amount, int[] buckets){
        this.amount = amount;
        this.buckets = buckets;
        this.currBucks = new int[buckets.length];
        for(int i = 0; i < currBucks.length; i++){
            currBucks[i] = 0;
        }
    }

    private WaterConfig(WaterConfig other){
        this.amount = other.amount;
        this.buckets = other.buckets;
        this.currBucks = other.currBucks;
    }

    @Override
    public boolean isSolution(){
        for(int i: currBucks){
            if(i == amount){
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors(){
        List<Configuration> neighbors = new LinkedList<>();
        int[] original = currBucks;
        for(int i = 0; i < currBucks.length; i++){
            if(currBucks[i] != buckets[i]){
                currBucks[i] = buckets[i];
                neighbors.add(new WaterConfig(this));
                currBucks = original;
            }
            if(currBucks[i] != 0){
                currBucks[i] = 0;
                neighbors.add(new WaterConfig(this));
                currBucks = original;
                for (int x = 0; x < currBucks.length; x++) {
                    if (x != i) {
                        currBucks[i] = currBucks[i] - (buckets[x] - currBucks[x]);
                        if (currBucks[i] < 0) {
                            currBucks[i] = 0;
                        }
                        currBucks[x] = currBucks[x] + original[i];
                        if (currBucks[x] > buckets[x]) {
                            currBucks[x] = buckets[x];
                        }
                        neighbors.add(new WaterConfig(this));
                        currBucks = original;
                    }
                    currBucks = original;
                }
            }
        }
        return neighbors;
    }

    @Override
    public int hashCode(){
        int code = Arrays.hashCode(currBucks);
        return code;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof WaterConfig)){
            return false;
        }
        WaterConfig o = (WaterConfig) obj;
        if(Arrays.equals(this.currBucks, o.currBucks)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        String string = "[";
        for(int i = 0; i < buckets.length; i++){
            if(i < buckets.length - 1){
                string = string + currBucks[i] + ", ";
            }
            else{
                string = string + currBucks[i] + "]";
            }
        }
        return string;
    }

}
