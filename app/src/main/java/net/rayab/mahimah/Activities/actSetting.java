package net.rayab.mahimah.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.rayab.mahimah.Activities.Offers.actOfferChoise;
import net.rayab.mahimah.Activities.Offers.actOfferTime;
import net.rayab.mahimah.Activities.Offers.actSkill;
import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.R;

public class actSetting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context = this;

    TextView lblSetting, lblTime, lblChoise,
            lblService1, lblService2, lblOfferPrice;
    EditText txtOfferPrice;
    FloatingActionButton btnAccept;
    RelativeLayout btnTime, btnChoise;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        lblSetting = findViewById(R.id.lblSetting);
        lblTime = findViewById(R.id.lblTime);
        lblChoise = findViewById(R.id.lblChoise);
        lblService1 = findViewById(R.id.lblService1);
        lblService2 = findViewById(R.id.lblService2);
        lblOfferPrice = findViewById(R.id.lblSkillList);

        txtOfferPrice = findViewById(R.id.txtSkillPrice);

        btnAccept = findViewById(R.id.btnAccept);

        btnTime = findViewById(R.id.btnTime);
        btnChoise = findViewById(R.id.btnChoise);

        btnMenuIcon = findViewById(R.id.btnMenuIcon);


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actOfferTime.class);
                Pair<View, String> p1 = Pair.create((View)lblService1, "lblService1");
                Pair<View, String> p2 = Pair.create((View)lblService2, "lblService2");
                Pair<View, String> p3 = Pair.create((View)lblOfferPrice, "lblOffer");
                Pair<View, String> p4 = Pair.create((View)txtOfferPrice, "txtOffer");
                Pair<View, String> p5 = Pair.create((View)btnAccept, "btn");
                Pair<View, String> p6 = Pair.create((View)lblTime, "lblTime");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4, p5, p6);
                startActivity(intent, options.toBundle());
                finish();
            }
        });

        btnChoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actOfferChoise.class);
                Pair<View, String> p1 = Pair.create((View)lblService1, "lblService1");
                Pair<View, String> p2 = Pair.create((View)lblService2, "lblService2");
                Pair<View, String> p3 = Pair.create((View)lblOfferPrice, "lblOffer");
                Pair<View, String> p4 = Pair.create((View)txtOfferPrice, "txtOffer");
                Pair<View, String> p5 = Pair.create((View)btnAccept, "btn");
                Pair<View, String> p6 = Pair.create((View)lblChoise, "lblChoise");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4, p5, p6);
                startActivity(intent, options.toBundle());
                finish();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent = new Intent(context, actMain.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_group) {
            Intent intent = new Intent(context, actGroups.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_service) {
            Intent intent = new Intent(context, actServices.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_offer_choise) {
            Intent intent = new Intent(context, actSkill.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}
