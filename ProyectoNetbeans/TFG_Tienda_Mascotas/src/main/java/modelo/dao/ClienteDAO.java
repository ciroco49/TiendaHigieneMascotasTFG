/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import modelo.vo.Cliente;
import org.hibernate.Session;

/**
 *
 * @author ciroi
 */
public class ClienteDAO {
    
    public Cliente getCliente(Session session, String DNI) {
        //OBTIENE CLIENTES POR LA PK
        return session.get(Cliente.class, DNI);
    }
    
    public void insertar(Session session, String dni, String nombre, String apellidos, 
                        String telefono, String correo, String residencia) throws Exception {
        
      Cliente cliente = new Cliente(dni, nombre, apellidos, telefono, correo, residencia);
      
      session.save(cliente);
    }
    
    public void borrar(Session session, Cliente cliente) {
        session.delete(cliente);
    }

    public void modificar(Session session, Cliente cliente, String nombre, String apellidos, String telefono, String correo, String residencia) {
            cliente.setNombre(nombre);
            cliente.setApellidos(apellidos);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setResidencia(residencia);
        session.update(cliente);
    }
    
    
}
