package puzzles.common.solver;

import puzzles.Configuration;

import java.util.*;

public class Solver {
    private HashMap<Configuration, Configuration> map;
    private LinkedList<Configuration> queue;
    private final Configuration config;

    public Solver(Configuration config){
        this.config = config;
        map = new HashMap<>();
        queue = new LinkedList<>();
    }

    public ArrayList<Configuration> solve(boolean print) {
        queue.add(config);
        map.put(config, null);
        int generations = 1;
        int unique = 1;
        boolean solution = true;
        Configuration dequeue = queue.remove(0);
        while (!dequeue.isSolution()) {
            Collection<Configuration> neighbors = dequeue.getNeighbors();
            generations += neighbors.size();
            solution = false;
            for (Configuration c : neighbors) {
                if (!map.containsKey(c)) {
                    queue.add(c);
                    map.put(c, dequeue);
                    unique += 1;
                    solution = true;
                }
            }
            if (!solution && queue.size() == 0) {
                break;
            }
            dequeue = queue.remove(0);
        }
        ArrayList<Configuration> steps = steps(dequeue, solution, generations, unique, print);
        if(print){
            printSteps(steps);
        }
        return steps;
    }

    public ArrayList<Configuration> steps(Configuration dequeue, boolean solution, int generations, int unique, boolean print){
        ArrayList<Configuration> steps = new ArrayList<>();
        if(dequeue.isSolution()){
            solution = true;
        }
        if(print) {
            System.out.println("Total generations: " + generations);
            System.out.println("Unique generations: " + unique);
        }
        if (!solution) {
            if(print) {
                System.out.println("No solution");
            }
        } else {
            ArrayList<Configuration> order = new ArrayList<>();
            Configuration next = dequeue;
            order.add(0, dequeue);
            while (order.size() > 0) {
                if (map.get(next) != null) {
                    order.add(0, map.get(next));
                    next = map.get(next);
                }
                else{
                    steps.add(order.remove(0));
                }
            }
        }
        return steps;
    }

    public void printSteps(ArrayList<Configuration> steps){
        for(int i = 0; i < steps.size(); i++) {
            System.out.println("Step " + i + ":\n" + steps.get(i));
        }
    }
}
