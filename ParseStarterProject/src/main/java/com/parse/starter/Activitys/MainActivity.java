/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.Adapter.TabsAdapter;
import com.parse.starter.Fragments.HomeFragment;
import com.parse.starter.R;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

  private Toolbar toolbarPrincipal;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      //Configuração da Toolbar

      toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
      setSupportActionBar( toolbarPrincipal );

      //Abas
      slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_main);
      viewPager = (ViewPager) findViewById(R.id.view_pager_main);

      //Config do adapterr

      TabsAdapter tabsAdapter = new TabsAdapter( getSupportFragmentManager(), this );
      viewPager.setAdapter( tabsAdapter );
      slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
      slidingTabLayout.setDistributeEvenly(true);
      slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.Branquin));
      slidingTabLayout.setViewPager( viewPager );




  }

    @Override

    //criação de menu da toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }
    //opções da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sair:
                deslogarUsuario();
                return true;

            case R.id.action_configuracoes:
                return true;
            case R.id.action_compartilhar:
                compartilharFoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void compartilharFoto(){

        Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Teste de retorno de dados
        if( requestCode==1 && resultCode == RESULT_OK && data != null ){


            //Recuperar local dos dados

            Uri localImagemSelecionada = data.getData();

            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap( getContentResolver(), localImagemSelecionada );

                //imagem em png
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                //Array para converter o stream em array de bytes
                byte[] byteArray = stream.toByteArray();


                //Arquivo do mesmo formato do parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeFoto = dateFormat.format(new Date() );
                ParseFile arquivoParse = new ParseFile( nomeFoto + "foto.png", byteArray );

                //Obj pra salvar no parse

                ParseObject parseObject = new ParseObject("Fotos");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername() );
                parseObject.put("foto", arquivoParse );

                //====//

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if( e==null ){
                            Toast.makeText(getApplicationContext(), "A imagem foi postada!", Toast.LENGTH_SHORT).show();

                            //Att do feed
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizaPostagens();

                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao postar, tente novamente", Toast.LENGTH_SHORT).show();

                        }



                    }
                });



            } catch (IOException e) {
                e.printStackTrace();

            }

        }


    }

    private void deslogarUsuario() {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }
}
