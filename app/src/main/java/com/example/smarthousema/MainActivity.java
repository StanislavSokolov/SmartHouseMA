package com.example.smarthousema;

import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "SmartHouse";
    Connection con;

    AddressSpace addressSpace;
    String URLaddress = "http://XX.XXX.XXX.XXX///SmartHouse/ESP32GWC/";
    Uri builtUri;
    String[] selectingMode = { "Основное освещение", "Интерьерная подцветка", "Динамическое освещение"};
    String[] selectingSubMode = { "Постоянный свет", "Плавная смена цвета", "Бегущая радуга"};
    String[] selectingSubMode2 = { "Шкала громкости (градиент)", "Шкала громкости (радуга)", "Цветомузыка (5 полос)", "Цветомузыка (3 полосы)", "Цветомузыка (1 полоса - 3 частоты)", "Цветомузыка (1 полоса - низкие)", "Цветомузыка (1 полоса - средние)", "Цветомузыка (1 полоса - высокие)", "Стробоскоп", "Бегущие частоты (3 частоты)", "Бегущие частоты (низкие)", "Бегущие частоты (средние)", "Бегущие частоты (высокие)", "Анализатор спектра"};

    private LinearLayout linearLayoutScanResults;

    boolean checkedSwitch = false;
    int progressBarLight = 0;
    int progressBarSpeed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        linearLayoutScanResults = (LinearLayout) findViewById(R.id.linearLayoutScanResults);
        addressSpace = new AddressSpace();
        final TextView textViewTest = (TextView) findViewById(R.id.textView14);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectingMode);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectingSubMode);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectingSubMode2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1: spinner2.setVisibility(View.VISIBLE);
                        spinner3.setVisibility(View.INVISIBLE);
                        if (checkedSwitch) {
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(162 + spinner2.getSelectedItemPosition()));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                        }
                        break;
                    case 2: spinner2.setVisibility(View.INVISIBLE);
                        spinner3.setVisibility(View.VISIBLE);
                        if (checkedSwitch) {
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(225 + spinner3.getSelectedItemPosition()));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                        }
                        break;
                    default: spinner2.setVisibility(View.INVISIBLE);
                        spinner3.setVisibility(View.INVISIBLE);
                        if (checkedSwitch) {
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(0));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                        }
                        break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (checkedSwitch) {
                    builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(162 + position));
                    textViewTest.setText(builtUri.toString());
                    con = new Connection(builtUri);
                    con.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner2.setOnItemSelectedListener(itemSelectedListener2);

        AdapterView.OnItemSelectedListener itemSelectedListener3 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (checkedSwitch) {
                    builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(225 + position));
                    textViewTest.setText(builtUri.toString());
                    con = new Connection(builtUri);
                    con.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner3.setOnItemSelectedListener(itemSelectedListener3);





        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        final TextView textView = (TextView) findViewById(R.id.textView11);
        final TextView textView2 = (TextView) findViewById(R.id.textView12);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(seekBar.getProgress()));
                progressBarLight = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(32, progressBarLight/4));
                textViewTest.setText(builtUri.toString());
                con = new Connection(builtUri);
                con.start();
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView2.setText(String.valueOf(seekBar.getProgress()));
                progressBarSpeed = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(32, progressBarSpeed/4));
                textViewTest.setText(builtUri.toString());
                con = new Connection(builtUri);
                con.start();

            }
        });

        Switch switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedSwitch = ((Switch) v).isChecked();
                if (checkedSwitch){
                    switch (spinner.getSelectedItemPosition()) {
                        case 1:
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(162 + spinner2.getSelectedItemPosition()));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                            break;
                        case 2:
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(225 + spinner3.getSelectedItemPosition()));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                            break;
                        default:
                            builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(0));
                            textViewTest.setText(builtUri.toString());
                            con = new Connection(builtUri);
                            con.start();
                            break;
                    }
                }
                else{
                    builtUri = Uri.parse(URLaddress + addressSpace.getURLcommand(128));
                    textViewTest.setText(builtUri.toString());
                    con = new Connection(builtUri);
                    con.start();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        switch(id){
            case R.id.action_settings :
                headerView.setText("Настройки");
                return true;
            case R.id.open_settings:
                headerView.setText("Открыть");
                return true;
            case R.id.save_settings:
                headerView.setText("Сохранить");
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    class Connection extends Thread {

        Uri uri;
        URL url;
        HttpURLConnection connection;

        public Connection(Uri uri) {
            this.uri = uri;
        }

        @Override
        public void run() {
            super.run();
            try {
                url = new URL(uri.toString());
                Log.i(LOG_TAG, String.valueOf(url.toString()));
            } catch (MalformedURLException e) {
                Log.i(LOG_TAG, String.valueOf(e.getMessage()));
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.i(LOG_TAG, "HTTP_OK");
                } else {
                    Log.i(LOG_TAG, "ERROR");
                }
            } catch (IOException e) {
                Log.i(LOG_TAG, String.valueOf(e.getMessage()));
            }
            connection.disconnect();
        }
    }
}