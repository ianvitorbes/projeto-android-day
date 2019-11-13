package com.parse.starter.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.parse.starter.Fragments.HomeFragment;
import com.parse.starter.Fragments.UsuariosFragment;
import com.parse.starter.R;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int[] icones = new int[] {R.drawable.ic_action_home, R.drawable.ic_person};
    private int tamanhoIcone;

    public TabsAdapter(FragmentManager fm, Context c ) {
        super(fm);
        this.context = c;
        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int) (36 * escala);
    }

    @Override
    //Metodo para recuperar os fragmentos
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch ( position ) {

            case 0:
                fragment =  new HomeFragment();
                break;

            case 1:
                fragment = new UsuariosFragment();
                break;

        }
        return fragment;

    }


    @Override

    //Retorna as abas de acordo com a posição
    public CharSequence getPageTitle(int position) {
        //Pegar o drawble e converter em char

        Drawable drawable = ContextCompat.getDrawable(this.context, icones[ position ]);
        drawable.setBounds(0,0,tamanhoIcone,tamanhoIcone);

        //Converter imagem em texto
        ImageSpan imageSpan = new ImageSpan( drawable );

        //Pesquisar classe para retornar o char!!!

        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;

    }

    @Override
    public int getCount() {
        return icones.length;

    }
}