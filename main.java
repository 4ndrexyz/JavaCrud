/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databuku;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author 4ndrexyz
 */
public class DataBuku {
    static final String URL = "com.mysql.jdbc.Driver";
    static final String DB = "jdbc:mysql://localhost/databuku";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            
            Class.forName(URL);
            conn = DriverManager.getConnection(DB, USER, PASS);
            stmt = conn.createStatement();
            
            while(!conn.isClosed()){
                showMenu();
            }
            
            conn.close();
            stmt.close();
            
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
    
     static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertBuku();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateBuku();
                    break;
                case 4:
                    deleteBuku();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM tb_buku";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA BUKU DI PERPUSTAKAAN   |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String judul = rs.getString("judul");
                String penulis = rs.getString("penulis");
                String penerbit = rs.getString("penerbit");
                
                System.out.println(String.format("No Buku      : %d ", id));
                System.out.println(String.format("Judul Buku   : %s ", judul));
                System.out.println(String.format("Penulis      : %s ", penulis));
                System.out.println(String.format("Penerbit     : %s ", penerbit));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertBuku() {
        try {
            // ambil input dari user
            System.out.print("Judul: ");
            String judul = input.readLine().trim();
            System.out.print("Penulis: ");
            String penulis = input.readLine().trim();
            System.out.print("Penerbit: ");
            String penerbit = input.readLine().trim();
 
            // query simpan
            String sql = "INSERT INTO tb_buku (judul, penulis, penerbit) VALUE('%s', '%s', '%s')";
            sql = String.format(sql, judul, penulis, penerbit);

            // simpan buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateBuku() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int id = Integer.parseInt(input.readLine());
            System.out.print("Judul: ");
            String judul = input.readLine().trim();
            System.out.print("Penulis: ");
            String penulis = input.readLine().trim();
            System.out.print("Penerbit: ");
            String penerbit = input.readLine().trim();

            // query update
            String sql = "UPDATE tb_buku SET judul='%s', penulis='%s', penerbit='%s' WHERE id=%d";
            sql = String.format(sql, judul, penulis, penerbit, id);

            // update data buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteBuku() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int id = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM tb_buku WHERE id=%d", id);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
