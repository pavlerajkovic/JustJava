package com.pavlerajkovic.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int quantity = 1;

    private boolean hasMilk;
    private boolean hasSugar;
    private boolean hasCocoa;
    private boolean hasCream;

    private Button buttonDecrement;
    private Button buttonIncrement;

    private TextView textQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDecrement = findViewById(R.id.button_decrement);
        buttonIncrement = findViewById(R.id.button_increment);

        textQuantity = findViewById(R.id.text_quantity);

        buttonDecrement.setEnabled(false);
    }

    public double calculatePrice() {
        double coffee = 2.7;
        double toppings = 0;

        if(hasMilk) toppings += 0.6;
        if(hasCocoa) toppings += 0.5;
        if(hasSugar) toppings += 0.4;
        if(hasCream) toppings += 0.3;

        return (coffee + toppings) * quantity;
    }

    public void incrementQuantity(View view) {
        if(quantity < 10) {
            quantity++;
            displayQuantity();
            buttonDecrement.setEnabled(true);

            if(quantity == 10)
                buttonIncrement.setEnabled(false);
        }
    }

    public void decrementQuantity(View view) {
        if(quantity > 1) {
            quantity--;
            displayQuantity();
            buttonIncrement.setEnabled(true);

            if(quantity == 1)
                buttonDecrement.setEnabled(false);
        }
    }

    public void displayQuantity() {
        textQuantity.setText(String.valueOf(quantity)); // or ("" + quantity)
    }

    public String createOrderSummary() {
        EditText editName = findViewById(R.id.edit_name);

        // NumberFormat.getCurrencyInstance().format(price)

        return "Name: " + editName.getText().toString() + "\n" +
                "Toppings: " + createToppingsSummary() + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price: $" + calculatePrice() + "\n" +
                "Thank You!";
    }

    public String createToppingsSummary() {
        StringBuilder toppings = new StringBuilder("");

        checkToppings();

        if(hasMilk) toppings.append("Milk, ");
        if(hasSugar) toppings.append("Sugar, ");
        if(hasCocoa) toppings.append("Cocoa, ");
        if(hasCream) toppings.append("Whipped Cream, ");

        return toppings.substring(0, toppings.length() - 2);
    }

    public void checkToppings() {
        CheckBox checkMilk = findViewById(R.id.check_milk);
        CheckBox checkSugar = findViewById(R.id.check_sugar);
        CheckBox checkCocoa = findViewById(R.id.check_cocoa);
        CheckBox checkCream = findViewById(R.id.check_cream);

        hasMilk = checkMilk.isChecked();
        hasSugar = checkSugar.isChecked();
        hasCocoa = checkCocoa.isChecked();
        hasCream = checkCream.isChecked();
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"order@justjava.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

}
