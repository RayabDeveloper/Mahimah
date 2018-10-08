package net.rayab.mahimah.Activities.Services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.rayab.mahimah.Activities.Offers.actOfferChoise;
import net.rayab.mahimah.Activities.Offers.actOfferTime;
import net.rayab.mahimah.Activities.Offers.actSkill;
import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.Customers.OnCustomerListChangedListenerGroup;
import net.rayab.mahimah.Adapter.Helper.OnStartDragListener;
import net.rayab.mahimah.Adapter.adapGroup;
import net.rayab.mahimah.Adapter.test3.SwipeAndDragHelper;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.Sort;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;
import net.rayab.mahimah.appExit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class actGroups extends AppCompatActivity implements OnCustomerListChangedListenerGroup,
        OnStartDragListener, NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    static Context contexti;
    adapGroup adapter;
    MySQLi SQL = new MySQLi(context);
    static MySQLi SQLi;
    WebApi wAPI = new WebApi();
    List<Datas.Services> lService = new ArrayList<>();
    List<Datas.SubServices> lSubService = new ArrayList<>();

    private ItemTouchHelper mItemTouchHelper;

    ImageView btnSetting, btnAccept;
    static TextView lblGroup, lblGroupCount, lblNewGroup, lblGroup2;
    Switch switche;
    EditText txtGroup;
    static RecyclerView lstMain;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;

    public static boolean isLuck = false;
    static boolean onSender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_groups);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appExit.act5 = context;

        switche = findViewById(R.id.switche);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnSetting = findViewById(R.id.btnSetting);
        btnAccept = findViewById(R.id.btnAccept);
        lblGroup = findViewById(R.id.lblSkill);
        lblGroupCount = findViewById(R.id.lblSkillCount);
        lblNewGroup = findViewById(R.id.lblNewSkill);
        lblGroup2 = findViewById(R.id.lblSkill2);
        txtGroup = findViewById(R.id.txtSkill);
        lstMain = findViewById(R.id.lstMain);
        btnMenuIcon = findViewById(R.id.btnMenuIcon);

        lstMain.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblGroup.setTypeface(tf);lblGroupCount.setTypeface(tf);lblNewGroup.setTypeface(tf);lblGroup2.setTypeface(tf);txtGroup.setTypeface(tf);

        contexti = context;
        SQLi = new MySQLi(contexti);

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
        switche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switche.isChecked())
                    isLuck = true;
                else
                    isLuck = false;
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actNewGroup.Type = 1;

                Intent intent = new Intent(context, actNewGroup.class);
                Pair<View, String> p1 = Pair.create((View)lblNewGroup, "lblNewGroup");
                Pair<View, String> p2 = Pair.create((View)lblGroup2, "lblGroup");
                Pair<View, String> p3 = Pair.create((View)btnAccept, "btnAccept");
                Pair<View, String> p4 = Pair.create((View)txtGroup, "txtGroup");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4);
                startActivity(intent, options.toBundle());
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        isLuck = false;
        initialize(context);
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
            drawer.closeDrawer(GravityCompat.END);
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
    public void onNoteListChanged(List<Datas.Services> customers, int Start, int End) {
//        String Query = "";
//        lService = new ArrayList<>();
//        Query = "SELECT * FROM TB_Services ORDER BY position";
//        lService = SQL.Select(Query, Datas.Services.class, context);
//        if(Start > End){//Payin be Bala
//            Query = "UPDATE TB_Services SET position='" + lService.get(End).position() +
//                    "' WHERE id='" + lService.get(Start).id() + "'";
//            SQL.Execute(Query);
//
//            Sender(Integer.toString(lService.get(Start).id()), Integer.toString(lService.get(End).position()), "Service");
//
//            for(int i=End; i<Start; i++){
//                Query = "UPDATE TB_Services SET position='" + (lService.get(i).position() + 1) +
//                        "' WHERE id='" + lService.get(i).id() + "'";
//                SQL.Execute(Query);
//
//                Sender(Integer.toString(lService.get(i).id()), Integer.toString((lService.get(i).position() + 1)), "Service");
//            }
//        }else if(Start < End){//Bala Be Payin
//            Query = "UPDATE TB_Services SET position='" + lService.get(End).position() +
//                    "' WHERE id='" + lService.get(Start).id() + "'";
//            SQL.Execute(Query);
//
//            Sender(Integer.toString(lService.get(Start).id()), Integer.toString(lService.get(End).position()), "Service");
//
//            for(int i=End; i>Start; i--){
//                Query = "UPDATE TB_Services SET position='" + (lService.get(i).position() - 1) +
//                        "' WHERE id='" + lService.get(i).id() + "'";
//                SQL.Execute(Query);
//
//                Sender(Integer.toString(lService.get(i).id()), Integer.toString((lService.get(i).position() - 1)), "Service");
//            }
//        }
//        reSorter();
    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
    @Override
    public void onBackPressed() {
        Sender();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressLint("SetTextI18n")
    public void initialize(Context mContext){
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        SQL = new MySQLi(mContext);
        lService = SQL.Select(Query, Datas.Services.class, mContext);

        if(mList.size() > 0)
            adapter = new adapGroup(mList, mContext);
        else
            adapter = new adapGroup(lService, mContext);
        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapter.setTouchHelper(touchHelper);
        lstMain.setAdapter(adapter);
        touchHelper.attachToRecyclerView(lstMain);
        for(int i=0; i<lService.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }

        lblGroupCount.setText(Integer.toString(lService.size()));
    }
    private void reSorter(){
        int ii = 0;
        lService = new ArrayList<>();
        lSubService = new ArrayList<>();
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);

        for(int i=0; i<lService.size(); i++){
            Query = "SELECT * FROM TB_SubServices WHERE service_id='" + lService.get(i).id() + "' ORDER BY position";
            lSubService = SQL.Select(Query, Datas.SubServices.class, context);

            for(int j=0; j<lSubService.size(); j++){
                ii++;
                Query = "UPDATE TB_SubServices SET position='" + Integer.toString(ii) + "' WHERE id='" + lSubService.get(j).id() + "'";
                SQL.Execute(Query);
            }
        }
    }
    static List<Datas.Services> mList = new ArrayList<>();
    public static void getList(List<Datas.Services> lList){
        mList = lList;
    }
    private static void Sender(){
        if(!onSender) {
            onSender = true;
            Gson gson = new Gson();
            String data = gson.toJson(mList);
            Sort.Sender(data, "1");

//        String Q = "UPDATE TB_Services SET position='" + FromPosition + "' WHERE id='" + ToId + "'";
//        String Q = "";
//        SQLi.Execute(Q);
////        Q = "UPDATE TB_Services SET position='" + ToPosition + "' WHERE id='" + FromId + "'";
//        Q = "";
//        SQLi.Execute(Q);
            for (int i = 0; i < mList.size(); i++) {
                String Query = "UPDATE TB_Services SET position='" + mList.get(i).position() + "' WHERE id='" + mList.get(i).id() + "'";
                SQLi.Execute(Query);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Sender();
    }

}
