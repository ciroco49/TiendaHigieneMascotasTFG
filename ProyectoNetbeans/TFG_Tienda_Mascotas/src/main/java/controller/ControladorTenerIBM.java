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
import modelo.dao.EspecialistaDAO;
import modelo.dao.MascotaDAO;
import modelo.vo.Cliente;
import modelo.vo.Especialista;
import modelo.vo.Mascota;
import modelo.vo.Tener;
import org.hibernate.Session;
import vista.EspecialistasIBM;
import vista.MascotasIBM;
import vista.TenerIBM;

/**
 *
 * @author ciroi
 */
public class ControladorTenerIBM {
    public static Session session;
    public static TenerDAO tenerDAO;
    public static TenerIBM ventana;
    
    public static void iniciar(JFrame parent) {
        ventana = new TenerIBM(parent, true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        tenerDAO = new TenerDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }

    public static void insertar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarDNICliente().getText().isEmpty() || ventana.getInsertarDNIMascota().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNICliente().getText())) {
            JOptionPane.showMessageDialog(null, "El dni de cliente proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNIMascota().getText())) {
            JOptionPane.showMessageDialog(null, "El dni de mascota proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente y la mascota existen antes de insertar
            Cliente cliente = new ClienteDAO().getCliente(session, ventana.getInsertarDNICliente().getText());
            
            if(cliente == null) {
                JOptionPane.showMessageDialog(null, "El cliente proporcionado no existe");
                return;
            }
            
            Mascota mascota = new MascotaDAO().getMascota(session, ventana.getInsertarDNIMascota().getText());
            
            if(mascota == null) {
                JOptionPane.showMessageDialog(null, "La mascota proporcionada no existe");
                return;
            }
            
            //Compruebo que no exista ya el registro en la tabla
            Tener tener = tenerDAO.getTener(session, cliente, mascota);
           
            if(tener != null) {
                JOptionPane.showMessageDialog(null, "El registro proporcionado ya existe");
                return;
            }
            
            tenerDAO.insertar(session, cliente, mascota);
            
            JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch(Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void borrar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getBorrarDNICliente().getText().isEmpty() || ventana.getBorrarDNIMascota().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getBorrarDNICliente().getText())) {
            JOptionPane.showMessageDialog(null, "El dni de cliente proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.validarDNI(ventana.getBorrarDNIMascota().getText())) {
            JOptionPane.showMessageDialog(null, "El dni de mascota proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente y la mascota existen antes de borrar
            Cliente cliente = new ClienteDAO().getCliente(session, ventana.getBorrarDNICliente().getText());
            
            if(cliente == null) {
                JOptionPane.showMessageDialog(null, "El cliente proporcionado no existe");
                return;
            }
            
            Mascota mascota = new MascotaDAO().getMascota(session, ventana.getBorrarDNIMascota().getText());
            
            if(mascota == null) {
                JOptionPane.showMessageDialog(null, "La mascota proporcionada no existe");
                return;
            }
            
            //Compruebo que el registro existe antes de borrar
            Tener tener = tenerDAO.getTener(session, cliente, mascota);
            
            if(tener == null) {
                JOptionPane.showMessageDialog(null, "No existe el registro proporcionado");
                return;
            }

            tenerDAO.borrar(session, tener);
                
            JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }
 
    
}
