package erin.erinward_adamcordeiro_a3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Erin on 2015-04-06.
 */
public class UserPreferenceActivity extends PreferenceActivity {

    // create an instance of our listener (strong reference)
    SettingsListener settingsListener = new SettingsListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // call the deprecated method to the load the
        // preference screen
        addPreferencesFromResource(R.xml.user_pref);

        // Get the SharedPreferences used by the PreferenceActivity
        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(this);

        // register the listener to receive change events
        settings.registerOnSharedPreferenceChangeListener(settingsListener);

        Preference saveNumbers = findPreference("saveNumbers");
        saveNumbers.setOnPreferenceChangeListener(sBinder);

        String savedPizzas = settings.getString("saveNumbers", "1");
        sBinder.onPreferenceChange(saveNumbers, savedPizzas);

    }
    class SettingsListener implements
            SharedPreferences.OnSharedPreferenceChangeListener {


        @Override
        public void onSharedPreferenceChanged(
                SharedPreferences sharedPreferences, String key) {

            String value;
            switch(key) {
                case "saveNumbers":
                    value = sharedPreferences.getString(key, "Not set");
                    break;
                case "saveOrder":
                case "keepHistory":
                    value = String.valueOf(
                            sharedPreferences.getBoolean(key, false));
                    if (sharedPreferences.getBoolean(key, false)) {
                        MainActivity.saveOrder = true;
                        Log.i("Menus", "Should be changed to special");
                    }
                    else {
                        MainActivity.saveOrder = false;
                        Log.i("Menus", "Should be changed to regular");
                    }
                    break;
                default:
                    value = "Key not handled";
                    break;
            }

            Toast.makeText(UserPreferenceActivity.this,
                    key + ": " + value, Toast.LENGTH_SHORT).show();

        }
    }

    private static Preference.OnPreferenceChangeListener
            sBinder = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            // declare a String variable to hold the newValue
            String stringValue = newValue.toString();

            // first check if it's a ListPreference
            if (preference instanceof ListPreference) {

                // cast to a ListPreference
                ListPreference listPref = (ListPreference) preference;

                // get the index of the selected entry in the values array
                int index = listPref.findIndexOfValue(stringValue);

                // set the summary to the corresponding entries array
                String newSummary = listPref.getEntries()[index].toString();

                listPref.setSummary(newSummary);
            } else {
                //
                preference.setSummary(stringValue);

            }

            return true;
        }
    };

}
