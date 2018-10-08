package net.rayab.mahimah.Activities.Services;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.rayab.mahimah.Activities.Offers.actOfferChoise;
import net.rayab.mahimah.Activities.Offers.actOfferTime;
import net.rayab.mahimah.Activities.Offers.actSkill;
import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.adapServices;
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

public class actServices extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    static Context contexti;
    adapServices adapter;
    MySQLi SQL = new MySQLi(context);
    static MySQLi SQLi;
    WebApi wAPI = new WebApi();
    List<Datas.Services> lService = new ArrayList<>();
    List<Datas.SubServices> lSubService = new ArrayList<>();
    List<Datas.MainItems> lMainItem = new ArrayList<>();
    List<Datas.Services> lService2 = new ArrayList<>();
    List<Datas.Services> lService3 = new ArrayList<>();

    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    ImageView btnSetting;
    static TextView lblService, lblServiceCount,
            lblNewService, lblService2, lblGroup, lblPrice;
    Switch switche;
    EditText txtService, txtPrice, txtDescription;
    static RecyclerView lstMain;
    Spinner spinGroup;
    ImageView btnMenuIcon;
    DrawerLayout drawer;
    NavigationView navigationView;

    public static boolean isLuck = false;
    static boolean onSender = false;
    public static boolean isFromNew = false;
    public static int FromNewId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_services);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appExit.act8 = context;

        switche = findViewById(R.id.switche);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        btnSetting = findViewById(R.id.btnSetting);

        lblService = findViewById(R.id.lblService);
        lblServiceCount = findViewById(R.id.lblServiceCount);
        lblNewService = findViewById(R.id.lblNewService);
        lblService2 = findViewById(R.id.lblService2);
        lblGroup = findViewById(R.id.lblSkill);
        lblPrice = findViewById(R.id.lblPrice);

        spinGroup = findViewById(R.id.spinGroup);

        txtService = findViewById(R.id.txtService);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);


        lstMain = findViewById(R.id.lstMain);

        btnMenuIcon = findViewById(R.id.btnMenuIcon);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblService.setTypeface(tf);lblServiceCount.setTypeface(tf);lblNewService.setTypeface(tf);lblService2.setTypeface(tf);
        lblGroup.setTypeface(tf);lblPrice.setTypeface(tf);txtService.setTypeface(tf);txtPrice.setTypeface(tf);txtDescription.setTypeface(tf);
        contexti = context;
        SQLi = new MySQLi(contexti);

        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService2 = SQL.Select(Query, Datas.Services.class, context);
        List<String> spinnerArray = new ArrayList<>();
        for(int i=0; i<lService2.size(); i++){
            spinnerArray.add(lService2.get(i).title().replace("|", ""));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item_test, spinnerArray){
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent){
                View v = super.getView(position, convertView, parent);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
                ((TextView) v).setTypeface(tf);

                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorWhite));
                v.setBackgroundColor(getResources().getColor(R.color.colorbColor));

                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGroup.setAdapter(adapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        spinGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sId = lService2.get(position).id();
                FromNewId = sId;
                mList = new ArrayList<>();
                initialize(Integer.toString(sId), context);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                Sender2();
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actNewService.class);
                Pair<View, String> p1 = Pair.create((View)lblNewService, "lblService");
                Pair<View, String> p2 = Pair.create((View)lblService2, "lblService2");
                Pair<View, String> p3 = Pair.create((View)lblGroup, "lblGroup");
                Pair<View, String> p4 = Pair.create((View)lblPrice, "lblPrice");
                Pair<View, String> p5 = Pair.create((View)txtService, "txtService");
                Pair<View, String> p6 = Pair.create((View)txtPrice, "txtPrice");
                Pair<View, String> p7 = Pair.create((View)txtDescription, "txtDes");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4, p5, p6, p7);

                actNewService.Type = 1;
                actNewService.uID = 0;
                actNewService.positioner = 0;
                actNewService.Titlerer = "";
                actNewService.Pricer = "";
                actNewService.Descriptioner = "";
                startActivity(intent, options.toBundle());
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        isLuck = false;
        int ServiceId = 0;
        if(!isFromNew) {
            String Query = "SELECT * FROM TB_Services ORDER BY position";
            lService3 = SQL.Select(Query, Datas.Services.class, context);
            ServiceId = lService3.get(0).id();
        }else{
            ServiceId = FromNewId;
        }
        initialize(Integer.toString(ServiceId), context);
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
            drawer.closeDrawer(GravityCompat.END);
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
        Sender();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    private void reSorter(List<Datas.MainItems> lList){

    }
    public void initialize(String sId, Context mContext){
        lMainItem = new ArrayList<>();
        String Query = "SELECT * FROM TB_SubServices WHERE service_id='" + sId + "' ORDER BY position";

        SQL = new MySQLi(mContext);
        lSubService = SQL.Select(Query, Datas.SubServices.class, mContext);
        for(int j=0; j<lSubService.size(); j++){
            String Title = lSubService.get(j).title();
            String Price = lSubService.get(j).price();
            String PriceNew = lSubService.get(j).pricenew();
            String Description = lSubService.get(j).description();
            int ServiceID = lSubService.get(j).service_id();

            Datas.MainItems Item = new Datas.MainItems();
            Item.title(Title);
            Item.description(Description);
            Item.price(Price);
            Item.pricenew(PriceNew);
            Item.position(lSubService.get(j).position());
            Item.service_id(ServiceID);
            Item.id(lSubService.get(j).id());

            lMainItem.add(Item);
        }
//        lstMain.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        lstMain.setLayoutManager(mLayoutManager);
//        adapter = new adapServices(lMainItem, context, this, this);
//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(lstMain);
//        lstMain.setItemAnimator(new DefaultItemAnimator());
//        lstMain.setAdapter(adapter);
        lstMain.setLayoutManager(new LinearLayoutManager(this));
        adapServices adapter;
        if(mList.size() > 0)
            adapter = new adapServices(mList, mContext);
        else
            adapter = new adapServices(lMainItem, mContext);
        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapter.setTouchHelper(touchHelper);
        lstMain.setAdapter(adapter);
        touchHelper.attachToRecyclerView(lstMain);


        for(int i=0; i<lMainItem.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }

        lblServiceCount.setText(Integer.toString(lMainItem.size()));
    }

    public void initialize2(String sId, Context mContext){
        lMainItem = new ArrayList<>();
        String Query = "SELECT * FROM TB_SubServices WHERE service_id='" + sId + "' ORDER BY position";

        SQL = new MySQLi(mContext);
        lSubService = SQL.Select(Query, Datas.SubServices.class, mContext);
        for(int j=0; j<lSubService.size(); j++){
            String Title = lSubService.get(j).title();
            String Price = lSubService.get(j).price();
            String PriceNew = lSubService.get(j).pricenew();
            String Description = lSubService.get(j).description();
            int ServiceID = lSubService.get(j).service_id();

            Datas.MainItems Item = new Datas.MainItems();
            Item.title(Title);
            Item.description(Description);
            Item.price(Price);
            Item.pricenew(PriceNew);
            Item.position(lSubService.get(j).position());
            Item.service_id(ServiceID);
            Item.id(lSubService.get(j).id());

            lMainItem.add(Item);
        }
//        lstMain.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        lstMain.setLayoutManager(mLayoutManager);
//        adapter = new adapServices(lMainItem, context, this, this);
//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(lstMain);
//        lstMain.setItemAnimator(new DefaultItemAnimator());
//        lstMain.setAdapter(adapter);
        lstMain.setLayoutManager(new LinearLayoutManager(this));
        adapServices adapter;
        adapter = new adapServices(lMainItem, mContext);
        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapter.setTouchHelper(touchHelper);
        lstMain.setAdapter(adapter);
        touchHelper.attachToRecyclerView(lstMain);


        for(int i=0; i<lMainItem.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }

        lblServiceCount.setText(Integer.toString(lMainItem.size()));
    }
    public void onClick(List<Datas.MainItems> lList){

    }
    static List<Datas.MainItems> mList = new ArrayList<>();
    public static void getList(List<Datas.MainItems> lList){
        mList = lList;
    }
    private static void Sender(){
        if(!onSender) {
            onSender = true;
            Gson gson = new Gson();
            String data = gson.toJson(mList);
            Sort.Sender(data, "2");

//        String Q = "UPDATE TB_SubServices SET position='" + FromPosition + "' WHERE id='" + ToId + "'";
//        String Q = "";
//        SQLi.Execute(Q);
////        Q = "UPDATE TB_SubServices SET position='" + ToPosition + "' WHERE id='" + FromId + "'";
//        Q = "";
//        SQLi.Execute(Q);
            for (int i = 0; i < mList.size(); i++) {
                String Query = "UPDATE TB_SubServices SET position='" + mList.get(i).position() + "' WHERE id='" + mList.get(i).id() + "'";
                SQLi.Execute(Query);
            }
        }
    }

    private static void Sender2(){
        Gson gson = new Gson();
        String data = gson.toJson(mList);
        Sort.Sender(data, "2");

        for (int i = 0; i < mList.size(); i++) {
            String Query = "UPDATE TB_SubServices SET position='" + mList.get(i).position() + "' WHERE id='" + mList.get(i).id() + "'";
            SQLi.Execute(Query);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Sender();
    }
}
