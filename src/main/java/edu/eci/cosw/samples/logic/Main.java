/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.logic;

import edu.eci.cosw.samples.model.Cliente;
import edu.eci.cosw.samples.model.Pedido;
import edu.eci.cosw.springdataintro.components.ClientEvaluationException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author User
 */
public class Main {
    
    public static void main(String[] args){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ServiceFacade sf = ac.getBean(ServiceFacade.class);
        List<Cliente> clientes = null;
        try {
            clientes = sf.clientesReportadosPorApellido("var");
        } catch (ClientEvaluationException ex) {
            System.out.println("Fallo consulta por apellido");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(clientes!=null){
            for(Cliente c: clientes){
                System.out.println(c.getIdcliente()+" "+c.getNombre()+" "+c.getDireccion()+" "+c.getTelefono());
            }
        }
        try {
            //Registrar un nuevo cliente
            sf.registrarCliente(1072672, "Guillermo Alvarez", "Calle 3#3E-116", "1234567");
        } catch (ClientEvaluationException ex) {
            System.out.println("Fallo insercion");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Crear un pedido
        Date d = new Date(2015-1900, 8, 23);
        int[] idProductos = {1};
        int[] cantidades = {3};
        sf.registrarPedido(1072672,idProductos , cantidades, d);
        //Mirar en la base de datos que se creo el pedido de este cliente     
        try {
            //Consulta Fallida
            sf.registrarCliente(333, "Luis Alvarez", "Calle 3E#3-116", "1234567");
        } catch (ClientEvaluationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
