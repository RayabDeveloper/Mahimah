package net.rayab.mahimah.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import net.rayab.mahimah.Activities.Fragment.FragmentDialog;
import net.rayab.mahimah.Activities.Fragment.fragMainService;
import net.rayab.mahimah.Activities.Fragment.fragOffer;
import net.rayab.mahimah.Activities.Fragment.fragSkill;
import net.rayab.mahimah.Activities.Offers.actSkill;
import net.rayab.mahimah.Activities.Services.actGroups;
import net.rayab.mahimah.Activities.Services.actServices;
import net.rayab.mahimah.Adapter.adapCounter;
import net.rayab.mahimah.Adapter.adapFilter;
import net.rayab.mahimah.Adapter.adapMain;
import net.rayab.mahimah.Component.ReternRecyclerView;
import net.rayab.mahimah.Component.ShowHideView.MyRecyclerScroll;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.Request.WebApi;
import net.rayab.mahimah.SQL.MySQLi;
import net.rayab.mahimah.appExit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class actMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    static Context context;
    static MySQLi SQL;
    WebApi wAPI = new WebApi();
    static adapMain adapter;
    static adapFilter adapter2;
    adapCounter adapter3;
    static List<Datas.Services> lService = new ArrayList<>();
    List<Datas.SubServices> lSubService = new ArrayList<>();
    List<Datas.Services> lSubService_BaseNeeded = new ArrayList<>();
    List<Datas.SubServices> lSubService_BaseItem = new ArrayList<>();
    public List<Datas.MainItems> lBaseItems = new ArrayList<>();
    public List<Datas.MainItems> lBaseItems_Static = new ArrayList<>();
    public List<Datas.MainItems> lPackageItems = new ArrayList<>();
    public static List<Datas.Counter> lCounter = new ArrayList<>();
    private static List<Datas.Services> lFilter = new ArrayList<>();
    PersianCalendar[] lCalendar;
    Dialog mDialog;


    private static RecyclerView.LayoutManager mLayoutManager;

    TranslateAnimation animPackage, animAdd, animDelete, animFilter, animDate, animReport;
    private Animation fab_close;
    private Animation rotate_backward;

    static CoordinatorLayout coordiantLayout;//Delete Line
    static ImageView btnPackage, btnAdd, btnDelete, btnGroup, btnService, btnDate, btnReport;//btnFilter
    FloatingActionButton btnAccept;
    @SuppressLint("StaticFieldLeak")
    public static TextView lblMainSum, lblCurrentSum;
//    SwipeRefreshLayout swipeRefresher;
    HorizontalListView lstCount, lstFilter;
    //    PullToZoomRecyclerViewEx lstMain;
    static ReternRecyclerView lstMain;
    RecyclerView lstFilterGrid;
    static LinearLayout linFilter, linTool;
    public static LinearLayout linWidth;
    ImageView btnMenuIcon, imgSumer;
    DrawerLayout drawer;
    RelativeLayout RelSetting, relTool, relT;
    NavigationView navigationView;
    TextView lblGroup, lblService, lblSetting, lblTime, lblChoise,
            lblService1, lblService2, lblOfferPrice, lblTime2;
    EditText txtOfferPrice;

    private Boolean isFabOpen = true, FilterOpener = false;
    static int filterID = 270278027, isPackageID = 270278027;
    public static int cIndex = 0;
    boolean mDis1 = false;

    int xSetting = 0;
    private Animation animShow, animHide;
    private static boolean isDown = false, isUp = false;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        swipeRefresher = findViewById(R.id.swipeRefresher);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        context = this;
        appExit.act9 = context;

        SQL = new MySQLi(context);

        toolbar.setNavigationIcon(R.drawable.header);

        Menu nav_Menu = navigationView.getMenu();
        navigationView.showContextMenu();
        toolbar.getMenu().clear();

        lblSetting = findViewById(R.id.lblSetting);
        lblTime2 = findViewById(R.id.lblTime);
        btnDate = findViewById(R.id.btnDate);
        btnReport = findViewById(R.id.btnReport);
        lblChoise = findViewById(R.id.lblChoise);
        lblService1 = findViewById(R.id.lblService1);
        lblService2 = findViewById(R.id.lblService2);
        lblOfferPrice = findViewById(R.id.lblSkillList);
        txtOfferPrice = findViewById(R.id.txtSkillPrice);
        btnAccept = findViewById(R.id.btnAccept);

        RelSetting = findViewById(R.id.RelSetting);
        relTool = findViewById(R.id.relTool);
        relT = findViewById(R.id.relT);

        lblGroup = findViewById(R.id.lblSkill);
        lblService = findViewById(R.id.lblService);
        lblSetting = findViewById(R.id.lblSetting);
        lblTime = findViewById(R.id.lblTime);
        lblChoise = findViewById(R.id.lblChoise);

        btnPackage = findViewById(R.id.btnPackage);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
