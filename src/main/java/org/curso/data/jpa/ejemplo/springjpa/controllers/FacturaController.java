package org.curso.data.jpa.ejemplo.springjpa.controllers;

import jakarta.validation.Valid;
import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.curso.data.jpa.ejemplo.springjpa.entities.ItemFactura;
import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.curso.data.jpa.ejemplo.springjpa.services.ClienteService;
import org.curso.data.jpa.ejemplo.springjpa.services.IFacturaService;
import org.curso.data.jpa.ejemplo.springjpa.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IFacturaService facturaService;

    @GetMapping("/form/{clienteId}")
    public String form(@PathVariable Long clienteId, Model model, RedirectAttributes mensajes) {
        Cliente cliente = clienteService.findById(clienteId);
        if(cliente == null){
            mensajes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear Factura");
        return "factura/form";
    }

    //utilizado para javascript para la busqueda de productos
    @GetMapping(value = "/cargar-producto/{term}", produces = ("application/json"))
    public @ResponseBody List<Producto> cargarProducto(@PathVariable String term) {
        return productoService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result, Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes mensajes, SessionStatus status) {

        if(result.hasErrors()){
            model.addAttribute("titulo", "Crear Factura");
            return "factura/form";
        }
        if(itemId == null || itemId.length == 0){
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error",  "La factura debe tener productos");
            return "factura/form";
        }
        for (int i = 0; i < itemId.length; i++) {
            Producto p = productoService.findById(itemId[i]);
            ItemFactura item = new ItemFactura();
            item.setProducto(p);
            item.setCantidad(cantidad[i]);
            factura.addItem(item);
        }
        facturaService.save(factura);
        mensajes.addFlashAttribute("msg", "Factura guardado exitosamente");
        status.setComplete();
        return  "redirect:/ver/" + factura.getCliente().getId();
    }

    @GetMapping("/detalle/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes mensajes) {
        Factura factura = facturaService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        if(factura == null){
            mensajes.addFlashAttribute("error", "Factura no encontrado");
        }
        model.addAttribute("factura", factura);
        return "factura/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, RedirectAttributes mensajes) {
        Factura factura = facturaService.findById(id);
        if(factura != null){
            facturaService.deleteById(factura.getId());
            model.addAttribute("factura", factura);
            mensajes.addFlashAttribute("msg", "Factura eliminado exitosamente");
            return "redirect:/ver/" + factura.getCliente().getId();
        }
        mensajes.addFlashAttribute("error", "Factura no encontrado, No se puede eliminar");
        return "redirect:/";
    }

}
