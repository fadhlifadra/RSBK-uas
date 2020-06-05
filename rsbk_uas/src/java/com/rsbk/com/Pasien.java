/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsbk.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped

public class Pasien {


    private String pasien_id;
    private String pasien_nama;
    private String pasien_alamat;
    private String pasien_kondisi;

    
    public void setPasien_id(String pasien_id) {
        this.pasien_id = pasien_id;
    }
    
    public String getPasien_id() {
        return pasien_id;
    }

    
   public void setPasien_nama(String pasien_nama) {
        this.pasien_nama = pasien_nama;
    }
    public String getPasien_nama() {
        return pasien_nama;
    }

    public void setPasien_alamat(String pasien_alamat) {
        this.pasien_alamat = pasien_alamat;
    }

    public String getPasien_alamat() {
        return pasien_alamat;
    }

    public void setPasien_kondisi(String pasien_kondisi) {
        this.pasien_kondisi = pasien_kondisi;
    }

    public String getPasien_kondisi() {
        return pasien_kondisi;
    }
  
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

    
    public ArrayList<Pasien> getGet_all_pasien(){
        ArrayList<Pasien> list_of_pasiens=new ArrayList<Pasien>();
        try {
            
            
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            
            Statement st=connection.createStatement();
            
            ResultSet rs=st.executeQuery("select * from pasien");
            
            while(rs.next()){
                Pasien obj_Pasien=new Pasien();
                
                obj_Pasien.setPasien_id(rs.getString("id"));
                obj_Pasien.setPasien_nama(rs.getString("nama"));
                obj_Pasien.setPasien_alamat(rs.getString("alamat"));
                obj_Pasien.setPasien_kondisi(rs.getString("kondisi"));
                
                list_of_pasiens.add(obj_Pasien);
            }
        } catch (Exception e) {
            System.out.println(e);
            
        }
        return list_of_pasiens;
        
    }
    
    
    public void add_pasien(){
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
        PreparedStatement ps=connection.prepareStatement("insert into pasien(nama, alamat, kondisi) value('"+pasien_nama+"','"+pasien_alamat+"','"+pasien_kondisi+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String delete_pasien(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String field_id= params.get("action");
      try {
         DB_connection obj_DB_connection=new DB_connection();
         Connection connection=obj_DB_connection.get_connection();
       PreparedStatement ps=connection.prepareStatement("delete from pasien where id=?");
         ps.setString(1, field_id);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/index.xhtml?faces-redirect=true";   
}
    
    public String edit_pasien(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     String field_pasien_id= params.get("action");
     try {
          DB_connection obj_DB_connection=new DB_connection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from pasien where id="+field_pasien_id);
          Pasien obj_Pasien=new Pasien();
          rs.next();
          obj_Pasien.setPasien_id(rs.getString("id"));
          obj_Pasien.setPasien_nama(rs.getString("nama"));
          obj_Pasien.setPasien_alamat(rs.getString("alamat"));
          obj_Pasien.setPasien_kondisi(rs.getString("kondisi"));
          sessionMap.put("editpasien", obj_Pasien);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/edit.xhtml?faces-redirect=true";   
}
    public String update_pasien(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String update_id= params.get("update_id");
        try {
            DB_connection obj_DB_connection=new DB_connection();
            Connection connection=obj_DB_connection.get_connection();
      PreparedStatement ps=connection.prepareStatement("update pasien set nama=?, alamat=?, kondisi=? where id=?");
            
            ps.setString(1, pasien_nama);
            ps.setString(2, pasien_alamat);
            ps.setString(3, pasien_kondisi);
            ps.setString(4, update_id); 
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("lala");
        }
       return "/index.xhtml?faces-redirect=true";   
}
    
    
    public Pasien() {
    }
    
}
