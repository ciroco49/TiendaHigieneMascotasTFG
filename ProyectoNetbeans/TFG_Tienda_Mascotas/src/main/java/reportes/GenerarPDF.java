/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import controller.ControladorInicio;
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

/**
 *
 * @author ciroi
 */
public class GenerarPDF {
    
    public static void generarPDF(String ruta_reporte, String ruta_destino) {
        String baseDatos = "jdbc:mysql://localhost:3306/tienda_higiene_mascotas";
        String usuario = "root";
        String clave = "root";
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, clave);
            Map parametros = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(ruta_reporte, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(print, ruta_destino);
            JOptionPane.showMessageDialog(null, "¡Informe generado!");

        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorInicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorInicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorInicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


