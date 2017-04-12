/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    boolean hasWippedCream;
    boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox  wippedCreamCheckBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        hasWippedCream = wippedCreamCheckBox.isChecked();

        CheckBox  chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        EditText text = (EditText) findViewById(R.id.name_text);
        String name = text.getText().toString();

        int price = calculatePrice(quantity);
        String summary = orderSummary(price, hasWippedCream, hasChocolate, name);
       // displayMessage(summary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL,"lifecoderohit@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Coffee Break");
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    public String orderSummary(int price, boolean hasWippedCream, boolean hasChocolate, String name) {
        int quantity = this.quantity;
        String message = "Thank You!";
        String summary = "Name: " + name + "\n" + "Has Wipped Cream? "+ hasWippedCream + "\n" + "Has Chocolate? "+ hasChocolate + "\n" +"Quantity: " + quantity + "\n" + "Total: " + price + "\n" + message;
        return summary;
    }

    public int calculatePrice(int quantity) {
            if(hasWippedCream == true && hasChocolate == true)
                return quantity * (50+10+20);
            else if(hasChocolate == true)
                return quantity * (50+20);
            else if(hasWippedCream == true)
                return quantity * (50+10);
            else
                return quantity * 50;
    }

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, "Not more than 100 coffee allowed at once. ",
                    Toast.LENGTH_LONG).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
        displayPrice(calculatePrice(quantity));
    }

    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "Not less than 1 coffee allowed. ",
                    Toast.LENGTH_LONG).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
        displayPrice(calculatePrice(quantity));
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(float number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("Rs. " + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity    (int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSmmaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSmmaryTextView.setText(message);
    }

}