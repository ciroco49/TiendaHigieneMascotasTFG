/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.factory.HibernateUtil;
import org.hibernate.Session;
import vista.Inicio;

/**
 *
 * @author ciroi
 */
public class ControladorInicio {
    public static Session session;
    public static Inicio ventana = new Inicio();
    
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    } 
    
}
