package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import conexion.MongoConnection;
import model.Cocktail;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CocktailDAO {
    private final MongoCollection<Document> collection;

    public CocktailDAO() {
        MongoDatabase db = MongoConnection.getDatabase();
        this.collection = db.getCollection("cocktails");
    }

    public void insertar(Cocktail cocktail) {
        collection.insertOne(cocktail.toDocument());
    }

    public boolean borrarPorNombre(String nombre) {
        Bson filtro = Filters.eq("name", nombre);
        DeleteResult result = collection.deleteOne(filtro);
        return result.getDeletedCount() > 0;
    }

    public boolean cambiarVaso(String nombre, String nuevoVaso) {
        Bson filtro = Filters.eq("name", nombre);
        Bson actualizacion = Updates.set("glassType", nuevoVaso);
        UpdateResult result = collection.updateOne(filtro, actualizacion);
        return result.getModifiedCount() > 0;
    }

    public List<Document> getTop5Vasos() {
        return collection.aggregate(Arrays.asList(
                Aggregates.group("$glassType", Accumulators.sum("cantidad", 1)),
                Aggregates.sort(Sorts.descending("cantidad")),
                Aggregates.limit(5)
        )).into(new ArrayList<>());
    }

    public List<Document> getConteoPorCategoria() {
        return collection.aggregate(Arrays.asList(
                Aggregates.group("$category", Accumulators.sum("total", 1)),
                Aggregates.sort(Sorts.descending("total"))
        )).into(new ArrayList<>());
    }

    
    public List<Document> buscarPorIngrediente(String ingrediente) {
        Bson filtro = Filters.or(
                Filters.regex("strIngredient1", ingrediente, "i"),
                Filters.regex("strIngredient2", ingrediente, "i"),
                Filters.regex("strIngredient3", ingrediente, "i"),
                Filters.regex("strIngredient4", ingrediente, "i"),
                Filters.regex("strIngredient5", ingrediente, "i")
        );

        return collection.find(filtro).into(new ArrayList<>());
    }
    
    public List<Document> filtrarPorAlcohol(boolean esAlcoholica) {
        String alcohol = esAlcoholica ? "Alcoholic" : "Non alcoholic";
        Bson filtro = Filters.eq("strAlcoholic", alcohol);
        return collection.find(filtro).into(new ArrayList<>());
    }
    
    public List<Document> buscarPorLongitudInstrucciones(int caracteres, boolean buscarMayores) {
        String operador = buscarMayores ? "$gt" : "$lt";

        Bson filtro = Filters.expr(
            new Document(operador, Arrays.asList(new Document("$strLenCP", "$instructions"), caracteres))
        );

        return collection.find(filtro).into(new ArrayList<>());
    }
    
    public List<Document> obtenerTodos() {
        return collection.find().into(new ArrayList<>());
    }
}