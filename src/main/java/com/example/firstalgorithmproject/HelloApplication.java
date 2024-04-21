package com.example.firstalgorithmproject;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.PriorityQueue;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HelloApplication extends Application {
    private static final int INFINITY = Integer.MAX_VALUE;
    public int numOfCities = 0;
    public int fromCity = 0;
    public int toCity = 0;
    public String path="";
//    public static List<String> minimumCostPathList;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        ArrayList<Cities> cityArrayList= new ArrayList<>();

        Label startCity = new Label("Start: ");
        Label endCity = new Label("End: ");

        Font font = Font.font("Times New Roman", FontWeight.BOLD, 17);

        startCity.setTextFill(Color.FLORALWHITE);
        startCity.setFont(font);
        endCity.setTextFill(Color.FLORALWHITE);
        endCity.setFont(font);

        ComboBox<String> start = new ComboBox<>();
        ComboBox<String> end = new ComboBox<>();

        File file = new File("Input.txt");
        Scanner sc = new Scanner(file);


        String number = sc.nextLine();
        numOfCities = Integer.parseInt(number);            //number of cities

        int numOfLine = 0;
        String [] city = new String[numOfCities];

        String cityEnd = "";
        String input = "";

        while (sc.hasNext()) {
            numOfLine++;

            if(numOfLine == 1) {
                String s = sc.nextLine();
                String[] str = s.split(", ");
                cityEnd = str[1];            // str[1] = end
                city[0] = str[0];
                city[numOfCities - 1] = str[1];
                continue;
            }



            String s = sc.nextLine();        //line

            input += s + "\n";


            String[] str = s.split(", ");        //start city in each line

            city[numOfLine - 2] = str[0];        //add cities to the array

            start.getItems().addAll(str[0]);        //add cities to comboBox
            end.getItems().addAll(str[0]);            //add cities to comboBox
        }

        start.getItems().addAll(cityEnd);        //add endCity to comboBox
        end.getItems().addAll(cityEnd);            //add endCity to comboBox


        for(int i = 0; i < city.length ; i++)
            System.out.println(city[i]);

        int[][] table = new int[numOfCities][numOfCities];


        for (int i = 0; i < numOfCities; i++) {                    //fill the table
            for (int j = 0; j < numOfCities; j++) {
                if( i == j )
                    table[i][j] = 0;
                else
                    table[i][j] = Integer.MAX_VALUE;
            }
        }

//        for (int i = 0; i < numOfCities; i++) {                    //print the table
//            for (int j = 0; j < numOfCities; j++) {
//                if( j == numOfCities - 1) {
//                }
//            }
//        }


        String [][] next = new String[numOfCities][numOfCities];

        for (int i = 0; i < numOfCities; i++) {                    //To save the path
            for (int j = 0; j < numOfCities; j++) {
                next[i][j] = city[j];
            }
        }

        String [] x = new String[numOfCities - 1];
//	        String input = "";

        x =  input.split("\n");

//	        for(int i = 0 ; i < x.length ; i++) {
//	        	System.out.println(x[i]);
//	        }

//	        System.out.println(input);				//print the file from start to L


        for (int i = 0; i < table.length; i++) { // Initial values for the table
            for (int j = 0; j < table.length; j++) {
                if (i == j)
                    table[i][j] = 0;
                else
                    table[i][j] = Integer.MAX_VALUE;
                next[i][j] = "x";
            }
        }

        for(int i = 0 ; i < x.length ; i++) {
            String[] parts = x[i].split(", (?=\\[)");
            //System.out.println(x[i]);

            int city1 = i;            // city1  0 --> 12

            for (int j = 1; j < parts.length; j++) {
                String[] cityAndCosts = parts[j].replaceAll("[\\[\\]]", "").split(",");

//	            	if(start.contains(cityAndCosts[0].trim())){
                String item = cityAndCosts[0].trim();

                int city2 = 0;

                for(int k = 0 ; k < start.getItems().size(); k++) {
                    if(start.getItems().get(k).equals(item)) {
                        city2 = k;
                        break;
                    }
                }

                int petrolCost = Integer.parseInt(cityAndCosts[1].trim());
                int hotelCost = Integer.parseInt(cityAndCosts[2].trim());
                table[city1][city2] = petrolCost + hotelCost;

            }
        }

        List<String[]> data = new ArrayList<>();

        // Read the file and extract strings
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                String[] extractedStrings = new String[lineData.length];
                for (int i = 0; i < lineData.length; i++) {
                    lineData[i] = lineData[i].split(",")[0].trim().replaceAll("[\\[\\]]", "");
                }
                int index = 0;
                for (String element : lineData) {
                    if (!isNumeric(element)) {
                        extractedStrings[index] = element.trim();
                        index++;
                    }
                }

                data.add(Arrays.copyOf(extractedStrings, index));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(data.remove(0)));
        System.out.println(Arrays.toString(data.remove(0)));

        // Print the extracted data
