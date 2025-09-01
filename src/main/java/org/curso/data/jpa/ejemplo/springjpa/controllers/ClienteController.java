package org.curso.data.jpa.ejemplo.springjpa.controllers;

import jakarta.validation.Valid;
import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.curso.data.jpa.ejemplo.springjpa.services.ClienteService;
import org.curso.data.jpa.ejemplo.springjpa.services.IUploadService;
import org.curso.data.jpa.ejemplo.springjpa.util.paginator.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private IUploadService  uploadService;

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes mensaje) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            mensaje.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/";
        }
        model.addAttribute("titulo", "Detalle cliente");
        model.addAttribute("cliente", cliente);
        return "ver";
    }


    @GetMapping({"/", "/listar"})
    public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 4);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clienteService.findAll(pageable));

        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("clientes", clienteService.findAll(pageable));
        model.addAttribute("page", pageRender);
        return "listar";
    }


    /*
    @GetMapping({"/prueba", })
    public String prueba() {
        return "prueba";
    }
    @GetMapping({"/registro", })
    public String registro() {
        return "registro";
    }
     */


    @GetMapping("/form")
    public String form(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, @RequestParam("file") MultipartFile foto, RedirectAttributes mensaje, SessionStatus status) {
        if (result.hasErrors()) {
            return "form";
        }

        if(!foto.isEmpty()) {
            //para cuando se edita un usuario - se elimina la anterior y se reemplaza
            if(cliente.getId() != null && cliente.getFoto() != null && !cliente.getFoto().isEmpty()) {
                if(uploadService.eliminar(cliente.getFoto())) {
                    mensaje.addFlashAttribute("mensaje", "Borrado correctamente");
                }
            }
            String nombreUnicoArchivo = null;
            try {
                nombreUnicoArchivo = uploadService.copy(foto);
                cliente.setFoto(nombreUnicoArchivo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        clienteService.save(cliente);
        mensaje.addFlashAttribute("mensaje",  "Agregado correctamente");
        status.setComplete();
        return "redirect:/";
    }

    @GetMapping("/form/{id}")
    public String buscar (@PathVariable Long id, Model model) {
        Cliente cliente = null;
        if(id > 0){
            cliente = clienteService.findById(id);
        }
        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar (@PathVariable Long id, RedirectAttributes mensaje) {
        Cliente cliente = clienteService.findById(id);
        if (cliente != null) {

            clienteService.deleteById(id);

            if(uploadService.eliminar(cliente.getFoto())){
                mensaje.addFlashAttribute("mensaje", "Borrado exitosamente");
            }
        }
        return "redirect:/";
    }
}
