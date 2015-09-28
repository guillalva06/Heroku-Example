/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.restcontrollers;

import edu.eci.cosw.samples.logic.ServiceFacade;
import edu.eci.cosw.samples.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/pedidos")
public class PedidosRest {
    
    @Autowired
    ServiceFacade sf;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pedido consped(@PathVariable int id)throws OperationFailedException{
        Pedido p = null;
        if(sf.existePedido(id)){
            p = sf.buscarPedidoPorId(id);
        }else{
            throw new OperationFailedException();
        }
        return p;
    }
}
