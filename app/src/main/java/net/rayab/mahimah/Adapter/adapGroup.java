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
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actNewGroup;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperAdapter;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Adapter.Customers.OnCustomerListChangedListenerGroup;
import net.rayab.mahimah.Adapter.Helper.OnStartDragListener;
import net.rayab.mahimah.Adapter.test3.SwipeAndDragHelper;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.Sort;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class adapGroup extends RecyclerView.Adapter<adapGroup.AdapterMemberGtoup> implements SwipeAndDragHelper.ActionCompletionContract {

    Context context;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListenerGroup mListChangedListener;
    private static boolean moving = false;
    AlertDialog.Builder builder;
    private ItemTouchHelper touchHelper;
    MySQLi SQL;

    public List<Datas.Services> lList;
    public adapGroup(List<Datas.Services> lList, Context context){
        this.lList = lList;
        this.context = context;
        builder = new AlertDialog.Builder(context);
        SQL = new MySQLi(context);
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear(){
        try{
            this.lList.clear();
        }catch (Exception ignored){}
    }

    @Override
    public AdapterMemberGtoup onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_services, parent, false);

        return new AdapterMemberGtoup(itemView);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMemberGtoup holder, final int position) {
        TextView lblTitle = holder.lblTitle;
        CardView cardView = holder.cardView;
        ImageView btnDelete = holder.btnDelete;

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblTitle.setTypeface(tf);

        lblTitle.setText(lList.get(position).title());

//        setFadeAnimation(holder.itemView);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WebApi wAPI = new WebApi();
                        Map<String, String> Data = new HashMap<String, String>();
                        Data.put("services_id", Integer.toString(lList.get(position).id()));
                        Data.put("subservices_id", "empty");
                        Data.put("subservicesdicsount_id", "empty");
                        Data.put("subservicesdicsount_id2", "empty");

                        wAPI.API("https://mahimah.com/app/Deleter.php", Data);

                        String Query = "DELETE FROM TB_Services WHERE id='" + Integer.toString(lList.get(position).id()) + "'";
                        MySQLi SQL = new MySQLi(context);
                        SQL.Execute(Query);
                        Query = "DELETE FROM TB_SubServices WHERE service_id='" + Integer.toString(lList.get(position).id()) + "'";
                        SQL.Execute(Query);

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
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(actGroups.isLuck)
                    touchHelper.startDrag(holder);
                else{
                    actGroups act = new actGroups();
                    act.initialize(context);
                }
                if(event.getAction() == 1){
                    actNewGroup.uID = lList.get(position).id();
                    actNewGroup.Titlerer = lList.get(position).title();
                    actNewGroup.Type = 2;
                    Intent intent = new Intent(context, actNewGroup.class);
                    context.startActivity(intent);
                }
                return true;
            }
        });

    }
    public static class AdapterMemberGtoup extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public TextView lblTitle;
        public CardView cardView;
        public ImageView btnDelete;

        public AdapterMemberGtoup(View itemView){
            super(itemView);

            lblTitle = itemView.findViewById(R.id.lblTitle);
            cardView = itemView.findViewById(R.id.cardView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        @Override
        public void onItemSelected() {
            //xitemView.setBath://valokafor.com/wp-admin/post.php?post=1804&action=edit#ckgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }
    @Override
    public void onViewMoved(int fromPosition, int toPosition) {
            Datas.Services data = new Datas.Services();
            data.id(lList.get(fromPosition).id());
            data.title(lList.get(fromPosition).title());

            lList.get(fromPosition).id(lList.get(toPosition).id());
            lList.get(fromPosition).title(lList.get(toPosition).title());

            lList.get(toPosition).id(data.id());
            lList.get(toPosition).title(data.title());

            notifyItemMoved(fromPosition, toPosition);
            actGroups.getList(lList);
    }
    @Override
    public void onViewSwiped(int position) {
        notifyItemRemoved(position);
    }
    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

}
