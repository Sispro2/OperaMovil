package com.abarrotescasavargas.operamovil.Main.FunGenerales.Preloaders;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.abarrotescasavargas.operamovil.R;


public class EnviandoDialog  extends Dialog {
    public EnviandoDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view= LayoutInflater.from(context).inflate(R.layout.enviando_layout, null);
        setContentView(view);
    }
}
