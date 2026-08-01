package main.java.repository;

import main.java.config.SupabaseConfig;
import main.java.model.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class UserRepository implements IUserRepository {

   private final HttpClient client = HttpClient.newHttpClient();

// reads need IOex
    @Override
    public boolean registerUser(Player player) throws IOException, InterruptedException {
        String json = String.format(
                "{\"name\":\"%s\",\"username\":\"%s\",\"password_hash\":\"%s\"}",
                player.getName(), player.getUserName(), player.getPasswordHash());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.URL + "/rest/v1/users"))
                .header("apikey", SupabaseConfig.API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 201;
    }

    @Override
    public Player fetchUser(String username, String password) throws IOException, InterruptedException {
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.URL + "/rest/v1/users?username=eq." + username))
                .header("apikey", SupabaseConfig.API_KEY)
                .GET()
                .build();
        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
        if(array.isEmpty()){return null;}
        JsonObject obj = array.get(0).getAsJsonObject();
        String hashedInput = Player.hashPassword(password);
        if(obj.get("password_hash").getAsString().equals(hashedInput)){

            return Player.fromDatabase( obj.get("name").getAsString(), username,
                    hashedInput, obj.get("wins").getAsInt(), obj.get("games_played").getAsInt());
        }
       return null;
    }

}