//        btnFilter = findViewById(R.id.btnFilter);
        btnGroup = findViewById(R.id.btnSetting2);
        btnService = findViewById(R.id.btnSetting3);

        lblMainSum = findViewById(R.id.lblMainSum);
        lblCurrentSum = findViewById(R.id.lblCurrentSum);

        lstCount = findViewById(R.id.lstCount);
        lstFilter = findViewById(R.id.lstFilter);

        lstMain = findViewById(R.id.lstMain);
        lstFilterGrid = findViewById(R.id.lstFilterGrid);

        linFilter = findViewById(R.id.linFilter);
        linWidth = findViewById(R.id.linWidth);
        linTool = findViewById(R.id.linTool);

        btnMenuIcon = findViewById(R.id.btnMenuIcon);
        imgSumer = findViewById(R.id.imgSumer);

        coordiantLayout = findViewById(R.id.coordiantLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        RelSetting.post(new Runnable() {//TODO Ina chie ????? < xSetting xDate ina hich ja estefade nashode , Pak she bebin Okeye ?
//            @Override
//            public void run() {
//                xSetting = (RelSetting.getWidth()) * -1;
//                animPackage = new TranslateAnimation(xSetting, 0, 0, 0);
//                animPackage.setDuration(150);
//                animAdd = new TranslateAnimation(xSetting, 0, 0, 0);
//                animAdd.setDuration(300);
//                animDelete = new TranslateAnimation(xSetting, 0, 0, 0);
//                animDelete.setDuration(450);
//                animFilter = new TranslateAnimation(xSetting, 0, 0, 0);
//                animFilter.setDuration(600);
//                animDate = new TranslateAnimation(xSetting, 0, 0, 0);
//                animDate.setDuration(750);
//                animReport = new TranslateAnimation(xSetting, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//                animReport.setDuration(900);
//            }
//        });

//        linTool.setVisibility(View.VISIBLE);
//        linWidth.setVisibility(View.VISIBLE);
//        isUp = false;
//        lstMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    // Scrolling up
//                    slideUp(linWidth);
//                } else {
//                    // Scrolling down
//                    slideDown(linWidth);
//                }
//            }
//        });

        //todo injas
//        animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
//        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);
//        lstMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(dy > 0){
//                    linTool.startAnimation(animHide);
//                    linTool.setVisibility(View.GONE);
//                }else{
//                    linTool.setVisibility(View.VISIBLE);
//                    linTool.startAnimation( animShow );
//                }
//            }
//        });
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        linWidth.startAnimation(animation);
        String asd = "ASD";
        lstMain.setOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                linWidth.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                int a = adapter.getItemCount();
                linWidth.animate().translationY(linWidth.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });
//        lstMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 5) {
//                    slideUp(linWidth);
//                } else if (dy < -5) {
//                    slideDown(linWidth);
//                }
//            }
//        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar PC = new PersianCalendar();
                DatePickerDialog DateDialog;
                DateDialog = DatePickerDialog.newInstance(
                        actMain.this,
                        PC.getPersianYear(),
                        PC.getPersianMonth(),
                        PC.getPersianDay()
                );
                DateDialog.setHighlightedDays(lCalendar);
                DateDialog.show(getFragmentManager(), "Checker");
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                actTest.lCounter = lCounter;
//                Intent intent = new Intent(context, actTest.class);
//                startActivity(intent);

                //injams

                FragmentManager fm = getSupportFragmentManager();
                FragmentDialog overlay = new FragmentDialog();
                FragmentDialog.FragmentDialoge(context, lCounter);
                overlay.show(fm, "FragmentDialog");


//                mDialog = new Dialog(context);
//                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                mDialog.setCancelable(true);
//                mDialog.setCanceledOnTouchOutside(true);
//                mDialog.setContentView(R.layout.frag_dialog_report);
//                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                ViewPager viewPagerr = mDialog.findViewById(R.id.viewPagerr);
//
//                FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), getFragments("a", "a", "a"));
//                viewPagerr.setAdapter(adapter);
//
//                mDialog.show();
            }
        });

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        Animation rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);


//        final RecyclerViewHeaderAdapter mAdapter = new RecyclerAdapterCustom(this);
//
//        final GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getItemViewType(position) == RecyclerViewHeaderAdapter.INT_TYPE_HEADER ? 2 : 1;
//            }
//        });
//        adapter = new adapMain()
//        lstMain.setAdapterAndLayoutManager(mAdapter, manager);
//
//        //lstMain.setLayoutManager(new LinearLayoutManager(context)); todo puriya
        init();
        initialize();
        initializeCounter();
        setupRecyclerView();
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lFilter = SQL.Select(Query, Datas.Services.class, context);
//        Datas.Services mService = new Datas.Services();
//        mService.title("بدون فیلتر");
//        lService.add(0, mService);
        adapter2 = new adapFilter(context, lService);
        lstFilterGrid.setHasFixedSize(true);
        lstFilterGrid.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
        lstFilterGrid.setAdapter(adapter2);
        lstFilterGrid.setItemAnimator(new DefaultItemAnimator());
        lstFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

