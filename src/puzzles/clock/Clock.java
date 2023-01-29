package puzzles.clock;

import puzzles.common.solver.Solver;

public class Clock {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock hours start stop");
        }
        int hours = Integer.parseInt(args[0]);
        int start = Integer.parseInt(args[1]);
        int end = Integer.parseInt(args[2]);
        System.out.println("Hours: " + hours + ", Start: " + start + ", End: "+ end);
        ClockConfig clock = new ClockConfig(hours, start, end);
        Solver solve = new Solver(clock);
        solve.solve(true);
    }
}