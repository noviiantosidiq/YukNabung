package id.zone.yuknabung;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;


public class Myproses extends AppCompatActivity {
    private EditText tag;
    private EditText nom;
    private Button save;
    private TextView total;
    long maxid = 0;
    String[] daftar;
    ListView ListView01;
    protected Cursor cursor;

    DatabaseReference databaseReference,dataa;
    FirebaseAuth firebaseAuth;

    DatePickerDialog.OnDateSetListener listag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proses);

        nom = (EditText) findViewById(R.id.edNom);
        total = (TextView) findViewById(R.id.idTotal);
        tag = (EditText) findViewById(R.id.edTag);
        save = (Button) findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, login.class));
        }
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("email" + user.getUid());
        dataa = FirebaseDatabase.getInstance().getReference().child("email" + user.getUid());


        dataa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());
                    total.setText(dataSnapshot.child("tot").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Calendar cal = Calendar.getInstance();
        final int th = cal.get(Calendar.YEAR);
        final int bl = cal.get(Calendar.MONTH);
        final int hr = cal.get(Calendar.DAY_OF_MONTH);


        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog pickdat = new DatePickerDialog(Myproses.this, android.R.style.Theme_Holo_Dialog_MinWidth, listag, th, bl, hr);
                pickdat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickdat.show();


            }
        });
        listag = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = bl + 1;
                String tgl = hr + "/" + month + "/" + th;
                tag.setText(tgl);
            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nom == null) {
                    Toast.makeText(Myproses.this, "isi Nominalnya dulu", Toast.LENGTH_SHORT).show();
                } else if (tag == null) {
                    Toast.makeText(Myproses.this, "Masukan tanggalnya dulu", Toast.LENGTH_SHORT).show();
                    ;
                } else {
                    Integer jumlah = Integer.parseInt(nom.getText().toString());
                    String tanggal = tag.getText().toString().trim();
                    Integer awal = Integer.parseInt(total.getText().toString());
                    tabungan tabung = new tabungan(tanggal, jumlah);

                    dataa.child("tabungan").child(String.valueOf(maxid + 1)).setValue(tabung);
                    dataa.child("tot").setValue(awal + jumlah);
                    Toast.makeText(Myproses.this, "Data Berhasil dimasukan", Toast.LENGTH_LONG).show();
                    ;
                }
            }
        });
    }
}
