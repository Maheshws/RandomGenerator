package ws.mahesh.apps.randomgenerator.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.TextView;

import ws.mahesh.apps.randomgenerator.R;

/**
 * Created by Mahesh on 8/15/2014.
 */
public class SavedPasswordAdapter extends ResourceCursorAdapter {

    public SavedPasswordAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView TimeStamp = (TextView) view.findViewById(R.id.textViewTimeStamp);
        TextView Password = (TextView) view.findViewById(R.id.textViewPassword);
        TextView Tag = (TextView) view.findViewById(R.id.textViewTag);

        Tag.setText(cursor.getString(cursor.getColumnIndex("name")));
        TimeStamp.setText("Generated at " + cursor.getString(cursor.getColumnIndex("timestamp")));
        Password.setText(cursor.getString(cursor.getColumnIndex("password")));

        if (cursor.getString(cursor.getColumnIndex("name")).equalsIgnoreCase(" "))
            Tag.setVisibility(View.GONE);
    }
}
