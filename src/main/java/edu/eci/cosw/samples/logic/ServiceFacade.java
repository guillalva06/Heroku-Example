/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.logic;

import edu.eci.cosw.samples.model.Cliente;
import edu.eci.cosw.samples.model.DetallePedido;
import edu.eci.cosw.samples.model.DetallePedidoId;
import edu.eci.cosw.samples.model.Pedido;
import edu.eci.cosw.samples.model.Producto;
import edu.eci.cosw.samples.persistence.ClientsRepository;
import edu.eci.cosw.samples.persistence.OrdersRepository;
import edu.eci.cosw.samples.persistence.ProductsRepository;
import edu.eci.cosw.springdataintro.components.ClientEvaluationException;
import edu.eci.cosw.springdataintro.components.ClientEvaluator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 3079945
 */
@Service
public class ServiceFacade {
    
    
    @Autowired
    ClientsRepository cp;
    
    
    @Autowired
    ClientEvaluator ce;
    
    @Autowired
    OrdersRepository or;
    
    @Autowired
    ProductsRepository pr;
    
/*
    *@obj: obtener los clientes cuyo nombre contenga un apellido
    *determinado, y que se encuentren reportados en una central de riesgo.
    *@param: apellido del cliente
    *@return: la lista de los clientes reportados
*/
public List<Cliente> clientesReportadosPorApellido(String apellido) throws ClientEvaluationException{
    List<Cliente> temp = cp.search("%"+apellido+"%");
    List<Cliente> clientesReportados = new LinkedList<>();
    for(Cliente c : temp){
        if(ce.reportedUser(c.getIdcliente()))
            clientesReportados.add(c);
    }
    return clientesReportados;
}
 
/*
*@obj: registrar un nuevo cliente local y remotamente, garantizando
* que sólo se registrará localmente si queda registrado en el
* sistema remoto.
*@param id identificador del cliente
*@param nombre nombre del cliente
*@param direccion direccion del cliente
*@param telefono telefono fel cliente
*/
@Transactional(rollbackFor = ClientEvaluationException.class)
public void registrarCliente(int id, String nombre, String direccion, String telefono) throws ClientEvaluationException{
    ce.addNewClient(id, nombre);
    cp.save(new Cliente(id, nombre, direccion, telefono));
}

/**
* Hacer persistente un nuevo pedido, creado a partir del identificador de cliente dado, los identificadores
* de los productos, sus cantidades, y la fecha.
* @pre identificadorProductos.lenght==cantidades.lenght
* @param idCliente identificador del cliente que hace el pedido
* @param identificadoresProductos conjunto de identificadores de los productos que componen el pedido
* @param cantidades conjunto de cantidades de los productos pedidos. La cantidad i, será entonces,
* la cantidad asociada al producto i. */

@Transactional
public void registrarPedido(int idCliente, int[] identificadoresProductos, int[] cantidades, Date fecha){    
    Pedido ped = new Pedido(cp.findOne(idCliente), fecha);
    or.save(ped);
    Set<DetallePedido> detallePedidos = ped.getDetallesPedidos();
    for(int i=0;i<identificadoresProductos.length;i++){
        Producto prod = pr.findOne(identificadoresProductos[i]);        
        DetallePedidoId detallePedidoId = new DetallePedidoId(identificadoresProductos[i], ped.getIdpedido());
        DetallePedido detallePedido = new DetallePedido(detallePedidoId, prod, cantidades[i]);
        Set<DetallePedido>tempSet = prod.getDetallesPedidos();
        detallePedidos.add(detallePedido);
        //dr.save(detallePedido);        
        tempSet.add(detallePedido);
        pr.save(prod);
    }    
    or.save(ped);
    System.out.println("Tamaño pedido "+ped.getDetallesPedidos().size());
}


public void registrarProducto(int idProducto, String nombre, long precio){
    System.out.println(pr);
    pr.save(new Producto(idProducto, nombre, precio));
}


public List<Pedido> buscarPedidosDeUnClientePorFecha(int idCliente, Date date){
     return or.search(idCliente, date);
}

public Pedido buscarPedidoPorId(int idPedido){
    return or.findOne(idPedido);
}

public boolean existePedido(int idPedido){        
    return or.exists(idPedido);
}

public boolean existeCliente(int idCliente){
    return cp.exists(idCliente);
}

public Cliente buscarClientePorId(int idCliente){
    return cp.findOne(idCliente);
}

public void guardarCliente(Cliente cliente){
    cp.save(cliente);
}

}
