package tr.edu.atauni.hafizaoyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button basla = findViewById(R.id.baslaBtn);
        
        final EditText edt = findViewById(R.id.isimText);
        final android.widget.RadioGroup difficultyGroup = findViewById(R.id.difficultyGroup);

        basla.setOnClickListener(new View.OnClickListener() {
    
            @Override
            public void onClick(View v) {
                EditText edt = findViewById(R.id.isimText);
                
                String isim = edt.getText().toString();
                if (isim.isEmpty()) {
                    android.widget.Toast.makeText(getApplicationContext(), "İsim boş bırakılamaz!", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedId = difficultyGroup.getCheckedRadioButtonId();
                String zorluk = "medium"; // default
                if (selectedId == R.id.easy) zorluk = "easy";
                else if (selectedId == R.id.hard) zorluk = "hard";
    
                Intent i = new Intent(getApplicationContext(),oyunEkrani.class);
                i.putExtra("ism",isim);
                i.putExtra("zorluk", zorluk);
                startActivity(i);
            }
        });
    }
}
