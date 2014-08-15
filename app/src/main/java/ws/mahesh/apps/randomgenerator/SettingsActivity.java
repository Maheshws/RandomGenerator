package ws.mahesh.apps.randomgenerator;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import ws.mahesh.apps.randomgenerator.utils.DBAdapter;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs_holder);
        addPreferencesFromResource(R.xml.pref_general);

        Preference DeleteAllRows = findPreference("DeleteAllPass");
        DeleteAllRows.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DBAdapter db=new DBAdapter(SettingsActivity.this);
                db.open();
                db.deleteAll();
                db.close();
                Toast.makeText(SettingsActivity.this,"All passwords deleted",Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
