package net.rayab.mahimah.Activities.Services;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Offers.actOfferChoise;
import net.rayab.mahimah.Activities.Offers.actOfferTime;
import net.rayab.mahimah.Activities.Offers.actSkill;
import net.rayab.mahimah.Activities.actMain;
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

public class actNewGroup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    MySQLi SQL = new MySQLi(context);
    WebApi wAPI = new WebApi();
    List<Datas.Services> lService = new ArrayList<>();
    List<Datas.Services> lService2 = new ArrayList<>();
    List<Datas.SubServices> lSubService = new ArrayList<>();
    AlertDialog.Builder builder;

    CoordinatorLayout coordiantLayout;
    ImageView btnCancell, btnAccept;
    TextView lblNewGroup, lblGroup;
    EditText txtGroup;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;

    String mID = "empty";
    public static int Type = 1;
    public static int uID = 0;
    public static String Titlerer = "";
    String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_group);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(context);
        appExit.act6 = context;

        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        btnAccept = findViewById(R.id.btnAccept);
        btnCancell = findViewById(R.id.btnCancell);

        lblNewGroup = findViewById(R.id.lblNewSkill);
        lblGroup = findViewById(R.id.lblSkill);

        txtGroup = findViewById(R.id.txtSkill);

        btnMenuIcon = findViewById(R.id.btnMenuIcon);

        coordiantLayout = findViewById(R.id.coordiantLayout);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblNewGroup.setTypeface(tf);lblGroup.setTypeface(tf);txtGroup.setTypeface(tf);

        if(Type == 2){
            txtGroup.setText(Titlerer);

            btnAccept.setImageResource(0);
            btnAccept.setImageResource(R.drawable.update2);
        }else{
            btnAccept.setImageResource(0);
            btnAccept.setImageResource(R.drawable.new_services);
        }

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

        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtGroup.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Done();

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return false;
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Done();
            }
        });

        wAPI.setOnIncomingResult(new WebApi.onResponseResult2(){

            @Override
            public void onResponseResults2(String Result) {

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
                //String Er = Error;
                Toast.makeText(context, Error, Toast.LENGTH_LONG).show();
            }
        });

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

    private void Done(){
        if(txtGroup.getText().toString().length() > 0) {
            if(Type == 1) {
                if (SQL.LastId("TB_Services") < 3000) {
                    mID = Integer.toString(3000 + SQL.LastId("TB_Services"));
                } else {
                    mID = Integer.toString(SQL.LastId("TB_Services") + 1);
                }
                mQuery = "INSERT INTO TB_Services (id, title, hidden, position) VALUES " +
                        "('" + mID.trim() + "', '" + txtGroup.getText().toString().trim() + "', '0', '" + mID.trim() + "')";

                //INSERT Into WebHost
                Map<String, String> Data = new HashMap<String, String>();
                Data.put("services_id", mID);
                Data.put("services_title", txtGroup.getText().toString());
                Data.put("services_url", "empty");
                Data.put("services_adress", "empty");
                Data.put("services_hidden", "0");
                Data.put("services_position", mID);
                Data.put("services_type", "0");

                Data.put("subservices_id", "empty");
                Data.put("service_id", "empty");
                Data.put("subservices_title", "empty");
                Data.put("subservices_price", "empty");
                Data.put("subservices_pricenew", "empty");
                Data.put("subservices_description", "empty");
                Data.put("subservices_hidden", "empty");
                Data.put("subservices_position", "empty");

                Data.put("subservicesdicsount_id", "empty");
                Data.put("subservicesdicsount_subservice_id_1", "empty");
                Data.put("subservicesdicsount_subservice_id_2", "empty");
                Data.put("subservicesdicsount_discount_amount", "empty");

                Data.put("subservicesdicsount_id2", "empty");
                Data.put("subservicesdicsount_subservice_id_12", "empty");
                Data.put("subservicesdicsount_subservice_id_22", "empty");
                Data.put("subservicesdicsount_discount_amount2", "empty");

                wAPI.API("https://mahimah.com/app/Setter.php", Data);

                SQL.Execute(mQuery);

                Snackbar name = Snackbar.make(coordiantLayout, "گروه وارد شده اضافه شد.", Snackbar.LENGTH_LONG);
                name.setAction("حذف", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Undoo();
                    }
                });
                name.setActionTextColor(Color.YELLOW);
                name.show();

                txtGroup.setText("");
            }else if(Type == 2){
                String Query = "SELECT * FROM TB_Services WHERE id='" + Integer.toString(uID) + "'";
                lService = SQL.Select(Query, Datas.Services.class, context);
                mQuery = "UPDATE TB_Services SET title='" + txtGroup.getText().toString().trim() + "' WHERE id='" + Integer.toString(uID).trim() + "'";

                //UPDATE Into WebHost
                Map<String, String> Data = new HashMap<String, String>();
                Data.put("services_id", Integer.toString(uID));
                Data.put("services_title", txtGroup.getText().toString());
                Data.put("services_url", "empty");
                Data.put("services_adress", "empty");
                Data.put("services_hidden", "0");
                Data.put("services_position", Integer.toString(lService.get(0).position()));
                Data.put("services_type", "0");

                Data.put("subservices_id", "empty");
                Data.put("service_id", "empty");
                Data.put("subservices_title", "empty");
                Data.put("subservices_description", "empty");
                Data.put("subservices_price", "empty");
                Data.put("subservices_pricenew", "empty");
                Data.put("subservices_hidden", "empty");
                Data.put("subservices_position", "empty");

                Data.put("subservicesdicsount_id", "empty");
                Data.put("subservicesdicsount_subservice_id_1", "empty");
                Data.put("subservicesdicsount_subservice_id_2", "empty");
                Data.put("subservicesdicsount_discount_amount", "empty");

                Data.put("subservicesdicsount_id2", "empty");
                Data.put("subservicesdicsount_subservice_id_12", "empty");
                Data.put("subservicesdicsount_subservice_id_22", "empty");
                Data.put("subservicesdicsount_discount_amount2", "empty");

                wAPI.API("https://mahimah.com/app/Updater.php", Data);

                SQL.Execute(mQuery);

                Snackbar name = Snackbar.make(coordiantLayout, "گروه وارد شده ویرایش شد", Snackbar.LENGTH_LONG);
                name.show();

                txtGroup.setText("");
                finish();
            }
        }else{
            Snackbar name = Snackbar.make(coordiantLayout, "موارد خالی هستند", Snackbar.LENGTH_LONG);
            name.show();
        }
    }
    void Undoo(){
        String Query = "DELETE FROM TB_SubServices WHERE id='" + mID + "'";
        Snackbar name = Snackbar.make(coordiantLayout, "گروه وارد شده حذف شد", Snackbar.LENGTH_LONG);
        name.show();
    }

}
