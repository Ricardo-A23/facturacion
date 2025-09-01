package org.curso.data.jpa.ejemplo.springjpa;

import org.curso.data.jpa.ejemplo.springjpa.services.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class SpringJpaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Autowired
    private IUploadService uploadService;

    @Override
    public void run(String... args) throws Exception {
        uploadService.eliminarTodos();
        uploadService.init();
    }
}
