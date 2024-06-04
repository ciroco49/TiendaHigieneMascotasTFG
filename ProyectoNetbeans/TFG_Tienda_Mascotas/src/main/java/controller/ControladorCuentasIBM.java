/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import modelo.dao.TenerDAO;
import AA_Main.Regex;
import controller.factory.HibernateUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.ClienteDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.EspecialistaDAO;
import modelo.dao.MascotaDAO;
import modelo.vo.Cliente;
import modelo.vo.Cuenta;
import modelo.vo.Especialista;
import modelo.vo.Mascota;
import modelo.vo.Tener;
import org.hibernate.Session;
import vista.CuentasIBM;
import vista.EspecialistasIBM;
import vista.MascotasIBM;
import vista.TenerIBM;

/**
 *
 * @author ciroi
 */
public class ControladorCuentasIBM {
    public static Session session;
    public static CuentaDAO cuentaDAO;
    public static CuentasIBM ventana;
    
    public static void iniciar(JFrame parent) {
        ventana = new CuentasIBM(parent, true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        cuentaDAO = new CuentaDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }

    public static void insertar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarCorreo().getText().isEmpty() || ventana.getInsertarContraseña().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarCorreo(ventana.getInsertarCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que la cuenta no existe antes de insertar
            Cuenta cuenta = cuentaDAO.getCuenta(session, ventana.getInsertarCorreo().getText());
            
            if(cuenta != null) {
                JOptionPane.showMessageDialog(null, "La cuenta proporcionada ya existe");
                return;
            }
            
            cuentaDAO.insertar(session, ventana.getInsertarCorreo().getText(), ventana.getInsertarContraseña().getText());
            
            JOptionPane.showMessageDialog(null, "Cuenta insertada correctamente");
            
            HibernateUtil.commitTx(session);
        } catch(Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void borrar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getBorrarPorCorreo().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar el campo correo");
            return;
        } else if(!Regex.validarCorreo(ventana.getBorrarPorCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que la cuenta existe antes de borrar
            Cuenta cuenta = cuentaDAO.getCuenta(session, ventana.getBorrarPorCorreo().getText());
            
            if(cuenta == null) {
                JOptionPane.showMessageDialog(null, "La cuenta proporcionada no existe");
                return;
            }

            cuentaDAO.borrar(session, cuenta);
                
            JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }
 
    
}
