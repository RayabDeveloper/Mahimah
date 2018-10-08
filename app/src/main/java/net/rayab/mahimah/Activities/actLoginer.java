package net.rayab.mahimah.Activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class actLoginer extends AppCompatActivity {

    Context context = this;
    WebApi wAPI = new WebApi();
    MySQLi SQL = new MySQLi(context);

    ImageView imgLogoGIF, imgLogoFa, imgLogoEn;
    RelativeLayout relMain;

    boolean SuccessLoad = false;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loginer);

        imgLogoGIF = (ImageView)findViewById(R.id.imgLogoGIF);
        imgLogoFa = (ImageView)findViewById(R.id.imgLogoFa);
        imgLogoEn = (ImageView)findViewById(R.id.imgLogoEn);

        relMain = (RelativeLayout)findViewById(R.id.relMain);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgLogoGIF);
        Glide.with(this).load(R.raw.logo_gif).into(imageViewTarget);

        SuccessLoading(7000);

        relMain.post(new Runnable() {
            @Override
            public void run() {
                int screenHeight = relMain.getHeight();
                int descriptionHeight = imgLogoFa.getHeight() + imgLogoEn.getHeight();
                int imgHeight = imgLogoGIF.getHeight();
                int targetY = screenHeight - imgHeight - (descriptionHeight * 5);
                ObjectAnimator anime = ObjectAnimator.ofFloat(imgLogoGIF, "y", 0, targetY);//"y" == mehvare Amudi ( Height ya Y ), 0 noghteye shoru, targetY noghteye payan.
                anime.setDuration(1500);
                anime.setInterpolator(new BounceInterpolator());//age in khat nabashe, sorat yeknavakht nist
                anime.start();

                width = relMain.getWidth();
                int start = (width * 2) * -1;
                int start2 = (width * 2);
                TranslateAnimation animation1 = new TranslateAnimation(start, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation1.setDuration(2000);
                imgLogoFa.startAnimation(animation1);

                TranslateAnimation animation2 = new TranslateAnimation(start2, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation2.setDuration(3000);
                imgLogoEn.startAnimation(animation2);
            }
        });

    }

    void SuccessLoading(int Delay){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                if(SuccessLoad) {
//                    Intent intent = new Intent(context, actMain.class);
//                    startActivity(intent);
                    Intent intent = new Intent(context, actMain.class);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) context, (View)imgLogoGIF, "logo");
//                    startActivity(intent, options.toBundle());
                    startActivity(intent);
                    finish();
                }else{
                    SuccessLoading(500);
                }
            }
        }, Delay);//time to Milisecond
    }

    @Override
    protected void onResume() {
        super.onResume();
        String Query = "SELECT * FROM TB_Services";
        List<Datas.Services> lServices = SQL.Select(Query, Datas.Services.class, context);
        if(lServices.size() > 0){
            Intent intent = new Intent(context, actMain.class);
            startActivity(intent);
            finish();
        }else{
            Loading();
        }
    }

    void Loading(){
        wAPI.API("https://mahimah.com/app/Getter.php");
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
                        try {
                            JSONObject DataObject = Result.getJSONObject("data");
                            JSONArray Services = DataObject.getJSONArray("services");
                            JSONArray SubServices = DataObject.getJSONArray("sub_services");
                            JSONArray SubServicesDiscount = DataObject.getJSONArray("sub_services_discount");

                            new Datas.parseService(context, Services);
                            new Datas.parseSubService(context, SubServices);
                            new Datas.parseSubServiceDiscount(context, SubServicesDiscount);

                            SuccessLoad = true;
                        }catch (Exception Ex){
                            String Er = Ex.getMessage();
                        }
                    }
                }, new WebApi.onResponseResultError() {
                    @Override
                    public void onResponseResultErrors(String Error) {
                        //String Er = Error;
                        Toast.makeText(context, Error, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
