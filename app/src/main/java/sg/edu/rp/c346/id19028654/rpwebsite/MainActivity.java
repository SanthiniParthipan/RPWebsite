package sg.edu.rp.c346.id19028654.rpwebsite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnC1,spnSC2;
    Button btnGo;
    ArrayList<String> alWebsite;
    ArrayAdapter<String> aaWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnC1=findViewById(R.id.spinner1C);
        spnSC2=findViewById(R.id.spinner2SC);
        btnGo=findViewById(R.id.buttonGo);
        alWebsite=new ArrayList<>();
        aaWebsite=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,alWebsite);

        spnC1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        alWebsite.clear();
                        String[] RPthings = getResources().getStringArray(R.array.RP_Sub_Category);
                        alWebsite.addAll(Arrays.asList(RPthings));
                        spnSC2.setAdapter(aaWebsite);
                        break;
                    case 1:
                        alWebsite.clear();
                        String[] SOIthings = getResources().getStringArray(R.array.SOI_Sub_Category);
                        alWebsite.addAll(Arrays.asList(SOIthings));
                        spnSC2.setAdapter(aaWebsite);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                String[][] sites = {
                        {
                                "https://www.rp.edu.sg/",
                                "https://www.rp.edu.sg/student-life",
                        },
                        {
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                        }
                };
                String url = sites[spnC1.getSelectedItemPosition()][spnSC2.getSelectedItemPosition()];
                intent.putExtra("website", url);
                int category = spnC1.getSelectedItemPosition();
                int subCategory = spnSC2.getSelectedItemPosition();
                SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putInt("category", category);
                prefEdit.putInt("subCategory", subCategory);
                prefEdit.commit();
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selCategory = prefs.getInt("category", 0);
        int selSubCategory = prefs.getInt("subCategory", 0);
        spnC1.setSelection(selCategory);
        spnSC2.setSelection(selSubCategory);
        Toast.makeText(this, "" + selSubCategory, Toast.LENGTH_SHORT).show();
    }
}
