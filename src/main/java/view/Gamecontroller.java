package main.java.view;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.model.*;

import java.util.List;

public class Gamecontroller {

    @FXML VBox player2Block;
    @FXML ImageView player2Avatar;
    @FXML Label player2Username;
    @FXML HBox player2Cards;

    @FXML VBox player3Block;
    @FXML ImageView player3Avatar;
    @FXML Label player3Username;
    @FXML VBox player3Cards;

    @FXML VBox player4Block;
    @FXML ImageView player4Avatar;
    @FXML Label player4Username;
    @FXML VBox player4Cards;

    @FXML HBox floorCards;
    @FXML ImageView deckPile;
    @FXML Label deckCount;
    @FXML Label currentTurnLabel;
    @FXML Label statusLabel;

    @FXML VBox player1Block;
    @FXML HBox player1Cards;
    @FXML ImageView player1Avatar;
    @FXML Label player1Username;
    Player p1 = new Player("Youssef", "youssef_99");
    Player p2 = new Player("Test", "test_player2");


    // Local game objects for testing one turn
    private Game game;
    private GamePlay gameplay;

    @FXML   // start game
    public void initialize() {

        game = new Game();

        game.addPlayers(p1, p2);
        game.start();
        gameplay = new GamePlay(game);
        CardImageCache.preloadAll(new Deck().withdraw(52));
        renderHand(p1.getHand(), player1Cards, true);
        renderHand(p2.getHand(), player2Cards, false);
        player1Username.setText(p1.getUserName());


        player2Block.setVisible(true);
        player2Username.setText(p2.getUserName());

        renderFloor();

        deckPile.setImage(CardImageCache.getBackImage());
        deckCount.setText(String.valueOf(game.getDeckSize()));
        currentTurnLabel.setText(gameplay.getCurrPlayer().getUserName());
    }

    private void renderHand(List<Card> hand, HBox container, boolean faceUp) {
        container.getChildren().clear();
        for (Card card : hand) {
            ImageView iv = faceUp ? makeFaceCard(card) : makeBackCard();
            container.getChildren().add(iv);
        }
    }

    private void renderFloor() {
        floorCards.getChildren().clear();
        for (Card card : game.getFloor()) {
            floorCards.getChildren().add(makeFloorCard(card));
        }
    }

    private ImageView makeFaceCard(Card card) {
        ImageView iv = new ImageView(CardImageCache.getImage(card));
        iv.setFitWidth(65);
        iv.setFitHeight(95);
        iv.setPreserveRatio(false);
        iv.setOnMouseEntered(e -> iv.setTranslateY(-10));
        iv.setOnMouseExited(e -> iv.setTranslateY(0));
        iv.setStyle("-fx-cursor: hand;");
        iv.setOnMouseClicked(e -> playCardAnimation(iv, card));

        return iv;
    }

    private ImageView makeBackCard() {
        ImageView iv = new ImageView(CardImageCache.getBackImage());
        iv.setFitWidth(65);
        iv.setFitHeight(95);
        iv.setPreserveRatio(false);
        return iv;
    }

    private ImageView makeFloorCard(Card card) {
        ImageView iv = new ImageView(CardImageCache.getImage(card));
        iv.setFitWidth(70);
        iv.setFitHeight(100);
        iv.setPreserveRatio(false);
        return iv;
    }

    private void handleCardClick(Card card) {
        Player current = gameplay.getCurrPlayer();
        gameplay.playTurn(card, current);

        // auto-play p2
        if (!p2.getHand().isEmpty()) {
            Card randomCard = p2.getHand().get((int)(Math.random() * p2.getHand().size()));
            gameplay.playTurn(randomCard, p2);
        }

        renderFloor();
        renderHand(p1.getHand(), player1Cards, true);
        renderHand(p2.getHand(), player2Cards, false);
        deckCount.setText(String.valueOf(game.getDeckSize()));
        currentTurnLabel.setText(gameplay.getCurrPlayer().getUserName());
    }

    private void playCardAnimation(ImageView iv, Card card) {
        Bounds bounds = iv.localToScene(iv.getBoundsInLocal());
        double cardCenterX = bounds.getMinX() + bounds.getWidth() / 2;
        double cardCenterY = bounds.getMinY() + bounds.getHeight() / 2;

        double targetX = iv.getScene().getWidth() / 2;
        double targetY = iv.getScene().getHeight() / 2;

        TranslateTransition move = new TranslateTransition(Duration.millis(400), iv);
        move.setToX(targetX - cardCenterX);
        move.setToY(targetY - cardCenterY);
        move.setOnFinished(e -> handleCardClick(card));
        move.play();
    }
}