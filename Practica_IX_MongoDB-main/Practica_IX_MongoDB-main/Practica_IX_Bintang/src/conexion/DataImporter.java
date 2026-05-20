package conexion;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Cocktail;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;

public class DataImporter {
    public static void importarJson(String rutaRelativa) {
        try {
            File archivo = new File(rutaRelativa);

            if (!archivo.exists()) {
                throw new IllegalArgumentException("El archivo NO existe en la ruta: " + archivo.getAbsolutePath());
            }

            String contenido = new String(Files.readAllBytes(archivo.toPath()));
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
            e.printStackTrace();
        }
    }
}