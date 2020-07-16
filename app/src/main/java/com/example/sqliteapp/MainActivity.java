package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        editTextId = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_view);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_del);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Dado deletado", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Dado não deletado", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public  void onClick(View v){
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());

                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Dado editado", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Dado não editado", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());

                        if(isInserted = true)
                            Toast.makeText(MainActivity.this, "Dado Inserido", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Dado não inserido", Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }

    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View v){
                        Cursor res = myDb.getAllData();
                            if(res.getCount() == 0){
                                // mostras mensagem
                                showMessage("Erro", "Nada encontrado");
                                return;
                            }

                            StringBuffer buffer = new StringBuffer();
                            while(res.moveToNext()){
                                buffer.append("Id :" + res.getString(0)+"\n");
                                buffer.append("Nome :" + res.getString(1)+"\n");
                                buffer.append("Sobrenome :" + res.getString(2)+"\n");
                                buffer.append("Nota :" + res.getString(3)+"\n\n");
                            }

                            // Mostrar dados
                            showMessage("Dado",buffer.toString());
                    }
                }
        );
    }

        public void showMessage(String title, String Message){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(Message);
            builder.show();
        }

}