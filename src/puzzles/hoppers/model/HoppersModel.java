package puzzles.hoppers.model;

import puzzles.Configuration;
import puzzles.common.Observer;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, HoppersClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    private HoppersConfig original;
    private int num = 0;
    private int num1 = 0;
    private int num2 = 0;
    private boolean victory = false;
    private Scanner scan;

    public HoppersModel(HoppersConfig config){
        currentConfig = config;
        original = config;
        scan = new Scanner(System.in);
    }

    public void load(String filename){
        try{
            HoppersConfig newCon = new HoppersConfig(filename);
            currentConfig = newCon;
            original = newCon;
            alertObservers(new HoppersClientData("Loaded " + filename));
        }
        catch(IOException ioe){
            alertObservers(new HoppersClientData("Could not load " + filename));
            System.out.println(ioe.getMessage());
        }
    }

    public void hint(){
        if(!victory) {
            Solver solve = new Solver(currentConfig);
            ArrayList<Configuration> configs = solve.solve(false);
            if (configs.size() > 0) {
                currentConfig = (HoppersConfig) configs.get(1);
                alertObservers(new HoppersClientData("Made next move"));
            } else {
                alertObservers(new HoppersClientData("No solution"));
                victory = true;
            }
        }
        else{
            alertObservers(new HoppersClientData("This function has been disabled due to game completion."));
        }
    }

    public void reset(){
        currentConfig = original;
        victory = false;
        alertObservers(new HoppersClientData("Reset the game"));
    }

    public void select(int n, int m){
        if(n < getSize()[0] * getSize()[1] && n >= 0 && m < getSize()[0] * getSize()[1] && m >= 0) {
            int row = -1;
            int col = -1;
            int count = 0;
            for (int i = 0; i < getSize()[0]; i++) {
                for (int x = 0; x < getSize()[1]; x++) {
                    if (count == n) {
                        row = i;
                        col = x;
                        break;
                    }
                    count++;
                }
                if (row != -1) {
                    break;
                }
            }
            count = 0;
            int row2 = -1;
            int col2 = -1;
            for (int i = 0; i < getSize()[0]; i++) {
                for (int x = 0; x < getSize()[1]; x++) {
                    if (count == m) {
                        row2 = i;
                        col2 = x;
                        break;
                    }
                    count++;
                }
                if (row2 != -1) {
                    break;
                }
            }
            if (currentConfig.setConfig(row, col, row2, col2)) {
                currentConfig = (HoppersConfig) currentConfig.setConfig2(row, col, row2, col2);
                alertObservers(new HoppersClientData("Moved (" + row + ", " + col+ ") " +
                        " to (" + row2 + ", " + col2 + ")"));
            } else {
                alertObservers(new HoppersClientData("Invalid move (" + row + ", " + col+ ") " +
                        " to (" + row2 + ", " + col2 + ")"));
            }
        }
        else{
            alertObservers(new HoppersClientData("At least one of your selections is not a valid space"));
        }
    }
    public void selectButton(int n){
        if(!victory) {
            if (num == 0) {
                num1 = n;
                num = 1;
                alertObservers(new HoppersClientData("Selected space " + n + ". Please select another space."));
            } else if (num == 1) {
                num2 = n;
                num = 0;
                alertObservers(new HoppersClientData("Second space selected as " + n));
                select(num1, num2);
            }
        }
        else{
            alertObservers(new HoppersClientData("This function has been disabled due to game completion."));
        }
    }

    public void loadGui(){
        System.out.println("Enter a filename");
        String s = scan.nextLine();
        load(s);
    }

    public void setVictory(){
        victory = true;
    }

    public char getChar(int row, int col){
        return currentConfig.getChar(row, col);
    }

    public boolean isSolution(){
        return currentConfig.isSolution();
    }

    public Configuration getConfig(){
        return currentConfig;
    }

    public int[] getSize(){
        return currentConfig.getSize();
    }
    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, HoppersClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(HoppersClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
}
