package net.rayab.mahimah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actNewService;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Adapter.test1.OnCustomerListChangedListener;
import net.rayab.mahimah.Adapter.test1.ItemTouchHelperAdapter;
import net.rayab.mahimah.Adapter.test1.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Adapter.test1.OnStartDragListener;
import net.rayab.mahimah.Adapter.test3.SwipeAndDragHelper;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.Sort;
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

public class adapServices extends RecyclerView.Adapter<adapServices.AdapterMember> implements SwipeAndDragHelper.ActionCompletionContract {

    private Context context;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;
    private List<Datas.MainItems> lList;
    private static boolean moving = false;
    AlertDialog.Builder builder;
    boolean inTouch = false;
    private ItemTouchHelper touchHelper;
    MySQLi SQL;

    public adapServices(List<Datas.MainItems> lList, Context context){
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

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblTitle.setTypeface(tf);

        lblTitle.setText(lList.get(position).title());

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
                        Data.put("subservices_id", Integer.toString(lList.get(position).id()));
                        Data.put("subservicesdicsount_id", "empty");
                        Data.put("subservicesdicsount_id2", "empty");

                        wAPI.API("https://mahimah.com/app/Deleter.php", Data);

                        String Query = "DELETE FROM TB_SubServices WHERE id='" + Integer.toString(lList.get(position).id()) + "'";
                        MySQLi SQL = new MySQLi(context);
                        SQL.Execute(Query);
                        Query = "DELETE FROM TB_OfferTime WHERE subservice_id_1='" + Integer.toString(lList.get(position).id()) + "' OR subservice_id_2='" + Integer.toString(lList.get(position).id()) + "'";
                        SQL.Execute(Query);
                        Query = "DELETE FROM TB_OfferChoise WHERE subservice_id_1='" + Integer.toString(lList.get(position).id()) + "' OR subservice_id_2='" + Integer.toString(lList.get(position).id()) + "'";
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
                if(actServices.isLuck)
                    touchHelper.startDrag(holder);
                else{
                    actServices act = new actServices();
                    act.initialize(Integer.toString(lList.get(0).service_id()), context);
                }
                if(event.getAction() == 1){
                    try {
                        actNewService.uID = lList.get(position).id();
                        String Titleriu = lList.get(position).title();
//                    String[] mTitle = Titleriu.split(" \\|");
                        actNewService.Titlerer = Titleriu;
                        actNewService.Pricer = lList.get(position).price();
                        actNewService.PriceNew = lList.get(position).pricenew();
                        actNewService.Descriptioner = lList.get(position).description();
                        actNewService.positioner = lList.get(position).service_id();
                        actNewService.mPosition = lList.get(position).position();
                        actNewService.FromNewId = actServices.FromNewId;
                        actNewService.Type = 2;
                        Intent intent = new Intent(context, actNewService.class);
                        context.startActivity(intent);
                    }catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                return true;
            }
        });

    }
    @Override
    public void onViewMoved(int fromPosition, int toPosition) {
            Datas.MainItems data = new Datas.MainItems();
            data.id(lList.get(fromPosition).id());
            data.title(lList.get(fromPosition).title());

            lList.get(fromPosition).id(lList.get(toPosition).id());
            lList.get(fromPosition).title(lList.get(toPosition).title());

            lList.get(toPosition).id(data.id());
            lList.get(toPosition).title(data.title());

            notifyItemMoved(fromPosition, toPosition);
            actServices.getList(lList);
    }
    @Override
    public void onViewSwiped(int position) {
        notifyItemRemoved(position);
        String asdf = "ASD";
    }
    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

    public static class AdapterMember extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView lblTitle;
        private CardView cardView;
        private ImageView btnDelete;

        private AdapterMember(View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            lblTitle = itemView.findViewById(R.id.lblTitle);
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
    private void setFadeAnimation(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                int mWidth = view.getWidth();

                int start = ((mWidth * 2) -1);
                TranslateAnimation animation1 = new TranslateAnimation(start, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation1.setDuration(new Random().nextInt(1300 - 501) + 501);
                view.startAnimation(animation1);
            }
        });
    }

}
