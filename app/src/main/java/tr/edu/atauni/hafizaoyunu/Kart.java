package tr.edu.atauni.hafizaoyunu;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatButton;

public class Kart extends AppCompatButton {
    Drawable arkaPlan, onPlan;
    Boolean acikMi=false;
    int resId;
    Boolean  eslesti=false;
    public Kart(Context cnt, int resimId, int kartId) {
        super(cnt);
        setId(kartId);
        resId=resimId;
        arkaPlan = cnt.getDrawable(R.drawable.kart_arkaplan);
        if(resimId == 0) {
            onPlan = cnt.getDrawable(R.drawable.kart1);
        }
        if(resimId == 2) {
            onPlan = cnt.getDrawable(R.drawable.kart2);
        }
        if(resimId == 4) {
            onPlan = cnt.getDrawable(R.drawable.kart3);
        }
        if(resimId == 6) {
            onPlan = cnt.getDrawable(R.drawable.kart4);
        }
        if(resimId == 8) {
            onPlan = cnt.getDrawable(R.drawable.kart5);
        }
        if(resimId == 10) {
            onPlan = cnt.getDrawable(R.drawable.kart6);
        }
        if(resimId == 12) {
            onPlan = cnt.getDrawable(R.drawable.kart7);
        }
        if(resimId == 14) {
            onPlan = cnt.getDrawable(R.drawable.kart8);
        }
        setBackground(arkaPlan);
    }
    public void dondur() {
        if(eslesti) {
            return;
        }
        if(acikMi) {
            setBackground(arkaPlan);
            acikMi=false;
        } else {
            setBackground(onPlan);
            acikMi=true;
        }
    }
}
