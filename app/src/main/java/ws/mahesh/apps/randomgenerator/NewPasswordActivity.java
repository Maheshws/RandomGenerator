package ws.mahesh.apps.randomgenerator;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ws.mahesh.apps.randomgenerator.utils.Base;
import ws.mahesh.apps.randomgenerator.utils.DBAdapter;
import ws.mahesh.apps.randomgenerator.utils.PassGenerator;


public class NewPasswordActivity extends ActionBarActivity {

    DBAdapter db;
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    Button generate;
    EditText len, customChar;
    TextView password, hint;
    CheckBox small, caps, numb, specialc, undersc;
    PassGenerator psg = new PassGenerator();
    private int LENGTH;
    private String FINAL_STRING;
    private String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        small = (CheckBox) findViewById(R.id.checkBoxSmall);
        caps = (CheckBox) findViewById(R.id.checkBoxCaps);
        numb = (CheckBox) findViewById(R.id.checkBoxNumbers);
        specialc = (CheckBox) findViewById(R.id.checkBoxSpecialChar);
        undersc = (CheckBox) findViewById(R.id.checkBoxUnderscore);

        password = (TextView) findViewById(R.id.textViewPassword);
        hint = (TextView) findViewById(R.id.textViewHint);

        len = (EditText) findViewById(R.id.editTextLen);
        customChar = (EditText) findViewById(R.id.editTextCustomChar);

        generate = (Button) findViewById(R.id.buttonGenerate);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyPassword();
            }
        });
        db = new DBAdapter(this);

    }

    private void copyPassword() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(password.getText());
            if (clipboard.getText().equals(password.getText()))
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(password.getText());
            if (clipboard.getText().equals(password.getText()))
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        }

        Date date = new Date();
        db.open();
        db.insertRow(" ", password.getText().toString(), "" + dateFormat.format(date));
        db.close();
    }

    private void generate() {
        FINAL_STRING = "";
        String CUSTOMCHAR = customChar.getText().toString();
        if (len.getText().toString().equals(""))
            LENGTH = psg.randomLen();
        else
            LENGTH = Integer.parseInt(len.getText().toString());
        if (LENGTH > 50) {
            Toast.makeText(this, "Select a smaller length", Toast.LENGTH_SHORT).show();
            return;
        }
        if (small.isChecked() || caps.isChecked() || numb.isChecked() || specialc.isChecked() || undersc.isChecked() || !CUSTOMCHAR.equals("")) {
            if (small.isChecked())
                FINAL_STRING = FINAL_STRING + Base.abc;
            if (caps.isChecked())
                FINAL_STRING = FINAL_STRING + Base.ABC;
            if (numb.isChecked())
                FINAL_STRING = FINAL_STRING + Base.numbers;
            if (specialc.isChecked())
                FINAL_STRING = FINAL_STRING + Base.extrachars + Base.extrachars + FINAL_STRING;
            if (undersc.isChecked())
                FINAL_STRING = FINAL_STRING + "_____";
            if (!CUSTOMCHAR.equals(""))
                FINAL_STRING = FINAL_STRING + CUSTOMCHAR + CUSTOMCHAR + CUSTOMCHAR + CUSTOMCHAR;
        } else {
            Toast.makeText(this, "Select a valid option", Toast.LENGTH_SHORT).show();
            return;
        }
        PASSWORD = psg.randomString(LENGTH, FINAL_STRING);
        password.setText(PASSWORD);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_password, menu);
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
