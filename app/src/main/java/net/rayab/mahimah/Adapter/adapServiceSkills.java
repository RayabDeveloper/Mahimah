package net.rayab.mahimah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Offers.actSkillNew;
import net.rayab.mahimah.Activities.Services.actNewService;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Adapter.test1.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Adapter.test1.OnCustomerListChangedListener;
import net.rayab.mahimah.Adapter.test1.OnStartDragListener;
import net.rayab.mahimah.Adapter.test3.SwipeAndDragHelper;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class adapServiceSkills extends RecyclerView.Adapter<adapServiceSkills.AdapterMember>{

    private Context context;
    private List<Datas.Services> lList;
    AlertDialog.Builder builder;
    private ItemTouchHelper touchHelper;
    MySQLi SQL;

    public adapServiceSkills(List<Datas.Services> lList, Context context){
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
                .inflate(R.layout.items_services, parent, false);

        return new AdapterMember(itemView);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblTitle = holder.lblTitle;
        ImageView btnDelete = holder.btnDelete;
        CardView cardView = holder.cardView;
        final RelativeLayout lin = holder.lin;

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblTitle.setTypeface(tf);
        btnDelete.setVisibility(View.GONE);

        if(lList.get(position).isSelect())
            lin.setBackgroundColor(context.getResources().getColor(R.color.Gray));
        else
            lin.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        lblTitle.setText(lList.get(position).title());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lList.get(position).isSelect()){
                    lList.get(position).isSelect(false);
                    lin.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    actSkillNew.lService.get(position).isSelect(false);
//                    notifyDataSetChanged();
                }else{
                    lList.get(position).isSelect(true);
                    lin.setBackgroundColor(context.getResources().getColor(R.color.Gray));
                    actSkillNew.lService.get(position).isSelect(true);
//                    notifyDataSetChanged();
                }
            }
        });

    }
    public static class AdapterMember extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView lblTitle;
        private CardView cardView;
        private ImageView btnDelete;
        private RelativeLayout lin;

        private AdapterMember(View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            lin = itemView.findViewById(R.id.lin);
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
