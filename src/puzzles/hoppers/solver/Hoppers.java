package puzzles.hoppers.solver;

import puzzles.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;

public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        try {
            Configuration hop = new HoppersConfig(args[0]);
            Solver solve = new Solver(hop);
            solve.solve(true);
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
