package poly.haule.autoselling;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import poly.haule.autoselling.Fragment.FragmentAddNewProduct;
import poly.haule.autoselling.Fragment.FragmentLogin;
import poly.haule.autoselling.Fragment.FragmentManage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Fragment fragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set info user to header
        ArcNavigationView arcNavigationView = (ArcNavigationView)findViewById(R.id.nav_view);
        View header = arcNavigationView.getHeaderView(0);

        TextView name = (TextView)header.findViewById(R.id.tv_user_name);
        TextView email = (TextView) header.findViewById(R.id.tv_user_email);
        CircleImageView image = (CircleImageView)header.findViewById(R.id.imageView_user_avatar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            Picasso.with(this).load(user.getPhotoUrl()).into(image);
        }

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fragment default..

        fragment = new FragmentManage();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ArcNavigationView arcNavigationView = (ArcNavigationView)findViewById(R.id.nav_view);
        menu = arcNavigationView.getMenu();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            menu.getItem(4).setEnabled(true);
        }else{
            menu.getItem(5).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //change fragment


        switch (id){
            case R.id.nav_login:
                fragment = new FragmentLogin();
                break;
            case R.id.nav_favorite:
                fragment = new FragmentAddNewProduct();
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_manage:
                fragment = new FragmentManage();
                break;
            case R.id.nav_log_out:

                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                fragment = new FragmentManage();
                Toast.makeText(this, "Signed out!", Toast.LENGTH_LONG).show();

                break;
            case R.id.nav_home:
                break;
            default:
                fragment = new FragmentManage();
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}



