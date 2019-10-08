package com.example.animaciones_graficos_y_multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.animaciones_graficos_y_multimedia.fragments.animacionesFragment;
import com.example.animaciones_graficos_y_multimedia.fragments.graficosFragment;
import com.example.animaciones_graficos_y_multimedia.fragments.imagenFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private static final int REQUEST_CODE=1;
    private static final String [] PERMISOS={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    ActionBarDrawerToggle toogle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int leer = ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        int leer1 = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int leer2 = ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        if(leer== PackageManager.PERMISSION_DENIED || leer1 ==  PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this,PERMISOS,REQUEST_CODE);
        }

        setContentView(R.layout.activity_main);






        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null){
            navigationView.setNavigationItemSelectedListener(this);
        }




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);





        if (savedInstanceState == null){
            cambiarFragment(new animacionesFragment(), "animaciones_fragment");
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()){

            case R.id.nav_inicio:
                cambiarFragment(new animacionesFragment(), "animaciones_fragment");
                break;
            case R.id.nav_graficos:
                cambiarFragment(new graficosFragment(), "graficos_fragment");
                break;
            case R.id.nav_video:
                cambiarFragment(new videoFragment(), "videos_fragment");
                break;
            case R.id.nav_audio:
                cambiarFragment(new audioFragment(), "audio_fragment");
                break;
            case R.id.nav_imagen:
                cambiarFragment(new imagenFragment(), "imagen_fragment");
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    public void cambiarFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment,tag)
                .commit();
    }

    public void cambiarNavigation(int id){
        if (navigationView != null){
            navigationView.setCheckedItem(id);
        }
    }

    public void updateView(String title, String subtitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }
        setSupportActionBar(toolbar);

        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

}
