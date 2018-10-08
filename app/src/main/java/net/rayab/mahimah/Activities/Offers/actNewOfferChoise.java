package net.rayab.mahimah.Activities.Offers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;

import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.SpinAdapter;
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

public class actNewOfferChoise extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Context context = this;
    MySQLi SQL = new MySQLi(context);
    WebApi wAPI = new WebApi();
    List<Datas.Services> lService = new ArrayList<>();
    List<Datas.SubServices> lSubService0 = new ArrayList<>();
    List<Datas.SubServices> lSubService1 = new ArrayList<>();
    List<Datas.SubServices> lSubService2 = new ArrayList<>();
    List<Datas.SubServicesDiscount> lSubServicesDiscount = new ArrayList<>();
    AlertDialog.Builder builder;
    SpinAdapter adapter;

    CoordinatorLayout coordiantLayout;
    ImageView btnCancell, btnAccept;
    TextView lblOfferTime, lblService1, lblService2, lblOfferPrice;
    EditText txtOfferPrice;
    Spinner spinService1, spinService2;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;
    HorizontalListView lstCount;

    int positioner = 0;
    public static String mID = "empty";
    public static int Type = 1;
    public static int Service1 = 0, Service2 = 0;
    public static String Amount;
    boolean isManualChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_offer_choise);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(context);
        appExit.act1 = context;

        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        btnAccept = findViewById(R.id.btnAccept);
        btnCancell = findViewById(R.id.btnCancell);

        lblOfferTime = findViewById(R.id.lblOfferTime);
        lblService1 = findViewById(R.id.lblService1);
        lblService2 = findViewById(R.id.lblService2);
        lblOfferPrice = findViewById(R.id.lblSkillList);

        txtOfferPrice = findViewById(R.id.txtSkillPrice);

        spinService1 = findViewById(R.id.spinService1);
        spinService2 = findViewById(R.id.spinService2);

        btnMenuIcon = findViewById(R.id.btnMenuIcon);

        lstCount = findViewById(R.id.lstCount);

        coordiantLayout = findViewById(R.id.coordiantLayout);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblOfferTime.setTypeface(tf);lblService1.setTypeface(tf);lblService2.setTypeface(tf);lblOfferPrice.setTypeface(tf);txtOfferPrice.setTypeface(tf);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Confirm");
