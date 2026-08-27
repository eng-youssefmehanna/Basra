package main.java.repository;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.*;
import javafx.application.Platform;
import main.java.config.SupabaseConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//TODO need to understand more

public class LobbyRealTimeClient extends WebSocketClient {

    //  callback fires on the UI thread when players list changes

    private final Consumer<List<String>> onPlayersUpdated;

    // The game code we are subscribing to — we only care about THIS lobby
    private final String gameCode;

    public LobbyRealTimeClient(String gameCode, Consumer<List<String>> onPlayersUpdated) throws Exception {
        // Connect to Supabase Realtime WebSocket endpoint
        super(new URI("wss://gpbnrcqmodagqloldhwm.supabase.co/realtime/v1/websocket?apikey="
                + SupabaseConfig.API_KEY + "&vsn=1.0.0"));
        this.gameCode = gameCode;
        this.onPlayersUpdated = onPlayersUpdated;
    }

    // Fires when WebSocket connection is established
    //  send a subscription message here to tell Supabase what we want to listen to
    @Override
    public void onOpen(ServerHandshake handshake) {

        // subscribe to UPDATE events on the lobbies table where game_code = our code

        System.out.println("WebSocket connected");
        String subscribeMsg = String.format("""
            {
                "event": "phx_join",
                "topic": "realtime:public:lobbies:game_code=eq.%s",
                "payload": {},
                "ref": "1"
            }
            """, gameCode);
        send(subscribeMsg);
    }

    // Fires every time Supabase pushes a message to us
    @Override
    public void onMessage(String message) {
        System.out.println("Message received: " + message);
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();

            // Supabase wraps the actual data in payload → record → new
            // We only care about UPDATE events (when players array changes)
            if (!json.has("payload")) return;
            JsonObject payload = json.getAsJsonObject("payload");
            if (!payload.has("record")) return;
            JsonObject record = payload.getAsJsonObject("record");
            if (!record.has("players")) return;

            // Extract the updated players array
            JsonArray playersArray = record.getAsJsonArray("players");
            List<String> updatedPlayers = new ArrayList<>();
            for (int i = 0; i < playersArray.size(); i++) {
                updatedPlayers.add(playersArray.get(i).getAsString());
            }

            // Platform.runLater —  on a background thread (WebSocket thread)
            // JavaFX UI can only be touched from the UI thread
            // runLater schedules callback to run on the UI thread
            Platform.runLater(() -> onPlayersUpdated.accept(updatedPlayers));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Realtime connection closed: " + reason);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}