//        for (int i = 0; i < data.size(); i++) {
//            System.out.println(Arrays.toString(data.get(i)));
//        }


        //String path = "";
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();


        for (int i = 0; i < numOfCities; i++) {        //fill the table with minimum
            for (int j = 0; j < numOfCities; j++) {
                for (int k = 0 ; k < numOfCities; k++) {
                    if(table[j][i] == Integer.MAX_VALUE  || table[i][k]  == Integer.MAX_VALUE)
                        continue;

                    if(i == 0) {
                        int w = table[j][i] + table[i][k];
                        minHeap.add(w);
                    }

                    else if(table[j][k] > table[j][i] + table[i][k]) {
                        table[j][k] = table[j][i] + table[i][k];
                        next[j][k] = city[i];
                     }
                }

            }
        }

        start.setValue("Start");
        end.setValue("End");

        Label bestCost = new Label("The Minimum Cost: ");
        bestCost.setFont(font);
        bestCost.setTextFill(Color.FLORALWHITE);
        TextField bestCostField = new TextField();
        bestCostField.setEditable(false);
        bestCostField.setPromptText("The Minimum Cost");
        bestCostField.setPrefColumnCount(6);
        bestCostField.setPrefWidth(500);
        bestCostField.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        HBox hbStart = new HBox();
        hbStart.getChildren().addAll(startCity, start);
        hbStart.setSpacing(5);

        HBox hbEnd = new HBox();
        hbEnd.getChildren().addAll(endCity, end);
        hbEnd.setSpacing(5);

        Button findMinCost = new Button("Find The Minimum Cost");
        findMinCost.setFont(font);
        findMinCost.setTextFill(Color.FIREBRICK);

        HBox best = new HBox(bestCost, bestCostField, findMinCost);
        best.setAlignment(Pos.CENTER);
        best.setSpacing(20);

        HBox hbChooser = new HBox();
        hbChooser.getChildren().addAll(hbStart, hbEnd);
        hbChooser.setSpacing(200);
        hbChooser.setAlignment(Pos.CENTER);

        Label bestPath = new Label("The Best Path: ");
        bestPath.setTextFill(Color.FLORALWHITE);
        bestPath.setFont(font);
        TextArea pathArea = new TextArea();
        pathArea.setEditable(false);
        pathArea.setPrefHeight(50);
        pathArea.setPrefWidth(300);
        pathArea.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        Button printPath = new Button("Print Path");
        printPath.setFont(font);
        printPath.setTextFill(Color.FIREBRICK);

        Button btTable = new Button("The DP Table");
        btTable.setFont(font);
        btTable.setTextFill(Color.FIREBRICK);

        HBox hbPath = new HBox();
        hbPath.getChildren().addAll(bestPath, printPath, btTable);
        hbPath.setSpacing(70);

        Label otherPaths = new Label("The other tracks are in order of best: ");
        otherPaths.setTextFill(Color.FLORALWHITE);
        otherPaths.setFont(font);
        TextArea othersArea = new TextArea();
        othersArea.setEditable(false);
        othersArea.setPrefHeight(300);
        othersArea.setPrefWidth(300);
        othersArea.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        Button printOthers = new Button("The Alternative Paths");
        printOthers.setFont(font);
        printOthers.setTextFill(Color.FIREBRICK);

        Button clearOtherPaths = new Button("Clear The Other Paths!");
        clearOtherPaths.setFont(font);
        clearOtherPaths.setTextFill(Color.FIREBRICK);

        HBox hbAlternative = new HBox();
        hbAlternative.getChildren().addAll(otherPaths, printOthers, clearOtherPaths);
        hbAlternative.setSpacing(40);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hbChooser, best, hbPath, pathArea, hbAlternative, othersArea);
        vBox.setSpacing(5);
        vBox.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vBox, 1300, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Table Scene

        Label lbTable = new Label("The Dynamic Programming Table is Shown Below");
        TextArea taTable = new TextArea();
        taTable.setEditable(false);
        taTable.setPrefHeight(600);
        taTable.setPrefWidth(600);
        taTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        lbTable.setTextFill(Color.FLORALWHITE);
        lbTable.setFont(font);

        Button showTable = new Button("Show The Dynamic Table");
        showTable.setFont(font);
        showTable.setTextFill(Color.FIREBRICK);

        Button back = new Button("Back");
        back.setFont(font);
        back.setTextFill(Color.FIREBRICK);

        HBox hbTable = new HBox();
        hbTable.getChildren().addAll(showTable, back);
        hbTable.setSpacing(40);

        VBox vbTable = new VBox(lbTable, taTable, hbTable);
        vbTable.setSpacing(50);
        vbTable.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene sceneTable = new Scene(vbTable, 900, 1100);



        findMinCost.setOnAction(e -> {
            fromCity = start.getSelectionModel().getSelectedIndex();
            System.out.println("from City: " + fromCity);
            toCity = end.getSelectionModel().getSelectedIndex();
            System.out.println("to City: " + toCity);
            System.out.println(table[fromCity][toCity]);
            if(table[fromCity][toCity]==2147483647) {
                bestCostField.setText("No Connection");
                printPath.setDisable(true);
                printOthers.setDisable(true);
            }
            else{
                bestCostField.setText(String.valueOf(table[fromCity][toCity]));
                printPath.setDisable(false);
                printOthers.setDisable(false);
            }
        });


        printPath.setOnAction(e -> {
            boolean bool =false;
            //String[] d=data.remove(0);
            List<List<String>> allPaths = findAllPaths(data, start.getValue(), end.getValue());
            pathArea.setText(allPaths.get(0).toString());
//            System.out.println(minimumCostPathList.get(0));
            //data.add(d);


        });

        printOthers.setOnAction(e -> {
            boolean bool=true;
            List<List<String>> allPaths = findAllPaths(data, start.getValue(), end.getValue());
            if (!allPaths.isEmpty()) {
                System.out.println("All Paths: ");
                for (List<String> alternativePaths : allPaths) {
                    if(bool){
                        bool = false;
                                continue;
                    }
                    if(alternativePaths.toString().equals(pathArea.getText())){

                    }
                    else {
                        othersArea.appendText(alternativePaths.toString());
                        othersArea.appendText("\n");
                        System.out.println(alternativePaths);
                    }
                }
            } else {
                System.out.println("No paths found.");
            }
        });


        clearOtherPaths.setOnAction(e->{
            othersArea.clear();
        });

        btTable.setOnAction(e -> {
            primaryStage.setScene(sceneTable);
        });

        back.setOnAction(e -> {
            primaryStage.setScene(scene);
        });


        showTable.setOnAction(e -> {
            for(int i = 0; i < numOfCities; i++) {                        //print the table
                for(int j = 0; j < numOfCities; j++) {
                    if (table[i][j] == Integer.MAX_VALUE) {
                        taTable.appendText("x\t");
                        System.out.printf("%-5s", "x");
                    }
                    else {
                        taTable.appendText(table[i][j]+"\t");
                        System.out.printf("%-5d", table[i][j]);
                    }
                    if(j == numOfCities - 1) {
                        taTable.appendText("\n");
                        System.out.println();
                    }
                }
            }
        });

        primaryStage.setTitle("The First Algorithm Project");
        primaryStage.setScene(scene);

    }

    public static List<List<String>> findAllPaths(List<String[]> arrayList, String start, String end) {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> path = new ArrayList<>();
        List<String> minimumCostPathList = new ArrayList<>(); // Initialize minimumCostPath
        int[] minCost = {Integer.MAX_VALUE}; // Create an array to hold the minimum cost
        path.add(start);
        dfs(arrayList, start, end, 0, path, allPaths, minCost,minimumCostPathList);
        allPaths.add(0,minimumCostPathList);
        return allPaths;
    }

    public static void dfs(List<String[]> arrayList, String current, String end, int currentCost, List<String> path, List<List<String>> allPaths, int[] minCost,List<String> minimumCostPathList) {
        if (current.equals(end)) {
            int pathCost = calculateCost(arrayList, path);
            if (pathCost < minCost[0]) {
                //pathCost < minCost[0] || currentCost < pathCost || currentCost < calculateCost(arrayList,minimumCostPathList)
                minCost[0] = pathCost; // Update the minimum cost
                minimumCostPathList.clear();
                minimumCostPathList.addAll(path);
            }
            allPaths.add(new ArrayList<>(path));
            return;
        }

        for (String[] array : arrayList) {
            if (array[0].equals(current)) {
                for (int i = 1; i < array.length; i++) {
                    String next = array[i];
                    int cost = getCost(array, next); // Get the cost for this path
                    path.add(next);
                    dfs(arrayList, next, end, currentCost + cost, path, allPaths, minCost,minimumCostPathList);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    public static int getCost(String[] array, String city) {
        for (int i = 1; i < array.length - 1; i += 2) {
            if (array[i].equals(city)) {
                try {
                    return Integer.parseInt(array[i + 1]);
                } catch (NumberFormatException e) {
                    // Invalid cost value, return a default cost
                    return 0;
                }
            }
        }
        return 0; // Default cost if not found
    }

    private static int calculateCost(List<String[]> arrayList, List<String> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String currentCity = path.get(i);
            String nextCity = path.get(i + 1);
            for (String[] neighbor : arrayList) {
                if (neighbor.length >= 3 && neighbor[0].equals(currentCity) && neighbor[1].equals(nextCity)) {
                    try {
                        cost += Integer.parseInt(neighbor[2]);
                    } catch (NumberFormatException e) {
                        // Invalid cost value, use a default cost
                        cost += 0;
                    }
                    break;
                }
            }
        }
        return cost;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    public static void main(String[] args) throws FileNotFoundException {

        //System.out.println(minCost());

        launch(args);

    }
}