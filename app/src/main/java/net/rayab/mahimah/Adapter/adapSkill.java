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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Offers.actNewOfferChoise;
import net.rayab.mahimah.Activities.Offers.actSkillNew;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapSkill extends RecyclerView.Adapter<adapSkill.AdapterMember>{

    private Context context;
    private List<Datas.Skills> lList;
    private AlertDialog.Builder builder;
    private MySQLi SQL;

    public adapSkill(List<Datas.Skills> lList, Context context){
        this.lList = lList;
        this.context = context;
        builder = new AlertDialog.Builder(context);
        SQL = new MySQLi(context);
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
        TextView lblSkillName1 = holder.lblSkillName1;
        TextView lblSkillName2 = holder.lblSkillName2;
        TextView lblPrice = holder.lblSkillPrice;
        ImageView btnDelete = holder.btnDelete;
        CardView cardView = holder.cardView;

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblSkillName1.setTypeface(tf);
        lblPrice.setTypeface(tf);

        lblSkillName1.setText(lList.get(position).name());
        List<Datas.SkillSub> lSub = SQL.Select("SELECT * FROM TB_SkillProducts WHERE skill_id='" + lList.get(position).id() + "'", Datas.SkillSub.class, context);
        String ServicesName = "";
        for (Datas.SkillSub data : lSub) {
            int sId = data.service_id();
            List<Datas.Services> lServices = SQL.Select("SELECT * FROM TB_Services WHERE id='" + sId + "' ORDER BY position", Datas.Services.class, context);
            ServicesName += lServices.get(0).title() + ", ";
        }
        try {
            ServicesName = ServicesName.substring(0, ServicesName.length() - 2);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        lblSkillName2.setText(ServicesName);

        String Pricer = Integer.toString(lList.get(position).price());
        try {
            String value = Pricer;
            String reverseValue = new StringBuilder(value).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(",");
                }
            }
            Pricer = finalValue.reverse().toString();
        } catch (Exception e) {
            // Do nothing since not a number
        }

        lblPrice.setText(Pricer);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] DeleteSkill = Datas.deleteSkillById(lList.get(position).id());
                        SQL.Execute(DeleteSkill[0]);
                        SQL.Execute(DeleteSkill[1]);
                        lList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                actSkillNew.Type = true;
                actSkillNew.SkillId = lList.get(position).id();
                actSkillNew.SkillName = lList.get(position).name();
                actSkillNew.position = lList.get(position).price();

                List<Datas.Skills> lSkill = SQL.Select(Datas.getSkillProducts(lList.get(position).id()), Datas.Skills.class, context);
                actSkillNew.lSkill = lSkill;

                Intent intent = new Intent(context, actSkillNew.class);
                context.startActivity(intent);
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView lblSkillName1, lblSkillName2, lblSkillPrice;
        private CardView cardView;
        private ImageView btnDelete;

        private AdapterMember(View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            lblSkillName1 = itemView.findViewById(R.id.lblService1);
            lblSkillName2 = itemView.findViewById(R.id.lblService2);
            lblSkillPrice = itemView.findViewById(R.id.lblAmount);
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