//                builder.setMessage("Are you sure?");
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Done();
//
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // Do nothing
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
                Done();
            }
        });

        wAPI.setOnIncomingResult(new WebApi.onResponseResult2() {
            @Override
            public void onResponseResults2(String Result) {
//                String Query = "DELETE FROM TB_OfferChoise WHERE id='" + mID + "'";
//                String Ex = SQL.Execute(Query);
//                initialize(positioner);
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

            }
        });

        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinService1.setSelection(0);
                spinService2.setSelection(0);
                txtOfferPrice.setText("");
                mID = "empty";
                positioner = 0;
            }
        });

        btnMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        txtOfferPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }
                try {
                    String value = txtOfferPrice.getText().toString().replace(",", "");
                    String reverseValue = new StringBuilder(value).reverse().toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    isManualChange = true;
                    txtOfferPrice.setText(finalValue.reverse());
                    txtOfferPrice.setSelection(finalValue.length());
                } catch (Exception e) {
                    // Do nothing since not a number
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initialize();
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
        }
    }
    private void Done(){
        String s1 = spinService1.getSelectedItem().toString();
        String s2 = spinService2.getSelectedItem().toString();

        String Query = "SELECT * FROM TB_SubServices WHERE title='" + s1 + "'";
        lSubService1 = SQL.Select(Query, Datas.SubServices.class, context);
        Query = "SELECT * FROM TB_SubServices WHERE title='" + s2 + "'";
        lSubService2 = SQL.Select(Query, Datas.SubServices.class, context);
        Query = "SELECT * FROM TB_OfferChoise WHERE id='" + SQL.LastId("TB_OfferChoise") + "'";
        lSubServicesDiscount = SQL.Select(Query, Datas.SubServices.class, context);
        if(Type == 1) {
            int nId = SQL.LastId("TB_OfferChoise");
            if (nId > 1999) {
                mID = Integer.toString(nId + 1);
            } else {
                mID = Integer.toString(2000 + nId);
            }
//            Query = "INSERT INTO TB_OfferChoise (id, subservice_id_1, subservice_id_2, discount_amount) VALUES ('" +
//                    mID.trim() + "', '" + Integer.toString(lSubService1.get(0).id()).trim() + "', '" + Integer.toString(lSubService2.get(0).id()).trim() +
//                    "', '" + txtSkillPrice.getText().toString() + "')";
            Query = "INSERT INTO TB_OfferChoise (id, subservice_id_1, subservice_id_2, discount_amount) VALUES ('" +
                    mID.trim() + "', '" + adapter.getItemString(spinService1.getSelectedItemPosition(), "id") +
                    "', '" + adapter.getItemString(spinService2.getSelectedItemPosition(), "id") +
                    "', '" + txtOfferPrice.getText().toString() + "')";

            SQL.Execute(Query);

            Service1 = 0;
            Service2 = 0;
            Amount = "empty";
            Type = 1;
        }else{
            Query = "UPDATE TB_OfferChoise SET subservice_id_1='" + Integer.toString(lSubService1.get(0).id()).trim() +
                    "', subservice_id_2='" + Integer.toString(lSubService2.get(0).id()).trim() +
                    "', discount_amount='" + txtOfferPrice.getText().toString().trim() + "' WHERE id='" + mID.trim() + "'";

            SQL.Execute(Query);

            Service1 = 0;
            Service2 = 0;
            Amount = "empty";
            Type = 1;
        }

        Map<String, String> Data = new HashMap<>();
        Data.put("services_id", "empty");
        Data.put("services_title", "empty");
        Data.put("services_url", "empty");
        Data.put("services_adress", "empty");
        Data.put("services_hidden", "empty");
        Data.put("services_position", "empty");
        Data.put("services_type", "empty");

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

        Data.put("subservicesdicsount_id2", mID);
        Data.put("subservicesdicsount_subservice_id_12", adapter.getItemString(spinService1.getSelectedItemPosition(), "id"));
        Data.put("subservicesdicsount_subservice_id_22", adapter.getItemString(spinService2.getSelectedItemPosition(), "id"));
        Data.put("subservicesdicsount_discount_amount2", txtOfferPrice.getText().toString().replace(",", ""));

        wAPI.API("https://mahimah.com/app/Setter.php", Data);

        initialize();

        txtOfferPrice.setText("");
        spinService1.setSelection(0);
        spinService2.setSelection(0);
        mID = "empty";
        finish();
    }
    void initialize(){
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        List<Datas.SubServices> lSub = new ArrayList<>();
        for (int i = 0; i < lService.size(); i++) {
            Query = "SELECT * FROM TB_SubServices WHERE service_id='" + lService.get(i).id() + "'";
            lSubService0 = SQL.Select(Query, Datas.SubServices.class, context);
            Datas.SubServices spinData = new Datas.SubServices();
            spinData.id(lService.get(i).id());
            spinData.title(lService.get(i).title() + "     ____________________     ");
            lSub.add(spinData);
            for (int j = 0; j < lSubService0.size(); j++) {
                Datas.SubServices spinData2 = lSubService0.get(j);
                lSub.add(spinData2);
            }
        }
        adapter = new SpinAdapter(context, android.R.layout.simple_spinner_item, lSub, "title");
        spinService1.setAdapter(adapter);
        spinService2.setAdapter(adapter);

        if(Type == 1) {
            spinService1.setSelection(0);
            spinService2.setSelection(0);
            txtOfferPrice.setText("");
            mID = "empty";

            btnAccept.setImageResource(0);
            btnAccept.setImageResource(R.drawable.new_services);
        }else{
            String s1 = Integer.toString(Service1);
            String s2 = Integer.toString(Service2);
            Query = "SELECT * FROM TB_SubServices WHERE id='" + s1 + "'";
            lSubService1 = SQL.Select(Query, Datas.SubServices.class, context);
            Query = "SELECT * FROM TB_SubServices WHERE id='" + s2 + "'";
            lSubService2 = SQL.Select(Query, Datas.SubServices.class, context);

            int a1 = 0, a2 = 0;
            for (int i=0;i<spinService1.getCount();i++){
                if (spinService1.getItemAtPosition(i).toString().equalsIgnoreCase(lSubService1.get(0).title())){
                    a1 = i;
                    break;
                }
            }
            for (int i=0;i<spinService2.getCount();i++){
                if (spinService2.getItemAtPosition(i).toString().equalsIgnoreCase(lSubService2.get(0).title())){
                    a2 = i;
                    break;
                }
            }
            spinService1.setSelection(a1);
            spinService2.setSelection(a2);
            txtOfferPrice.setText(Amount);

            btnAccept.setImageResource(0);
            btnAccept.setImageResource(R.drawable.update2);
        }
    }
    void Undoo(){
        String Query = "DELETE FROM TB_OfferChoise WHERE id='" + mID + "'";
        SQL.Execute(Query);
        Snackbar name = Snackbar.make(coordiantLayout, "تخفیف انتخاب حذف شد", Snackbar.LENGTH_LONG);
        name.show();

        initialize();
    }

}
