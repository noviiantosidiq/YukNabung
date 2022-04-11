package id.zone.yuknabung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView showUser;
    private TextView total;
    private Button BtnProfile;
    private Button BtnProses;
    private Button BtnHadiah;
    private Button BtnSetting;
    private Button BtnAbout;
    private Button Btnlo;
    private DatabaseReference databaseReference;
    private DatabaseReference dataa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showUser = (TextView) findViewById(R.id.boom);
        total = (TextView) findViewById(R.id.idTotal);
        BtnProfile = (Button) findViewById(R.id.mnProfile);
        BtnProses = (Button) findViewById(R.id.mnProgres);
        BtnHadiah = (Button) findViewById(R.id.mnHadiah);
        BtnSetting = (Button) findViewById(R.id.mnSetting);
        BtnAbout = (Button) findViewById(R.id.mnAbout);
        Btnlo = (Button) findViewById(R.id.mnExit);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, login.class));
        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        showUser.setText(user.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dataa = FirebaseDatabase.getInstance().getReference().child("email"+user.getUid());

            dataa.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("nama").getValue().toString() == null){

                    }else {
                        showUser.setText(dataSnapshot.child("nama").getValue().toString());
                        total.setText("Rp "+dataSnapshot.child("tot").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        BtnProfile.setOnClickListener(this);
        BtnProses.setOnClickListener(this);
        BtnHadiah.setOnClickListener(this);
        BtnSetting.setOnClickListener(this);
        BtnAbout.setOnClickListener(this);
        Btnlo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == BtnProfile){
            startActivity(new Intent(getApplicationContext(),Myprofil.class));

        }

        if(view == BtnProses){
           startActivity(new Intent(getApplicationContext(),Myproses.class));

        }
        if(view == BtnHadiah){
            startActivity(new Intent(getApplicationContext(),Myhadiah.class));

        }
        if(view == BtnSetting){
            startActivity(new Intent(getApplicationContext(),Mysetting.class));

        }
        if(view == BtnAbout){
           startActivity(new Intent(getApplicationContext(),Myabout.class));

        }
        if(view == Btnlo){
                finish();
            System.exit(0);
            }

    }
}