//        swipeRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefresher.setRefreshing(false);
//            }
//        });
//        lstFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    lCounter.get(cIndex).filterID(270278027);
//                    filterID = 270278027;
//                } else {
//                    filterID = lFilter.get(position - 1).id();
//                    lCounter.get(cIndex).filterID(filterID);
//                }
//                if (lCounter.size() > 1) {
//                    if (position > 0) {
//                        comingPKG(lCounter.get(cIndex).filterID());
//                    } else {
//                        unFilter();
//                    }
//                } else {
//                    if (position > 0) {
//                        comingPKG3(lFilter.get(position - 1).id());
//                    } else {
//                        unFilter();
//                    }
//                }
//                reNewFilterList(lService, position);
//            }
//        });
//        btnFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animateFAB2();
//                if(!FilterOpener) {// From Close to Open
//                    linFilter.setVisibility(View.VISIBLE);
//                    int width = linWidth.getWidth();
//                    int start = (width * 2) * -1;
//                    TranslateAnimation animation1 = new TranslateAnimation(start, 0, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//                    animation1.setDuration(500);
//                    linFilter.startAnimation(animation1);
//                    FilterOpener = true;
//                }else{// From Open to Close
//                    int width = linWidth.getWidth();
//                    int start = (width * 2) * -1;
//                    TranslateAnimation animation1 = new TranslateAnimation(0, start, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//                    animation1.setDuration(500);
//                    animation1.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            linFilter.setVisibility(View.INVISIBLE);
//                        }
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    linFilter.startAnimation(animation1);
//                    FilterOpener = false;
//                }
//            }
//        });
        btnPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecker = false;
                String Query = "SELECT * FROM TB_Services ORDER BY position";
                lService = SQL.Select(Query, Datas.Services.class, context);
                for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
                    if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                        isChecker = true;
                    }
                }
                if (isChecker) {
                    if (lCounter.get(cIndex).isPackage()) {
                        isPackageID = lCounter.get(cIndex).filterID();
                        lCounter.get(cIndex).isPackageID(isPackageID);
                        lCounter.get(cIndex).isPackage(false);
                        comingPKG2(lCounter.get(cIndex).filterID());
                        btnPackage.setImageResource(0);
                        btnPackage.setImageResource(R.drawable.package_variant);
                    } else {
                        isPackageID = lCounter.get(cIndex).filterID();
                        lCounter.get(cIndex).isPackageID(isPackageID);
                        lCounter.get(cIndex).isPackage(true);
                        comingPKG2(lCounter.get(cIndex).filterID());
                        btnPackage.setImageResource(0);
                        btnPackage.setImageResource(R.drawable.package_variant_closed);
                    }
                } else {
                    Snackbar name = Snackbar.make(coordiantLayout, "هیچ موردی انتخاب نشده", Snackbar.LENGTH_SHORT);
                    name.show();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lCounter.size() < 6) {
                    initialize();
                    initializeCounter();
                    RefreshCount(lCounter.size() - 1);
                    cIndex = lCounter.size() - 1;
//                    unFilter();
                    CalculateAgain();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFrist = false;
                for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
                    if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                        isFrist = true;
                        lCounter.get(cIndex).lBaseItems().get(i).isCheck(false);
                    }
                }
                for (int j = 0; j < lCounter.get(cIndex).lPackageItems().size(); j++) {
                    lCounter.get(cIndex).lPackageItems().get(j).isCheck(false);
                }
                if (isFrist) {
                    isPackageID = lCounter.get(cIndex).filterID();
                    comingPKG4(filterID);
                    btnPackage.setImageResource(0);
                    btnPackage.setImageResource(R.drawable.package_variant);
                    lCounter.get(cIndex).isPackage(false);

                    CalculateAgain();
                } else {
                    //Remove Current Tab , Or if Tab is 0 Nothing
                    if (lCounter.size() > 1) {
                        if ((lCounter.size() - 1) > cIndex) {
                            lCounter.remove(cIndex);
                        } else if ((lCounter.size() - 1) == cIndex) {
                            if (lCounter.size() == 1) {
                                lCounter.remove(cIndex);
                                cIndex = 0;
                            } else {
                                lCounter.remove(cIndex);
                                cIndex--;
                            }
                        }
                        lBaseItems = new ArrayList<>(lCounter.get(cIndex).lBaseItems());
                        lPackageItems = new ArrayList<>(lCounter.get(cIndex).lPackageItems());
                        initialize4(lPackageItems);
                        if (!lCounter.get(cIndex).isPackage()) {
                            btnPackage.setImageResource(0);
                            btnPackage.setImageResource(R.drawable.package_variant_closed);
                        } else {
                            btnPackage.setImageResource(0);
                            btnPackage.setImageResource(R.drawable.package_variant);
                        }
                        RefreshCount(cIndex);
                        comingPKG01(lCounter.get(cIndex).filterID());
                    }
                    isPackageID = lCounter.get(cIndex).filterID();
                    CalculateAgain();
                }
