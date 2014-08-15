package ws.mahesh.apps.randomgenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ws.mahesh.apps.randomgenerator.utils.DBAdapter;
import ws.mahesh.apps.randomgenerator.utils.SavedPasswordAdapter;


public class SavedPasswordsActivity extends ActionBarActivity {
    SavedPasswordAdapter adapter;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_passwords);
        db = new DBAdapter(this);
        db.open();
        populateListViewFromDB();
        db.close();
        registerListClickCallback();
    }

    private void populateListViewFromDB() {

        Cursor cursor = db.getAllRows();
        // Set the adapter for the list view
        adapter = new SavedPasswordAdapter(this, R.layout.saved_pass_item, cursor, 0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(adapter);
    }

    private void registerListClickCallback() {
        final ListView myList = (ListView) findViewById(R.id.listView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {
                db.open();
                Cursor cursor = db.getRow(idInDB);
                db.close();
                copyPassword(cursor.getString(DBAdapter.COL_PASSWORD));
            }
        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                db.open();
                                db.deleteRow(l);
                                populateListViewFromDB();
                                db.close();
                                //UpdateTag(l);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SavedPasswordsActivity.this);
                builder.setTitle("Delete").setMessage("Are you sure?");
                builder.setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            }
        });
    }

    private void copyPassword(String password) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(password);
            if (clipboard.getText().equals(password))
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(password);
            if (clipboard.getText().equals(password))
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }


/*
    private void UpdateTag(final long l) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SavedPasswordsActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("TAG");

        // Setting Dialog Message
        alertDialog.setMessage("Enter a Tag");
        final EditText input = new EditText(SavedPasswordsActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tag=input.getText().toString();
                        if(!tag.equalsIgnoreCase("")) {
                            db.open();
                            Cursor cursor = db.getRow(l);
                            db.updateRow(l,tag,cursor.getString(DBAdapter.COL_PASSWORD),cursor.getString(DBAdapter.COL_TIMESTAMP));
                            populateListViewFromDB();
                            db.close();
                            Log.e("TAG", tag);
                        }
                        // Write your code here to execute after dialog
                    }

                });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saved_passwords, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
