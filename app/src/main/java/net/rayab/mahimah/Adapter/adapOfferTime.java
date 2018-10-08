package net.rayab.mahimah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Offers.actNewOfferChoise;
import net.rayab.mahimah.Activities.Offers.actNewOfferTime;
import net.rayab.mahimah.Activities.Services.actNewService;
import net.rayab.mahimah.Adapter.Customers.OnCustomerListChangedListener;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperAdapter;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Adapter.Helper.OnStartDragListener;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class adapOfferTime extends RecyclerView.Adapter<adapOfferTime.AdapterMember>{

    private Context context;
    private List<Datas.SubServicesDiscount> lList;
    private AlertDialog.Builder builder;

    public adapOfferTime(List<Datas.SubServicesDiscount> lList, Context context){
        this.lList = lList;
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }
    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_offers, parent, false);

        return new AdapterMember(itemView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final AdapterMember holder, @SuppressLint("RecyclerView") final int position) {
        TextView lblService1 = holder.lblService1;
        TextView lblService2 = holder.lblService2;
        TextView lblAmount = holder.lblAmount;
        ImageView btnDelete = holder.btnDelete;
        CardView cardView = holder.cardView;

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblService1.setTypeface(tf);lblService2.setTypeface(tf);lblAmount.setTypeface(tf);

        MySQLi SQL = new MySQLi(context);

        List<Datas.Services> lService1 = new ArrayList<>();
        List<Datas.Services> lService2 = new ArrayList<>();
        List<Datas.SubServices> lSubService1 = new ArrayList<>();
        List<Datas.SubServices> lSubService2 = new ArrayList<>();

        String s1 = Integer.toString(lList.get(position).subservice_id_1());
        String s2 = Integer.toString(lList.get(position).subservice_id_2());
        String Query = "SELECT * FROM TB_SubServices WHERE id='" + s1 + "'";
        lSubService1 = SQL.Select(Query, Datas.SubServices.class, context);
        Query = "SELECT * FROM TB_SubServices WHERE id='" + s2 + "'";
        lSubService2 = SQL.Select(Query, Datas.SubServices.class, context);
        Query = "SELECT * FROM TB_Services WHERE id='" + lSubService1.get(0).service_id() + "'";
        lService1 = SQL.Select(Query, Datas.Services.class, context);
        Query = "SELECT * FROM TB_Services WHERE id='" + lSubService2.get(0).service_id() + "'";
        lService2 = SQL.Select(Query, Datas.Services.class, context);


        lblService1.setText(lService1.get(0).title() + " | " + lSubService1.get(0).title());
        lblService2.setText(lService2.get(0).title() + " | " + lSubService2.get(0).title());

        StringBuilder PriceAmount = new StringBuilder("");
        try {
            String value = lList.get(position).discount_amount().replace(",", "");
            String reverseValue = new StringBuilder(value).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(",");
                }
            }
            PriceAmount = finalValue.reverse();
        } catch (Exception e) {
            // Do nothing since not a number
        }

        lblAmount.setText("تخفیف : " + PriceAmount);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WebApi wAPI = new WebApi();
                        Map<String, String> Data = new HashMap<String, String>();
                        Data.put("services_id", "empty");
                        Data.put("subservices_id", "empty");
                        Data.put("subservicesdicsount_id", Integer.toString(lList.get(position).id()));
                        Data.put("subservicesdicsount_id2", "empty");

                        wAPI.API("https://mahimah.com/app/Deleter.php", Data);

                        String Query = "DELETE FROM TB_OfferTime WHERE id='" + Integer.toString(lList.get(position).id()) + "'";
                        MySQLi SQL = new MySQLi(context);
                        String Resulter = SQL.Execute(Query);

                        Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).cancel();

                        lList.remove(position);
                        notifyDataSetChanged();

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
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actNewOfferTime.mID = Integer.toString(lList.get(position).id());
                actNewOfferTime.Service1 = lList.get(position).subservice_id_1();
                actNewOfferTime.Service2 = lList.get(position).subservice_id_2();
                actNewOfferTime.Amount = lList.get(position).discount_amount();
                actNewOfferTime.Type = 2;
                Intent intent = new Intent(context, actNewOfferTime.class);
                context.startActivity(intent);
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView lblService1, lblService2, lblAmount;
        private CardView cardView;
        private ImageView btnDelete;

        private AdapterMember(View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            lblService1 = itemView.findViewById(R.id.lblService1);
            lblService2 = itemView.findViewById(R.id.lblService2);
            lblAmount = itemView.findViewById(R.id.lblAmount);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }

}
