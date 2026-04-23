package conexion;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Cocktail;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportadorDatos {
    public static void importarJson(String rutaArchivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            JSONArray jsonArray = new JSONArray(contenido);

            MongoDatabase db = MongoConnection.getDatabase();
            MongoCollection<Document> collection = db.getCollection("cocktails");

            collection.drop();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                Cocktail c = new Cocktail(
                    obj.optString("strDrink", "Sin nombre"),
                    obj.optString("strCategory", "Otros"),
                    obj.optString("strAlcoholic", "Alcoholic"),
                    obj.optString("strGlass", "Normal Glass"),
                    obj.optString("strInstructions", "No instructions available")
                );
                
                collection.insertOne(c.toDocument());
            }
            System.out.println("Importación completada: " + jsonArray.length() + " cócteles subidos.");

        } catch (Exception e) {
            System.err.println("Error en la importación: " + e.getMessage());
        }
    }
}