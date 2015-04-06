package erin.erinward_adamcordeiro_a3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    RadioGroup grpSize;
    RadioButton radSize;
    CheckBox chkCheese;
    CheckBox chkPepperoni;
    CheckBox chkSausage;
    CheckBox chkBacon;
    CheckBox chkPepper;
    TextView txtOrder;

    public static boolean saveOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grpSize = (RadioGroup) findViewById(R.id.grpSize);
        chkCheese = (CheckBox) findViewById(R.id.chkCheese);
        chkPepperoni = (CheckBox) findViewById(R.id.chkPepperoni);
        chkSausage = (CheckBox) findViewById(R.id.chkSausage);
        chkBacon = (CheckBox) findViewById(R.id.chkBacon);
        chkPepper = (CheckBox) findViewById(R.id.chkPepper);
        txtOrder = (TextView) findViewById(R.id.txtOrder);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.i("Menus", String.format("%b", saveOrder));
        invalidateOptionsMenu();
    }

    public void loadLoad(MenuItem item) {
        loadPreferences();
    }

    private void loadPreferences() {

        chkCheese.setChecked(false);
        chkPepperoni.setChecked(false);
        chkSausage.setChecked(false);
        chkBacon.setChecked(false);
        chkPepper.setChecked(false);

        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);

        radSize = (RadioButton) findViewById(myPrefs.getInt("size", 0));
        radSize.setChecked(true);

        for (int i = 1; i < myPrefs.getAll().size(); i++) {
            String key = String.format("topping%d", i-1);
            int checkID = myPrefs.getInt(key, 0);
            CheckBox tempChkBox = (CheckBox) findViewById(checkID);
            tempChkBox.setChecked(true);
        }

        /*
        edtName.setText(myPrefs.getString("name", null));
        edtEmail.setText(myPrefs.getString("email", null));
        chkNewsLetter.setChecked(myPrefs.getBoolean("newsLetter", false));
        */
    }

    public void saveSave(MenuItem item) {
        savePreferences(this.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public void savePreferences(View view) {
        // put code here to save to shared preferences
        clearPreferences();

        // get the values from the form
        radSize = (RadioButton) findViewById(grpSize.getCheckedRadioButtonId());
        int size = radSize.getId();
        ArrayList<Integer> toppings = new ArrayList<Integer>();
        if (chkCheese.isChecked()) toppings.add(chkCheese.getId());
        if (chkPepperoni.isChecked()) toppings.add(chkPepperoni.getId());
        if (chkSausage.isChecked()) toppings.add(chkSausage.getId());
        if (chkBacon.isChecked()) toppings.add(chkBacon.getId());
        if (chkPepper.isChecked()) toppings.add(chkPepper.getId());

        // get the shared preferences for the Activity
        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);


        // get the editor and add the values to the SharedPrefs
        SharedPreferences.Editor editor = myPrefs.edit();

        editor.putInt("size", size).apply();
        for (int i = 0; i < toppings.size(); i++) {
            editor.putInt("topping" + i, toppings.get(i)).apply();
        }

        txtOrder.setText(String.format("Size: %d", size));

    }

    public void clearPreferences() {
        // put code here to clear the shared preferences
        // get the shared preferences for the Activity
        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);

        // get the editor and add the values to the SharedPrefs
        SharedPreferences.Editor editor = myPrefs.edit();

        editor.clear().apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (saveOrder) {
            Log.i("Menus", "Special Menu");
            getMenuInflater().inflate(R.menu.menu_prefs, menu);

        }
        else {
            Log.i("Menus", "Regular Menu");
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, UserPreferenceActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
