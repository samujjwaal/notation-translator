package com.samashish.notetrans;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    // define objects for UI components
    EditText input, output;
    Switch sharpSwitch, flatSwitch;
    Spinner scaleList;
    Button translate;

    String scaleSelected;

    // define strings for scale lists
    String[] indScale = {"sa", "re(k)", "re", "ga(k)", "ga", "ma", "ma(t)", "pa", "dha(k)", "dha", "ni(k)", "ni"};
    String[] westSharpScale = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    String[] westFlatScale = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        populateDropdown();

        // add the listener to the Scale selection dropdown spinner
        scaleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // on selecting a spinner item
                scaleSelected = (String) parent.getItemAtPosition(position);
                // showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + scaleSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        // add the listener to the Translate button
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharpSwitch.isChecked() && !flatSwitch.isChecked()) {
                    Toast.makeText(Main.this, "Please select an Accidental Note Type !", Toast.LENGTH_SHORT).show();
                } else if (input.getText().toString().equals("")) {
                    Toast.makeText(Main.this, "Please enter Notations to translate !", Toast.LENGTH_SHORT).show();
                } else {
                    if (sharpSwitch.isChecked()) {
                        output.setText(translateNotes(westSharpScale));
                    } else {
                        output.setText(translateNotes(westFlatScale));
                    }
                    Toast.makeText(Main.this, "Success! Notations translated !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init() {
        // bind the components to their respective objects
        input = findViewById(R.id.inputText);
        input.setMovementMethod(new ScrollingMovementMethod());
        output = findViewById(R.id.outputText);
        output.setMovementMethod(new ScrollingMovementMethod());
        sharpSwitch = findViewById(R.id.sharpSwitch);
        flatSwitch = findViewById(R.id.flatSwitch);
        scaleList = findViewById(R.id.scaleSelect);
        translate = findViewById(R.id.translateButton);
    }

    public void populateDropdown() {
        // Creating adapters for spinner
        final ArrayAdapter<String> sharpScale = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, westSharpScale);
        sharpScale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> flatScale = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, westFlatScale);
        flatScale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // add the listener to the Sharp switch
        sharpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on) {
                if (on) {
                    // if switch is checked
                    flatSwitch.setChecked(false);
                    // attaching data adapter to spinner
                    scaleList.setAdapter(sharpScale);
                    // set C as default scale selection
                    scaleList.setSelection(3);
                } else {
                    // if switch is off
                    flatSwitch.setChecked(true);
                    sharpSwitch.setChecked(false);
                }
            }
        });

        // add the listener to the Flat switch
        flatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on) {
                if (on) {
                    // if switch is checked
                    sharpSwitch.setChecked(false);
                    // attaching data adapter to spinner
                    scaleList.setAdapter(flatScale);
                    // set C as default scale selection
                    scaleList.setSelection(3);
                } else {
                    // if switch is off
                    sharpSwitch.setChecked(true);
                    flatSwitch.setChecked(false);
                }
            }
        });
    }

    public String translateNotes(String[] ScaleNotes) {
        String westernNote = input.getText().toString();
        StringBuilder hindiNote = new StringBuilder();

        int indexOfScale = getIndex(ScaleNotes, scaleSelected);
        String[] notations = westernNote.split("\\r?\\n");

        for (String line : notations) {
            String[] notes = line.split("\\s+");

            for (String note : notes) {
                String temp;
                if (getIndex(ScaleNotes, note) >= indexOfScale) {
                    temp = indScale[getIndex(ScaleNotes, note) - indexOfScale];
                } else {
                    temp = indScale[12 - indexOfScale + getIndex(ScaleNotes, note)];
                }

                hindiNote.append(temp).append(" ");
            }
            hindiNote.append("\n");
        }

        return hindiNote.toString();
    }

    public int getIndex(String[] a, String key) {
        int i;
        for (i = 0; i < a.length; i++) {
            if (key.compareTo(a[i]) == 0)
                return i;
        }
        return -1;
    }
}