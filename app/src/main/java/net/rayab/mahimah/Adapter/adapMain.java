package net.rayab.mahimah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.Helper.ItemTouchHelperViewHolder;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class adapMain extends RecyclerView.Adapter<adapMain.AdapterMember>{

    private Context context;
//    private OnStartDragListener mDragStartListener;
//    private OnCustomerListChangedListener mListChangedListener;

    private List<Datas.MainItems> lList;
    private boolean mDis1 = false, mDis2 = false, isCard = false;
    private List<Integer> disCount = new ArrayList<>();
    private int cPackage = 0;
    SpannableString content;
    public adapMain(List<Datas.MainItems> lList, Context context, int cPackage){
        this.lList = lList;
        this.context = context;
        this.cPackage = cPackage;
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_main, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, @SuppressLint("RecyclerView") final int position) {
        TextView lblPrice = holder.lblPrice;
        TextView lblPrice2 = holder.lblPrice2;
        TextView lblPriceNew = holder.lblPriceNew;
        TextView lblTitle = holder.lblTitle;
        CardView cardView = holder.cardView;
        TextView lblDescription = holder.lblDescription;

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblPrice.setTypeface(tf);lblTitle.setTypeface(tf);lblDescription.setTypeface(tf);

        lblTitle.setText(lList.get(position).title());
        lblDescription.setText(lList.get(position).description());

        lblPriceNew.setVisibility(View.GONE);
        lblPrice2.setVisibility(View.VISIBLE);
        lblPrice.setVisibility(View.GONE);
        lblPrice.setTextColor(context.getResources().getColor(R.color.colorbColor));
        if(lList.get(position).pricenew().length() == 0)
            lList.get(position).pricenew("0");
        if(Integer.parseInt(lList.get(position).pricenew().replace(",", "")) > 1) {
            lblPriceNew.setVisibility(View.VISIBLE);
            lblPrice.setTextColor(context.getResources().getColor(R.color.colorbColor));

            lblPrice.setText(lList.get(position).price());
            lblPrice.setPaintFlags(lblPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            lblPrice.setTextColor(context.getResources().getColor(R.color.colorPriceNew));
            lblPrice2.setVisibility(View.GONE);
            lblPrice.setVisibility(View.VISIBLE);
        }

        lblPrice.setText(lList.get(position).price());
        lblPrice2.setText(lList.get(position).price());
        lblPriceNew.setText(lList.get(position).pricenew());

        boolean iCheck = lList.get(position).isCheck();
        holder.chChoise.setChecked(iCheck);

//        setFadeAnimation(holder.itemView);

//        holder.imgMovinger.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCard = true;
                if(holder.chChoise.isChecked()){
                    holder.chChoise.setChecked(false);
                }else {
                    holder.chChoise.setChecked(true);
                }
                Checker(holder.chChoise.isChecked(), lList.get(position).id());
                lList.get(position).isCheck(holder.chChoise.isChecked());
            }
        });


        holder.chChoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCard){
                    Checker(holder.chChoise.isChecked(), lList.get(position).id());
                }
                isCard = false;
            }
        });

    }

    private void Checker(boolean isCheckChecker, int ssID){
        try {
            try {
                for (int i = 0; i < actMain.lCounter.get(actMain.cIndex).lBaseItems().size(); i++) {
                    if (actMain.lCounter.get(actMain.cIndex).lBaseItems().get(i).id() == ssID) {
                        actMain.lCounter.get(actMain.cIndex).lBaseItems().get(i).isCheck(isCheckChecker);
                    }
                }
                for (int i = 0; i < actMain.lCounter.get(actMain.cIndex).lPackageItems().size(); i++) {
                    if (actMain.lCounter.get(actMain.cIndex).lPackageItems().get(i).id() == ssID) {
                        actMain.lCounter.get(actMain.cIndex).lPackageItems().get(i).isCheck(isCheckChecker);
                    }
                }
            }catch (Exception Ex){
                String Er = Ex.getMessage();
            }
//             Calculate SumPrices - Offers
            MySQLi SQL = new MySQLi(context);
            List<Datas.OfferPrice> lOffers;
            String Query = "SELECT * FROM TB_OfferPrice";
            lOffers = SQL.Select(Query, Datas.OfferPrice.class, context);
            int Offer = lOffers.get(0).offer_price();
            List<Datas.SubServicesDiscount> lDiscount_Time;
            List<Datas.SubServicesDiscount> lDiscount_Choise;
            Query = "SELECT * FROM TB_OfferTime";
            lDiscount_Time = SQL.Select(Query, Datas.SubServicesDiscount.class, context);
            Query = "SELECT * FROM TB_OfferChoise";
            lDiscount_Choise = SQL.Select(Query, Datas.SubServicesDiscount.class, context);

            int cSum = 0, mSum = 0;
            List<Datas.SkillPriceAdder> SkillPrice = new ArrayList<>();
            List<String> lcSum = new ArrayList<>();
            for (Datas.Counter data : actMain.lCounter) {
                cSum = 0;
                SkillPrice = new ArrayList<>();
                List<Datas.Skills> lSkill = new ArrayList<>();
                List<Datas.PriceNew> lPriceNew = new ArrayList<>();
                for (Datas.MainItems mData : data.lBaseItems()) {
                    if(mData.isCheck()) {
                        if (mData.pricenew().length() > 0)
                            cSum += Integer.parseInt(mData.pricenew().replace(",", ""));
                        else
                            cSum += Integer.parseInt(mData.price().replace(",", ""));


                        SkillFinder:
                        {
                            List<Datas.SkillMain> lSkillMain = SQL.Select(Datas.getSkillMain, Datas.SkillMain.class, context);
                            for (Datas.SkillMain lData : lSkillMain) {
                                List<Datas.SkillSub> lSkillSub = SQL.Select(Datas.getSkillSub(lData.id()), Datas.SkillSub.class, context);
                                for (Datas.SkillSub nData : lSkillSub) {
                                    if (nData.service_id() == mData.service_id()) {
                                        Datas.SkillPriceAdder ddData = new Datas.SkillPriceAdder();
                                        ddData.id(lData.id());
                                        ddData.price(lData.price());
                                        SkillPrice.add(ddData);
                                    }
                                }
                            }
                        }
                    }
                }
                int a = 0;
                int m = 0;
                boolean isA = false;
                SkillPrice = sorter(SkillPrice);
                for(int i=0; i<SkillPrice.size(); i++){
                    List<Integer> lPrice = new ArrayList<>();
                    if(SkillPrice.get(i).id() != a) {
                        isA = true;
                        a = SkillPrice.get(i).id();
                    }else
                        isA = false;
                    if(isA) {
                        for (int j = 0; j < SkillPrice.size(); j++) {
                            if(SkillPrice.get(j).id() == a){
                                lPrice.add(SkillPrice.get(j).price());
                            }
                        }
                        if(lPrice.size() > 0)
                            lPrice.remove(0);
                        for (Integer inet : lPrice) {
                            m += inet;
                        }
                    }
                }
                cSum -= m;
                lcSum.add(getPriceCamma(cSum));
                mSum += cSum;
            }
            String cSummer = "0";
            for (String Summer : lcSum) {
                cSummer += Summer + " + ";
            }
            if(cSummer.length() > 1)
                cSummer = cSummer.substring(0, cSummer.length() - 3);
            if(cSummer.substring(0, 1).equalsIgnoreCase("0"))
                cSummer = cSummer.substring(1);
            actMain.lblCurrentSum.setText(cSummer);
            actMain.lblMainSum.setText(getPriceCamma(mSum));
//            int cSum, mSum = 0;
//            List<Datas.Offers> lcSum = new ArrayList<>();
//            for (int i = 0; i < actMain.lCounter.size(); i++) {
//                cSum = 0;
//                for (int j = 0; j < actMain.lCounter.get(i).lBaseItems().size(); j++) {
//                    if (actMain.lCounter.get(i).lBaseItems().get(j).isCheck()) {
//                        String mPricer = "";
//                        String priceNew = actMain.lCounter.get(i).lBaseItems().get(j).pricenew().replace(",", "");
//                        if(priceNew.length() == 0)
//                            priceNew = "0";
//                        if(Integer.parseInt(priceNew) < 2)
//                            mPricer = actMain.lCounter.get(i).lBaseItems().get(j).price().replace(",", "");
//                        else
//                            mPricer = priceNew;
//                        String Price = mPricer;
//                        cSum += Integer.parseInt(Price);
//                    }
//                }
//                boolean cDis1 = false, cDis2 = false;
//                for (int j = 0; j < lDiscount_Time.size(); j++) {
//                    for (int k = 0; k < actMain.lCounter.get(i).lBaseItems().size(); k++) {
//                        if(actMain.lCounter.get(i).lBaseItems().get(k).isCheck()) {
//                            if (actMain.lCounter.get(i).lBaseItems().get(k).id() == lDiscount_Time.get(j).subservice_id_1()) {
//                                cDis1 = true;
//                            }
//                            if (actMain.lCounter.get(i).lBaseItems().get(k).id() == lDiscount_Time.get(j).subservice_id_2()) {
//                                cDis2 = true;
//                            }
//                            if (cDis1 && cDis2) {
//                                break;
//                            }
//                        }
//                    }
//                }
//                if(cDis1 && cDis2){
////                    cSum -= Offer;
//                    lcSum.add(new Datas.Offers(cSum, true));
//                }else{
//                    lcSum.add(new Datas.Offers(cSum, false));
//                }
//                mSum += cSum;
//            }
//            StringBuilder msSum = new StringBuilder();
//            for (int i = 0; i < lcSum.size(); i++) {
//                if (lcSum.get(i).Number() > 0) {
//                    DecimalFormat mcSumFormat = new DecimalFormat("#,###,###");
//                    String mcurrentSum = mcSumFormat.format(lcSum.get(i).Number());
//                    if(lcSum.get(i).Offer()){
//                        mcurrentSum = "<font color='#78c936'>" + mcurrentSum + "</font>";
//                    }
//                    if(cPackage == i){
//                        mcurrentSum = "<u>" + mcurrentSum + "</u>";
//                    }
//                    msSum.append(mcurrentSum).append(" + ");
//                }else{
//                    String zero = "";
//                    if(cPackage == i){
//                        zero = "<u>T" + Integer.toString(i + 1) + "</u>";
//                    }else{
//                        zero = "T" + Integer.toString(i + 1);
//                    }
//                    msSum.append(zero).append(" + ");
//                }
//            }
//            if(msSum.length() > 3) {
//                msSum = new StringBuilder(msSum.substring(0, msSum.length() - 3));
//            }else{
//                msSum = new StringBuilder("0");
//            }
//            actMain.lblCurrentSum.setText(Html.fromHtml(msSum.toString()));
//
//            boolean avali = false, dovomi = false;
//            int avalii = 0, dovomii = 0;
//            Discount:
//            {
//                int ii = 0;
//                for (int i = 0; i < lDiscount_Choise.size(); i++) {
//                    int lDis1 = lDiscount_Choise.get(i).subservice_id_1();
//                    int lDis2 = lDiscount_Choise.get(i).subservice_id_2();
//                    Start:
//                    {
//                        for (int j = ii; j < actMain.lCounter.size(); j++) {
//                            Packager:
//                            {
//                                for (int k = 0; k < actMain.lCounter.get(j).lBaseItems().size(); k++) {
//                                    Inner:
//                                    {
//                                        if(actMain.lCounter.get(j).lBaseItems().get(k).isCheck()) {
//                                            int mDis = actMain.lCounter.get(j).lBaseItems().get(k).id();
//
//                                            if (lDis1 == mDis) {
//                                                if (!avali) {
//                                                    avali = true;
//                                                    avalii = mDis;
//                                                    ii++;
//                                                    break Packager;
//                                                } else if (!dovomi && avalii != mDis) {
//                                                    dovomi = true;
//                                                    dovomii = mDis;
//                                                    ii++;
//                                                    break Packager;
//                                                } else {
//                                                    if(!dovomi && avalii == mDis){
//
//                                                    }else{
//                                                        ii++;
//                                                        break Packager;
//                                                    }
//                                                }
//                                            }
//                                            if (lDis2 == mDis) {
//                                                if (!avali) {
//                                                    avali = true;
//                                                    avalii = mDis;
//                                                    ii++;
//                                                    break Packager;
//                                                } else if (!dovomi && avalii != mDis) {
//                                                    dovomi = true;
//                                                    dovomii = mDis;
//                                                    ii++;
//                                                    break Packager;
//                                                } else {
//                                                    if(!dovomi && avalii == mDis){
//
//                                                    }else{
//                                                        ii++;
//                                                        break Packager;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            String mainSum;
//            DecimalFormat mSumFormat = new DecimalFormat("#,###,###");
//            if(avali && dovomi){
////                mSum -= Offer;
//                mainSum = "<font color='#78c936'>" + mSumFormat.format(mSum) + "</font>";
//            }else{
//                mainSum = "<font color='#B2B2B2'>" + mSumFormat.format(mSum) + "</font>";
//                mDis1 = false;
//                mDis2 = false;
//            }
//            actMain.lblMainSum.setText(Html.fromHtml(mainSum));
        }catch (Exception ignored){}
        actMain.refreshServices();
    }
    private List<Datas.SkillPriceAdder> sorter(List<Datas.SkillPriceAdder> lList){
        if(lList.size() > 1) {
            for(int j=0; j<lList.size(); j++) {
                for (int i = 1; i < lList.size(); i++) {
                    if (lList.get(i).id() > lList.get(i - 1).id()) {
                        Datas.SkillPriceAdder data = new Datas.SkillPriceAdder();
                        data.id(lList.get(i).id());
                        data.price(lList.get(i).price());

                        lList.get(i).id(lList.get(i - 1).id());
                        lList.get(i).price(lList.get(i - 1).price());

                        lList.get(i - 1).id(data.id());
                        lList.get(i - 1).price(data.price());
                    }
                }
            }
        }
        return lList;
    }

//    @Override
//    public void onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(lList, fromPosition, toPosition);
//        mListChangedListener.onNoteListChanged(lList);
//        notifyItemMoved(fromPosition, toPosition);
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//
//    }

    private String getPriceCamma(int Price){
        String Pricer = "";
        try {
            String value = Integer.toString(Price);
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
        return Pricer;
    }
    public static class AdapterMember extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        private TextView lblPrice, lblPrice2, lblPriceNew, lblTitle, lblDescription;
        private AppCompatCheckBox chChoise;
        private CardView cardView;

        private AdapterMember(View itemView){
            super(itemView);

            lblPriceNew = itemView.findViewById(R.id.lblPriceNew);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            lblPrice2 = itemView.findViewById(R.id.lblPrice2);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            chChoise = itemView.findViewById(R.id.chChoise);
            cardView = itemView.findViewById(R.id.cardView);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }

//    private void setFadeAnimation(final View view) {
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                int mWidth = view.getWidth();
//
//                int start = ((mWidth * 2) -1);
//                TranslateAnimation animation1 = new TranslateAnimation(start, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//                animation1.setDuration(new Random().nextInt(1300 - 501) + 501);
//                view.startAnimation(animation1);
//            }
//        });
//    }

}
