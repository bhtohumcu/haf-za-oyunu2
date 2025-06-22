package tr.edu.atauni.hafizaoyunu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class oyunEkrani extends AppCompatActivity {

    int sonkart = 0;
    boolean bekle = false;
    int kullanimHakki = 10;
    int dogruTahmin = 0;
    long baslangicZamani = 0;
    long gecenSure = 0;
    boolean oyunDurdu = false;

    android.os.Handler zamanlayici = new android.os.Handler();
    Runnable zamanGuncelleme;

    TextView timerText;
    TextView matchCounter;
    Button pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);

        baslangicZamani = SystemClock.elapsedRealtime();
        timerText = findViewById(R.id.timerText);
        matchCounter = findViewById(R.id.matchCounter);
        pauseButton = findViewById(R.id.pauseBtn);

        pauseButton.setOnClickListener(v -> {
            if (oyunDurdu) {
                baslangicZamani = SystemClock.elapsedRealtime() - gecenSure;
                zamanlayici.postDelayed(zamanGuncelleme, 0);
                pauseButton.setText("Duraklat");
                oyunDurdu = false;
            } else {
                zamanlayici.removeCallbacks(zamanGuncelleme);
                pauseButton.setText("Devam Et");
                oyunDurdu = true;
                gecenSure = SystemClock.elapsedRealtime() - baslangicZamani;
            }
        });

        zamanGuncelleme = new Runnable() {
            @Override
            public void run() {
                long gecen = SystemClock.elapsedRealtime() - baslangicZamani;
                int saniye = (int) (gecen / 1000);
                int dakika = saniye / 60;
                saniye = saniye % 60;
                timerText.setText(String.format("%02d:%02d", dakika, saniye));
                zamanlayici.postDelayed(this, 1000);
            }
        };
        zamanlayici.postDelayed(zamanGuncelleme, 0);

        Intent intnt = getIntent();
        String isim = intnt.getStringExtra("ism");
        TextView karsilama = findViewById(R.id.karsilamaTv);
        karsilama.setText(isim + " Hoş geldiniz!");

        GridLayout gd = findViewById(R.id.grdLyt);
        int satir = 2;
        int sutun = 2;
        final int adet = satir * sutun;
        gd.setColumnCount(satir);
        gd.setRowCount(sutun);

        Kart[] kartlar = new Kart[adet];
        for (int i = 0; i < adet; i++) {
            if (i % 2 == 0) {
                kartlar[i] = new Kart(getApplicationContext(), i, i + 10);
            } else {
                kartlar[i] = new Kart(getApplicationContext(), i - 1, i + 10);
            }
            kartlar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kullanimHakki < 1) {
                        Intent i = new Intent(getApplicationContext(), Sonuc.class);
                        i.putExtra("kazandiMi", "hayır");
                        startActivity(i);
                        return;
                    }

                    final Kart suankiKart = (Kart) v;
                    if (suankiKart.acikMi || bekle) return;

                    suankiKart.dondur();

                    if (sonkart > 0) {
                        final Kart oncekiKart = findViewById(sonkart);
                        if (oncekiKart.resId == suankiKart.resId) {
                            dogruTahmin += 2;
                            oncekiKart.eslesti = true;
                            suankiKart.eslesti = true;
                            if (dogruTahmin == adet) {
                                Intent i = new Intent(getApplicationContext(), Sonuc.class);
                                i.putExtra("kazandiMi", "evet");
                                startActivity(i);
                                return;
                            }
                        } else {
                            bekle = true;
                            kullanimHakki--;
                            Handler h1 = new Handler();
                            h1.postDelayed(() -> {
                                oncekiKart.dondur();
                                suankiKart.dondur();
                                bekle = false;
                            }, 2000);
                        }
                        sonkart = 0;
                    } else {
                        sonkart = suankiKart.getId();
                    }
                }
            });
        }

        Collections.shuffle(Arrays.asList(kartlar));
        for (Kart k : kartlar) {
            gd.addView(k);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Oyunu bırakmak istiyor musun?")
                .setMessage("Tüm ilerlemen silinecek.")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Hayır", null)
                .show();
    }
}
