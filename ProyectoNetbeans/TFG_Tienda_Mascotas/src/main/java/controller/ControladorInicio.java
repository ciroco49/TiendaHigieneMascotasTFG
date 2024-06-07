/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.factory.HibernateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.Session;
import reportes.GenerarPDF;
import vista.Inicio;

/**
 *
 * @author ciroi
 */
public class ControladorInicio {
    public static Session session;
    public static Inicio ventana = new Inicio();
    
    
    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
    
    public static void generarPDF(String ruta_reporte, String ruta_destino) throws ClassNotFoundException, JRException, InstantiationException {
        GenerarPDF.generarPDF(ruta_reporte, ruta_destino);
    }
    
}
