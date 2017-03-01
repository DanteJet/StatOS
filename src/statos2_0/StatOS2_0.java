/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statos2_0;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import parser.JSONParser;

/**
 *
 * @author Yakovbubnov
 */
public class StatOS2_0 extends Application {
     private static String url_getstores = "http://f0100090.xsph.ru/getstores.php";
     
     private static String url_checkpass = "http://f0100090.xsph.ru/passcheck.php";
     private static String url_checksell = "http://f0100090.xsph.ru/sellcheck.php";
      private static String url_storeup = "http://f0100090.xsph.ru/storeup.php";
     int idstore;
      private String getMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte[] bb=md.digest();
        String res="";
        for(int i=0;i<bb.length; i++) {
            res += bb[i];
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
      private boolean storecheck(int id) throws NoSuchAlgorithmException{
         
       List<NameValuePair> param = new ArrayList<NameValuePair>();
        
        JSONObject jsons1 = new JSONObject();
        param.add(new BasicNameValuePair("idm",String.valueOf(id)));
        JSONParser jP1 = new JSONParser();
        boolean res;
        jsons1 = jP1.makeHttpRequest(url_checksell, "POST", param);
        int idsel =0;
        //System.out.println(jsons1);
       // String success = (String) jsons1.;  
        //System.out.println("!!!!"+jsons1.isNull("id"));
            if (!jsons1.isNull("id_seller")) {
                
                    
                    idsel= jsons1.getInt("id_seller");
                  //System.out.println("params "+param+"\n"+idsel);
                
            if(idsel>1){
            res = false;
            }else{
                res=true;
            }
            
            }else{
            res=true;
            }
         
      return res;   
     }
     
      
     private String [] checkpas(String login, String pass) throws NoSuchAlgorithmException{
         String res [] = new String[4];
         String hpas=getMD5(pass); 
       List<NameValuePair> param = new ArrayList<NameValuePair>();
        // получаем JSON строк с URL
        JSONObject jsons1 = new JSONObject();
        param.add(new BasicNameValuePair("login",login));
        JSONParser jP1 = new JSONParser();
        jsons1 = jP1.makeHttpRequest(url_checkpass, "POST", param);
        //System.out.println(jsons1);
       // String success = (String) jsons1.;  
        //System.out.println("!!!!"+jsons1.isNull("id"));
            if (!jsons1.isNull("id")) {
                
                    
                    res[0]=(String) jsons1.get("id");
                    res[1]=(String) jsons1.get("name");
                    res[2]=(String) jsons1.get("login");
                    res[3] =(String) jsons1.get("pass");
                
            if(!res[3].equals(hpas)){
                
                res[0]="-1";
                res[1]="-1";
                res[2]="-1";
                res[3]="-1";
            }    
            }else{
                res[0]="-1";
            }
         
      return res;   
     }
     private ObservableList<String> GetByTag() {
         JSONArray product = null;
       
        JSONParser jP = new JSONParser();
       ArrayList<HashMap<String, String>> prod = new ArrayList<HashMap<String, String>>();
       List<NameValuePair> params = new ArrayList<NameValuePair>();
        // получаем JSON строк с URL
        JSONObject jsons = null;
        jsons = jP.makeHttpRequest(url_getstores, "GET", params);
        int success = jsons.getInt("success");
            
            if (success == 1) {
                // продукт найден
                // Получаем масив из Продуктов
                product = jsons.getJSONArray("products");
                  
                // перебор всех продуктов
                for (int i = 0; i < product.length(); i++) {
                    JSONObject c = product.getJSONObject(i);

                    // Сохраняем каждый json элемент в переменную
                    String id = c.getString("id");
                    String name = c.getString("name");       
                    // Создаем новый HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // добавляем каждый елемент в HashMap ключ => значение
                    map.put("id", id);
                    map.put("name", name);                  
                    // добавляем HashList в ArrayList
                    prod.add(map);
                    
                    //System.out.println("prod="+prod);
                }
            }
        //Получение значение по двум параметрам
        ObservableList<String> al = FXCollections.observableArrayList();       
        //if(balance1.size()>=0){balanceadd();}
        
           // System.out.println("!!"+prod);
            
            for (int i = 0; i < prod.size(); i++) {
               
                    al.add(prod.get(i).get("name"));     
            }
    
        return al;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Авторизация");
       
        GridPane root = new GridPane();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25,25,25,25));
        
