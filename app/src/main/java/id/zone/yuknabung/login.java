package id.zone.yuknabung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button lgBtn;
    private EditText lgEmail;
    private EditText lgPass;
    private TextView lgText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        lgBtn = (Button) findViewById(R.id.lgBtn);
        lgEmail = (EditText) findViewById(R.id.lgEmail);
        lgPass = (EditText) findViewById(R.id.lgPass);
        lgText = (TextView) findViewById(R.id.textrg);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();


            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }

        lgBtn.setOnClickListener(this);
        lgText.setOnClickListener(this);

    }

    private void  userLogin(){
        String email = lgEmail.getText().toString().trim();
        String password = lgPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email kosong
            Toast.makeText(this, "Masukan email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Masukan password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Mencoba Login..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login Berhasil...", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        } else {
                            Toast.makeText(login.this, "Login Gagal...", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == lgBtn){
            userLogin();
        }

        if(view == lgText){
            finish();
            startActivity(new Intent(getApplicationContext(),register.class));

        }
    }
}
