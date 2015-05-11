package com.jmrodrigg.printing;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

/**
 * Author: jrodriguezg
 * Date: 11/05/2015.
 */
public class PrintingSettingsActivity extends Activity {

    /** Overriden methods **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printing_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_printing_settings, menu);
        return false;
    }

    /** Events **/

    public void onPrintModeClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.radioClip:
                if(checked) ((PrintingApplication)getApplication()).print_mode = PrintingApplication.PrintMode.PRINT_CLIP_CONTENT;
                break;
            case R.id.radioFit:
                if(checked) ((PrintingApplication)getApplication()).print_mode = PrintingApplication.PrintMode.PRINT_FIT_TO_PAGE;
                break;
        }
    }

    public void onMarginsModeClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.radioNoMargins:
                if(checked) ((PrintingApplication)getApplication()).margins_mode = PrintingApplication.MarginsMode.NO_MARGINS;
                break;
            case R.id.radioPrinterMargins:
                if(checked) ((PrintingApplication)getApplication()).margins_mode = PrintingApplication.MarginsMode.PRINTER_MARGINS;
                break;
        }
    }

    public void doPrint(View v) {
        PrintManager printManager = (PrintManager) getSystemService(
                Context.PRINT_SERVICE);

        printManager.print("test.pdf",new PrintAdapter(this,((PrintingApplication)getApplication()).renderer),null);
    }
}