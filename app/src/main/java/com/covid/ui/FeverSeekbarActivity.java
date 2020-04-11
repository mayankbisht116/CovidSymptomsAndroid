package com.covid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.covid.R;
import com.covid.databinding.FeverSeekbarBinding;

public class FeverSeekbarActivity extends AppCompatActivity {

    public double temperature=0;
    int min=950,max=1050,current=1000;
    String tvCurrent;

    FeverSeekbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        binding= DataBindingUtil.setContentView(this, R.layout.fever_seekbar);

        binding.seekBar.setMax(max-min);
        binding.seekBar.setProgress(current-min);
        current=current/10;
        tvCurrent=String.valueOf(current);
        binding.tvTemp.setText(tvCurrent);




        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String temp="";

                current=progress+min;
                double progressFloat=current*0.1;
                temperature=Math.round(progressFloat * 100.0) / 100.0;
                temp=String.valueOf(temperature);
                binding.tvTemp.setText(temp);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    private void initActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.add_report_actionbar);
        View actionView = getSupportActionBar().getCustomView();
        actionView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("Temp",temperature);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        actionView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("Skipped",true);
                setResult(4,intent);
                finish();
            }
        });

        ((TextView)actionView.findViewById(R.id.toolbar_title)).setText("Fever");
    }
}