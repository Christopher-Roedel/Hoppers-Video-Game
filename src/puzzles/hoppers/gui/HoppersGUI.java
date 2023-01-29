package puzzles.hoppers.gui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HoppersGUI extends Application implements Observer<HoppersModel, HoppersClientData> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    private HoppersModel model;

    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    private Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    private Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));
    private ArrayList<Button> buttons;
    private ArrayList<Label> labels;
    private String filename;
    private GridPane gp;
    private Stage stage;

    public void init() {
        filename = getParameters().getRaw().get(0);
        HoppersConfig hop;
        try{
            hop = new HoppersConfig(filename);
            this.model = new HoppersModel(hop);
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        int[] size = model.getSize();
        buttons = new ArrayList<>();
        labels = new ArrayList<>();
        BorderPane bp = new BorderPane();
        gp = new GridPane();
        int z = 0;
        for(int i = 0; i < size[0]; i++){
            for(int x = 0; x < size[1]; x++){
                Button button = new Button();
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                int num = z;
                button.setOnAction((actionEvent -> model.selectButton(num)));
                if(model.getChar(i, x) == 'G') {
                    button.setGraphic(new ImageView(greenFrog));
                }
                else if(model.getChar(i, x) == 'R') {
                    button.setGraphic(new ImageView(redFrog));
                }
                else if(model.getChar(i, x) == '.') {
                    button.setGraphic(new ImageView(lilyPad));
                }
                else if(model.getChar(i, x) == '*') {
                    button.setGraphic(new ImageView(water));
                }
                z++;
                buttons.add(button);
                gp.add(button, x, i);
            }
        }
        bp.setCenter(gp);
        FlowPane fp = new FlowPane();
        Button hint = new Button("Hint");
        hint.setOnAction((actionEvent -> model.hint()));
        Button reset = new Button("Reset");
        reset.setOnAction((actionEvent -> this.model.reset()));
        Button load = new Button("Load");
        load.setOnAction((actionEvent -> model.loadGui()));
        fp.getChildren().add(load);
        fp.getChildren().add(reset);
        fp.getChildren().add(hint);
        bp.setBottom(fp);
        Label top = new Label("Loaded: " + filename);
        labels.add(top);
        bp.setTop(top);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.setTitle("Hoppers GUI");
        stage.show();
        this.stage = stage;
        model.addObserver(this);
    }

    @Override
    public void update(HoppersModel hoppersModel, HoppersClientData hoppersClientData) {
        labels.get(0).setText(hoppersClientData.message);
        if(hoppersModel.isSolution()){
            labels.get(0).setText("Victory!");
            hoppersModel.setVictory();
        }
        int[] size = hoppersModel.getSize();
        int count = 0;
        gp.getChildren().clear();
        buttons.clear();
        int z = 0;
        for(int i = 0; i < size[0]; i++){
            for(int x = 0; x < size[1]; x++){
                Button button = new Button();
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                int num = z;
                button.setOnAction((actionEvent -> hoppersModel.selectButton(num)));
                if(hoppersModel.getChar(i, x) == 'G') {
                    button.setGraphic(new ImageView(greenFrog));
                }
                else if(hoppersModel.getChar(i, x) == 'R') {
                    button.setGraphic(new ImageView(redFrog));
                }
                else if(hoppersModel.getChar(i, x) == '.') {
                    button.setGraphic(new ImageView(lilyPad));
                }
                else if(hoppersModel.getChar(i, x) == '*') {
                    button.setGraphic(new ImageView(water));
                }
                z++;
                buttons.add(button);
                gp.add(button, x, i);
            }
        }
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
