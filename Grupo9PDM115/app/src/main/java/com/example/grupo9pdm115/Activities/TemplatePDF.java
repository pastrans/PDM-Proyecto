package com.example.grupo9pdm115.Activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Font fTitle = new Font (Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
    private Font fSubTitle = new Font (Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private Font fText = new Font (Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    private Font fHighText = new Font (Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD, BaseColor.RED);
    private Paragraph paragraph;

    public TemplatePDF(Context context) {
        this.context = context;
    }
    public void openDocument(){
        CreateFile();
        try{
            document= new Document(PageSize.A4);
            pdfWriter= PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e) {
            Log.e("openDocument: ",e.toString() );
        }
    }
    private void CreateFile(){
        File folder =  new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if(!folder.exists()){
            folder.mkdir();
            pdfFile= new File(folder, "Horario");
        }
    }

    public void closeDocument(){
            document.close();
    }
    public void addMetaData(String title, String subject, String author){
    document.addTitle(title);
    document.addSubject(subject);
    document.addAuthor(author);
    }
    public void addTitles(String title, String subTitle, String date){
        try{
            paragraph= new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            addChildP(new Paragraph(subTitle,fSubTitle));
            addChildP(new Paragraph("Generado:" + date,fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles",e.toString());
        }

    }
    public void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try{
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e) {
            Log.e("addParagraph: ",e.toString() );
        }
    }

    public void createTable(String[] header, ArrayList<String[]>Clients){
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(fText);
        PdfPTable pdfPTable = new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        int indexC= 0;
        PdfPCell pdfPCell;
        //para colocar el encabezado horas/Día

        while (indexC<header.length){
            pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GREEN);
            //Color.rgb(115,200,169)
            pdfPTable.addCell(pdfPCell);
        }
        for(int indexR=0;indexR<Clients.size(); indexR++ ){
            String[] fow =Clients.get(indexR) ;
            for(indexC= 0; indexC <header.length;indexC++ ){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);
                pdfPTable.addCell(pdfPCell);
            }
            try{
            paragraph.add(pdfPTable);
            document.add(paragraph);
            }catch (Exception e) {
                Log.e("createTable: ",e.toString() );
            }

        }

    }
    public void appViewPDF(Activity activity){
        if (pdfFile.exists()){
            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"application/pdf");
            try{
                activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(activity.getApplicationContext(),"Descarga PDF ",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(activity.getApplicationContext(),"El archivo no se encontro",Toast.LENGTH_SHORT).show();
        }
    }
}
