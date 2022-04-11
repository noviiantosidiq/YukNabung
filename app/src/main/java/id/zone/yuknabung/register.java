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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{
    private Button rgBtn;
    private EditText edEmail;
    private  EditText edPass;
    private TextView edLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        rgBtn = (Button) findViewById(R.id.rgBtn);
        edEmail = (EditText) findViewById(R.id.rgEmail);
        edPass = (EditText) findViewById(R.id.rgPass);
        edLogin = (TextView) findViewById(R.id.textlg);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rgBtn.setOnClickListener(this);
        edLogin.setOnClickListener(this);
    }

    private void registerUser(){
        String email = edEmail.getText().toString().trim();
        String password = edPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email kosong
            Toast.makeText(this, "Masukan email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Masukan password", Toast.LENGTH_SHORT).show();
            return;
        }

            progressDialog.setMessage("Mencoba Mendaftarkan user...");
            progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String kepala = "email"+user.getUid();
                                String email = user.getEmail();
                                String nama = "";
                                String nohp = "";
                                String password = "";
                                int tot = 0;
                                infoUser userinfo = new infoUser(email,nama,nohp,password,tot);


                                databaseReference.child(kepala).setValue(userinfo);
                                Toast.makeText(register.this, "Registrasi Berhasil...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(register.this, "Registrasi gagal silahkan coba lagi...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

        }


    @Override
    public void onClick(View view) {
            if(view == rgBtn){
                registerUser();
            }

            if(view == edLogin){
                finish();
                    startActivity(new Intent(this, login.class));

            }
    }
}
