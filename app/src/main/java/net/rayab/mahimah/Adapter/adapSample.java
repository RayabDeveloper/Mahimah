package net.rayab.mahimah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;

import java.util.List;

public class adapSample extends BaseAdapter {

    Context mContext;

    private LayoutInflater mInflater;
    public List<Datas.Services> lList;

    public adapSample(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
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

    public void Data(List<Datas.Services> lList){
        this.lList = lList;
    }

    public void Clear(){
        try{
            this.lList.clear();
        }catch (Exception ignored){}
    }

    public static class AdapterMember{

        public boolean NeedInflat;
        //RelativeLayout  rel_Charge;
        //LinearLayout    lin_Item;
        ImageView CardIMG;
        TextView        CardName;
        TextView        CardMaxUse;
        TextView CardMaxCharge;

    }

    AdapterMember Members = null;

    @Override
    public View getView(final int position, View ConvertView, final ViewGroup Parent){

        if(ConvertView == null){
            ConvertView                     = mInflater.inflate(R.layout.items_main, Parent, false);
            Members = new AdapterMember();
//            Members.CardIMG                 = (ImageView)ConvertView.findViewById(R.id.img_Card);
//            Members.CardName                = (TextView)ConvertView.findViewById(R.id.lbl_Name);
//            Members.CardMaxUse              = (TextView)ConvertView.findViewById(R.id.lbl_MaxUse);
//            Members.CardMaxCharge           = (TextView)ConvertView.findViewById(R.id.lbl_MaxCharge);

            ConvertView.setTag(Members);
        }else{
            Members = (AdapterMember)ConvertView.getTag();
        }

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/cardapp.ttf");
        Members.CardName.setTypeface(tf);
        Members.CardMaxUse.setTypeface(tf);
        Members.CardMaxCharge.setTypeface(tf);

        /*Members.CardName.setText(lList.get(position).Name());
        Members.CardMaxUse.setText(lList.get(position).Family());
        Glide.with(mContext).load(lList.get(position).Name()).into(Members.CardIMG);

        Members.rel_Charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Act_BuyCard_DetailsCard.class);
                mContext.startActivity(intent);
            }
        });

        Members.lin_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Act_MyCard_ChoiseCard.class);
                mContext.startActivity(intent);
            }
        });*/

        return ConvertView;

    }

}
