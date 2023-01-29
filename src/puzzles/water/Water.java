package puzzles.water;

import puzzles.common.solver.Solver;

import java.util.ArrayList;

public class Water {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));
        }
        else{
            int amount = Integer.parseInt(args[0]);
            ArrayList<Integer> buckets = new ArrayList<>();
            for(String s : args){
                buckets.add(Integer.parseInt(s));
            }
            int[] array = new int[buckets.size() - 1];
            for(int i = 0; i < array.length; i++){
                array[i] = buckets.get(i + 1);
            }
            WaterConfig wc = new WaterConfig(amount, array);
            Solver solve = new Solver(wc);
            solve.solve(true);
        }
    }
}
