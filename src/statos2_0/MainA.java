/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statos2_0;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.ProgressDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import parser.JSONParser;

/**
 *
 * @author Yakovbubnov
 */
public class MainA extends Application{
    //JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> prod;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PRICE = "price";
    private static final String TAG_ID = "id";
    private static final String TAG_M1 = "m1";
    private static final String TAG_M2 = "m2";
    private static final String TAG_M3 = "m3";
    private static final String TAG_ZT = "zt";
    private static final String TAG_SALES = "sales";
    private static final String TAG_CHECK ="idcheck";
    boolean metka=false;
    private static String url_all_pr = "http://f0100090.xsph.ru/get_all_products.php";//урл для получения товаров
    private static String url_send_pr = "http://f0100090.xsph.ru/send_json.php";//урл для отправки купленного товара
    private static String url_upd_m1 = "http://f0100090.xsph.ru/update_m1.php";
    private static String url_upd_m2 = "http://f0100090.xsph.ru/update_m2.php";
    private static String url_upd_m3 = "http://f0100090.xsph.ru/update_m3.php";
    private static String url_chsum = "http://f0100090.xsph.ru/chsum.php";
    private static String url_chupdate = "http://f0100090.xsph.ru/chupdate.php";
    private static String url_storeup = "http://f0100090.xsph.ru/storeup.php";
    private static String url_cashgetm = "http://f0100090.xsph.ru/cashgetm.php";
    private static String url_cashup = "http://f0100090.xsph.ru/cashup.php";
    private static String url_cashday = "http://f0100090.xsph.ru/cashday.php";
     
    String ost="Остаток "; 
    int m;//=1;//!!!!!!!!!!!!!!!!!!!!!!!!!!!
    int selid;
    String MT;//="m"+m;
    double itog;
    String nameseller;
    String storename;
    private int chcount=1;
    private double kassa; //сумма денег в кассе
    private double daycash; //дневная выручка
    ArrayList<JSONObject> jsares = new ArrayList<JSONObject>();
    private JSONObject jsores = new JSONObject();
    private static final String TIP = "idprod";
    private static final String TCOUNT = "count";
    private static final String TRES = "res";
    private static final String TM = "m";
    private static final String TSELLER = "seller";
    private static final String TDATE = "date";
    String typepay = "";
    ComboBox cbx1 = new ComboBox();
        ComboBox cbx2 = new ComboBox();
        ComboBox cbx3 = new ComboBox();
        ComboBox cbx4 = new ComboBox();
        TextArea res = new TextArea();
        TextArea t1 = new TextArea();//Остаток разливного пива
        TextArea t2 = new TextArea();//Остаток бутылочного пива
        TextArea t3 = new TextArea();//остаток закусок
        TextArea titg = new TextArea();
        TextField txg3 = new TextField();
      final Spinner<Double> sp1 = new Spinner<Double>();//спиннер литров разливного пива
        SpinnerValueFactory<Double> spfd = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,100.0,0.0,0.5);
        
        final Spinner<Integer> spb1 = new Spinner<Integer>();//спиннер тары разливного випа
        SpinnerValueFactory<Integer> sfi= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0,1);
        SpinnerValueFactory<Integer> sfi2= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0,1);
        SpinnerValueFactory<Integer> sfi3= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0,1);
        
        Spinner<Integer> sp2 = new Spinner<Integer>();//спиннер кол-ва бутылочного пива
        
        Spinner<Integer> sp3 = new Spinner<Integer>();//спиннер закусок
        
        Label lb1 = new Label();//подпись Л
        Label lb2 = new Label();//подпись Шт
        Label lb3 = new Label();
        Label p1 = new Label("Разливное");
        Label p2 = new Label("Бутылочное");
        Label p3 = new Label("Закуски");
        Label litg = new Label();//подпись ИТОГО
        Label lbb1 = new Label();//подпись тара
        Button bt1 = new Button();//кнопка добавление разливного пива
        Button bt2 = new Button();
        Button bt3 = new Button();
        Button bres = new Button();//кнопка Наличный
        Button bitg = new Button();//кнопка Готово
        Button bbn = new Button();//кнопка Безналичный
        Button bdlg = new Button();//кнопка Долг
        Button bp = new Button();
        Button btcl = new Button("Очистить");
        Label kasnbn = new Label("Магазин\n(Нал)");
        Label sumnbn = new Label(""); 
        Label kasvnbn = new Label("Смена\n(Нал/Безнал)");
        Label sumvnbn = new Label("");
        Label vyr = new Label("");       
        Button close = new Button("ВЫХОД");
        double smvyr=500;
        double days=0;
        JSONParser jParser = new JSONParser();
       
        public void chup(int chm){
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("mt",Integer.toString(m)));
         params.add(new BasicNameValuePair("m",Integer.toString(chm)));
         //System.out.println("{{{{{{"+params);
         jParser.makeHttpRequest(url_chupdate, "POST", params);        
        }
                
    public int sendJson(ArrayList<JSONObject> js) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
        //bid bc
         List<NameValuePair> params3 = new ArrayList<NameValuePair>();
        JSONObject jso = null;

        int c = 0;
        for (int i = 0; i < js.size(); i++) {
            jso = js.get(i);
            params = new ArrayList<NameValuePair>();
            params2 = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TIP, jso.get(TIP).toString()));
            params2.add(new BasicNameValuePair("id", jso.get(TIP).toString()));
            double rescoun=Double.parseDouble(getTwotagid(jso.get("idprod").toString(),MT))
                    -Double.parseDouble(jso.get("count").toString());
            int typ = Integer.parseInt(gettypebyid(jso.get(TIP).toString()));
            
            double bls = Double.parseDouble(getTwotagid(jso.get("idprod").toString(),MT))-balancech(getTwotagid(jso.get("idprod").toString(),"name"),typ);
            //System.out.println("!!"+bls);     
            System.out.println(jso);    
            params2.add(new BasicNameValuePair(MT, Double.toString(bls)));
            //if(!jso.isNull("bid")){          
            //params3.add(new BasicNameValuePair("id",jso.get("bid").toString()));
            //params3.add(new BasicNameValuePair("m".concat(Integer.toString(store)),jso.get("bc").toString()));
            //}
            params.add(new BasicNameValuePair(TCOUNT, jso.get(TCOUNT).toString()));
            params.add(new BasicNameValuePair(TRES, jso.get(TRES).toString()));
            params.add(new BasicNameValuePair(TM, jso.get(TM).toString()));
            params.add(new BasicNameValuePair(TSELLER, jso.get(TSELLER).toString()));
            params.add(new BasicNameValuePair(TDATE, jso.get(TDATE).toString()));
            params.add(new BasicNameValuePair("seltype", jso.get("seltype").toString()));
            params.add(new BasicNameValuePair("dolgid", jso.get("dolgid").toString()));
            params.add(new BasicNameValuePair("idcheck", jso.get("idcheck").toString()));
            
            //System.out.println(params);
            jso = jParser.makeHttpRequest(url_send_pr, "POST", params);
            int success = jso.getInt(TAG_SUCCESS);
            if (success == 1) {
                c++;
                jso = null;
            }
            if (m==1) {
                System.out.println(params2);
                jParser.makeHttpRequest(url_upd_m1, "POST", params2);               
            } else if (m==2) {
                jParser.makeHttpRequest(url_upd_m2, "POST", params2);
                if(params3.isEmpty()){}else{jParser.makeHttpRequest(url_upd_m2, "POST", params3);}

            } else if (m==3) {
                jParser.makeHttpRequest(url_upd_m3, "POST", params2);
                if(params3.isEmpty()){}else{jParser.makeHttpRequest(url_upd_m3, "POST", params3);}

            }

        }
            //params.add(new BasicNameValuePair("name", name));
        //params.add(new BasicNameValuePair("price", price));
        //params.add(new BasicNameValuePair("description", description));

            // получаем JSON объект
