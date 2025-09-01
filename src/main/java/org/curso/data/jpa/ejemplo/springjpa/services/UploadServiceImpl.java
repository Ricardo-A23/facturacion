package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements IUploadService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String UPLOADS_FOLDER = "uploads";

    @Override
    public String copy(MultipartFile file) throws IOException {
        //crear nombre unico de cada imagen que se suba
        String nombreUnicoImg = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path rutaAbsoluta = getPath(nombreUnicoImg);

        logger.info("Ruta absoluta: " + rutaAbsoluta.toString());

        Files.copy(file.getInputStream(), rutaAbsoluta);

        return nombreUnicoImg;
    }

    @Override
    public boolean eliminar(String nombreArchivo) {

        Path rutaAbsoluta = getPath(nombreArchivo);
        File archivo = rutaAbsoluta.toFile();

        if (archivo.exists() && archivo.canRead()) {
            return archivo.delete();
        }
        return false;
    }

    //borrar todos los archivos dentro del directorio
    @Override
    public void eliminarTodos() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    //crear el dsirectorio cuando se inicie la aplicacion
    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }

    public Path getPath(String nombreArchivo) {
        return Paths.get(UPLOADS_FOLDER).resolve(nombreArchivo).toAbsolutePath();
    }
}