//                adapter2.notifyDataSetChanged();
            }
        });
        lstCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cIndex = position;
                RefreshCount(cIndex);
                comingPKG01(lCounter.get(cIndex).filterID());
                CalculateAgain();
            }
        });
        btnMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        wAPI.setOnIncomingResult(new WebApi.onResponseResult2() {
            @Override
            public void onResponseResults2(String Result) {
                try {
                    JSONObject jo = new JSONObject(Result);
                    JSONArray ja = jo.getJSONArray("Dates");
                    lCalendar = new PersianCalendar[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jjo = ja.getJSONObject(i);
                        String date = jjo.getString("date");
                        String[] dates = date.split("-");
                        int y = Integer.parseInt(dates[0]);
                        int m = Integer.parseInt(dates[1]);
                        int d = Integer.parseInt(dates[2]);

                        PersianCalendar pc = new PersianCalendar();
                        pc.set(y, m - 1, d);
                        lCalendar[i] = pc;
                    }
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
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

            }
        });
    }

//    public void slideDown(final View view) {
//        if(isDown) {
//            view.setVisibility(View.VISIBLE);
//            Animation animate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_show);
//            animate.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    isDown = false;
//                }
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
//            view.startAnimation(animate);
//        }
//    }
//
//    public void slideUp(final View view) {
//        if(!isDown) {
//            Animation animate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_hide);
//            animate.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    view.setVisibility(View.GONE);
//                    isDown = true;
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
//            view.startAnimation(animate);
//        }
//    }

    public static void slideUp(final View view) {
        if(isDown) {
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,                 // fromYDelta
                    view.getHeight()); // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    isDown = false;
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animate);
        }
    }

    public static void slideDown(final View view) {
        if(!isDown) {
            view.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    view.getHeight(),  // fromYDelta
                    0);                // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    isDown = true;
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animate);
        }
    }

    static boolean isResume = false;

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        initializeCounter();
        RefreshCount(cIndex);
        CalculateAgain();

        lFilter = new ArrayList<>();
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lFilter = SQL.Select(Query, Datas.Services.class, context);
//        Datas.Services mService = new Datas.Services();
//        mService.title("بدون فیلتر");
//        lService.add(0, mService);
        adapter2 = new adapFilter(context, lService);
        lstFilterGrid.setAdapter(adapter2);

        init();
        initialize();
