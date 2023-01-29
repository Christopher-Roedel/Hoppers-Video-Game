package puzzles.clock;

import puzzles.Configuration;

import java.util.*;

public class ClockConfig implements Configuration{
    private int hours;
    private int start;
    private int end;
    public ClockConfig(int hours, int start, int end){
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    private ClockConfig(ClockConfig other, int change){
        this.hours = other.hours;
        this.start = other.start;
        this.end = other.end;
        this.start += change;
        if(start <= 0){
            start = hours + start;
        }
        if(start > hours){
            start = start - hours;
        }
    }

    @Override
    public boolean isSolution(){
        if(start == end){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Collection<Configuration> getNeighbors(){
        List<Configuration> neighbors = new LinkedList<>();
        neighbors.add(new ClockConfig(this, 1));
        neighbors.add(new ClockConfig(this, -1));
        return neighbors;
    }

    @Override
    public int hashCode(){
        return start;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ClockConfig)){
            return false;
        }
        ClockConfig o = (ClockConfig) obj;
        if(this.start == o.start){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        String string = "" + start;
        return string;
    }

}
