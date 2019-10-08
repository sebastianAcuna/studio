package com.example.persistencia_en_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.persistencia_en_android.fragments.cuentaFragment;
import com.example.persistencia_en_android.fragments.loginFragment;
import com.example.persistencia_en_android.fragments.parqueosFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBarDrawerToggle toogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null){
            navigationView.setNavigationItemSelectedListener(this);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (savedInstanceState == null){
            cambiarFragment(new loginFragment(), "login_fragment");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_inicio:
                cambiarFragment(new parqueosFragment(), "parqueos_fragment");
                break;
            case R.id.nav_graficos:
                cambiarFragment(new cuentaFragment(), "cuenta_fragment");
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



    public void setDrawerEnable(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

        if (drawerLayout!=null) drawerLayout.setDrawerLockMode(lockMode);
        if (toogle != null) toogle.setDrawerIndicatorEnabled(enabled);
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
