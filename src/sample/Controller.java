package sample;

import com.jfoenix.controls.*;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<Process> processes = new ArrayList<>();
    Block smallest = new Block();
    private Byte[] bytes = new Byte[1000];
    @FXML
    private JFXListView<JFXButton> listView;
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private JFXTextField processName;
    @FXML
    private JFXTextField processSize;
    @FXML
    private JFXTextField processDeallocatedName;
    @FXML
    private JFXComboBox algorthimNeeded;
    private Boolean finished;

    private Process currentProcess;
    private JFXButton currentLabel;
    int j;
    String processDeallocated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorthimNeeded.getItems().add(0, "First fit");
        algorthimNeeded.getItems().add(1, "Best fit");
        for (int i = 0; i < 1000; i++) {
            bytes[i] = new Byte();
        }

    }

    public void allocate() {

        currentProcess = new Process(Integer.parseInt(processSize.getText()), processName.getText());
        processes.add(currentProcess);
        if (algorthimNeeded.getSelectionModel().isSelected(0)) {
            firstFit();
            currentLabel = new JFXButton("   " + currentProcess.getName() + "   FROM   " + currentProcess.entered + "    TO    " + (currentProcess.entered + currentProcess.size - 1));
            currentLabel.setBackground(new Background(new BackgroundFill(colorPicker.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            currentLabel.setPrefWidth(340);
            // currentLabel.setPrefHeight(22);
            currentLabel.setTextFill(Color.WHITE);
            currentLabel.setPrefHeight(currentProcess.size * 40 / 100);
            currentLabel.setId("" + currentProcess.entered);
            listView.getItems().add(currentLabel);
            sortListView();
        } else if (algorthimNeeded.getSelectionModel().isSelected(1)) {
            bestFit();
            currentLabel = new JFXButton("   " + currentProcess.getName() + "   FROM   " + currentProcess.entered + "    TO    " + (currentProcess.entered + currentProcess.size - 1));
            currentLabel.setBackground(new Background(new BackgroundFill(colorPicker.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            currentLabel.setPrefWidth(340);
            // currentLabel.setPrefHeight(22);
            currentLabel.setTextFill(Color.WHITE);
            currentLabel.setPrefHeight(currentProcess.size * 40 / 100);
            currentLabel.setId("" + currentProcess.entered);
            listView.getItems().add(currentLabel);
            sortListView();

        }
    }

    public void firstFit() {
        finished = false;
        for (int i = 0; i < 1000; i++) {
            for (int j = i; j < i + currentProcess.size; j++) {
                if (bytes[j].getProcessName() != "empty") {
                    break;
                }
                if (j == (i + currentProcess.size) - 1) {
                    finished = true;
                }

            }
            if (finished) {
                currentProcess.entered = i;
                for (int k = currentProcess.entered; k < currentProcess.entered + currentProcess.size; k++) {
                    bytes[k].setProcessName(currentProcess.name);
                }
                break;
            }


        }


    }

    public void bestFit() {
        ArrayList<Block> currentProcessBlocks = new ArrayList<>();
        int j;
        for (int i = 0; i < 999; i += j) {
            j = 0;
            for (int k = i; k < 1000; k++) {
                if (bytes[k].getProcessName() != "empty") {
                    i++;
                    break;
                } else {
                    j++;
                }

            }
            if (j != 1 && j >= currentProcess.size) {
                Block b = new Block();
                try {
                    if (bytes[i - 1].getProcessName().equalsIgnoreCase("empty"))
                        i--;
                } catch (Exception e) {
                } finally {
                    b.from = i;
                    b.size = j;
                    currentProcessBlocks.add(b);

                }
            }

        }
        smallest = currentProcessBlocks.get(0);
        for (Block c : currentProcessBlocks
                ) {
            if (c.size < smallest.size) {
                smallest = c;
            }
        }

        currentProcess.entered = smallest.from;
        for (int k = currentProcess.entered; k < currentProcess.entered + currentProcess.size; k++) {
            bytes[k].setProcessName(currentProcess.name);
        }

    }


    public void deallocate() {

        for (Process p : processes) {
            processDeallocated = processDeallocatedName.getText();
            if (processDeallocated.equalsIgnoreCase(p.getName())) {
                for (int i = p.entered; i < (p.entered + p.size); i++) {
                    bytes[i].setProcessName("empty");
                }
                listView.getItems().remove(processes.indexOf(p));
                processes.remove(p);
                break;
            }

        }


    }

    public void viewMemory() {
        int i = 0;
        for (Byte b : bytes
                ) {
            System.out.println("byte [ " + i + "]  =  " + b.getProcessName());
            i++;
        }


    }

    public void sortListView() {

        for (int i = 0; i < listView.getItems().size(); i++) {

            if (Integer.parseInt(listView.getItems().get(i).getId()) > Integer.parseInt(listView.getItems().get(listView.getItems().size() - 1).getId())) {
                JFXButton temp;
                Process p;
                temp = listView.getItems().get(i);
                p = processes.get(i);
                listView.getItems().remove(i);
                processes.remove(i);
                listView.getItems().add(i, listView.getItems().get(listView.getItems().size() - 1));
                processes.add(i, processes.get(processes.size() - 1));
                listView.getItems().remove(listView.getItems().size() - 1);
                processes.remove(processes.size() - 1);
                listView.getItems().add(temp);
                processes.add(p);

            }


        }

    }


}