//            JSONObject json = jParser.makeHttpRequest(url_send_pr, "POST",js.get(1));
        //Log.d("Create Response", json.toString());
        return c;
    }
        
    public void printerstart(String titg, String res){
        PrinterService printerService = new PrinterService();
            String resitg = titg;
            String ritg="ИТОГО";
            int g=32-ritg.length()-resitg.length();
            for(int i=0; i<g; i++){
                ritg+="_";
            }
            SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        String tmt = "\n"+ff.format(System.currentTimeMillis());
        tmt+="\n"+"#"+checkcheck()+"\n"+typepay+"\nПродавец "+nameseller+
                "\nИНН 381606982814\n";
            ritg+=resitg+tmt+"\n"+"           СПАСИБО ЗА           "+"\n"+"            ПОКУПКУ!            ";
            //System.out.println(printerService.getPrinters());
            String head = "          ИП Пилипейко\n"
                    +"          'Литрушка-1'\n"
                    +"Продажа #"+checkcheck()+"\n"
                    +"********************************";
            String ends="********************************\n";
        //print some stuff. Change the printer name to your thermal printer name.
        
        printerService.printString("POS-58", "\n\n"+head+res+ends+ritg+"\n\n\n\n");
       
        // cut that paper!
        byte[] cutP = null; //= new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes("POS-58", cutP);
    }
    public int checkcheck(){
        JSONParser jP = new JSONParser();
        JSONArray product = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject jsons = null;
        jsons = jP.makeHttpRequest(url_chsum, "GET", params);
        int chk=0;
            int success = jsons.getInt(TAG_SUCCESS);
            if (success == 1) {
                product = jsons.getJSONArray(TAG_PRODUCTS);
                for (int i = 0; i < product.length(); i++) {
                    JSONObject c = product.getJSONObject(i);
                    chk = Integer.parseInt(c.getString(MT));                
                }          
            } 
        return chk;
    }
    public boolean runJsons() {
        JSONArray product = null;
       
        JSONParser jP = new JSONParser();
        prod = new ArrayList<HashMap<String, String>>();     
/*
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Редактирование товара");
                alert.setHeaderText("Внимание!");                
                alert.setContentText("Вы действительно хотите изменить товар?");
               Optional<ButtonType> result = alert.showAndWait();              
                if (result.get() == ButtonType.OK){
                    
                }else{
                    
                }
                alert.showAndWait();*/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // получаем JSON строк с URL
        JSONObject jsons = null;
        jsons = jP.makeHttpRequest(url_all_pr, "GET", params);
        
        
            int success = jsons.getInt(TAG_SUCCESS);
            
            if (success == 1) {
                // продукт найден
                // Получаем масив из Продуктов
                product = jsons.getJSONArray(TAG_PRODUCTS);
                  
                // перебор всех продуктов
                for (int i = 0; i < product.length(); i++) {
                    JSONObject c = product.getJSONObject(i);

                    // Сохраняем каждый json элемент в переменную
                    String id = c.getString(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String price = c.getString(TAG_PRICE);
                    String type = c.getString(TAG_TYPE);
                    String m1 = c.getString(TAG_M1);
                    String m2 = c.getString(TAG_M2);
                    String m3 = c.getString(TAG_M3);
                    String zt = c.getString(TAG_ZT);
                    String sales = c.getString(TAG_SALES);
                    // Создаем новый HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // добавляем каждый елемент в HashMap ключ => значение
                    map.put(TAG_ID, id);
                    map.put(TAG_NAME, name);
                    map.put(TAG_PRICE, price);
                    map.put(TAG_TYPE, type);
                    map.put(TAG_M1, m1);
                    map.put(TAG_M2, m2);
                    map.put(TAG_M3, m3);
                    map.put(TAG_ZT, zt);
                    map.put(TAG_SALES, sales);
                    // добавляем HashList в ArrayList
                    prod.add(map);
                    
                    //System.out.println("prod="+prod);
                }
                  
                
            } else {
               
            }
        
     
        return true;
    }
    public String printCh(String name, String tag, double count, double price){
       int l=32;
        String res="";
        double it=0;
        String itg="";
        if(name.length()>29){
        res +=chcount+". "+name.substring(0,28);
        }else{
            res+=chcount+". "+name;
        }
        res+=","+tag+" ";
        res+=count+"X"+price;
        it=count*price;
        itg+=it;
        int g=l-res.length()-itg.length();
        for(int i=0; i<g;i++){
            res+="_";
        }
        res+=itg;
        chcount++;
        return res;
        
    }
    public String printCh2(String name, String tag, double count, double price){
       int l=32;
        String res="";
        String res2="";
        double it=0;
        String itg="";
        if(name.length()>29){
            res2+=chcount+". "+name.substring(0,28)+", "+tag;
        }else{
        res2+=chcount+". "+name+", "+tag;
        }
        res+=count+" X "+price;
        it=count*price;
        itg+=it;
        int g=l-res.length()-itg.length();
        for(int i=0; i<g;i++){
            res+="_";
        }
        res+=itg;
        res2+="\n"+res;
        chcount++;
        return res2;
    }
    public String printCh3(String name, String tag, double count, double price){
       int l=32;
        String res="";
        String res2="";
        double it=0;
        String itg="";
        if(name.length()>29){
            res2+=chcount+". "+name.substring(0,28)+", "+tag;
        }else{
        res2+=chcount+". "+name+", "+tag;
        }
        res+=count+" X "+price;
        it=(count*price)/100;
        itg+=it;
        int g=l-res.length()-itg.length();
        for(int i=0; i<g;i++){
            res+="_";
        }
        res+=itg;
        res2+="\n"+res;
        chcount++;
        return res2;
    }
    
    private String getTwotagid(String name, String m) {
        String balance = "";
       
        for (int i = 0; i < prod.size(); i++) {
            if (prod.get(i).get("id").matches(name)) {
                balance = prod.get(i).get(m);
            }
        }
        return balance;
    }
    private String gettypebyid(String id) {
        String balance = "";
       
        for (int i = 0; i < prod.size(); i++) {
            if (prod.get(i).get("id").matches(id)) {
                balance = prod.get(i).get("type");
            }
        }
        return balance;
    }
    private String getTwotag(String name,int type, String m) {
        String balance = "";
       
        for (int i = 0; i < prod.size(); i++) {
            if (((prod.get(i).get("name").matches(name))&(prod.get(i).get("type").matches(Integer.toString(type))))) {
                balance = prod.get(i).get(m);
            }
        }
        return balance;
    }
    private boolean isHave(String name,int type){
        boolean res=true;
        if (getTwotag(name,type,MT).equals("0")){
            res=false;
        }
        return res;
    }
    private ObservableList<String> GetByTag(String tag, String price) {
        //Получение значение по двум параметрам
        ObservableList<String> al = FXCollections.observableArrayList();       
        //if(balance1.size()>=0){balanceadd();}
        if (price.matches("")) {
            for (int i = 0; i < prod.size(); i++) {
                al.add(prod.get(i).get(tag));
            }
        } else {
           // System.out.println("!!"+prod);
            
            for (int i = 0; i < prod.size(); i++) {
                if (prod.get(i).get("type").matches(price)) {
                    al.add(prod.get(i).get(tag));
                    
                    
                }
                
            }

        }
        return al;
    }
    public void finishnal(String ttitg, String ress,int selt){
       Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Завершение");
    alert.setHeaderText("");
    alert.setContentText("Завершение процесса покупки");
    ButtonType buttonCheck = new ButtonType("Печать чека");
    ButtonType buttonTOK = new ButtonType("ОК");
    ButtonType buttonCancel2 = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(buttonCheck, buttonTOK, buttonCancel2);
Optional<ButtonType> result3 = alert.showAndWait();
if (result3.get() == buttonCheck){
    printerstart(ttitg,ress);
    finishnal(ttitg,ress,selt);
}else if(result3.get()==buttonTOK){
  //jsares
    for(int i=0; i<jsares.size();i++){
        if(jsares.get(i).has("seltype")){
            
        }else{
        jsares.get(i).put("seltype",selt);
        jsares.get(i).put("idcheck", checkcheck());
        jsares.get(i).put("dolgid", 0);
        }
    }
   
    sendJson(jsares);
    if(selt==1){
        double titgr = Double.parseDouble(titg.getText());
        addkas(titgr,0,titgr,0);
    }else if(selt == 2){
        double titgr = Double.parseDouble(titg.getText());
        addkas(0,titgr,0,titgr);
    }
    jsares.clear();
    int chknum = checkcheck();
    chknum++;
    chup(chknum);
    itog = 0;
    res.setText("");
     titg.setText("");
            sp3.setVisible(false);
            lb3.setVisible(false);
            t3.setVisible(false);
            bt3.setVisible(false);
            sp1.setVisible(false);
            lb1.setVisible(false);
            t1.setVisible(false);
            lbb1.setVisible(false);
            spb1.setVisible(false);
            bt1.setVisible(false);
            cbx4.setVisible(false);
            sp2.setVisible(false);
            lb2.setVisible(false);
            t2.setVisible(false);
            bt2.setVisible(false);
            cbx1.getSelectionModel().clearSelection();
            cbx2.getSelectionModel().clearSelection();
            cbx3.getSelectionModel().clearSelection();
            cbx4.getSelectionModel().clearSelection();
            sp1.getValueFactory().setValue(0.0);
            sp2.getValueFactory().setValue(0);
            sp3.getValueFactory().setValue(0);
            spb1.getValueFactory().setValue(0);
            //касса
    
}else if(result3.get()==buttonCancel2){
    
}
    }
    
    private double balancech(String name,int type){
    String id = getTwotag(name,type,"id");
    double res=0;
    if(jsares.size()>0){
    for(int i=0; i<jsares.size(); i++){
        //System.out.println("< id >"+id+"< name >"+name);
       if(jsares.get(i).get("idprod").equals(id)){
           //System.out.println("jsares"+jsares.get(i));
           res+=Double.parseDouble(jsares.get(i).get(TCOUNT).toString());
       } 
    }  
        }
    return res;
    }
    
    private boolean updsel(int idm,int id_seller){
         
       List<NameValuePair> para = new ArrayList<NameValuePair>();
        
        JSONObject jsons1 = new JSONObject();
        para.add(new BasicNameValuePair("idm",String.valueOf(idm)));
        para.add(new BasicNameValuePair("id_seller",String.valueOf(id_seller)));
        JSONParser jP1 = new JSONParser();
        boolean res;
        jsons1 = jP1.makeHttpRequest(url_storeup, "POST", para);
        if(jsons1.get("success").equals("1")){
            res=true;
        }else{
            res=false;
        }
        
    return res;    
   }
    private void cashday(int idm){
         
       List<NameValuePair> pa = new ArrayList<NameValuePair>();
        
        JSONObject jsons3 = new JSONObject();
        pa.add(new BasicNameValuePair("idm",String.valueOf(idm)));
        pa.add(new BasicNameValuePair("dayall",Double.toString(0.0)));
        pa.add(new BasicNameValuePair("daybn",Double.toString(0.0)));
        JSONParser jP3 = new JSONParser();
        boolean res;
        jsons3 = jP3.makeHttpRequest(url_cashday, "POST", pa);
        //System.out.println(pa+"\n"+jsons3);
        if(!jsons3.isNull("success")){
            res=true;
        }else{
            res=false;
        }
   
   }
    private void addkas(double kn,double kbn, double vn, double vbn){
        List<NameValuePair> para = new ArrayList<NameValuePair>();
        
        JSONObject jsons1 = new JSONObject();
        para.add(new BasicNameValuePair("idm",String.valueOf(m)));
        JSONParser jP1 = new JSONParser();
        boolean res;
        jsons1 = jP1.makeHttpRequest(url_cashgetm, "POST", para);
        
        int success=jsons1.getInt("success");
        //System.out.println(success);
        if(!jsons1.isNull("success")){
            //res=true;
           // System.out.println("TRUE");
            
            double all=jsons1.getDouble("all")+kn;
            double allbn = Double.parseDouble(jsons1.get("allbn").toString())+kbn;
            double dayall = Double.parseDouble(jsons1.get("dayall").toString())+vn;
            double daybn = Double.parseDouble(jsons1.get("daybn").toString())+vbn;
            days=dayall+daybn;
           if(days<=7999&days>0){
               smvyr=500+((days/100)*3);
           }else if(days>7999){
               smvyr=500+((days/100)*4);
           }
           vyr.setText("Выручка:"+smvyr);
            sumnbn.setText(all+" Р.");
            sumvnbn.setText(dayall+" / "+daybn);
            ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
          
           par.add(new BasicNameValuePair("idm",Integer.toString(m)));
           par.add(new BasicNameValuePair("all",Double.toString(all)));
           par.add(new BasicNameValuePair("allbn",Double.toString(allbn)));
           par.add(new BasicNameValuePair("dayall",Double.toString(dayall)));
           par.add(new BasicNameValuePair("daybn",Double.toString(daybn)));
           
           JSONObject jsons2 = new JSONObject();
            JSONParser jP2 = new JSONParser();
         //  System.out.println(jsons1+"\n"+par);
           jsons2 = jP2.makeHttpRequest(url_cashup, "POST", par);
           
          
            
        }else{
            //res=false;
        }
        
    }  
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(nameseller+"["+storename+"]");
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
        
          updsel(m,1);
         }
        });
        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(14);
        grid.setPadding(new Insets(5,5,5,5));
        //System.out.println("****"+m);
        txg3.setVisible(false);
        //объявление спиннеров, которые увеличиваются на 1
        sp1.setValueFactory(spfd);
        spb1.setValueFactory(sfi);
        sp2.setValueFactory(sfi2);
        sp3.setValueFactory(sfi3);
        p1.setId("firstlab");
        p2.setId("firstlab");
        p3.setId("firstlab");
        kasnbn.setId("secondlab");
        kasvnbn.setId("secondlab");
        sumnbn.setId("secondlab");
        vyr.setId("vyr");
        sumvnbn.setId("secondlab");
        JSONParser jP5 = new JSONParser();
        JSONObject jsons5 = new JSONObject();
        List<NameValuePair> para5 = new ArrayList<NameValuePair>();
        
        para5.add(new BasicNameValuePair("idm",String.valueOf(m)));
        jsons5 = jP5.makeHttpRequest(url_cashgetm, "POST", para5);
        int success=jsons5.getInt("success");
        if(!jsons5.isNull("success")){
            //res=true;
           // System.out.println("TRUE");
            
            double all=jsons5.getDouble("all");
            double dayall = Double.parseDouble(jsons5.get("dayall").toString());
            double daybn = Double.parseDouble(jsons5.get("daybn").toString());
             days=dayall+daybn;
           if(days<=7999&days>0){
               smvyr+=(days/100)*3;
           }else if(days>7999){
               smvyr+=(days/100)*4;
           }
           vyr.setText("Выручка:"+smvyr);
            sumnbn.setText(all+" Р.");
            sumvnbn.setText(dayall+" / "+daybn);
        }
        
        litg.setText("ИТОГО:");
        {//убираем дополнительные кнопки окна разливного
            sp1.setVisible(false);
            spb1.setVisible(false);
            lb1.setVisible(false);
            t1.setVisible(false);
            bt1.setVisible(false);
            lbb1.setVisible(false);
            cbx4.setVisible(false);
        }
        {//убираем доп конпки,окна бутылочного
            sp2.setVisible(false);
            lb2.setVisible(false);
            t2.setVisible(false);
            bt2.setVisible(false);
        }
        {//убираем доп кнопки закусок
            
            sp3.setVisible(false);
            lb3.setVisible(false);
            t3.setVisible(false);
            bt3.setVisible(false);

        }
        
        res.editableProperty().setValue(Boolean.FALSE);
        t1.editableProperty().setValue(Boolean.FALSE);
        t2.editableProperty().setValue(Boolean.FALSE);
        t3.editableProperty().setValue(Boolean.FALSE);
        titg.editableProperty().setValue(Boolean.FALSE);
        //cbx1.setItems(GetByTag(TAG_NAME, "1"));
        //cbx2.setItems(GetByTag(TAG_NAME, "2"));
        //cbx3.setItems(GetByTag(TAG_NAME, "3"));
        //cbx4.setItems(GetByTag(TAG_NAME, "4"));
        
        //cbx1.valueProperty().addListener(new );
       cbx1.setId("comboprod");
        cbx2.setId("comboprod");
        cbx3.setId("comboprod");
        
        
        txg3.setPrefSize(70,80);
        
        sp1.setPrefSize(75,80);
        sp2.setPrefSize(75,80);
        sp3.setPrefSize(75,80);
        spb1.setPrefSize(75,80);
        lb1.setId("labl");
        lb2.setId("labl");
        lb3.setId("labl");
        lbb1.setId("labl");
        t1.setId("textost");
        t2.setId("textost");
        t3.setId("textost");
        bt1.setId("btitg");
        bt2.setId("btitg");
        bt3.setId("btitg");
        cbx4.setId("combbot");
        //lb1.setPrefSize(25, 40);
        //lb2.setPrefSize(25, 40);
        //lb3.setPrefSize(25, 40);
        
        //t1.setPrefSize(150, 40);
        //t2.setPrefSize(150, 40);
        //t3.setPrefSize(150, 40);
        
        //cbx4.setPrefSize(160,40);
        //bt2.setPrefSize(40, 40);
        //bt3.setPrefSize(40, 40);
        
        //spb1.setPrefSize(80, 40);
      
        //lbb1.setPrefSize(40,40);
        
        //bt1.setPrefSize(40, 40);
        
        titg.setPrefSize(120, 80);
        litg.setId("itgl");
        res.setPrefSize(300, 300);
        
        
        
        
        
        
        lb1.setText("Л");
        lb2.setText("ШТ");
        lb3.setText("ШТ");
        bt1.setText("+");
        bt2.setText("+");
        bt3.setText("+");
        lbb1.setText("ШТ");
        bres.setText("Наличный");
        bitg.setText("Готово");
        bdlg.setText("Долг");
        bbn.setText("Б/Н");
        //sp1.setValueFactory();
        grid.add(p1,0,0);
        grid.add(p2,0,1);
        grid.add(p3,0,2);
        
        grid.add(cbx1, 1, 0);
        grid.add(cbx2, 1, 1);
        grid.add(cbx3, 1, 2);
        
        grid.add(sp1, 2, 0);
        grid.add(sp2, 2, 1);
        grid.add(sp3, 2, 2);
        grid.add(txg3, 2, 2);
        grid.add(lb1, 3, 0);
        grid.add(lb2, 3, 1);
        grid.add(lb3, 3, 2);
        
        grid.add(t1, 4, 0);
        grid.add(t2, 4, 1);
        grid.add(t3, 4, 2);
        
        grid.add(cbx4,5,0);
        grid.add(bt2, 5, 1);
        grid.add(bt3, 5, 2);
        
        grid.add(spb1,6, 0);
        grid.add(lbb1, 7, 0);
        grid.add(bt1, 8, 0);
        //grid.add(res, 1, 4, 3, 3);
        grid.add(res,0,3,2,3);
        litg.setAlignment(Pos.BASELINE_RIGHT);
         grid.add(litg, 2, 3);
        
        grid.add(titg, 3, 3,2,1);
        grid.add(bitg, 2, 4);
        grid.add(btcl,4,4);
        
        grid.add(kasnbn,10,0);
        grid.add(sumnbn,10,1);     
        grid.add(kasvnbn,10,2);
        grid.add(sumvnbn,10,3);
        grid.add(vyr,10,4);
        grid.add(close, 10,8);
        
        close.setOnAction(event->{
       


/**
     Dialog<Void> dialog = new Dialog<>();
dialog.initModality(Modality.WINDOW_MODAL);
dialog.initOwner(primaryStage);//stage here is the stage of your webview
//dialog.initStyle(StageStyle.TRANSPARENT);
Label loader = new Label("LOADING");
//loader.setContentDisplay(ContentDisplay.DOWN);
loader.setGraphic(new ProgressIndicator());
dialog.getDialogPane().setGraphic(loader);
DropShadow ds = new DropShadow();
ds.setOffsetX(1.3); ds.setOffsetY(1.3); ds.setColor(Color.DARKGRAY);
dialog.getDialogPane().setEffect(ds);
//ButtonType btn = new ButtonType("OK",ButtonData.CANCEL_CLOSE);

//dialog.getDialogPane().getButtonTypes().add(btn);
dialog.show();  
    runJsons();
    
    dialog.hide();
    dialog.close();
 
   **/
       
       
       
    
            Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Подтверждение");
          
          alert.setHeaderText("Завершение работы");
          alert.setContentText("Вы действительно хотите закрыть смену?");
          ButtonType buttonTypeOne = new ButtonType("ДА");
    
          ButtonType buttonTypeCancel = new ButtonType("НЕТ", ButtonData.CANCEL_CLOSE);
          
         alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
Optional<ButtonType> result2 = alert.showAndWait();
if (result2.get() == buttonTypeOne){
            updsel(m,1);
            cashday(m);
            System.exit(0);
}


        });
        btcl.setOnAction(event->{
            jsares.clear();
            res.setText("");
            titg.setText("");
            itog=0;
            chcount=1;
            cbx1.getSelectionModel().clearSelection();
            cbx2.getSelectionModel().clearSelection();
            cbx3.getSelectionModel().clearSelection();
            cbx4.getSelectionModel().clearSelection();
            cbx4.setVisible(false);
            sp1.getValueFactory().setValue(0.0);
            sp2.getValueFactory().setValue(0);
            sp3.getValueFactory().setValue(0);
            spb1.getValueFactory().setValue(0);
            sp1.setVisible(false);
            sp2.setVisible(false);
            sp3.setVisible(false);
            spb1.setVisible(false);
            lb1.setVisible(false);
            lb2.setVisible(false);
            lb3.setVisible(false);
            lbb1.setVisible(false);
            t1.setVisible(false);
            t2.setVisible(false);
            t3.setVisible(false);
            bt1.setVisible(false);
            bt2.setVisible(false);
            bt3.setVisible(false);
            
            
        });
        bitg.setOnAction(event->{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Завершение покупки");
            alert.setContentText("Процесс покупки окончен?");
            ButtonType buttonOK = new ButtonType("ОК");
          ButtonType buttonCancel = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
           alert.getButtonTypes().setAll(buttonOK, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();           
            if (result.get() == buttonOK)
            
            {

          alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Подтверждение");
          
          alert.setHeaderText("Выбор типа оплаты");
          alert.setContentText("Выберите способ оплаты");
          ButtonType buttonTypeOne = new ButtonType("Наличный");
          ButtonType buttonTypeTwo = new ButtonType("Безналичный");
          //ButtonType buttonTypeThree = new ButtonType("Three");
          ButtonType buttonTypeCancel = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
          
         alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, /*buttonTypeThree,*/ buttonTypeCancel);
Optional<ButtonType> result2 = alert.showAndWait();
if (result2.get() == buttonTypeOne){
    // ... user chose "One"
    typepay="Наличный";
    finishnal(titg.getText(),res.getText(),1);
     
} else if (result2.get() == buttonTypeTwo) {
    // ... user chose "Two"
    typepay="Безналичный";
    finishnal(titg.getText(),res.getText(),2);
} /*

else if (result2.get() == buttonTypeThree) {
    // ... user chose "Three"
}*/ else {
    // ... user chose CANCEL or closed the dialog
}
                   
            } else {
                   // ... user chose CANCEL or closed the dialog
                }
        });
        txg3.setOnMouseClicked(event->{
            Dialog dialog = new Dialog();
            GridPane gr = new GridPane();
            gr.setHgap(3);
            gr.setVgap(5);
            gr.setPadding(new Insets(10,10,10,10));
            Button b1 = new Button("1");
            b1.setPrefSize(50,50);
            Button b2 = new Button("2");
            b2.setPrefSize(50,50);
            Button b3 = new Button("3");
            b3.setPrefSize(50,50);
            Button b4 = new Button("4");
            b4.setPrefSize(50,50);
            Button b5 = new Button("5");
            b5.setPrefSize(50,50);
            Button b6 = new Button("6");
            b6.setPrefSize(50,50);
            Button b7 = new Button("7");
            b7.setPrefSize(50,50);
            Button b8 = new Button("8");
            b8.setPrefSize(50,50);
            Button b9 = new Button("9");
            b9.setPrefSize(50,50);
            Button b0 = new Button("0");
            b0.setPrefSize(50,50);
            Button bd = new Button(".");
            bd.setPrefSize(50,50);
            Button bc = new Button("C");
            bc.setPrefSize(50,50);
            Button bok = new Button("ОК");
            bc.setPrefSize(50,50);
            Button bno = new Button("НЕТ");
            bc.setPrefSize(50,50);
            gr.add(b1,0, 0);
            gr.add(b2,1, 0);
            gr.add(b3,2, 0);
            gr.add(b4,0, 1);
            gr.add(b5,1, 1);
            gr.add(b6,2, 1);
            gr.add(b7,0, 2);
            gr.add(b8,1, 2);
            gr.add(b9,2, 2);
            gr.add(bd,0, 3);
            gr.add(b0,1, 3);
            gr.add(bc,2, 3);
            //gr.add(bok, 0, 4);
            //gr.add(bno, 3, 4);
            
            b1.setOnAction(even->{
             txg3.setText(txg3.getText()+"1");
            });
            b2.setOnAction(even->{
             txg3.setText(txg3.getText()+"2");
            });
            b3.setOnAction(even->{
             txg3.setText(txg3.getText()+"3");
            });
            b4.setOnAction(even->{
             txg3.setText(txg3.getText()+"4");
            });
            b5.setOnAction(even->{
             txg3.setText(txg3.getText()+"5");
            });
            b6.setOnAction(even->{
             txg3.setText(txg3.getText()+"6");
            });
            b7.setOnAction(even->{
             txg3.setText(txg3.getText()+"7");
            });
            b8.setOnAction(even->{
             txg3.setText(txg3.getText()+"8");
            });
            b9.setOnAction(even->{
             txg3.setText(txg3.getText()+"9");
            });
            bc.setOnAction(even->{
             txg3.setText("");
             
            });
            bd.setOnAction(even->{
             txg3.setText(txg3.getText()+".");
            });
            b0.setOnAction(even->{
             txg3.setText(txg3.getText()+"0");
            });
            
            
            
            ButtonType okk = new ButtonType("OK", ButtonData.OK_DONE);
            ButtonType no = new ButtonType("НЕТ", ButtonData.CANCEL_CLOSE);
            //gr.add(okk, 0, 4);
            
            dialog.getDialogPane().setContent(gr);
            dialog.getDialogPane().getButtonTypes().addAll(no,okk);
            dialog.setX(350);
            dialog.setY(260);
            Optional res = dialog.showAndWait();
            runJsons();
           // dialog.setResult(ButtonData.CANCEL_CLOSE);
                
            
            //dialog.showAndWait();
        });
        txg3.lengthProperty().addListener(new ChangeListener<Number>(){
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		  if (newValue.intValue() > oldValue.intValue()) {
			  char ch = txg3.getText().charAt(oldValue.intValue());
                        
			  // Check if the new character is the number or other's
			  if ((!(ch >= '0' && ch <= '9'))) {
                              
				   // if it's not number then just setText to previous one
				  if(ch=='.'){}else{ txg3.setText(txg3.getText().substring(0,txg3.getText().length()-1)); }
			  }
                          double res;
                     
                          if(cbx3.getSelectionModel().getSelectedIndex()>=0){
                              res=Double.parseDouble(txg3.getText());
                              res=res/1000;
                              //System.out.println("RES-"+res+"balanc"+balancech(cbx3.getSelectionModel().getSelectedItem().toString())+"BASE"+Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),MT)));
                              if((res+balancech(cbx3.getSelectionModel().getSelectedItem().toString(),3))>Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,MT))){
                                  txg3.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Такого количества товара \n нет в магазине!");
                    alert.showAndWait();
                              }
                          }
                          
		 }
	}
 
});
        cbx1.setOnMouseClicked(new EventHandler(){
            
            @Override
            public void handle(Event event) {               
             spb1.getValueFactory().setValue(0);
              sp1.getValueFactory().setValue(0.0);
            runJsons();       
            cbx1.setItems(GetByTag(TAG_NAME,"1"));
            //cbx1.getSelectionModel().clearSelection(); 
            int[] remove = new int[cbx1.getItems().size()];
            for(int i=0; i<cbx1.getItems().size(); i++){     
                //System.out.println("****"+m);
            if(!isHave(cbx1.getItems().get(i).toString(),1)){
              //нетупива
              
            }else{
                
            }
        }
        
        cbx1.show();
            
            }
            
        });
        
        sp1.setOnMouseClicked(event->{
            if(cbx1.getSelectionModel().getSelectedItem()!=null){
            double spres = Double.parseDouble(sp1.getEditor().getText().toString().replace(",","."));
            double salesres = balancech(cbx1.getSelectionModel().getSelectedItem().toString(),1);
            double balancestore = Double.parseDouble(getTwotag(cbx1.getSelectionModel().getSelectedItem().toString(),1,MT));
           // System.out.println(balancestore+" "+spres+" "+salesres);
            if((balancestore-(spres+salesres))>=0){
                
            }else{              
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Такого количества пива \n нет в магазине!");
                    alert.showAndWait();
                    sp1.getValueFactory().setValue(0.0);
                    
            }
            }
        });
        sp2.setOnMouseClicked(event->{
            if(cbx2.getSelectionModel().getSelectedIndex()>=0){
            double spres = Double.parseDouble(sp2.getEditor().getText().toString());
            double salesres = balancech(cbx2.getSelectionModel().getSelectedItem().toString(),2);
            double balancestore = Double.parseDouble(getTwotag(cbx2.getSelectionModel().getSelectedItem().toString(),2,MT));
            if((balancestore-(spres+salesres))>=0){
                
            }else{
               
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Такого количества товара \n нет в магазине!");
                    alert.showAndWait();                    
                     sp2.getValueFactory().setValue(0);
                    
            }
            }
        });
        spb1.setOnMouseClicked(event->{
            if(cbx4.getSelectionModel().getSelectedIndex()>=0){
            double spres = Double.parseDouble(spb1.getEditor().getText().toString());
            double salesres = balancech(cbx4.getSelectionModel().getSelectedItem().toString(),4);
            double balancestore = Double.parseDouble(getTwotag(cbx4.getSelectionModel().getSelectedItem().toString(),4,MT));
            if((balancestore-(spres+salesres))>=0){
                
            }else{
              
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Такого количества тары \n нет в магазине!");
                    alert.showAndWait();
                    spb1.getValueFactory().setValue(0);
            }
            }
        });
        sp3.setOnMouseClicked(event->{
            if(cbx3.getSelectionModel().getSelectedIndex()>=0){
            double spres = Double.parseDouble(sp3.getEditor().getText().toString());
            double salesres = balancech(cbx3.getSelectionModel().getSelectedItem().toString(),3);
            double balancestore = Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,MT));
            if((balancestore-(spres+salesres))>=0){
                
            }else{
              
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Такого количества товара \n нет в магазине!");
                    alert.showAndWait();
                    sp3.getValueFactory().setValue(0);
            }
            }
        });
        cbx4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) { 
            if(observable.getValue()==null){
                
            }else{
                if(!isHave(cbx4.getSelectionModel().getSelectedItem().toString(),4)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText(cbx4.getSelectionModel().getSelectedItem().toString()+" отсутствует в магазине!");
                    Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){                   
                    cbx4.getSelectionModel().clearSelection();
                }else{
                 cbx4.getSelectionModel().clearSelection();   
                }
                }else{
                    
                    
                    
                }   
            }
            }
        });
        cbx1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) { 
            
            if(observable.getValue()==null){
                
             }else{ 
               
                if(!isHave(cbx1.getSelectionModel().getSelectedItem().toString(),1)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText(cbx1.getSelectionModel().getSelectedItem().toString()+" отсутствует в магазине!");
                    Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    sp1.setVisible(false);
                    sp1.getValueFactory().setValue(0.0);
                    lb1.setVisible(false);
                    t1.setVisible(false);
                    cbx4.setVisible(false);
                    spb1.setVisible(false);
                     spb1.getValueFactory().setValue(0);
                    lbb1.setVisible(false);
                    bt1.setVisible(false);
                    cbx1.getSelectionModel().clearSelection();
                }else{
                    sp1.setVisible(false);
                    sp1.getValueFactory().setValue(0.0);
                    lb1.setVisible(false);
                    t1.setVisible(false);
                    cbx4.setVisible(false);
                    spb1.setVisible(false);
                     spb1.getValueFactory().setValue(0);
                    lbb1.setVisible(false);
                    bt1.setVisible(false);
                    cbx1.getSelectionModel().clearSelection();
                    
                }
                }else{
                    
            sp2.setVisible(false);
            lb2.setVisible(false);
            t2.setVisible(false);       
            bt2.setVisible(false);
             sp2.getValueFactory().setValue(0);          
            cbx2.getSelectionModel().clearSelection();        
            
            txg3.setVisible(false);            
            sp3.setVisible(false);
            lb3.setVisible(false);
            t3.setVisible(false);
            bt3.setVisible(false);
             sp3.getValueFactory().setValue(0);          
            cbx3.getSelectionModel().clearSelection();
            double curbal = Double.parseDouble(getTwotag(cbx1.getSelectionModel().getSelectedItem().toString(),1,MT))-balancech(cbx1.getSelectionModel().getSelectedItem().toString(),1);
            t1.setText(ost+" "+curbal+" л.");
            cbx4.setItems(GetByTag(TAG_NAME,"4"));
            sp1.setVisible(true);
            lb1.setVisible(true);
            t1.setVisible(true);          
            cbx4.setVisible(true);
            spb1.setVisible(true);
            lbb1.setVisible(true);
            bt1.setVisible(true);
            
             if(cbx1.getSelectionModel().getSelectedItem().toString().matches("Тара")){
              sp1.setVisible(false);
              t1.setVisible(false);
              lb1.setVisible(false);
                }
                }
            }
            }
        });
        bt1.setOnAction(event->{
            if(cbx1.getSelectionModel().getSelectedItem().toString().matches("Тара")){
             
              if(spb1.getValueFactory().getValue()<1){
               Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали количество Тары!");
           alert.showAndWait();   
              }else{
                  
                  double bottlprice = Double.parseDouble(getTwotag(cbx4.getSelectionModel().getSelectedItem().toString(),4,"price"));
                  int bottlc=spb1.getValueFactory().getValue();
                  double bottleitg=bottlc*bottlprice;

                  res.setText(res.getText()+printCh2(cbx4.getSelectionModel().getSelectedItem().toString(),"ШТ",
             bottlc,
             bottlprice)+"\n");
                  
                  itog = itog + bottleitg;
           titg.setText(String.valueOf(itog));
           jsores = new JSONObject();
           jsores.put(TIP, getTwotag(cbx4.getSelectionModel().getSelectedItem().toString(),4, "id"));
           jsores.put(TCOUNT,bottlc);
           jsores.put(TRES, bottleitg);
           jsores.put(TM, m); 
           jsores.put(TSELLER,selid);//тут долен быть ID продавца
           SimpleDateFormat fff = new SimpleDateFormat("dd.MM.yyyy HH.mm");
          jsores.put(TDATE, fff.format(System.currentTimeMillis()));
          jsores.put(TAG_CHECK,checkcheck());
          jsares.add(jsores);
            sp1.setVisible(!true);
            lb1.setVisible(!true);
            t1.setVisible(!true);          
            cbx4.setVisible(!true);
            spb1.setVisible(!true);
            lbb1.setVisible(!true);
            bt1.setVisible(!true);
             spb1.getValueFactory().setValue(0); 
             sp1.getValueFactory().setValue(0.0);
            cbx1.getSelectionModel().clearSelection();
            cbx4.getSelectionModel().clearSelection();
                  
              }
            }else{
           if(sp1.getValueFactory().getValue()<0.5){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали количество пива!");
           alert.showAndWait(); 
           }else if(cbx4.getSelectionModel().getSelectedItem()==null){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали тип бутылок!");
           alert.showAndWait();
               
           }else if(spb1.getValueFactory().getValue()<1){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали количество Бутылок!");
           alert.showAndWait();     
           }/*else if(cbx4.getSelectionModel().getSelectedItem().toString().matches("Стакан")){
             Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Стаканы не продаются с пивом!");
           alert.showAndWait();  
           }*/else{
            double liters = sp1.getValueFactory().getValue();
            double bottlc = spb1.getValueFactory().getValue();
            String sb = cbx4.getSelectionModel().getSelectedItem().toString();
            double bottlsz=0;
            if(sb.contains("0.5")|sb.contains("0,5")){
                bottlsz=0.5;
            }else if(sb.contains("1.0")|sb.contains("1,0")){
                bottlsz=1.0;
            }else if(sb.contains("1.5")|sb.contains("1,5")){
                bottlsz=1.5;
            }else if(sb.contains("2,0")|sb.contains("2.0")|sb.contains("2")){
                bottlsz=2.0;
            }else if(sb.contains("3,0")|sb.contains("3.0")|sb.contains("3")){
                bottlsz=3.0;
            }else if(sb.contains("Стакан")){
                bottlsz=0.5;
            }
                    
            if((bottlsz*bottlc)==liters){  
            double price = Double.parseDouble(getTwotag(cbx1.getSelectionModel().getSelectedItem().toString(),1, TAG_PRICE));
            String prodn=cbx1.getSelectionModel().getSelectedItem().toString();
            double itg=0;
            if(getTwotag(prodn,1,"sales").matches("1")){
            if(liters%3==0){
             price = ((liters*price)-((liters/3)*price))/liters; 
             itg = liters * price ; 
            }else{
              itg = liters * price ;   
            }
            }else{
               itg = liters * price ; 
            }
            double bottlprice=0;
            if(sp1.isVisible()&!cbx4.getSelectionModel().getSelectedItem().toString().contains("Стакан")){
            bottlprice = Double.parseDouble(getTwotag(cbx4.getSelectionModel().getSelectedItem().toString(),4,"price"));
            }else if(sp1.isVisible()&cbx4.getSelectionModel().getSelectedItem().toString().contains("Стакан")){
             bottlprice=0;   
            }else if(!sp1.isVisible()&cbx4.getSelectionModel().getSelectedItem().toString().contains("Стакан")){
              bottlprice=0;  
            }
            res.setText(res.getText()+printCh2(cbx1.getSelectionModel().getSelectedItem().toString(),"Л",liters,price)+"\n");
             res.setText(res.getText()+printCh2(cbx4.getSelectionModel().getSelectedItem().toString(),"ШТ",
             bottlc,
             bottlprice)+"\n");
             itog+= itg;
             
            jsores = new JSONObject();
            jsores.put(TIP, getTwotag(cbx1.getSelectionModel().getSelectedItem().toString(),1, "id"));
            jsores.put(TCOUNT, String.valueOf(liters));
            jsores.put(TRES,itg);
            jsores.put(TM, m);             
            jsores.put(TSELLER, selid);//тут долен быть ID продавца
            //jsores.put("balance", (balance1.get(cbx1.getSelectionModel().getSelectedIndex()) - coun));          
           SimpleDateFormat ff = new SimpleDateFormat("dd.MM.yyyy HH.mm");
            jsores.put(TDATE, ff.format(System.currentTimeMillis()));
            //jsores.put("seltype","1"); В результирующей кнопке в цикле добавить эти значения
            //jsores.put("dolgid","1");
            jsores.put(TAG_CHECK,checkcheck());
            jsares.add(jsores);
            double bottleitg=bottlprice*bottlc;
                
           itog = itog + bottleitg;
           titg.setText(String.valueOf(itog));
           jsores = new JSONObject();
           jsores.put(TIP, getTwotag(cbx4.getSelectionModel().getSelectedItem().toString(),4, "id"));
           jsores.put(TCOUNT,bottlc);
           jsores.put(TRES, bottleitg);
           jsores.put(TM, m); 
           jsores.put(TSELLER,selid);//тут долен быть ID продавца
           //jsores.put("balance", (balanceb.get(cbx4.getSelectionModel().getSelectedIndex()) - counb));          
           SimpleDateFormat fff = new SimpleDateFormat("dd.MM.yyyy HH.mm");
          jsores.put(TDATE, fff.format(System.currentTimeMillis()));
          //jsores.put("seltype",1); Добавить в результирующую кнопку!
          //jsores.put("dolgid",1);
          jsores.put(TAG_CHECK,checkcheck());
          jsares.add(jsores);
            //System.out.println(jsares);
            sp1.setVisible(!true);
            lb1.setVisible(!true);
            t1.setVisible(!true);          
            cbx4.setVisible(!true);
            spb1.setVisible(!true);
            lbb1.setVisible(!true);
            bt1.setVisible(!true);
             spb1.getValueFactory().setValue(0); 
             sp1.getValueFactory().setValue(0.0);
            cbx1.getSelectionModel().clearSelection();
            cbx4.getSelectionModel().clearSelection();
           
           }else{
                 Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Количество бутылок не соответствует литрам!");
           alert.showAndWait();
                   
                   }
           }
            }
        });
        
        cbx2.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {                     
            runJsons();                    
            cbx2.setItems(GetByTag(TAG_NAME,"2"));               
            cbx2.show();
            }   
        });
        
        
        cbx2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
             
                if(observable.getValue()==null){
                
             }else{
                    if(!isHave(cbx2.getSelectionModel().getSelectedItem().toString(),2)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText(cbx2.getSelectionModel().getSelectedItem().toString()+" отсутствует в магазине!");
                    Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    sp2.setVisible(false);
                    sp2.getValueFactory().setValue(0);
                    lb2.setVisible(false);
                    t2.setVisible(false);
                    bt2.setVisible(false);
                    cbx2.getSelectionModel().clearSelection();
                }else{
                    sp2.setVisible(false);
                    sp2.getValueFactory().setValue(0);
                    lb2.setVisible(false);
                    t2.setVisible(false);
                    bt2.setVisible(false);
                    cbx2.getSelectionModel().clearSelection();
                }
                }else{
            txg3.setVisible(false);
            sp3.setVisible(!true);
            lb3.setVisible(!true);
            t3.setVisible(!true);
            bt3.setVisible(!true);
             sp3.getValueFactory().setValue(0);
            cbx3.getSelectionModel().clearSelection();        
            
           
            cbx4.setVisible(!true);
            sp1.setVisible(!true);
            lb1.setVisible(!true);
            t1.setVisible(!true);                      
            spb1.setVisible(!true);
            lbb1.setVisible(!true);
            bt1.setVisible(!true);
            sp1.getValueFactory().setValue(0.0);
             spb1.getValueFactory().setValue(0);
            cbx1.getSelectionModel().clearSelection();
            
            sp2.setVisible(true);
            lb2.setVisible(true);
            t2.setVisible(true);       
            bt2.setVisible(true);
             sp2.getValueFactory().setValue(0);
            //System.out.println("////////"+newValue+"/////");
           double curbal = Double.parseDouble(getTwotag(cbx2.getSelectionModel().getSelectedItem().toString(),2,MT))-balancech(cbx2.getSelectionModel().getSelectedItem().toString(),2);
            t2.setText(ost+" "+curbal+" шт.");
             }
                }
            }
        });
        bt2.setOnAction(event->{
           if(sp2.getValueFactory().getValue()<1){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали количество бутылок!");
           alert.showAndWait();
           }else{
           int coun = Integer.parseInt(sp2.getEditor().getText());
           double price = Double.parseDouble(getTwotag(cbx2.getSelectionModel().getSelectedItem().toString(),2, TAG_PRICE));
           double itg = coun * price;
           res.setText(res.getText()+printCh2(cbx2.getSelectionModel().getSelectedItem().toString(),"ШТ",coun,price)+"\n");
           itog = itog + itg;
           titg.setText(String.valueOf(itog));
           jsores = new JSONObject();
           jsores.put(TIP, getTwotag(cbx2.getSelectionModel().getSelectedItem().toString(),2, TAG_ID));
           jsores.put(TCOUNT, String.valueOf(coun));
           jsores.put(TRES, itg);
           jsores.put(TM, m);
           jsores.put(TSELLER, selid);//тут долен быть ID продавца
                //jsores.put("balance", (balance2.get(cbx2.getSelectionModel().getSelectedIndex()) - coun));
           SimpleDateFormat ff = new SimpleDateFormat("dd.MM.yyyy HH.mm");
           jsores.put(TDATE, ff.format(System.currentTimeMillis()));
           //jsores.put("seltype","1"); ДОБАВИТЬ ПРИ НАЖАТИИ КОНТРОЛЬНОЙ КНОПКИ!!!!!!
           //jsores.put("dolgid","1");
           jsores.put(TAG_CHECK,checkcheck());
           jsares.add(jsores);
           sp2.setVisible(!true);
            lb2.setVisible(!true);
            t2.setVisible(!true);       
            bt2.setVisible(!true);
             sp2.getValueFactory().setValue(0);
             cbx2.getSelectionModel().clearSelection();
           }
        });
        cbx3.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
             
             sp3.getValueFactory().setValue(0);
            runJsons();
            cbx3.setItems(GetByTag(TAG_NAME,"3"));               
            cbx3.show();
            }   
        });
        
        cbx3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
             if(observable.getValue()==null){
                
             }else{
                 
                 if(!isHave(cbx3.getSelectionModel().getSelectedItem().toString(),3)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText(cbx3.getSelectionModel().getSelectedItem().toString()+" отсутствует в магазине!");
                    Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    sp3.setVisible(false);
                    sp3.getValueFactory().setValue(0);
                    lb3.setVisible(false);
                    t3.setVisible(false);
                    bt3.setVisible(false);
                    cbx3.getSelectionModel().clearSelection();
                }else{
                    sp3.setVisible(false);
                    sp3.getValueFactory().setValue(0);
                    lb3.setVisible(false);
                    t3.setVisible(false);
                    bt3.setVisible(false);
                    cbx3.getSelectionModel().clearSelection();
                }
                }else{
            sp1.setVisible(!true);
            lb1.setVisible(!true);
            t1.setVisible(!true);          
            cbx4.setVisible(!true);
            spb1.setVisible(!true);
            lbb1.setVisible(!true);
            bt1.setVisible(!true);
            //spb1.getEditor().setText("0");
            //sp1.getEditor().setText("0");
            cbx1.getSelectionModel().clearSelection();
             
             
            sp2.setVisible(!true);
            lb2.setVisible(!true);
            t2.setVisible(!true);       
            bt2.setVisible(!true);
             sp2.getValueFactory().setValue(0);
            cbx2.getSelectionModel().clearSelection();
             
            sp3.setVisible(true);
            lb3.setVisible(true);
            t3.setVisible(true);
            bt3.setVisible(true);
             sp3.getValueFactory().setValue(0);
            if(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,TAG_ZT).equals("2")){
              
             double curbal = Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,MT))-(balancech(cbx3.getSelectionModel().getSelectedItem().toString(),3));
             String pattern = "##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String format = decimalFormat.format(curbal);
            
             t3.setText(ost+" "+format+" кг.");lb3.setText("ГР");
            
              sp3.setVisible(false);
              txg3.setVisible(true);
              txg3.setText("");
   
            }else{
            double curbal = Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,MT))-balancech(cbx3.getSelectionModel().getSelectedItem().toString(),3);
            t3.setText(ost+" "+curbal+" шт.");lb3.setText("ШТ");
            txg3.setVisible(false);
            sp3.setVisible(true);
            }
            }
            }
            }
        });
        
        bt3.setOnAction(event->{
            int typez = Integer.parseInt(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3,"zt"));
            
         if(sp3.isVisible()&sp3.getValueFactory().getValue()<1){
             
                
            Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!");
           alert.setContentText("Вы не выбрали количество закусок!");
           alert.showAndWait();          
           }else if(txg3.isVisible()&txg3.getText().equals("")){
               
           
          Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Внимание!");
           alert.setHeaderText("Ошибка!!");
           alert.setContentText("Вы не выбрали количество закусок!");
           alert.showAndWait();
           
           }else{
                
          double coun=0;
          double price = Double.parseDouble(getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3, TAG_PRICE));
          double itg=0;
          double itgcoun=0;
          if(lb3.getText().equals("ШТ")){
              //System.out.println(lb3.getText());
              coun = sp3.getValueFactory().getValue();
              itg = coun * price;
              itgcoun=coun;
             // System.out.println("SPINNER" + coun);
          res.setText(res.getText()+printCh2(cbx3.getSelectionModel().getSelectedItem().toString(),"ШТ",coun,price)+"\n");             
       
          }else if(lb3.getText().equals("ГР")){
             // System.out.println(lb3.getText());
          coun = Double.parseDouble(txg3.getText());
           
              itg = ((price*coun)/100);
             // System.out.println("!!!???!!!"+coun+"   "+price);
          res.setText(res.getText()+printCh3(cbx3.getSelectionModel().getSelectedItem().toString(),"ГР",coun,price)+"\n");                      
          itgcoun = (double)coun/1000;
          //System.out.println("TEXT" + coun+"  "+itgcoun);
          
          }
            String pattern = "##0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String format = decimalFormat.format(itg);
                itog = itog + itg;
                titg.setText(String.valueOf(itog));
                jsores = new JSONObject();
                jsores.put(TIP, getTwotag(cbx3.getSelectionModel().getSelectedItem().toString(),3, TAG_ID));
                jsores.put(TCOUNT, Double.parseDouble(String.valueOf(itgcoun)));
                jsores.put(TRES, itg);
                jsores.put(TM, m);
                jsores.put(TSELLER, selid);//тут долен быть ID продавца
                //jsores.put("balance", (balance3.get(cbx3.getSelectionModel().getSelectedIndex()) - coun));
                SimpleDateFormat ff = new SimpleDateFormat("dd.MM.yyyy HH.mm");
                jsores.put(TDATE, ff.format(System.currentTimeMillis()));
                //jsores.put("seltype","1");ДОБАВИТЬ В РЕЗУЛЬТИР КНОПКУ!!!!!
                //jsores.put("dolgid","1");
                jsores.put(TAG_CHECK,checkcheck());
                //System.out.println(jsores);
                jsares.add(jsores);
          sp3.setVisible(!true);
           lb3.setVisible(!true);
            t3.setVisible(!true);
            bt3.setVisible(!true);
            sp3.getValueFactory().setValue(0);
            cbx3.getSelectionModel().clearSelection();
            txg3.setVisible(false);
            // }
        }
        });
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(grid, sSize.width, sSize.height);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
        (MainA.class.getResource("main.css").toExternalForm());
        primaryStage.show();

        
        
    }
    
    
}
