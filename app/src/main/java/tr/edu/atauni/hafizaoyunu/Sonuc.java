package tr.edu.atauni.hafizaoyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Sonuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);
        Intent intent = getIntent();
        String isim = intent.getStringExtra("ism");
        long gecenSure = intent.getLongExtra("sure", 0);

        int saniye = (int)(gecenSure / 1000);
        int dakika = saniye / 60;
        saniye = saniye % 60;

        String zamanStr = String.format("%02d:%02d", dakika, saniye);

        TextView sonucText = findViewById(R.id.sonucText);
        sonucText.setText("Tebrikler " + isim + "! Süreniz: " + zamanStr);

        SharedPreferences prefs = getSharedPreferences("hafizaoyunu", MODE_PRIVATE);
        long enIyiSure = prefs.getLong("enIyiSure", Long.MAX_VALUE);

        if (gecenSure < enIyiSure) {
            Toast.makeText(this, "Yeni Rekor!", Toast.LENGTH_LONG).show();
            prefs.edit().putLong("enIyiSure", gecenSure).apply();
        }

        Button tekrarBtn = findViewById(R.id.tekrarBtn);
        tekrarBtn.setOnClickListener(v -> {
            Intent yeni = new Intent(getApplicationContext(), MainActivity.class);
            yeni.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(yeni);
        });
    
        Intent i = getIntent();
        String sonuc;
        sonuc = i.getStringExtra("kazandiMi");
        TextView sonucTv = findViewById(R.id.sonucText);

        if(sonuc.equals("evet")) {
            sonucTv.setText("Tebrikler Kazandınız");
        } else {
            sonucTv.setText("Üzgünüm Kazanamadınız");
        }

    }
}
