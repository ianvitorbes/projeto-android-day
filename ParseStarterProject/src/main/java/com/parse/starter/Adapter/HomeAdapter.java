package com.parse.starter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> postagens;

    public HomeAdapter(Context c, ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        //verificaçao de view

        if ( view==null ){

            //Inicializa a montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            //Cria a view xml em view
            view = inflater.inflate(R.layout.lista_postagem, parent, false);

        }
        if (postagens.size() > 0){
            //Recupera os comp da tela
            ImageView imagemPostagem = (ImageView) view.findViewById(R.id.image_lista_postagem);

            ParseObject parseObject = postagens.get( position );
            //parseObject.getParseFile("foto");
            Picasso.with( context ).load( parseObject.getParseFile("foto").getUrl())
                    .fit()
                    .into(imagemPostagem);




        }


        return view;
    }
}