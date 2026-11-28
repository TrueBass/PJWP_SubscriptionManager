package com.yurtar.subman.subs;

import com.yurtar.subman.model.Subscription;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.List;
import java.util.function.Consumer;

// Popup with an empty SubscriptionForm. Covers the app window and blurs it while open.
public class AddSubscriptionDialog extends Stage {

  public AddSubscriptionDialog(Window owner, Consumer<Subscription> onAdd) {
    initOwner(owner);
    initModality(Modality.WINDOW_MODAL);
    initStyle(StageStyle.TRANSPARENT);

    Label title = new Label("New subscription");
    title.getStyleClass().add("dialog-title");

    SubscriptionForm form = new SubscriptionForm();

    Button cancel = new Button("Cancel");
    cancel.getStyleClass().add("secondary-button");
    cancel.setOnAction(e -> close());
    Button add = new Button("Add");
    add.getStyleClass().add("primary-button");
    add.setDefaultButton(true);
    add.setOnAction(e -> {
      Subscription sub = form.getSubscription();
      if (sub == null) return; // form shows the error
      onAdd.accept(sub);
      close();
    });
    for (Button b : List.of(cancel, add)) {
      b.setMaxWidth(Double.MAX_VALUE);
      HBox.setHgrow(b, Priority.ALWAYS);
    }

    VBox card = new VBox(12, title, form, new HBox(10, cancel, add));
    card.getStyleClass().add("dialog-card");
    card.setMaxSize(400, VBox.USE_PREF_SIZE);
    // clicks inside the card shouldn't close the dialog
    card.setOnMouseClicked(e -> e.consume());

    // dark see-through layer over the whole app, click on it = cancel
    StackPane backdrop = new StackPane(card);
    backdrop.getStyleClass().add("dialog-backdrop");
    backdrop.setOnMouseClicked(e -> close());

    Scene scene = new Scene(backdrop, Color.TRANSPARENT);
    // the popup is a separate window, so it needs the app styles again
    scene.getStylesheets().addAll(owner.getScene().getStylesheets());
    scene.getStylesheets().add(getClass().getResource("/subs.css").toExternalForm());
    scene.getStylesheets().add(getClass().getResource("/form.css").toExternalForm());
    scene.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) close();
    });
    setScene(scene);

    // same position and size as the inside of the app window
    setX(owner.getX() + owner.getScene().getX());
    setY(owner.getY() + owner.getScene().getY());
    setWidth(owner.getScene().getWidth());
    setHeight(owner.getScene().getHeight());

    // blur the app behind while the dialog is open
    owner.getScene().getRoot().setEffect(new GaussianBlur(12));
    setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
  }
}
