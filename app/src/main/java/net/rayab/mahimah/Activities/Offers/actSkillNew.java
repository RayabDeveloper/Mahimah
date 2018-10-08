package net.rayab.mahimah.Activities.Offers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import net.rayab.mahimah.Adapter.adapServiceSkills;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

import java.util.ArrayList;
import java.util.List;

public class actSkillNew extends AppCompatActivity {

    Context context = this;
    MySQLi SQL;
    adapServiceSkills adapter;

    ScrollView scrollView;
    TextView lblSkillTitle;
    TextView lblSkillName;
    TextView lblSkillPrice;
    TextView lblSkillList;
    EditText txtSkillPrice;
    EditText txtSkillName;
    ImageView btnMenuIcon;
    ImageView btnAccept;
    ImageView btnCancell;
    LinearLayout linWidth;
    RelativeLayout lin;
    RelativeLayout RelSetting;
    CoordinatorLayout coordiantLayout;
    Toolbar toolbar;
    RecyclerView lstMain;
    RecyclerView.LayoutManager mLayoutManager;

    public static List<Datas.Skills> lSkill = new ArrayList<>();
    public static List<Datas.Services> lService = new ArrayList<>();

    public static String SkillName = "";
    public static int SkillId = 0;
    public static int position = 0;
    public static boolean Type = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skill_new);

        scrollView = findViewById(R.id.scrollView);
        lblSkillTitle = findViewById(R.id.lblSkillTitle);
        lblSkillName = findViewById(R.id.lblSkillName);
        lblSkillPrice = findViewById(R.id.lblSkillPrice);
        lblSkillList = findViewById(R.id.lblSkillList);
        txtSkillPrice = findViewById(R.id.txtSkillPrice);
        txtSkillName = findViewById(R.id.txtSkillName);
        btnMenuIcon = findViewById(R.id.btnMenuIcon);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancell = findViewById(R.id.btnCancell);
        linWidth = findViewById(R.id.linWidth);
        lin = findViewById(R.id.lin);
        RelSetting = findViewById(R.id.RelSetting);
        coordiantLayout = findViewById(R.id.coordiantLayout);
        toolbar = findViewById(R.id.toolbar);
        lstMain = findViewById(R.id.lstMain);
        mLayoutManager = new LinearLayoutManager(this);

        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblSkillTitle.setTypeface(tFace);
        lblSkillName.setTypeface(tFace);
        lblSkillPrice.setTypeface(tFace);
        lblSkillList.setTypeface(tFace);
        txtSkillPrice.setTypeface(tFace);
        txtSkillName.setTypeface(tFace);

        SQL = new MySQLi(context);
        lService = SQL.Select(Datas.getServices, Datas.Services.class, context);

        if(Type){
            for (Datas.Skills data : lSkill) {
                for (Datas.Services mData : lService) {
                    if(mData.id() == data.service_id()){
                        mData.isSelect(true);
                    }
                }
            }
            txtSkillName.setText(SkillName);
            try {
                String value = Integer.toString(position);
                String reverseValue = new StringBuilder(value).reverse().toString();
                StringBuilder finalValue = new StringBuilder();
                for (int i = 1; i <= reverseValue.length(); i++) {
                    char val = reverseValue.charAt(i - 1);
                    finalValue.append(val);
                    if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                        finalValue.append(",");
                    }
                }
                txtSkillPrice.setText(finalValue.reverse());
                txtSkillPrice.setSelection(finalValue.length());
            } catch (Exception e) {
                // Do nothing since not a number
            }
        }

        txtSkillPrice.addTextChangedListener(new TextWatcher() {
            boolean isManualChange = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }
                try {
                    String value = txtSkillPrice.getText().toString().replace(",", "");
                    String reverseValue = new StringBuilder(value).reverse().toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    isManualChange = true;
                    txtSkillPrice.setText(finalValue.reverse());
                    txtSkillPrice.setSelection(finalValue.length());
                } catch (Exception e) {
                    // Do nothing since not a number
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lSkill = new ArrayList<>();
                for (Datas.Services data : lService) {
                    if(data.isSelect()){
                        Datas.Skills mData = new Datas.Skills();
                        mData.name(txtSkillName.getText().toString());
                        int aa = 0;
                        try{
                            aa = Integer.parseInt(txtSkillPrice.getText().toString().replace(",", ""));
                        }catch (Exception ignored){}
                        mData.price(aa);
                        mData.service_id(data.id());

                        lSkill.add(mData);
                    }
                }
                if(lSkill.size() > 0) {
                    String Query = "";
                    if(!Type)
                        Query = "INSERT INTO TB_Skill (name, price) VALUES ('" + txtSkillName.getText().toString() + "', '" + txtSkillPrice.getText().toString().replace(",", "") + "')";
                    else
                        Query = "UPDATE TB_Skill SET name='" + txtSkillName.getText().toString() + "', price='" + txtSkillPrice.getText().toString().replace(",", "") + "' WHERE id='" + Integer.toString(SkillId) + "'";
                    SQL.Execute(Query);
                    Query = "DELETE FROM TB_SkillProducts WHERE skill_id='" + Integer.toString(SkillId) + "'";
                    SQL.Execute(Query);
                    int SkilleId = 0;
                    if(!Type)
                        SkilleId = SQL.LastId("TB_Skill");
                    else
                        SkilleId = SkillId;
                    for (Datas.Skills data : lSkill) {
                        Query = "INSERT INTO TB_SkillProducts (service_id, skill_id) VALUES ('" + Integer.toString(data.service_id()) + "', '" + Integer.toString(SkilleId) + "')";
                        SQL.Execute(Query);
                    }
                    Toast.makeText(context, "ثبت شد", Toast.LENGTH_LONG).show();
                    finish();
                }else
                    Toast.makeText(context, "حداقل یک سرویس نیاز است", Toast.LENGTH_LONG).show();
            }
        });

        RefreshService();
    }
    private void RefreshService(){
        adapter = new adapServiceSkills(lService, context);
//        lstMain.setNestedScrollingEnabled(false);
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for(int i=0; i<lService.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
        scrollView.smoothScrollTo(0, 0);
    }
}