        Text scenetitle = new Text("Вход");
        
        root.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setId("welcome-text");
        Label userName = new Label("Логин:");
        //userName.setId("label");
        root.add(userName, 0, 1);
 
        TextField userTextField = new TextField();
        root.add(userTextField, 1, 1);
 
        Label pw = new Label("Пароль:");
        root.add(pw, 0, 2);
 
        PasswordField pwBox = new PasswordField();
        root.add(pwBox, 1, 2);
        ComboBox store = new ComboBox();
        store.setItems(GetByTag());
        root.add(store,1,3);
        
        Button btn = new Button("Вход");
        //btn.setPrefSize(100, 20);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
     
        root.add(hbBtn, 1, 4);
        
        Button btn2 = new Button("Выход");
        //btn2.setPrefSize(100, 20);
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);    
        root.add(hbBtn2, 1, 5);
        
        final Text actiontarget = new Text();
        root.add(actiontarget, 1, 6);
        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
         @Override
        public void handle(ActionEvent e) {
         System.exit(0);}});
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
        public void handle(ActionEvent e) {
            if(userTextField.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Вы не ввели логин!");
                    alert.showAndWait();  
            }else if(pwBox.getText().equals("")){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Вы не ввели пароль!");
                    alert.showAndWait();     
            }else if (store.getSelectionModel().getSelectedIndex()<0){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Вы не выбрали магазин!");
                    alert.showAndWait();  
            }else{
             try {
                 String[] resu = checkpas(userTextField.getText(),pwBox.getText());
                 if (resu[0].equals("-1")){
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("Введеные данные не верны!");
                    alert.showAndWait();  
                 }else if(storecheck((store.getSelectionModel().getSelectedIndex()+1))==false){
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                   alert.setTitle("Внимание!");
                    alert.setHeaderText("Ошибка!");
                    alert.setContentText("В данном магазине уже авторизирован продавец!"
                            + "\nЕсли это ошибка и вам надо авторизироваться - нажмите ОК");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
    // ... user chose OK
    idstore=store.getSelectionModel().getSelectedIndex()+1;
                     updsel(idstore,Integer.parseInt(resu[0]));
                     MainA ma = new MainA();
                     ma.m=(idstore);
                     ma.MT="m"+idstore;
                     ma.selid=Integer.parseInt(resu[0]);
                     ma.nameseller=resu[1]; 
                     ma.storename=store.getSelectionModel().getSelectedItem().toString();
                     ma.start(primaryStage);
                    } else {
    // ... user chose CANCEL or closed the dialog
                    }
                
                  
                    
                 }else{
                     //Процедура входа
                    
                     
                     idstore=store.getSelectionModel().getSelectedIndex()+1;
                     updsel(idstore,Integer.parseInt(resu[0]));
                     MainA ma = new MainA();
                     ma.m=(idstore);
                     ma.MT="m"+idstore;
                     ma.selid=Integer.parseInt(resu[0]);
                     ma.nameseller=resu[1];
                     ma.storename=store.getSelectionModel().getSelectedItem().toString();
                     
                     ma.start(primaryStage);
                     
                     
                 }
                 
                 } catch (NoSuchAlgorithmException ex) {
                 Logger.getLogger(StatOS2_0.class.getName()).log(Level.SEVERE, null, ex);
             }  catch (Exception ex) {
                    Logger.getLogger(StatOS2_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                 /**
                  * if((userTextField.getText().equals("admin"))&(pwBox.getText().equals("admin"))){
                  * actiontarget.setId("acttrue");
                  * actiontarget.setText("Данные верны!");
                  * MainA ma = new MainA();
                  * ma.m=1;
                  * try {
                  * ma.m=1;
                  * ma.MT="m1";
                  * ma.start(primaryStage);
                  * } catch (Exception ex) {
                  * Logger.getLogger(StatOS2_0.class.getName()).log(Level.SEVERE, null, ex);
                  * }
                  * }else{
                  * actiontarget.setId("actfalse");
                  * actiontarget.setText("Проверьте данные!");
                  * } **/

        }
        });
        //Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        //sSize.getHeight();
        Scene scene = new Scene(root,sSize.getWidth(),sSize.getHeight());
        
        
        primaryStage.setScene(scene);
        scene.getStylesheets().add
        (StatOS2_0.class.getResource("adminStatOS.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
