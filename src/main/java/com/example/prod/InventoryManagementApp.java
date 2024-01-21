package com.example.prod;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class InventoryManagementApp extends Application {
    private TextField productNameField;
    private TextField quantityField;
    private List<Product> products;
    private TextArea notificationArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Инициализация списка товаров
        products = new ArrayList<>();
        // Добавление некоторых товаров в список
        products.add(new Product("Волосы искуственные", 100));
        products.add(new Product("Декоративные глазки", 150));
        products.add(new Product("Носик винтовой", 200));
        products.add(new Product("Нитки", 400));

        // Создание вкладок
        TabPane tabPane = new TabPane();
        Tab productsTab = new Tab("Список товаров");
        Tab entrepreneurTab = new Tab("Предприниматель");

        // Создание списка товаров на первой вкладке
        VBox productsLayout = new VBox();
        ListView<String> productList = new ListView<>();
        updateProductList(productList);
        productsLayout.getChildren().add(productList);
        productsTab.setContent(productsLayout);

        // Создание окна предпринимателя на второй вкладке
        VBox entrepreneurLayout = new VBox();
        Button createPurchaseOrderButton = new Button("Создать акт на покупку");
        createPurchaseOrderButton.setOnAction(event -> createPurchaseOrder());
        notificationArea = new TextArea();
        notificationArea.setEditable(false);
        entrepreneurLayout.getChildren().addAll(createPurchaseOrderButton, notificationArea);
        entrepreneurTab.setContent(entrepreneurLayout);

        tabPane.getTabs().addAll(productsTab, entrepreneurTab);

        // Запуск потока для отправки уведомлений
        startNotificationThread();

        // Отображение сцены
        Scene scene = new Scene(tabPane, 400, 400);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Управление запасами");
        primaryStage.show();
    }

    private void updateProductList(ListView<String> productList) {
        List<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getName() + " - " + product.getQuantity());
        }
        productList.getItems().setAll(productNames);
    }

    private void createPurchaseOrder() {
        // Реализация создания акта на покупку
        // (например, отображение диалогового окна для заполнения информации о покупке)
        imployer(new Stage());
        System.out.println("Создание акта на покупку...");
    }

    private void startNotificationThread() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notifyEntrepreneur();
            }
        }, 0, 20 * 1000); // Уведомление каждые 20 секунд
    }

    private void notifyEntrepreneur() {
        Random random = new Random();
        int productIndex = random.nextInt(products.size());
        Product randomProduct = products.get(productIndex);
        int quantity = random.nextInt(10) + 1; // Рандомное количество от 1 до 10
        // Тут можно сделать чтобы еще убавлялось кол-во товара
        // С помощью метода updateProductList сделать обработку при уменьшении
        // Отправка уведомления предпринимателю
        String notification = "Купить " + quantity + " единиц товара " + randomProduct.getName();
        Platform.runLater(() -> notificationArea.appendText(notification + "\n"));
    }

    public void imployer(Stage primaryStage) {
        primaryStage.setTitle("Введите информацию о товаре");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        productNameField = new TextField();
        quantityField = new TextField();

        grid.add(new Label("Название товара:"), 0, 0);
        grid.add(productNameField, 1, 0);
        grid.add(new Label("Количество:"), 0, 1);
        grid.add(quantityField, 1, 1);

        //grid.add(new Button("Отправить акт"),0 ,3);

        Button FinishPurchaseB = new Button("Отправить акт");
        FinishPurchaseB.setOnAction(event -> FinishPurchaseB());
        grid.add(FinishPurchaseB, 0, 3);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void FinishPurchaseB() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Завершение акта");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Акт отправлен!");


        //Тут можно сделать обработку для сохранения акта

        alert.showAndWait();

    }

    private static class Product {
        private String name;
        private int quantity;

        public Product(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}