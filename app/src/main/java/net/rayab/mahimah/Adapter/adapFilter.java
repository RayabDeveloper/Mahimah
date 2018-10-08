package net.rayab.mahimah.Adapter;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;

import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;

import java.util.ArrayList;
import java.util.List;

//public class adapFilter extends BaseAdapter {
public class adapFilter extends RecyclerView.Adapter<adapFilter.SimpleViewHolder> {

    private Context context;
    public List<Datas.Services> lList;

    public adapFilter(Context context, List<Datas.Services> lList) {
        this.context = context;
        this.lList = lList;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardViewMain;
        public final TextView lblCount;
        public final ImageView imgRed;

        public SimpleViewHolder(View view) {
            super(view);
            cardViewMain = view.findViewById(R.id.cardViewMain);
            lblCount = view.findViewById(R.id.lblCount);
            imgRed = view.findViewById(R.id.imgRed);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.items_filter_grid, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        holder.lblCount.setTypeface(tf);
        holder.lblCount.setText(lList.get(position).title());

        if (lList.get(position).isSelect()) {
            holder.lblCount.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.lblCount.setTextColor(context.getResources().getColor(R.color.colorOpasion));
        }
        boolean hasCheck = false;
        for (Datas.MainItems data : actMain.lCounter.get(actMain.cIndex).lBaseItems()) {
            if (data.service_id() == lList.get(position).id() && data.isCheck())
                hasCheck = true;
        }
        if (hasCheck)
            holder.imgRed.setVisibility(View.VISIBLE);
        else
            holder.imgRed.setVisibility(View.GONE);

        holder.cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actMain acet = new actMain();
                acet.lstFilterClick(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.lList.size();
    }

    public void updateResults(List<Datas.Services> lList) {
        this.lList = lList;
        notifyDataSetChanged();
    }
}


//    Context mContext;
//
//    private LayoutInflater mInflater;
//    public List<Datas.Services> lList;
//
//    public adapFilter(Context context, List<Datas.Services> lList){
//        mInflater = LayoutInflater.from(context);
//        mContext = context;
//        this.lList = lList;
//    }
//
//    public void updateResults(List<Datas.Services> lList) {
//        this.lList = lList;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount(){
//        return lList.size();
//    }
//
//    @Override
//    public Object getItem(int position){
//        return  null;
//    }
//
//    @Override
//    public long getItemId(int position){
//        return 0;
//    }
//
//    public void Data(List<Datas.Services> lList){
//        this.lList = lList;
//    }
//
//    public void Clear(){
//        try{
//            this.lList.clear();
//        }catch (Exception ignored){}
//    }
//
//    public static class AdapterMember{
//
//        public boolean NeedInflat;
//        TextView        lblCount;
//        ImageView       imgRed;
//
//    }
//
//    private AdapterMember Members = null;
//
//    @Override
//    public View getView(final int position, View ConvertView, final ViewGroup Parent){
//
//        if(ConvertView == null){
//            ConvertView                         = mInflater.inflate(R.layout.items_filter_grid, Parent, false);
//            Members                             = new AdapterMember();
//            Members.lblCount                    = ConvertView.findViewById(R.id.lblCount);
//            Members.imgRed                      = ConvertView.findViewById(R.id.imgRed);
//
//            ConvertView.setTag(Members);
//        }else{
//            Members = (AdapterMember)ConvertView.getTag();
//        }
//
//        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/ir_sans.ttf");
//        Members.lblCount.setTypeface(tf);
//        Members.lblCount.setText(lList.get(position).title());
//
//        if(lList.get(position).isSelect()){
//            Members.lblCount.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
//        }else{
//            Members.lblCount.setTextColor(mContext.getResources().getColor(R.color.colorOpasion));
//        }
//        boolean hasCheck = false;
//        for (Datas.MainItems data : actMain.lCounter.get(actMain.cIndex).lBaseItems()) {
//            if(data.service_id() == lList.get(position).id() && data.isCheck())
//                hasCheck = true;
//        }
//        if(hasCheck)
//            Members.imgRed.setVisibility(View.VISIBLE);
//        else
//            Members.imgRed.setVisibility(View.GONE);
//
//        return ConvertView;
//
//    }


//    Context mContext;
//
//    private LayoutInflater mInflater;
//    public List<Datas.Services> lList;
//
//    public adapFilter(Context context, List<Datas.Services> lList){
//        mInflater = LayoutInflater.from(context);
//        mContext = context;
//        this.lList = lList;
//    }
//
//    public void updateResults(List<Datas.Services> lList) {
//        this.lList = lList;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount(){
//        return lList.size();
//    }
//
//    @Override
//    public Object getItem(int position){
//        return  null;
//    }
//
//    @Override
//    public long getItemId(int position){
//        return 0;
//    }
//
//    public void Data(List<Datas.Services> lList){
//        this.lList = lList;
//    }
//
//    public void Clear(){
//        try{
//            this.lList.clear();
//        }catch (Exception ignored){}
//    }
//
//    public static class AdapterMember{
//
//        public boolean NeedInflat;
//        TextView        lblCount;
//
//    }
//
//    private AdapterMember Members = null;
//
//    @Override
//    public View getView(final int position, View ConvertView, final ViewGroup Parent){
//
//        if(ConvertView == null){
//            ConvertView                         = mInflater.inflate(R.layout.items_filter, Parent, false);
//            Members                             = new AdapterMember();
//            Members.lblCount                    = ConvertView.findViewById(R.id.lblCount);
//
//            ConvertView.setTag(Members);
//        }else{
//            Members = (AdapterMember)ConvertView.getTag();
//        }
//
//        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/ir_sans.ttf");
//        Members.lblCount.setTypeface(tf);
//        Members.lblCount.setText(lList.get(position).title());
//
//        if(lList.get(position).isSelect()){
//            Members.lblCount.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
//        }else{
//            Members.lblCount.setTextColor(mContext.getResources().getColor(R.color.colorOpasion));
//        }
//
//        return ConvertView;
//
//    }


