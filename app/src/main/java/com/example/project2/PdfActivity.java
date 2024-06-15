package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

//        PDFView pdfView = (PDFView) findViewById(R.id.pdf_view);
//
//        pdfView.fromAsset("simple.pdf").load();
    }
}