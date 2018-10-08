package net.rayab.mahimah.Activities.Offers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.adapOfferChoise;
import net.rayab.mahimah.Adapter.adapSkill;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;
import net.rayab.mahimah.appExit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class actSkill extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context = this;
    adapSkill adapter;
    MySQLi SQL = new MySQLi(context);
    List<Datas.Skills> lDiscount = new ArrayList<>();

    ImageView btnSetting, btnAccept;
    TextView lblSkill, lblSkillCount, lblNewSkill, lblSkill2;
    EditText txtSkill;
    RecyclerView lstMain;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appExit.act3 = context;

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnSetting = findViewById(R.id.btnSetting);
        btnAccept = findViewById(R.id.btnAccept);
        lblSkill = findViewById(R.id.lblSkill);
        lblSkillCount = findViewById(R.id.lblSkillCount);
        lblNewSkill = findViewById(R.id.lblNewSkill);
        lblSkill2 = findViewById(R.id.lblSkill2);
        txtSkill = findViewById(R.id.txtSkill);
        lstMain = findViewById(R.id.lstMain);
        btnMenuIcon = findViewById(R.id.btnMenuIcon);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblSkill.setTypeface(tf);
        lblSkillCount.setTypeface(tf);
        lblNewSkill.setTypeface(tf);
        lblSkill2.setTypeface(tf);
        txtSkill.setTypeface(tf);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        btnMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actSkillNew.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initialize();
        lblSkillCount.setText(Integer.toString(lDiscount.size()));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            drawer.closeDrawer(GravityCompat.END);
        }else if(id == R.id.nav_dataTransporter){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_datatransporter);

            final TextView lbl = dialog.findViewById(R.id.lblHavij);
            final EditText txt = dialog.findViewById(R.id.txtHavij);
            final Button btnY = dialog.findViewById(R.id.btnYes);
            final Button btnN = dialog.findViewById(R.id.btnNo);

            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
            lbl.setTypeface(tf);txt.setTypeface(tf);
            btnY.setTypeface(tf);btnN.setTypeface(tf);

            btnY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txt.getText().toString().equalsIgnoreCase("Havij")){
                        Map<String, String> keyVal = new HashMap<>();
                        keyVal.put("key", "Havij");
                        WebApi wAPI = new WebApi();
                        wAPI.API("https://mahimah.com/app/DataTransformer.php", keyVal);

                        wAPI.setOnIncomingResult(new WebApi.onResponseResult2() {
                            @Override
                            public void onResponseResults2(String Result) {
                                String Query = "";
                                Query = "DELETE FROM TB_Services";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_SubServices";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_SubServicesDiscount";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferTime";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferChoise";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferPrice";
                                SQL.Execute(Query);

                                Toast.makeText(context, "Data is Reset, Please reOpen Application", Toast.LENGTH_LONG).show();
                                appExit.ExitApp();
                            }
                        }, new WebApi.onResponseResult() {
                            @Override
                            public void onResponseResults(JSONArray Result) {

                            }
                        }, new WebApi.onResponseObjectResult() {
                            @Override
                            public void onResponseObjectResults(JSONObject Result) {

                            }
                        }, new WebApi.onResponseResultError() {
                            @Override
                            public void onResponseResultErrors(String Error) {
                                Toast.makeText(context, "Error On reNew Data, Contact Us Only :D", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(context, "Please Enter 'Havij' in Box", Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            finish();
        }
    }
    @SuppressLint("SetTextI18n")
    void initialize(){
        lDiscount = SQL.Select(Datas.getSkill, Datas.Skills.class, context);

        lstMain.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
        adapter = new adapSkill(lDiscount, context);
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for(int i=0; i<lDiscount.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }

        lblSkillCount.setText(Integer.toString(lDiscount.size()) + " تخفیف");
    }
}
