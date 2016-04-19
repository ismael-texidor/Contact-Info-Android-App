package com.example.n00465408.midtermapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private String name;
    private Button mContactButton;
    private String mFilename = "testFile.json";
    private ArrayList<String> todoItems = new ArrayList<String>();
    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readToDoItems();

        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                todoItems);
        ListView mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(aa);

                   if (name != null)
                   {
                       todoItems.add(0, name);
                       aa.notifyDataSetChanged();
                   }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addContact:
                startActivity(new Intent(this, EnterContactInfo.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPause()
    {
        super.onPause();
        saveToDoItems();
    }

    public void saveToDoItems()
    {
        JSONArray array = new JSONArray(todoItems);
        Writer writer = null;
        try
        {
            OutputStream out = openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            writer.close();
        } catch (IOException e)
        {
            Log.d("Writer", "IO exception:", e);
        }
    }

    private void readToDoItems()
    {
        BufferedReader reader = null;
        try
        {
            InputStream in = openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new
                    JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++)
                todoItems.add(array.getString(i));
            reader.close();
        } catch (IOException e)
        {
            Log.d("Reader", "IO exception:", e);
        } catch (JSONException e)
        {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


