package lv.donins.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the + button is clicked.
     */
    public void increment (View view) {
        if (quantity > 99) {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toastUp);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1 ;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement (View view) {
        if (quantity < 1) {

            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toastDown);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }
        quantity = quantity - 1 ;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Figure out if the user wants Whipped Cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        int WhippedCreamPrice;
        if (hasWhippedCream) {
            WhippedCreamPrice = 1;
        } else {
            WhippedCreamPrice = 0;
        }
        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolateCheckBox.isChecked();
        int ChocolatePrice;
        if (hasChocolate) {
            ChocolatePrice = 2;
        } else {
            ChocolatePrice = 0;
        }
        int topping = WhippedCreamPrice + ChocolatePrice;
        // GET user NAME
        EditText userName = (EditText) findViewById(R.id.user_name_view);
        String name = userName.getText().toString();

        int price = calculatePrice(topping);
        String priceMessage = CreatOrderSummary(name, price, hasWhippedCream, hasChocolate);

        String subject = getString(R.string.subject, name);


        displayMessage(priceMessage);
        composeEmail(subject, priceMessage);
    }

    public void composeEmail(String subject, String priceMessage) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Calculates the price of the order.
     *
     * @param @quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int topping) {

        return quantity * (5 + topping);
    }

    private String CreatOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.add_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.quantity) + ":" + quantity;
        priceMessage += "\n" + getString(R.string.total, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}