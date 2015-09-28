/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.restcontrollers;

import edu.eci.cosw.samples.logic.ServiceFacade;
import edu.eci.cosw.samples.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/clientes")
public class ClienteRest {
    
    @Autowired
    ServiceFacade sf;
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Cliente consultarCliente(@PathVariable int id)throws OperationFailedException{
        Cliente cliente = null;
        if(sf.existeCliente(id)){
            cliente = sf.buscarClientePorId(id);
        }else{
            throw new OperationFailedException();
        }
        return cliente;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> persist(@RequestBody Cliente c){
        if(sf.existeCliente(c.getIdcliente())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            sf.guardarCliente(c);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
