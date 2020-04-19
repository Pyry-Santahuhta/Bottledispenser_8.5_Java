package com.example.a72button;
import android.view.View;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class BottleDispenser extends AppCompatActivity {
    Bottle pepsiMax = new Bottle();
    Bottle bigPepsiMax = new Bottle();
    Bottle colaZero = new Bottle();
    Bottle bigColaZero = new Bottle();
    Bottle fantaZero = new Bottle();
    Bottle receipt = null;
    TextView console; SeekBar moneyslider; TextView moneysliderProgress; Spinner drinkSpinner; Spinner sizeSpinner;
    int i;
    ArrayAdapter<String> StringAdapter;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> spinnerSizeAdapter;
    ArrayList<Bottle> bottles = new ArrayList<>();
    ArrayList<String> screenList = new ArrayList<>();
    ArrayList<String> spinnerDrinkList = new ArrayList<>();
    ArrayList<String> spinnerSizeList = new ArrayList<>();
    private double money = 0;
    private static BottleDispenser bd = null;
    public static BottleDispenser getInstance(){
        if (bd == null){
            bd = new BottleDispenser();
        }
        return bd;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, screenList);
        console = findViewById(R.id.console);

        moneyslider = findViewById(R.id.ms);
        moneysliderProgress = findViewById(R.id.msProgress);

        drinkSpinner = findViewById(R.id.drinkSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,  spinnerDrinkList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,  spinnerSizeList);
        spinnerSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        drinkSpinner.setAdapter(spinnerAdapter);
        sizeSpinner.setAdapter(spinnerSizeAdapter);

        moneysliderProgress.setText(String.valueOf(moneyslider.getProgress()+1));
        moneyslider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moneysliderProgress.setText(String.valueOf(progress+1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ListView lw = findViewById(R.id.bottlelist);
        lw.setAdapter(StringAdapter);
        BottleDispenser.getInstance();
        listBottles();
    }
    public BottleDispenser() {
        money = 0;
        pepsiMax.setName("Pepsi max").setManufacturer("Pepsi").setEnergy(0.9).setSize(0.5).setPrize(1.8);
        bigPepsiMax.setName("Pepsi max").setManufacturer("Pepsi").setEnergy(0.9).setSize(1.5).setPrize(2.2);
        colaZero.setName("Coca-Cola Zero").setManufacturer("Coca-Cola").setEnergy(0.3).setSize(0.5).setPrize(2);
        bigColaZero.setName("Coca-Cola Zero").setManufacturer("Coca-Cola").setEnergy(0.9).setSize(1.5).setPrize(2.5);
        fantaZero.setName("Fanta Zero").setManufacturer("Fanta").setEnergy(0.3).setSize(0.5).setPrize(1.95);
        bottles.add(pepsiMax);
        bottles.add(bigPepsiMax);
        bottles.add(colaZero);
        bottles.add(bigColaZero);
        bottles.add(fantaZero);
        spinnerDrinkList.add("Pepsi max");spinnerDrinkList.add("Coca-Cola Zero");spinnerDrinkList.add("Fanta Zero");
        spinnerSizeList.add("1.5");spinnerSizeList.add("0.5");

    }

    public void addMoney(View v) {
        int moneyAdd = moneyslider.getProgress()+1;
        money += moneyAdd;
        console.setText("Klink! Added " + moneyAdd +"€ money!");
    }

    public void listBottles() {
        screenList.clear();
        if (bottles.isEmpty()){
            StringAdapter.clear();
            StringAdapter.notifyDataSetChanged();
        }
        else{
            for (Bottle alkio : bottles) {
                i++;
                screenList.add(i + ". Name: " + alkio.getName() + "\t Size: " + alkio.getSize() + "\t Price: " + alkio.getPrize());
                StringAdapter.notifyDataSetChanged();

            }
        }
        i=0;

    }

    public void buyBottle(View v) {
        if (money < 1) {
            console.setText("Add money first!");

        }
        else if(bottles.isEmpty()){
            console.setText("Uh oh ran outta bottles");
        }
        else {

            int count = bottles.size();
            for (Bottle bottle : bottles) {
                if (drinkSpinner.getSelectedItem().equals(bottle.getName()) && sizeSpinner.getSelectedItem().equals(String.valueOf(bottle.getSize()))) {
                    if (money < bottle.getPrize()){
                        console.setText("Not enough money for that!");
                        count++;
                        break;
                    }
                    else {
                        money = money - bottle.getPrize();
                        console.setText("KACHUNK! " + bottle.getName() + " came out of the dispenser!");
                        bottles.remove(bottle);
                        receipt = bottle;

                        break;
                    }
                }
            }
            if (bottles.size() == count) console.setText("Your selected beverage has ran out.");
        }
        listBottles();

        }

    public void printReceipt(View V) {
        if (receipt == null) {
            console.setText("Buy something first!");
        }
        else {
            try {
                OutputStreamWriter owS = new OutputStreamWriter(getApplicationContext().openFileOutput("receipt.txt", getApplicationContext().MODE_PRIVATE));
                owS.write("****RECEIPT****\n" + "Product: " + receipt.getName() + "\n Prize: " + receipt.getPrize() + "€");
                console.setText("Printed receipt.");
                owS.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void returnMoney(View v) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (money < 0.01){
            console.setText("No money to return!");
        }
        else {
            console.setText("You got " + formatter.format(money) + "€ back");
            money = 0;
        }
    }
}