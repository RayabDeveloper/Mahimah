package net.rayab.mahimah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;

import java.util.List;

public class adapCounter extends BaseAdapter {

    Context mContext;

    private LayoutInflater mInflater;
    public List<Datas.Counter> lList;
    int positioner = 0;

    public adapCounter(Context context, List<Datas.Counter> lList, int positioner){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.lList = lList;
        this.positioner = positioner;
    }

    @Override
    public int getCount(){
        return lList.size();
    }

    @Override
    public Object getItem(int position){
        return  null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    public void Clear(){
        try{
            lList.clear();
        }catch (Exception ignored){}
    }

    public static class AdapterMember{

        ImageView imgCounter;
        ImageView imgSelected;
    }

    AdapterMember Members = null;

    @Override
    public View getView(final int position, View ConvertView, final ViewGroup Parent){

        if(ConvertView == null){
            ConvertView                         = mInflater.inflate(R.layout.items_count, Parent, false);
            Members                             = new AdapterMember();
            Members.imgCounter                  = ConvertView.findViewById(R.id.imgCount);
            Members.imgSelected                 = ConvertView.findViewById(R.id.imgSelected);

            ConvertView.setTag(Members);
        }else{
            Members = (AdapterMember)ConvertView.getTag();
        }

        /*Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/cardapp.ttf");
        Members.CardName.setTypeface(tf);*/
        if(position == positioner){
            Members.imgSelected.setVisibility(View.VISIBLE);
        }else{
            Members.imgSelected.setVisibility(View.GONE);
        }

        Members.imgCounter.setImageResource(0);
        switch (position){
            case 0:
                Members.imgCounter.setImageResource(R.drawable.numeric_1);
                break;
            case 1:
                Members.imgCounter.setImageResource(R.drawable.numeric_2);
                break;
            case 2:
                Members.imgCounter.setImageResource(R.drawable.numeric_3);
                break;
            case 3:
                Members.imgCounter.setImageResource(R.drawable.numeric_4);
                break;
            case 4:
                Members.imgCounter.setImageResource(R.drawable.numeric_5);
                break;
            case 5:
                Members.imgCounter.setImageResource(R.drawable.numeric_6);
                break;
            case 6:
                Members.imgCounter.setImageResource(R.drawable.numeric_7);
                break;
            case 7:
                Members.imgCounter.setImageResource(R.drawable.numeric_8);
                break;
            case 8:
                Members.imgCounter.setImageResource(R.drawable.numeric_9);
                break;
            case 9:
                Members.imgCounter.setImageResource(R.drawable.numeric_9_plus);
                break;
        }
        if(position > 9)
            Members.imgCounter.setImageResource(R.drawable.numeric_9_plus);

        return ConvertView;

    }

}