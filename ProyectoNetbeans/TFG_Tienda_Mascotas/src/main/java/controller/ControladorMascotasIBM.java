/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import AA_Main.Regex;
import controller.factory.HibernateUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.EspecialistaDAO;
import modelo.dao.MascotaDAO;
import modelo.vo.Especialista;
import modelo.vo.Mascota;
import org.hibernate.Session;
import vista.EspecialistasIBM;
import vista.MascotasIBM;

/**
 *
 * @author ciroi
 */
public class ControladorMascotasIBM {
    public static Session session;
    public static MascotaDAO mascotaDAO;
    public static MascotasIBM ventana;
    
    public static void iniciar(JFrame parent) {
        ventana = new MascotasIBM(parent, true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        mascotaDAO = new MascotaDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }

    public static void insertar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarDNI().getText().isEmpty() || ventana.getInsertarNombre().getText().isEmpty() ||
           ventana.getInsertarEdad().getText().isEmpty() || ventana.getInsertarSexo().getText().isEmpty() ||
           ventana.getInsertarEspecie().getText().isEmpty() || ventana.getInsertarRaza().getText().isEmpty() ||
           ventana.getInsertarDNIespecialista().getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.datoEsEntero(ventana.getInsertarEdad().getText())) {
            JOptionPane.showMessageDialog(null, "La edad proporcionado debe estar conformada por números enteros");
            return;
        } else if(!(ventana.getInsertarSexo().getText().charAt(0) == 'M') &&
                !(ventana.getInsertarSexo().getText().charAt(0) == 'H')) {
            JOptionPane.showMessageDialog(null, "El sexo proporcionado debe ser 'H' para hembras y 'M' para machos");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNIespecialista().getText())) {
            JOptionPane.showMessageDialog(null, "El dni de especialista proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que la mascota no existe antes de insertar
            Mascota mascota = mascotaDAO.getMascota(session, ventana.getInsertarDNI().getText());
            
            if(mascota != null) {
                JOptionPane.showMessageDialog(null, "La mascota proporcionado ya existe");
                return;
            }
            
            //Obtengo el especialista que asociarle a la mascota
            Especialista especialista = new EspecialistaDAO().getEspecialista(session, ventana.getInsertarDNIespecialista().getText());
            
            if(especialista == null) {
                JOptionPane.showMessageDialog(null, "No existe el especialista proporcionado");
                return;
            }
            
            mascotaDAO.insertar(session, ventana.getInsertarDNI().getText(), ventana.getInsertarNombre().getText(), 
                                Integer.parseInt(ventana.getInsertarEdad().getText()), ventana.getInsertarSexo().getText().charAt(0),
                                ventana.getInsertarEspecie().getText(), ventana.getInsertarRaza().getText(), especialista);
            
            JOptionPane.showMessageDialog(null, "Mascota insertada correctamente");
            
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
            Mascota mascota = mascotaDAO.getMascota(session, ventana.getBorrarPorDNI().getText());
            
            if(mascota == null) {
                JOptionPane.showMessageDialog(null, "No existe la mascota proporcionada");
                return;
            }

            mascotaDAO.borrar(session, mascota);
                
            JOptionPane.showMessageDialog(null, "Mascota borrada correctamente");
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }
 
    
}
