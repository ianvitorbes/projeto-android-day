package com.parse.starter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends ArrayAdapter<ParseUser>{

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(Context c,  ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //verificaçao de view

        if ( view==null ){

            //Inicializa a montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            //Cria a view xml em view
            view = inflater.inflate(R.layout.lista_usuarios, parent, false);

        }

        //Recuperar elemento pra exibir

        TextView username = (TextView) view.findViewById(R.id.text_username);

        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername() );

        return view;

    }
}