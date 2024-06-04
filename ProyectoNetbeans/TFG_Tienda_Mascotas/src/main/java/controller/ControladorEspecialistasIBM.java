/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import AA_Main.Regex;
import controller.factory.HibernateUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.ClienteDAO;
import modelo.dao.EspecialistaDAO;
import modelo.vo.Cliente;
import modelo.vo.Especialista;
import org.hibernate.Session;
import vista.ClientesIBM;
import vista.EspecialistasIBM;

/**
 *
 * @author ciroi
 */
public class ControladorEspecialistasIBM {
    public static Session session;
    public static EspecialistaDAO especialistaDAO;
    public static EspecialistasIBM ventana;
    
    public static void iniciar(JFrame parent) {
        ventana = new EspecialistasIBM(parent, true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        especialistaDAO = new EspecialistaDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }

    public static void insertar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarDNI().getText().isEmpty() || ventana.getInsertarNombre().getText().isEmpty() ||
           ventana.getInsertarApellidos().getText().isEmpty() || ventana.getInsertarTelefono().getText().isEmpty() ||
           ventana.getInsertarCorreo().getText().isEmpty() || ventana.getInsertarResidencia().getText().isEmpty() ||
           ventana.getInsertarSueldo().getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.datoEsEntero(ventana.getInsertarTelefono().getText())) {
            JOptionPane.showMessageDialog(null, "El telefono proporcionado debe estar conformado por números enteros");
            return;
        } else if(!Regex.validarCorreo(ventana.getInsertarCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.datoEsDouble(ventana.getInsertarSueldo().getText())) {
            JOptionPane.showMessageDialog(null, "El sueldo proporcionado debe estar conformado por números decimales");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el especialista no existe antes de insertar
            Especialista especialista = especialistaDAO.getEspecialista(session, ventana.getInsertarDNI().getText());
            
            if(especialista != null) {
                JOptionPane.showMessageDialog(null, "El especialista proporcionado ya existe");
                return;
            }
            
            especialistaDAO.insertar(session, ventana.getInsertarDNI().getText(), ventana.getInsertarNombre().getText(), 
                                ventana.getInsertarApellidos().getText(), ventana.getInsertarTelefono().getText(),
                                ventana.getInsertarCorreo().getText(), ventana.getInsertarResidencia().getText(),
                                Double.parseDouble(ventana.getInsertarSueldo().getText()));
            
            JOptionPane.showMessageDialog(null, "Especialista insertado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch(Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void borrar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getBorrarPorDNI().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar el campo DNI");
            return;
        } else if(!Regex.validarDNI(ventana.getBorrarPorDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente existe antes de borrar
            Especialista especialista = especialistaDAO.getEspecialista(session, ventana.getBorrarPorDNI().getText());
            
            if(especialista == null) {
                JOptionPane.showMessageDialog(null, "No existe el especialista proporcionado");
                return;
            }
            
            int aceptar = 0;
            int respuesta = JOptionPane.showConfirmDialog(null, "Al borrar un especialista, se borrarán "
                    + "las mascotas asociadas.\n Es recomendable modificar el especialista a dichas mascotas "
                    + "antes de borrar al especialista para evitar este problema. \n ¿Está seguro de borrar a este especialista?", 
                    "Peligro pérdida de datos", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if(respuesta == aceptar) {
                especialistaDAO.borrar(session, especialista);
                JOptionPane.showMessageDialog(null, "Cliente borrado correctamente");
            }
            
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }
 
    
}
