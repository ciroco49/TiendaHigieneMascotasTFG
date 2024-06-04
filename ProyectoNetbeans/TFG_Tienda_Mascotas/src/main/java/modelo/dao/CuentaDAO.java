/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.query.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import modelo.vo.Cliente;
import modelo.vo.Cuenta;
import modelo.vo.Especialista;
import modelo.vo.Mascota;
import modelo.vo.Tener;
import org.hibernate.Session;

/**
 *
 * @author ciroi
 */
public class CuentaDAO {
    
    public Cuenta getCuenta(Session session, String correo) {
        //OBTIENE CUENTAS POR LA PK
        return session.get(Cuenta.class, correo);
    }
    
    public void insertar(Session session, String correo, String contraseña) throws Exception {
        
      Cuenta cuenta = new Cuenta(correo, contraseña);
      
      session.save(cuenta);
    }
    
    public void borrar(Session session, Cuenta cuenta) {
        session.delete(cuenta);
    }
    
    
}
