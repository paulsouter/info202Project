/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.DatabaseManager;
import gui.MainMenu;
/**
 *
 * @author soupa868
 */
public class Administration {
    public static void main(String[] args) {
        MainMenu main = new MainMenu( new DatabaseManager());
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }
}