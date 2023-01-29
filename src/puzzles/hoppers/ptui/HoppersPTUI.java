package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import java.io.IOException;
import java.util.Scanner;

public class HoppersPTUI implements Observer<HoppersModel, HoppersClientData> {
    private HoppersModel model;
    private boolean victory = false;

    public HoppersPTUI(String arg){
        HoppersConfig hop;
        try{
            hop = new HoppersConfig(arg);
            this.model = new HoppersModel(hop);
            initializeView();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    private void run(){
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "game command: " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if ( words.length > 0 ) {
                if ( words[ 0 ].startsWith( "q" ) ) {
                    break;
                }
                else if ( words[ 0 ].startsWith( "h" ) && !victory) {
                    this.model.hint();
                }
                else if ( words[ 0 ].startsWith( "l" ) ) {
                    victory = false;
                    String n = words[1];
                    this.model.load(n);
                }
                else if ( words[ 0 ].startsWith( "r" ) ) {
                    victory = false;
                    this.model.reset();
                }
                else if ( words[ 0 ].startsWith( "s" ) && !victory ) {
                    int n = Integer.parseInt( words[ 1 ] );
                    int m = Integer.parseInt( words[ 2] );
                    this.model.select(n, m);
                }
                else if(victory){
                    System.out.println("You can not perform this action because the game is over");
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    public void initializeView(){
        this.model.addObserver(this);
        update(this.model, null);
    }

    private void displayBoard(){
        System.out.println(this.model.getConfig());
    }

    private void displayHelp(){
        int[] size = model.getSize();
        int count = 0;
        for(int i = 0; i < size[0]; i++){
            for(int x = 0; x < size[1]; x++){
                if(count < 10) {
                    System.out.print("00" + count + " ");
                }
                else if(count < 100){
                    System.out.print("0" + count + " ");
                }
                else{
                    System.out.print(count + " ");
                }
                count ++;
            }
            System.out.print("\n");
        }
        System.out.println("s(elect) m n    -- select to move contents of m to n");
        System.out.println("l(oad) filename -- load the game file");
        System.out.println("h(int)          -- hint the next move");
        System.out.println("q(uit)          -- quit the game");
        System.out.println("r(eset)         -- reset the current game");
    }

    @Override
    public void update(HoppersModel model, HoppersClientData data) {
        if(data != null){
            System.out.println(data.message);
            if(model.isSolution()){
                System.out.println("Victory!");
                victory = true;
            }
        }
        displayBoard();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
        HoppersPTUI ptui = new HoppersPTUI(args[0]);
        ptui.run();
    }
}
