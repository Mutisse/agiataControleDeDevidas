/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import ficha01.controller.Controller;
import ficha01.view.TelaLogin;
import ficha01.view.TelaRegistoAdmin;

/**
 *
 * @author Mutisse
 */
public class Ficha01 {

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.CaminhoPrincipal();

        if (!controller.lista().isEmpty()) {
            new TelaLogin().setVisible(true);
        } else {
            new TelaRegistoAdmin().setVisible(true);
        }
    }

}
