package id.zone.yuknabung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Myprofil extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private EditText ednama;
    private EditText edibu;
    private EditText jkel;
    private TextView showUser;
    private TextView contohh;
    private Button btnsave;
    private Button btnedit;
    private DatabaseReference databaseReference;
    private DatabaseReference dataa;
    private RadioGroup rgGender;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        showUser = (TextView) findViewById(R.id.idUser);
        btnsave = (Button) findViewById(R.id.btnSimpan);
        btnedit = (Button) findViewById(R.id.btnEdit);
        ednama = (EditText) findViewById(R.id.edNama);
        jkel = (EditText) findViewById(R.id.jkelamin);
        edibu = (EditText) findViewById(R.id.edIbu);
        showUser = (TextView) findViewById(R.id.idUser);
        contohh = (TextView) findViewById(R.id.contoh2);
        rgGender = (RadioGroup) findViewById(R.id.jk);
        btnsave.setOnClickListener(this);
        btnedit.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, login.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        dataa = FirebaseDatabase.getInstance().getReference().child("email"+user.getUid());
        if(dataa == null){
            ednama.setEnabled(true);
            edibu.setEnabled(true);
            rgGender.setVisibility(View.VISIBLE);
            jkel.setVisibility(View.GONE);
            jkel.setText(" ");
        }else {
            dataa.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    rgGender.setVisibility(View.GONE);
                    jkel.setVisibility(View.VISIBLE);
                    showUser.setText(dataSnapshot.child("email").getValue().toString());
                    ednama.setText(dataSnapshot.child("nama").getValue().toString());
                    edibu.setText(dataSnapshot.child("ibu").getValue().toString());
                    jkel.setText(dataSnapshot.child("jkelamin").getValue().toString());
                    ednama.setEnabled(false);
                    edibu.setEnabled(false);
                    jkel.setEnabled(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



        rgGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final String value = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId()))
                                .getText().toString();
                contohh.setText(value);
                Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();



            }

        });
    }
    private void Updateuser(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String kepala = "email"+user.getUid();
        String email = user.getEmail();
        String nama = ednama.getText().toString().trim();
        String jkelamin = contohh.getText().toString().trim();
        String ibu = edibu.getText().toString().trim();
        Integer tot = 0;
        infoUser userinfo = new infoUser(email,nama,jkelamin,ibu,tot);


       databaseReference.child(kepala).setValue(userinfo);
       Toast.makeText(this, "Berhasil disimapan",Toast.LENGTH_LONG).show();

    }




    @Override
    public void onClick(View view) {
        if(view == btnsave){
            if(ednama == null){
                Toast.makeText(this, "Nama belum diisi",Toast.LENGTH_SHORT).show();
            }else if(edibu == null){

                Toast.makeText(this, "Nama belum diisi",Toast.LENGTH_SHORT).show();
            }else {
                Updateuser();
            }
        }
        if(view == btnedit){
            ednama.setEnabled(true);
            edibu.setEnabled(true);
            rgGender.setVisibility(View.VISIBLE);
            jkel.setVisibility(View.GONE);
            jkel.setText(" ");
        }

    }


    }

