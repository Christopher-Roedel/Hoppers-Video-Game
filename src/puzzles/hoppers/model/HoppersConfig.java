package puzzles.hoppers.model;

import puzzles.Configuration;
import puzzles.clock.ClockConfig;
import puzzles.hoppers.solver.Hoppers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HoppersConfig implements Configuration{
    private static int rowDIM;
    private static int colDIM;
    private char[][] pond;
    private char[][] hashing;

    public HoppersConfig(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String[] temp = in.readLine().split(" ");
        rowDIM = Integer.parseInt(temp[0]);
        colDIM = Integer.parseInt(temp[1]);
        this.pond = new char[rowDIM][colDIM];
        hashing = new char[rowDIM][colDIM];
        String[] fields;
        for (int row = 0; row < rowDIM; ++row) {
            fields = in.readLine().split(" ");
            for (int col = 0; col < colDIM; ++col) {
                this.pond[row][col] = fields[col].charAt(0);
                this.hashing[row][col] = fields[col].charAt(0);;
            }
        }
        in.close();
    }

    private HoppersConfig(char[][] copy){
        this.pond = copy;
        this.hashing = copy;
    }

    @Override
    public boolean isSolution(){
        for(int i = 0; i < rowDIM; i++){
            for(int x = 0; x < colDIM; x++){
                if(pond[i][x] == 'G'){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Collection<Configuration> getNeighbors(){
        List<Configuration> neighbors = new LinkedList<>();
        char[][] copy = copyAr(pond);
        for(int i = 0; i < copy.length; i++){
            for(int x = 0; x < copy[i].length; x++){
                if(copy[i][x] == 'G' || copy[i][x] == 'R'){
                    if(i % 2 == 0 && x % 2 == 0){
                        even(neighbors, i, x, copy[i][x]);
                    }
                    else{
                        odd(neighbors, i, x, copy[i][x]);
                    }
                }
            }
        }
        return neighbors;
    }

    private void odd(List<Configuration> neighbors, int rowNum, int colNum, char frog){
        char[][] copy = copyAr(pond);
        if(colDIM >= 5 || rowDIM >= 5){
            if(rowNum + 2 <= rowDIM - 1 && colNum + 2 <= colDIM - 1){
                if(copy[rowNum + 1][colNum + 1] == 'G' && copy[rowNum + 2][colNum + 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum + 1][colNum + 1] = '.';
                    copy[rowNum + 2][colNum + 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum - 2 >= 0 && colNum + 2 <= colDIM - 1){
                if(copy[rowNum - 1][colNum + 1] == 'G' && copy[rowNum - 2][colNum + 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 1][colNum + 1] = '.';
                    copy[rowNum - 2][colNum + 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum + 2 <= rowDIM - 1 && colNum - 2 >= 0){
                if(copy[rowNum + 1][colNum - 1] == 'G' && copy[rowNum + 2][colNum - 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum + 1][colNum - 1] = '.';
                    copy[rowNum + 2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum - 2 >= 0 && colNum - 2 >= 0){
                if(copy[rowNum - 1][colNum - 1] == 'G' && copy[rowNum - 2][colNum - 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 1][colNum - 1] = '.';
                    copy[rowNum - 2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
    }

    private void even(List<Configuration> neighbors, int rowNum, int colNum, char frog){
        char[][] copy = copyAr(pond);
        if(rowNum == 0 && colNum == 0){
            if(rowDIM > 3){
                if(copy[2][0] == 'G' && copy[4][0] == '.'){
                    copy[0][0] = '.';
                    copy[2][0] = '.';
                    copy[4][0] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM > 3){
                if(copy[0][2] == 'G' && copy[0][4] == '.'){
                    copy[0][0] = '.';
                    copy[0][2] = '.';
                    copy[0][4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM >= 3 && rowDIM >= 3){
                if(copy[1][1] == 'G' && copy[2][2] == '.'){
                    copy[0][0] = '.';
                    copy[1][1] = '.';
                    copy[2][2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
        if(rowNum == 0 && colNum == colDIM - 1){
            if(rowDIM > 3){
                if(copy[2][colNum] == 'G' && copy[4][colNum] == '.'){
                    copy[0][colNum] = '.';
                    copy[2][colNum] = '.';
                    copy[4][colNum] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM > 3){
                if(copy[0][colNum - 2] == 'G' && copy[0][colNum - 4] == '.'){
                    copy[0][colNum] = '.';
                    copy[0][colNum - 2] = '.';
                    copy[0][colNum - 4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM >= 3 && rowDIM >= 3){
                if(copy[1][colNum - 1] == 'G' && copy[2][colNum - 2] == '.'){
                    copy[0][colNum] = '.';
                    copy[1][colNum - 1] = '.';
                    copy[2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
        if(rowNum == rowDIM - 1 && colNum == 0){
            if(rowDIM > 3){
                if(copy[rowNum - 2][0] == 'G' && copy[rowNum - 4][0] == '.'){
                    copy[0][0] = '.';
                    copy[rowNum - 2][0] = '.';
                    copy[rowNum - 4][0] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM > 3){
                if(copy[rowNum][2] == 'G' && copy[rowNum][4] == '.'){
                    copy[rowNum][0] = '.';
                    copy[rowNum][2] = '.';
                    copy[rowNum][4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM >= 3 && rowDIM >= 3){
                if(copy[rowNum - 1][1] == 'G' && copy[rowNum - 2][2] == '.'){
                    copy[rowNum][0] = '.';
                    copy[rowNum - 1][1] = '.';
                    copy[rowNum - 2][2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
        if(rowNum == rowDIM - 1 && colNum == colDIM - 1){
            if(rowDIM > 3){
                if(copy[rowNum - 2][colNum] == 'G' && copy[rowNum - 4][colNum] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 2][colNum] = '.';
                    copy[rowNum - 4][colNum] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM > 3){
                if(copy[rowNum][colNum - 2] == 'G' && copy[rowNum][colNum - 4] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum][colNum - 2] = '.';
                    copy[rowNum][colNum - 4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colDIM >= 3 && rowDIM >= 3){
                if(copy[rowNum - 1][colNum - 1] == 'G' && copy[rowNum - 2][colNum - 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 1][colNum - 1] = '.';
                    copy[rowNum - 2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
        if(colDIM >= 5 || rowDIM >= 5){
            if(rowNum + 4 <= rowDIM - 1){
                if(copy[rowNum + 2][colNum] == 'G' && copy[rowNum + 4][colNum] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum + 2][colNum] = '.';
                    copy[rowNum + 4][colNum] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum - 4 >= 0){
                if(copy[rowNum - 2][colNum] == 'G' && copy[rowNum - 4][colNum] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 2][colNum] = '.';
                    copy[rowNum - 4][colNum] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colNum - 4 >= 0){
                if(copy[rowNum][colNum - 2] == 'G' && copy[rowNum][colNum - 4] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum][colNum - 2] = '.';
                    copy[rowNum][colNum - 4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(colNum + 4 <= colDIM - 1){
                if(copy[rowNum][colNum + 2] == 'G' && copy[rowNum][colNum + 4] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum][colNum + 2] = '.';
                    copy[rowNum][colNum + 4] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum + 2 <= rowDIM - 1 && colNum + 2 <= colDIM - 1){
                if(copy[rowNum + 1][colNum + 1] == 'G' && copy[rowNum + 2][colNum + 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum + 1][colNum + 1] = '.';
                    copy[rowNum + 2][colNum + 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum - 2 >= 0 && colNum + 2 <= colDIM - 1){
                if(copy[rowNum - 1][colNum + 1] == 'G' && copy[rowNum - 2][colNum + 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 1][colNum + 1] = '.';
                    copy[rowNum - 2][colNum + 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum + 2 <= rowDIM - 1 && colNum - 2 >= 0){
                if(copy[rowNum + 1][colNum - 1] == 'G' && copy[rowNum + 2][colNum - 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum + 1][colNum - 1] = '.';
                    copy[rowNum + 2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
            if(rowNum - 2 >= 0 && colNum - 2 >= 0){
                if(copy[rowNum - 1][colNum - 1] == 'G' && copy[rowNum - 2][colNum - 2] == '.'){
                    copy[rowNum][colNum] = '.';
                    copy[rowNum - 1][colNum - 1] = '.';
                    copy[rowNum - 2][colNum - 2] = frog;
                    neighbors.add(new HoppersConfig(copy));
                    copy = copyAr(pond);
                }
            }
        }
    }

    private char[][] copyAr(char[][] pond){
        char[][] copy = new char[rowDIM][colDIM];
        for(int i = 0; i < pond.length; i++){
            for(int x = 0; x < pond[i].length; x++){
                copy[i][x] = pond[i][x];
            }
        }
        return copy;
    }

    public int[] getSize(){
        int rows = rowDIM;
        int cols = colDIM;
        int[] size = {rows, cols};
        return size;
    }

    public boolean setConfig(int row, int col, int row2, int col2){
        char[][] copy = copyAr(pond);
        char frog = copy[row][col];
        List<Configuration> neighbors = new LinkedList<>();
        if((frog == 'G' || frog == 'R') && (Math.abs(row - row2) == 2 || Math.abs(row - row2) == 4 || row - row2 == 0) &&
                (Math.abs(col - col2) == 2 || Math.abs(col - col2) == 4 || col - col2 == 0)){
            copy[row][col] = '.';
            copy[row - (row - row2)/2][col - (col - col2)/2] = '.';
            copy[row2][col2] = frog;
            if(row % 2 == 0){
                even(neighbors, row, col, frog);
            }
            else{
                odd(neighbors, row, col, frog);
            }
            Configuration c = new HoppersConfig(copy);
            if(neighbors.contains(c)){
                return true;
            }
        }
        return false;
    }

    public Configuration setConfig2(int row, int col, int row2, int col2){
        char[][] copy = copyAr(pond);
        char frog = copy[row][col];
        List<Configuration> neighbors = new LinkedList<>();
        copy[row][col] = '.';
        copy[row - (row - row2)/2][col - (col - col2)/2] = '.';
        copy[row2][col2] = frog;
        if(row % 2 == 0){
            even(neighbors, row, col, frog);
        }
        else{
            odd(neighbors, row, col, frog);
        }
        return new HoppersConfig(copy);
    }

    public char getChar(int row, int col){
        return hashing[row][col];
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(hashing);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof HoppersConfig)){
            return false;
        }
        HoppersConfig o = (HoppersConfig) obj;
        return Arrays.deepEquals(hashing, o.hashing);
    }

    @Override
    public String toString(){
        String string = "";
        for(char[] a : hashing){
            for(char c : a){
                string += c + " ";
            }
            string += "\n";
        }
        return string;
    }

}
