package org.curso.data.jpa.ejemplo.springjpa.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {


    private String url; // Ruta base para los enlaces
    private Page<T> pagina; // Objeto de Spring Data JPA con info de la paginación
    private int totalPaginas; // total de páginas
    private int paginaActual; // página actual
    private List<PageItem> items; // lista de botones



    public PageRender(String url, Page<T> pagina) {
        this.url = url;
        this.pagina = pagina;
        this.items = new ArrayList<>();

        this.totalPaginas = pagina.getTotalPages();
        this.paginaActual = pagina.getNumber() + 1; // Spring empieza en 0

        int maxBotones = 5; // cuántos botones queremos mostrar
        int desde, hasta;

        if (totalPaginas <= maxBotones) {
            // Caso simple: hay menos páginas que botones
            desde = 1;
            hasta = totalPaginas;
        } else {
            // Caso complejo: más páginas que botones
            if (paginaActual <= maxBotones / 2) {
                // Estamos al inicio
                desde = 1;
                hasta = maxBotones;
            } else if (paginaActual >= totalPaginas - maxBotones / 2) {
                // Estamos al final
                desde = totalPaginas - maxBotones + 1;
                hasta = totalPaginas;
            } else {
                // Estamos en el medio
                desde = paginaActual - maxBotones / 2;
                hasta = paginaActual + maxBotones / 2;
            }
        }

        // Generar la lista de botones
        for (int i = desde; i <= hasta; i++) {
            items.add(new PageItem(i, i == paginaActual));
        }
    }
    public String getUrl() {
        return url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getItems() {
        return items;
    }
    public boolean isFirst() {
        return pagina.isFirst();
    }

    public boolean isLast() {
        return pagina.isLast();
    }

    public boolean hasNext() {
        return pagina.hasNext();
    }

    public boolean hasPrevious() {
        return pagina.hasPrevious();
    }
}