//        unFilter();

        Map<String, String> mValues = new HashMap<>();
        mValues.put("MP", "Bilakh");
        wAPI.API2("https://mahimah.com/app/getForbideDate.php", mValues);

        lstFilterClick(0);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.nav_group) {
            Intent intent = new Intent(context, actGroups.class);
            startActivity(intent);
        } else if (id == R.id.nav_service) {
            Intent intent = new Intent(context, actServices.class);
            startActivity(intent);
        } else if (id == R.id.nav_offer_choise) {
            Intent intent = new Intent(context, actSkill.class);
            startActivity(intent);
        } else if (id == R.id.nav_dataTransporter) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_datatransporter);

            final TextView lbl = dialog.findViewById(R.id.lblHavij);
            final EditText txt = dialog.findViewById(R.id.txtHavij);
            final Button btnY = dialog.findViewById(R.id.btnYes);
            final Button btnN = dialog.findViewById(R.id.btnNo);

            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
            lbl.setTypeface(tf);
            txt.setTypeface(tf);
            btnY.setTypeface(tf);
            btnN.setTypeface(tf);

            btnY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt.getText().toString().equalsIgnoreCase("Havij")) {
                        Map<String, String> keyVal = new HashMap<>();
                        keyVal.put("key", "Havij");
                        WebApi wAPI = new WebApi();
                        wAPI.API("https://mahimah.com/app/DataTransformer.php", keyVal);

                        wAPI.setOnIncomingResult(new WebApi.onResponseResult2() {
                            @Override
                            public void onResponseResults2(String Result) {
                                String Query = "";
                                Query = "DELETE FROM TB_Services";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_SubServices";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_SubServicesDiscount";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferTime";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferChoise";
                                SQL.Execute(Query);
                                Query = "DELETE FROM TB_OfferPrice";
                                SQL.Execute(Query);

                                Toast.makeText(context, "Data is Reset, Please reOpen Application", Toast.LENGTH_LONG).show();
                                appExit.ExitApp();
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
                                Toast.makeText(context, "Error On reNew Data, Contact Us Only :D", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Snackbar name = Snackbar.make(coordiantLayout, "Please Enter 'Havij' in Box", Snackbar.LENGTH_SHORT);
                        name.show();
                    }
                }
            });

            btnN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            isResume = true;
        }
    }

    public void animateFAB2() {
        if (isFabOpen) {
            btnPackage.startAnimation(fab_close);
            btnAdd.startAnimation(fab_close);
            btnDelete.startAnimation(fab_close);
            btnDate.startAnimation(fab_close);
            btnReport.startAnimation(fab_close);
//            btnFilter.startAnimation(rotate_backward);
            btnPackage.setClickable(false);
            btnAdd.setClickable(false);
            btnDelete.setClickable(false);
            btnDate.setClickable(false);
            btnReport.setClickable(false);
            isFabOpen = false;
        } else {//isClose
            btnPackage.startAnimation(animPackage);
            btnAdd.startAnimation(animAdd);
            btnDelete.startAnimation(animDelete);
//            btnFilter.startAnimation(animFilter);
            btnDate.startAnimation(animDate);
            btnReport.startAnimation(animDate);
//            btnFilter.startAnimation(rotate_backward);
            btnPackage.setClickable(true);
            btnAdd.setClickable(true);
            btnDelete.setClickable(true);
            btnDate.setClickable(true);
            btnReport.setClickable(true);

            btnPackage.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
//            btnFilter.setVisibility(View.VISIBLE);
            btnDate.setVisibility(View.VISIBLE);
            btnReport.setVisibility(View.VISIBLE);

            isFabOpen = true;
        }
    }

    private void init() {//lBaseItems_Static
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);

        int position = 0;
        lBaseItems_Static = new ArrayList<>();
        for (int j = 0; j < lService.size(); j++) {
            Query = "SELECT * FROM TB_SubServices WHERE service_id='" + lService.get(j).id() + "' ORDER BY position";
            lSubService_BaseItem = SQL.Select(Query, Datas.SubServices.class, context);
            for (int i = 0; i < lSubService_BaseItem.size(); i++) {
                try {
                    Query = "SELECT * FROM TB_Services WHERE id='" + lSubService_BaseItem.get(i).service_id() + "' ORDER BY position";
//                Query = "SELECT * FROM TB_Services ORDER BY position";
                    lSubService_BaseNeeded = new ArrayList<>();
                    lSubService_BaseNeeded = SQL.Select(Query, Datas.Services.class, context);

                    String Title = lSubService_BaseNeeded.get(0).title().replace("|", "") + " | " + lSubService_BaseItem.get(i).title();
                    String Price = lSubService_BaseItem.get(i).price();
                    String PriceNew = lSubService_BaseItem.get(i).pricenew();
                    String Description = lSubService_BaseItem.get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lSubService_BaseItem.get(i).id());
                    Item.service_id(lSubService_BaseItem.get(i).service_id());

                    lBaseItems_Static.add(Item);
                    position++;
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void initialize() {
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        Query = "SELECT * FROM TB_SubServices WHERE service_id='" + lService.get(0).id() + "' ORDER BY position";
        lSubService = SQL.Select(Query, Datas.SubServices.class, context);
        Query = "SELECT * FROM TB_SubServices ORDER BY position";
        lSubService_BaseItem = SQL.Select(Query, Datas.SubServices.class, context);

        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int j = 0; j < lSubService.size(); j++) {
            String Title = lService.get(0).title().replace("|", "") + " | " + lSubService.get(j).title();
            String Price = lSubService.get(j).price();
            String PriceNew = lSubService.get(j).pricenew();
            String Description = lSubService.get(j).description();

            Datas.MainItems Item = new Datas.MainItems();
            Item.title(Title);
            Item.description(Description);
            Item.price(Price);
            Item.pricenew(PriceNew);
            Item.position(position);
            Item.id(lSubService.get(j).id());
            Item.service_id(lSubService.get(j).service_id());
            lPackageItems.add(Item);
            position++;
        }
        lBaseItems = new ArrayList<>();
        for (int i = 0; i < lSubService_BaseItem.size(); i++) {
            try {
                Query = "SELECT * FROM TB_Services WHERE id='" + lSubService_BaseItem.get(i).service_id() + "' ORDER BY position";
                lSubService_BaseNeeded = new ArrayList<>();
                lSubService_BaseNeeded = SQL.Select(Query, Datas.Services.class, context);

                String Title = lSubService_BaseNeeded.get(0).title().replace("|", "") + " | " + lSubService_BaseItem.get(i).title();
                String Price = lSubService_BaseItem.get(i).price();
                String PriceNew = lSubService_BaseItem.get(i).pricenew();
                String Description = lSubService_BaseItem.get(i).description();

                Datas.MainItems Item = new Datas.MainItems();
                Item.title(Title);
                Item.description(Description);
                Item.price(Price);
                Item.pricenew(PriceNew);
                Item.position(position);
                Item.id(lSubService_BaseItem.get(i).id());
                Item.service_id(lSubService_BaseItem.get(i).service_id());

                lBaseItems.add(Item);
                position++;
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void unFilter() {
        try {
            String Query = "SELECT * FROM TB_Services ORDER BY position";
            lService = SQL.Select(Query, Datas.Services.class, context);
            int position = 0;
            lPackageItems = new ArrayList<>();
            for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
                if (lCounter.get(cIndex).isPackage()) {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                } else {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            }
            if (lCounter.get(cIndex).isPackage()) {
                btnPackage.setImageResource(0);
                btnPackage.setImageResource(R.drawable.package_variant_closed);
            } else {
                btnPackage.setImageResource(0);
                btnPackage.setImageResource(R.drawable.package_variant);
            }

            lstMain.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            lstMain.setLayoutManager(mLayoutManager);
//            lstMain.setReturningView(linWidth);
            adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
            lstMain.setItemAnimator(new DefaultItemAnimator());
            lstMain.setAdapter(adapter);
            for (int i = 0; i < lPackageItems.size(); i++) {
                lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }

    private void comingPKG(int filter) {
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        int pkgID;
        pkgID = lCounter.get(cIndex).isPackageID();
        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
            if (lCounter.get(cIndex).lBaseItems().get(i).service_id() == filter) {
                if (lCounter.get(cIndex).isPackage() && pkgID == filter) {
                    if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                        String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                        String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                        String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                        String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                        Datas.MainItems Item = new Datas.MainItems();
                        Item.title(Title);
                        Item.description(Description);
                        Item.price(Price);
                        Item.pricenew(PriceNew);
                        Item.position(position);
                        Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                        Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                        Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                        lPackageItems.add(Item);
                        position++;
                    }
                } else {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            }
        }
        if (lCounter.get(cIndex).isPackage()) {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant_closed);
        } else {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant);
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void comingPKG01(int filter) {
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        int pkgID;
        pkgID = lCounter.get(cIndex).isPackageID();
        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
            if (lCounter.get(cIndex).isPackage() && pkgID == filter) {
                if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            } else {
                String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                Datas.MainItems Item = new Datas.MainItems();
                Item.title(Title);
                Item.description(Description);
                Item.price(Price);
                Item.pricenew(PriceNew);
                Item.position(position);
                Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                lPackageItems.add(Item);
                position++;
            }
        }
        if (lCounter.get(cIndex).isPackage()) {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant_closed);
        } else {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant);
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void comingPKG2(int filter) {
        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
            if (lCounter.get(cIndex).isPackage() && (lCounter.get(cIndex).filterID() == filter)) {
                if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            } else {
                String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                Datas.MainItems Item = new Datas.MainItems();
                Item.title(Title);
                Item.description(Description);
                Item.price(Price);
                Item.pricenew(PriceNew);
                Item.position(position);
                Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                lPackageItems.add(Item);
                position++;
            }
        }
        if (lCounter.get(cIndex).isPackage()) {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant_closed);
        } else {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant);
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void comingPKG4(int filter) {
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        int pkgID;
        pkgID = isPackageID;
        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
            if (lCounter.get(cIndex).lBaseItems().get(i).service_id() == filter) {
                if (lCounter.get(cIndex).isPackage() && (pkgID == filter)) {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                } else {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            }
        }
        if (lCounter.get(cIndex).isPackage()) {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant_closed);
        } else {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant);
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void comingPKG3(int filter) {
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);
        int pkgID;
        pkgID = 123123;
        int position = 0;
        lPackageItems = new ArrayList<>();
        for (int i = 0; i < lCounter.get(cIndex).lBaseItems().size(); i++) {
            if (lCounter.get(cIndex).lBaseItems().get(i).service_id() == filter) {
                if (!lCounter.get(cIndex).isPackage() && filter == pkgID) {
                    if (lCounter.get(cIndex).lBaseItems().get(i).isCheck()) {
                        String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                        String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                        String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                        String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                        Datas.MainItems Item = new Datas.MainItems();
                        Item.title(Title);
                        Item.description(Description);
                        Item.price(Price);
                        Item.pricenew(PriceNew);
                        Item.position(position);
                        Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                        Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                        Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                        lPackageItems.add(Item);
                        position++;
                    }
                } else {
                    String Title = lCounter.get(cIndex).lBaseItems().get(i).title();
                    String Price = lCounter.get(cIndex).lBaseItems().get(i).price();
                    String PriceNew = lCounter.get(cIndex).lBaseItems().get(i).pricenew();
                    String Description = lCounter.get(cIndex).lBaseItems().get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.description(Description);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.position(position);
                    Item.id(lCounter.get(cIndex).lBaseItems().get(i).id());
                    Item.service_id(lCounter.get(cIndex).lBaseItems().get(i).service_id());
                    Item.isCheck(lCounter.get(cIndex).lBaseItems().get(i).isCheck());

                    lPackageItems.add(Item);
                    position++;
                }
            }
        }
        if (lCounter.get(cIndex).isPackage()) {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant_closed);
        } else {
            btnPackage.setImageResource(0);
            btnPackage.setImageResource(R.drawable.package_variant);
        }

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void initialize4(List<Datas.MainItems> lMainItemPackage) {
//        for(int i=0; i<lMainItemPackage.size(); i++){
//            lMainItemPackage.get(i).isCheck(true);
//        }
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lMainItemPackage, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lMainItemPackage.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void setupRecyclerView() {
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstMain.setLayoutManager(mLayoutManager);
//        lstMain.setReturningView(linWidth);
        adapter = new adapMain(lPackageItems, context, cIndex);
//        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .colorResId(R.color.colorWhite)
//                .size(2)
//                .build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        for (int i = 0; i < lPackageItems.size(); i++) {
            lstMain.removeItemDecoration(lstMain.getItemDecorationAt(i));
        }
    }

    private void initializeCounter() {
        Datas.Counter Counter = new Datas.Counter();
        Counter.isPackage(false);
        Counter.filterID(270278027);
        Counter.isPackageID(isPackageID);

        lService = new ArrayList<>();
        lSubService = new ArrayList<>();
        String Query = "SELECT * FROM TB_Services ORDER BY position";
        lService = SQL.Select(Query, Datas.Services.class, context);

        int position = 0;
        lBaseItems_Static = new ArrayList<>();
        for (int j = 0; j < lService.size(); j++) {
            Query = "SELECT * FROM TB_SubServices WHERE service_id='" + lService.get(j).id() + "' ORDER BY position";
            lSubService_BaseItem = SQL.Select(Query, Datas.SubServices.class, context);
            for (int i = 0; i < lSubService_BaseItem.size(); i++) {
                try {
                    Query = "SELECT * FROM TB_Services WHERE id='" + lSubService_BaseItem.get(i).service_id() + "' ORDER BY position";
//                Query = "SELECT * FROM TB_Services ORDER BY position";
                    lSubService_BaseNeeded = new ArrayList<>();
                    lSubService_BaseNeeded = SQL.Select(Query, Datas.Services.class, context);

                    String Title = lSubService_BaseNeeded.get(0).title().replace("|", "") + " | " + lSubService_BaseItem.get(i).title();
                    String Price = lSubService_BaseItem.get(i).price();
                    String PriceNew = lSubService_BaseItem.get(i).pricenew();
                    String Description = lSubService_BaseItem.get(i).description();

                    Datas.MainItems Item = new Datas.MainItems();
                    Item.title(Title);
                    Item.price(Price);
                    Item.pricenew(PriceNew);
                    Item.description(Description);
                    Item.position(position);
                    Item.id(lSubService_BaseItem.get(i).id());
                    Item.service_id(lSubService_BaseItem.get(i).service_id());

                    lBaseItems_Static.add(Item);
                    position++;
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
            }
        }

        Counter.lBaseItems(new ArrayList<>(lBaseItems_Static));
        Counter.lPackageItems(new ArrayList<>(lBaseItems_Static));

        if (!isResume) {
            lCounter.add(Counter);
        } else {
            lCounter.get(lCounter.size() - 1).lBaseItems(Counter.lBaseItems());
            lCounter.get(lCounter.size() - 1).lPackageItems(Counter.lPackageItems());
            isResume = false;
        }
        adapter3 = new adapCounter(context, lCounter, cIndex);
        lstCount.setAdapter(adapter3);

        initialize();
    }

    private void RefreshCount(int cIndexer) {
        adapter3 = new adapCounter(context, lCounter, cIndexer);
        lstCount.setAdapter(adapter3);
    }

    private void CalculateAgain() {
        try {
            MySQLi SQL = new MySQLi(context);
//            List<Datas.OfferPrice> lOffers;
//            String Query = "SELECT * FROM TB_OfferPrice";
//            lOffers = SQL.Select(Query, Datas.OfferPrice.class, context);
//            int Offer = lOffers.get(0).offer_price();
//            List<Datas.SubServicesDiscount> lDiscount_Time;
//            List<Datas.SubServicesDiscount> lDiscount_Choise;
//            Query = "SELECT * FROM TB_OfferTime";
//            lDiscount_Time = SQL.Select(Query, Datas.SubServicesDiscount.class, context);
//            Query = "SELECT * FROM TB_OfferChoise";
//            lDiscount_Choise = SQL.Select(Query, Datas.SubServicesDiscount.class, context);

            int cSum = 0, mSum = 0;
            List<Datas.SkillPriceAdder> SkillPrice = new ArrayList<>();
            List<String> lcSum = new ArrayList<>();
            for (Datas.Counter data : lCounter) {
                cSum = 0;
                SkillPrice = new ArrayList<>();
                List<Datas.Skills> lSkill = new ArrayList<>();
                List<Datas.PriceNew> lPriceNew = new ArrayList<>();
                for (Datas.MainItems mData : data.lBaseItems()) {
                    if (mData.isCheck()) {
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
                for (int i = 0; i < SkillPrice.size(); i++) {
                    List<Integer> lPrice = new ArrayList<>();
                    if (SkillPrice.get(i).id() != a) {
                        isA = true;
                        a = SkillPrice.get(i).id();
                    } else
                        isA = false;
                    if (isA) {
                        for (int j = 0; j < SkillPrice.size(); j++) {
                            if (SkillPrice.get(j).id() == a) {
                                lPrice.add(SkillPrice.get(j).price());
                            }
                        }
                        if (lPrice.size() > 0)
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
            if (cSummer.length() > 1)
                cSummer = cSummer.substring(0, cSummer.length() - 3);
            if (cSummer.substring(0, 1).equalsIgnoreCase("0"))
                cSummer = cSummer.substring(1);
            actMain.lblCurrentSum.setText(cSummer);
            lblMainSum.setText(getPriceCamma(mSum));
//            for (int i = 0; i < lCounter.size(); i++) {
//                cSum = 0;
//                for (int j = 0; j < lCounter.get(i).lBaseItems().size(); j++) {
//                    if (lCounter.get(i).lBaseItems().get(j).isCheck()) {
//                        String mPricer = "";
//                        String priceNew = lCounter.get(i).lBaseItems().get(j).pricenew().replace(",", "");
//                        if(priceNew.length() == 0)
//                            priceNew = "0";
//                        if(Integer.parseInt(priceNew) < 2)
//                            mPricer = lCounter.get(i).lBaseItems().get(j).price().replace(",", "");
//                        else
//                            mPricer = priceNew;
//                        String Price = mPricer;
//                        cSum += Integer.parseInt(Price);
//                    }
//                }
//                boolean cDis1 = false, cDis2 = false;
//                for (int j = 0; j < lDiscount_Time.size(); j++) {
//                    for (int k = 0; k < lCounter.get(i).lBaseItems().size(); k++) {
//                        if(lCounter.get(i).lBaseItems().get(k).isCheck()) {
//                            if (lCounter.get(i).lBaseItems().get(k).id() == lDiscount_Time.get(j).subservice_id_1()) {
//                                cDis1 = true;
//                            }
//                            if (lCounter.get(i).lBaseItems().get(k).id() == lDiscount_Time.get(j).subservice_id_2()) {
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
//                    if(cIndex == i){
//                        mcurrentSum = "<u>" + mcurrentSum + "</u>";
//                    }
//                    msSum.append(mcurrentSum).append(" + ");
//                }else{
//                    String zero = "";
//                    if(cIndex == i){
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
//            lblCurrentSum.setText(Html.fromHtml(msSum.toString()));
//
//            boolean md2 = false, distrue = false;
//            int p1 = 200, p2 = 300;
//            for (int i = 0; i < lDiscount_Choise.size(); i++) {
//                for (int j = 0; j < lCounter.size(); j++) {
//                    search:
//                    {
//                        for (int k = 0; k < lCounter.get(j).lBaseItems().size(); k++) {
//                            if (lCounter.get(j).lBaseItems().get(k).isCheck()) {
//                                if (!mDis1) {
//                                    if (lCounter.get(j).lBaseItems().get(k).id() == lDiscount_Choise.get(i).subservice_id_1()) {
//                                        if(p1 != p2) {
//                                            mDis1 = true;
//                                            p1 = j;
//                                            break search;
//                                        }
//                                    }
//                                }else{
//                                    distrue = true;
//                                }
//                                if (lCounter.get(j).lBaseItems().get(k).id() == lDiscount_Choise.get(i).subservice_id_2()) {
//                                    if(p1 != p2) {
//                                        md2 = true;
//                                        p2 = j;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if(!distrue){
//                mDis1 = false;
//            }
//            String mainSum;
//            DecimalFormat mSumFormat = new DecimalFormat("#,###,###");
//            if(mDis1 && md2 && (p1 != p2)){
////                mSum -= Offer;
//                mainSum = "<font color='#78c936'>" + mSumFormat.format(mSum) + "</font>";
//            }else{
//                mainSum = "<font color='#B2B2B2'>" + mSumFormat.format(mSum) + "</font>";
//            }
//            lblMainSum.setText(Html.fromHtml(mainSum));
        } catch (Exception ignored) {
        }
        refreshServices();
    }

    private List<Datas.SkillPriceAdder> sorter(List<Datas.SkillPriceAdder> lList) {
        if (lList.size() > 1) {
            for (int j = 0; j < lList.size(); j++) {
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

    private String getPriceCamma(int Price) {
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

    private static void reNewFilterList(List<Datas.Services> lList, int position) {
//        if (!lList.get(0).title().equals("بدون فیلتر")) {
//            Datas.Services mService = new Datas.Services();
//            mService.title("بدون فیلتر");
//            lList.add(0, mService);
//        }
        lList.get(position).isSelect(true);
        adapter2.updateResults(lList);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    private List<Fragment> getFragments(String Main, String Offer, String Skill) {
        List<Fragment> fList = new ArrayList<>();
        fList.add(fragSkill.newInstance(context, Skill));
        fList.add(fragOffer.newInstance(context, Offer));
        fList.add(fragMainService.newInstance(context, Main));
        return fList;

    }

    public static List<Datas.SkillOffers> removeDuplicateYears(final Collection<Datas.SkillOffers> awards) {
        final ArrayList<Datas.SkillOffers> input = new ArrayList<>(awards);
        // If there's only one element (or none), guaranteed unique.
        if (input.size() <= 1) {
            return input;
        }
        final HashSet<Integer> years = new HashSet<Integer>(input.size(), 1);
        final Iterator<Datas.SkillOffers> iter = input.iterator();
        while (iter.hasNext()) {
            final Datas.SkillOffers award = iter.next();
            final Integer year = award.id();
            if (years.contains(year)) {
                iter.remove();
            } else {
                years.add(year);
            }
        }
        return input;

    }

    public static void refreshServices(){
        adapter2.notifyDataSetChanged();
    }

    public void lstFilterClick(int position){
        linWidth.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        MyRecyclerScroll.isVisible = true;

        filterID = lFilter.get(position).id();
        lCounter.get(cIndex).filterID(filterID);
        if (lCounter.size() > 1) {
            comingPKG(lCounter.get(cIndex).filterID());
        } else {
            comingPKG3(lFilter.get(position).id());
        }
        reNewFilterList(lService, position);
    }

}
