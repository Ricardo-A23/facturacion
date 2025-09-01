package org.curso.data.jpa.ejemplo.springjpa.services;

import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

public interface IUploadService {

    public String copy(MultipartFile file) throws IOException;

    public boolean eliminar(String nombreArchivo);

    public void eliminarTodos();

    public void init() throws IOException;
}
