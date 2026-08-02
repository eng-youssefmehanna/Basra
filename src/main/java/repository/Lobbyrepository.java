package main.java.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.config.SupabaseConfig;
import main.java.model.Lobby;
import main.java.model.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lobbyrepository {
   private final HttpClient client= HttpClient.newHttpClient();

   public boolean registerLobby(Lobby lobby) throws IOException, InterruptedException {
      String playersJson = "[" + lobby.players().stream()
              .map(p -> "\"" + p + "\"")
              .collect(Collectors.joining(",")) + "]";

      String json = String.format(
              "{\"game_code\":\"%s\",\"host_username\":\"%s\",\"players\":%s}",
              lobby.gameCode(), lobby.hostUsername(), playersJson);
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(SupabaseConfig.URL + "/rest/v1/lobbies"))
              .header("apikey", SupabaseConfig.API_KEY)
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(json))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() == 201;
   }



   public Lobby fetchLobby(String gameCode) throws IOException, InterruptedException {
      HttpRequest request= HttpRequest.newBuilder()
              .uri(URI.create(SupabaseConfig.URL + "/rest/v1/lobbies?game_code=eq." + gameCode))
              .header("apikey", SupabaseConfig.API_KEY)
              .GET()
              .build();
      HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
      JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
      if(array.isEmpty()){return null;}

      JsonObject obj = array.get(0).getAsJsonObject();

      String gameCode2 = obj.get("game_code").getAsString();
      String hostUsername = obj.get("host_username").getAsString();

      JsonArray playersArray = obj.get("players").getAsJsonArray();
      List<String> players = new ArrayList<>();
      for(int i = 0; i < playersArray.size(); i++) {
         players.add(playersArray.get(i).getAsString());
      }

      return new Lobby(gameCode2, hostUsername, players);

   }

   public boolean updateLobby(String gameCode, String username) throws IOException, InterruptedException {
      Lobby current = fetchLobby(gameCode);
      if(current == null){ return false;}

      List<String> updatedPlayers = new ArrayList<>(current.players());
      updatedPlayers.add(username);

      String playersJson = "[" + updatedPlayers.stream()
              .map(p -> "\"" + p + "\"")
              .collect(Collectors.joining(",")) + "]";

      String json = String.format("{\"players\":%s}", playersJson);

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(SupabaseConfig.URL + "/rest/v1/lobbies?game_code=eq." + gameCode))
              .header("apikey", SupabaseConfig.API_KEY)
              .header("Content-Type", "application/json")
              .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() == 200;
   }


   }

