package net.rayab.mahimah.Data;

import android.content.Context;

import net.rayab.mahimah.SQL.MySQLi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class Datas {

    public static class Services{
         String url, adress, title;
         int id, hidden, position, type;
         boolean isSelect;
         boolean isHas;

        public boolean isHas() {
            return isHas;
        }
        public void isHas(boolean isHas) {
            this.isHas = isHas;
        }

        public boolean isSelect() {
            return isSelect;
        }
        public void isSelect(boolean select) {
            isSelect = select;
        }

        public String url() {
            return url;
        }
        public void url(String url) {
            this.url = url;
        }

        public String title() {
            return title;
        }
        public void title(String title) {
            this.title = title;
        }

        public String adress() {
            return adress;
        }
        public void adress(String adress) {
            this.adress = adress;
        }

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int hidden() {
            return hidden;
        }
        public void hidden(int hidden) {
            this.hidden = hidden;
        }

        public int position() {
            return position;
        }
        public void position(int position) {
            this.position = position;
        }

        public int type() {
            return type;
        }
        public void type(int type) {
            this.type = type;
        }
    }
    public static class SubServices{
         String title, description, price, pricenew;
         int id, service_id, hidden, position;

        public String title() {
            return title;
        }
        public void title(String title) {
            this.title = title;
        }

        public String price() {
            return price;
        }
        public void price(String price) {
            this.price = price;
        }

        public String pricenew() {
            return pricenew;
        }
        public void pricenew(String pricenew) {
            this.pricenew = pricenew;
        }

        public String description() {
            return description;
        }
        public void description(String description) {
            this.description = description;
        }

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int service_id() {
            return service_id;
        }
        public void service_id(int service_id) {
            this.service_id = service_id;
        }

        public int hidden() {
            return hidden;
        }
        public void hidden(int hidden) {
            this.hidden = hidden;
        }

        public int position() {
            return position;
        }
        public void position(int position) {
            this.position = position;
        }
    }
    public static class SubServicesDiscount{
         String discount_amount;
         int id, subservice_id_1, subservice_id_2;

        public String discount_amount() {
            return discount_amount;
        }
        public void discount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int subservice_id_1() {
            return subservice_id_1;
        }
        public void subservice_id_1(int subservice_id_1) {
            this.subservice_id_1 = subservice_id_1;
        }

        public int subservice_id_2() {
            return subservice_id_2;
        }
        public void subservice_id_2(int subservice_id_2) {
            this.subservice_id_2 = subservice_id_2;
        }
    }
    public static class PriceNew{
        int price, pricenew;
        String title;

        public int price() {
            return price;
        }
        public void price(int price) {
            this.price = price;
        }

        public int pricenew() {
            return pricenew;
        }
        public void pricenew(int pricenew) {
            this.pricenew = pricenew;
        }

        public String title() {
            return title;
        }
        public void title(String title) {
            this.title = title;
        }
    }
    public static class SkillOffers{
        int id, price;
        String name;

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int price() {
            return price;
        }
        public void price(int price) {
            this.price = price;
        }

        public String name() {
            return name;
        }
        public void name(String name) {
            this.name = name;
        }
    }
    public static class Skills{
        int id, service_id, price;
        String name;

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int service_id() {
            return service_id;
        }
        public void service_id(int service_id) {
            this.service_id = service_id;
        }

        public int price() {
            return price;
        }
        public void price(int price) {
            this.price = price;
        }

        public String name() {
            return name;
        }
        public void name(String name) {
            this.name = name;
        }
    }
    public static class SkillMain{
        private int id, price;
        private String name;

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int price() {
            return price;
        }
        public void price(int price) {
            this.price = price;
        }

        public String name() {
            return name;
        }
        public void name(String name) {
            this.name = name;
        }
    }
    public static class SkillSub{
        private int id, service_id, skill_id;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }
        public void isCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int service_id() {
            return service_id;
        }
        public void service_id(int service_id) {
            this.service_id = service_id;
        }

        public int skill_id() {
            return skill_id;
        }
        public void skill_id(int skill_id) {
            this.skill_id = skill_id;
        }
    }
    public static class SkillPriceAdder{
        int id, price;

        public int price() {
            return price;
        }
        public void price(int price) {
            this.price = price;
        }

        public int id() {

            return id;
        }
        public void id(int id) {
            this.id = id;
        }
    }

    public static String getServices = "SELECT * FROM TB_Services ORDER BY position";
    public static String getSkillMain = "SELECT * FROM TB_Skill";
    public static String getSkillSub(int Id){
        return "SELECT * FROM TB_SkillProducts WHERE skill_id='" + Id + "'";
    }
    public static String[] deleteSkillById(int Id){
        String[] a = new String[2];
        a[0] = "DELETE FROM TB_Skill WHERE id='" + Integer.toString(Id) + "'";
        a[1] = "DELETE FROM TB_SkillProducts WHERE skill_id='" + Integer.toString(Id) + "'";
        return a;
    }
    public static String getSkill = "SELECT * FROM TB_Skill";
    public static String getSkillProducts = "SELECT TB_Skill.id as id, TB_SkillProducts.service_id as service_id, TB_Skill.name as name, TB_Skill.price as price " +
                                        "FROM TB_SkillProducts " +
                                        "INNER JOIN TB_Skill " +
                                        "ON TB_SkillProducts.skill_id = TB_Skill.id";
    public static String getSkillProducts(int Id){
        return "SELECT TB_Skill.id as id, TB_SkillProducts.service_id as service_id, TB_Skill.name as name, TB_Skill.price as price " +
                "FROM TB_SkillProducts " +
                "INNER JOIN TB_Skill " +
                "ON TB_SkillProducts.skill_id='" + Integer.toString(Id) + "'";
    }
    
    public static class MainItems{
        String price, pricenew, title, description;
        int id, service_id, position;
        boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }
        public void isCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public String price() {
            return price;
        }
        public void price(String price) {
            this.price = price;
        }

        public String pricenew() {
            return pricenew;
        }
        public void pricenew(String pricenew) {
            this.pricenew = pricenew;
        }

        public String title() {
            return title;
        }
        public void title(String title) {
            this.title = title;
        }

        public String description() {
            return description;
        }
        public void description(String description) {
            this.description = description;
        }

        public int position() {
            return position;
        }
        public void position(int position) {
            this.position = position;
        }

        public int id() {
            return id;
        }
        public void id(int id) {
            this.id = id;
        }

        public int service_id() {
            return service_id;
        }
        public void service_id(int service_id) {
            this.service_id = service_id;
        }
    }
    public static class Counter{
        boolean isPackage;
        int filterID, isPackageID;
        List<MainItems> lBaseItems;
        List<MainItems> lPackageItems;

        public int filterID() {
            return filterID;
        }
        public void filterID(int filterID) {
            this.filterID = filterID;
        }

        public int isPackageID() {
            return isPackageID;
        }
        public void isPackageID(int isPackageID) {
            this.isPackageID = isPackageID;
        }

        public boolean isPackage() {
            return isPackage;
        }
        public void isPackage(boolean isPackage) {
            this.isPackage = isPackage;
        }

        public List<MainItems> lBaseItems() {
            return lBaseItems;
        }
        public void lBaseItems(List<MainItems> lBaseItems) {
            this.lBaseItems = lBaseItems;
        }

        public List<MainItems> lPackageItems() {
            return lPackageItems;
        }
        public void lPackageItems(List<MainItems> lPackageItems) {
            this.lPackageItems = lPackageItems;
        }
    }
    public static class OfferPrice{
        int id, offer_price;

        public int id() {
            return id;
        }
        public void id(int id) {
            id = id;
        }

        public int offer_price() {
            return offer_price;
        }
        public void offer_price(int offer_price) {
            offer_price = offer_price;
        }
    }
    public static class Offers{
        int number;
        boolean offer;

        public Offers(int num, boolean off){
            this.number = num;
            this.offer = off;
        }

        public int Number() {
            return number;
        }
        public void Number(int number) {
            this.number = number;
        }

        public boolean Offer() {
            return offer;
        }
        public void Offer(boolean offer) {
            this.offer = offer;
        }
    }

    public static class parseService{

        public parseService(Context context, JSONArray Services){
            MySQLi SQL = new MySQLi(context);
            try {
                for(int i=0; i<Services.length(); i++){
                    JSONObject ServiceObject = Services.getJSONObject(i);
                    Datas.Services mService = new Datas.Services();
                    mService.id(ServiceObject.getInt("id"));
                    mService.url(ServiceObject.getString("url"));
                    mService.title(ServiceObject.getString("title"));
                    mService.adress(ServiceObject.getString("adress"));
                    mService.hidden(ServiceObject.getInt("hidden"));
                    mService.position(ServiceObject.getInt("position"));
                    mService.type(ServiceObject.getInt("type"));

                    String Query = "SELECT * FROM TB_Services WHERE id='" + mService.id() + "'";
                    List<Datas.Services> lServices = SQL.Select(Query, Datas.Services.class, context);

                    if(lServices.size() == 0) {
                        Query = "INSERT INTO TB_Services (id, url, title, adress, hidden, position, type) VALUES " +
                                "('" + mService.id() + "', '" + mService.url() + "', '" + mService.title().replace("|", "") + "', '" +
                                mService.adress() + "', '" + mService.hidden() + "', '" + mService.position() +
                                "', '" + mService.type() + "')";
                        String asdf = SQL.Execute(Query);
                        String asd = "ASD";
                    }

                    String Result = SQL.Execute("INSERT INTO TB_OfferPrice (offer_price) VALUES ('500000')");
                }
            }catch (Exception Ex){
                String Er = Ex.getMessage();
            }
        }

    }
    public static class parseSubService{

        public parseSubService(Context context, JSONArray SubServices){
            MySQLi SQL = new MySQLi(context);
            try {
                for(int i=0; i<SubServices.length(); i++){
                    JSONObject SubServiceObject = SubServices.getJSONObject(i);
                    Datas.SubServices mSubService = new Datas.SubServices();
                    mSubService.id(SubServiceObject.getInt("id"));
                    mSubService.id(SubServiceObject.getInt("id"));
                    mSubService.service_id(SubServiceObject.getInt("service_id"));
                    mSubService.title(SubServiceObject.getString("title"));
                    mSubService.price(SubServiceObject.getString("price"));
                    mSubService.pricenew(SubServiceObject.getString("pricenew"));
                    mSubService.description(SubServiceObject.getString("description"));
                    mSubService.hidden(SubServiceObject.getInt("hidden"));
                    mSubService.position(SubServiceObject.getInt("position"));

                    String Query = "SELECT * FROM TB_SubServices WHERE id='" + mSubService.id() + "'";
                    List<Datas.SubServices> lSubServices = SQL.Select(Query, Datas.SubServices.class, context);

                    String yourFormattedString = "";
                    String yourFormattedString2 = "";
                    if(lSubServices.size() == 0) {
                        if(Integer.parseInt(mSubService.price()) > 0) {
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            yourFormattedString = formatter.format(Integer.parseInt(mSubService.price()));
                        }
                        if(mSubService.pricenew.length() > 0) {
                            if (Integer.parseInt(mSubService.pricenew()) > 0) {
                                DecimalFormat formatter2 = new DecimalFormat("#,###,###");
                                yourFormattedString2 = formatter2.format(Integer.parseInt(mSubService.pricenew()));
                            }
                        }
                        Query = "INSERT INTO TB_SubServices (id, service_id, title, price, pricenew, description, hidden, position) VALUES " +
                                "('" + mSubService.id() + "', '" + mSubService.service_id() + "', '" +
                                mSubService.title().replace("|", "") + "', '" +
                                yourFormattedString + "', '" + yourFormattedString2 + "', '" + mSubService.description() +
                                "', '" + mSubService.hidden() + "', '" + mSubService.position() + "')";
                        SQL.Execute(Query);
                    }
                }
            }catch (Exception Ex){
                String Er = Ex.getMessage();
            }
        }

    }
    public static class parseSubServiceDiscount{

        public parseSubServiceDiscount(Context context, JSONArray SubServicesDiscount){
            MySQLi SQL = new MySQLi(context);
            try {
                for (int i = 0; i < SubServicesDiscount.length(); i++) {
                    JSONObject SubServiceDiscountObject = SubServicesDiscount.getJSONObject(i);
                    Datas.SubServicesDiscount mSubServiceDiscount = new Datas.SubServicesDiscount();
                    mSubServiceDiscount.id(SubServiceDiscountObject.getInt("id"));
                    mSubServiceDiscount.id(SubServiceDiscountObject.getInt("id"));
                    mSubServiceDiscount.subservice_id_1(SubServiceDiscountObject.getInt("subservice_id_1"));
                    mSubServiceDiscount.subservice_id_2(SubServiceDiscountObject.getInt("subservice_id_2"));
                    mSubServiceDiscount.discount_amount(SubServiceDiscountObject.getString("discount_amount"));

                    String Query = "SELECT * FROM TB_OfferChoise WHERE id='" + mSubServiceDiscount.id() + "'";
                    List<Datas.SubServicesDiscount> lSubServicesDiscount = SQL.Select(Query, Datas.SubServicesDiscount.class, context);

                    if (lSubServicesDiscount.size() == 0) {
                        Query = "INSERT INTO TB_OfferChoise (id, subservice_id_1, subservice_id_2, discount_amount) VALUES " +
                                "('" + mSubServiceDiscount.id() + "', '" + mSubServiceDiscount.subservice_id_1() +
                                "', '" + mSubServiceDiscount.subservice_id_2() + "', '" + mSubServiceDiscount.discount_amount() + "')";
                        String Result = SQL.Execute(Query);
                        String asdf = "ASD";
                    }
                }
            }catch (Exception Ex){
                String Er = Ex.getMessage();
            }
        }

    }

}
