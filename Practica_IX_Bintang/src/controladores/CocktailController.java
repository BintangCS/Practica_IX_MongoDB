package controladores;

import dao.CocktailDAO;
import model.Cocktail;
import org.bson.Document;

import java.util.List;

public class CocktailController {
    private final CocktailDAO dao;

    public CocktailController() {
        this.dao = new CocktailDAO();
    }

    public List<Document> listarTodos() {
        return dao.obtenerTodos();
    }

    public String insertarCocktail(String nombre, String categoria, String alcohol, String vaso, String instrucciones) {
        if (nombre.isEmpty()) return "El nombre no puede estar vacío";
        
        Cocktail nuevo = new Cocktail(nombre, categoria, alcohol, vaso, instrucciones);
        dao.insertar(nuevo);
        return "Cóctel añadido con éxito";
    }

    public String borrarCocktail(String nombre) {
        boolean borrado = dao.borrarPorNombre(nombre);
        return borrado ? "Eliminado correctamente" : "No se encontró el cóctel";
    }

    public String cambiarVaso(String nombre, String nuevoVaso) {
        boolean modificado = dao.cambiarVaso(nombre, nuevoVaso);
        return modificado ? "Vaso cambiado" : "Error al cambiar el vaso";
    }

    public List<Document> buscarIngrediente(String ingrediente) {
        return dao.buscarPorIngrediente(ingrediente);
    }

    
    public List<Document> TopVasos() {
        return dao.getTop5Vasos();
    }

    public List<Document> ConteoCategorias() {
        return dao.getConteoPorCategoria();
    }
    
    public List<Document> filtrarPorAlcohol(boolean esAlcoholico) {
        return dao.filtrarPorAlcohol(esAlcoholico);
    }
    
    public List<Document> filtrarPorComplejidad(int limite, boolean esMayor) {
        return dao.buscarPorLongitudInstrucciones(limite, esMayor);
    }
    
}