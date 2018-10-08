package net.rayab.mahimah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.rayab.mahimah.R;

import java.lang.reflect.Field;
import java.util.List;

public class SpinAdapter extends ArrayAdapter<Object> {

    /*
    How To Use :
    Context context = this;
    private Spinner mySpinner;
    private SpinAdapter adapter;

    Datas data = new Datas();
    data.id(1);
    data.name("Joaquin");
    lData.add(data);

    data = new Datas();
    data.id(2);
    data.name("Alberto");
    lData.add(data);

    adapter = new SpinAdapter(context, android.R.layout.simple_spinner_item, lData, "name");
    mySpinner = findViewById(R.id.spin);
    mySpinner.setAdapter(adapter);
     */

    private Context context;
    private List<Object> values;
    private String fieldName = "name";

    public <T> SpinAdapter(Context context, int textViewResourceId, List<T> values, String fieldName) {
        super(context, textViewResourceId, ((List<Object>) values));
        this.context = context;
        this.values = ((List<Object>) values);
        this.fieldName = fieldName;
    }
    public <T> SpinAdapter(Context context, List<T> values, String fieldName) {
        super(context, R.layout.spinner_item_test, ((List<Object>) values));
        this.context = context;
        this.values = ((List<Object>) values);
        this.fieldName = fieldName;
    }
    public <T> SpinAdapter(Context context, int textViewResourceId, List<T> values) {
        super(context, textViewResourceId, ((List<Object>) values));
        this.context = context;
        this.values = ((List<Object>) values);
    }
    public <T> SpinAdapter(Context context, List<T> values) {
        super(context, R.layout.spinner_item_test, ((List<Object>) values));
        this.context = context;
        this.values = ((List<Object>) values);
    }

    @Override
    public int getCount(){
        return values.size();
    }
    @Override
    public Object getItem(int position){
        return values.get(position);
    }
    public <T> T getItem(Class<T> mClass, int position){
        try{
            return mClass.cast(values.get(position));
        }catch (ClassCastException Ex){
            String Er = Ex.getMessage();
            return null;
        }
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    public int getId(int position, String Key){
        return 0;
    }
    public int getId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
//        label.setTextColor(Color.BLACK);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        label.setTypeface(tf);
        label.setText(getValue(position));

        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
//        label.setTextColor(Color.BLACK);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        label.setTypeface(tf);
        label.setText(getValue(position));

        return label;
    }

    private String getValue(int position){
        String mVal = "";
        try {
            Class abc = values.get(position).getClass();
            Object abject = abc.newInstance();
            abject = values.get(position);
            for (Field field : values.get(position).getClass().getDeclaredFields()) {

                try {
                    field.setAccessible(true);
                    String name = field.getName();
                    if(name.equalsIgnoreCase(fieldName)) {
                        mVal = field.get(abject).toString();
                    }

                    String rr = "ASD";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (IllegalAccessException Ex){
            String asd = "ASD";
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return mVal;
    }
    public int getIdItem(int position, String Key){
        int mVal = 0;
        try {
            Class abc = values.get(position).getClass();
            Object abject = abc.newInstance();
            abject = values.get(position);
            for (Field field : values.get(position).getClass().getDeclaredFields()) {

                try {
                    field.setAccessible(true);
                    String name = field.getName();
                    if(name.equalsIgnoreCase(Key)) {
                        mVal = Integer.parseInt(field.get(abject).toString());
                    }

                    String rr = "ASD";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (IllegalAccessException Ex){
            String asd = "ASD";
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return mVal;
    }
    private int getIdItem(int position){
        int mVal = 0;
        try {
            Class abc = values.get(position).getClass();
            Object abject = abc.newInstance();
            abject = values.get(position);
            for (Field field : values.get(position).getClass().getDeclaredFields()) {

                try {
                    field.setAccessible(true);
                    String name = field.getName();
                    if(name.equalsIgnoreCase("id") || name.equalsIgnoreCase("Id")) {
                        mVal = Integer.parseInt(field.get(abject).toString());
                    }

                    String rr = "ASD";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (IllegalAccessException Ex){
            String asd = "ASD";
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return mVal;
    }
    public String getItemString(int position, String Key){
        String mVal = "";
        try {
            Class abc = values.get(position).getClass();
            Object abject = abc.newInstance();
            abject = values.get(position);
            for (Field field : values.get(position).getClass().getDeclaredFields()) {

                try {
                    field.setAccessible(true);
                    String name = field.getName();
                    if(name.equalsIgnoreCase(Key)) {
                        mVal = field.get(abject).toString();
                    }

                    String rr = "ASD";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (IllegalAccessException Ex){
            String asd = "ASD";
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return mVal;
    }
    public int getItemPosition(String Key, String Value){
        try {
            for(int i=0; i<values.size(); i++) {
                Class abc = values.get(i).getClass();
                Object abject = abc.newInstance();
                abject = values.get(i);
                for (Field field : values.get(i).getClass().getDeclaredFields()) {
                    try {
                        field.setAccessible(true);
                        if (Key.equalsIgnoreCase(field.getName()) && Value.equalsIgnoreCase(field.get(abject).toString())) {
                            return i;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (IllegalAccessException Ex){
            String asd = "ASD";
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
