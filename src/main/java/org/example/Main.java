package org.example;

import org.example.model.*;
import org.example.repository.*;
import org.example.service.*;

import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    private static AdminService adminService;
    private static ClientService clientService;
    private static ProductService productService;
    private static OrderService orderService;
    private static CartService cartService;

    public static void main(String[] args) {
        try {
            // Установить соединение с базой данных
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/erlans", "emirkhanismailov", "0771714629");

            // Создать сервисы
            AdminRepository adminRepository = new AdminRepository(connection);
            adminService = new AdminService(adminRepository);

            ClientRepository clientRepository = new ClientRepository(connection);
            clientService = new ClientService(clientRepository);

            ProductRepository productRepository = new ProductRepository(connection);
            productService = new ProductService(productRepository);

            OrderRepository orderRepository = new OrderRepository(connection);
            orderService = new OrderService(orderRepository);
            CartRepository cartRepository = new CartRepository(connection);
            cartService = new CartService(cartRepository);

            // Главное меню
            while (true) {
                System.out.println("Добро пожаловать! Пожалуйста, выберите опцию:");
                System.out.println("1. Вход как администратор");
                System.out.println("2. Вход как клиент");
                System.out.println("3. Регистрация как администратор");
                System.out.println("4. Регистрация как клиент");
                System.out.println("5. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Пропустить оставшуюся строку

                switch (choice) {
                    case 1:
                        loginAdmin();
                        break;
                    case 2:
                        loginClient();
                        break;
                    case 3:
                        registerAdmin();
                        break;
                    case 4:
                        registerClient();
                        break;
                    case 5:
                        System.out.println("Выход из программы...");
                        connection.close();
                        return;
                    default:
                        System.out.println("Некорректный выбор. Попробуйте снова.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerAdmin() {
        System.out.println("Регистрация администратора:");
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        Admin admin = new Admin();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setLogin(login);
        admin.setPassword(password);

        try {
            adminService.registerAdmin(admin);
            System.out.println("Администратор успешно зарегистрирован!");
        } catch (SQLException e) {
            System.out.println("Ошибка регистрации администратора: " + e.getMessage());
        }
    }

    private static void registerClient() {
        System.out.println("Регистрация клиента:");
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите адрес: ");
        String address = scanner.nextLine();
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAddress(address);
        client.setLogin(login);
        client.setPassword(password);

        try {
            clientService.registerClient(client);
            System.out.println("Клиент успешно зарегистрирован!");
        } catch (SQLException e) {
            System.out.println("Ошибка регистрации клиента: " + e.getMessage());
        }
    }

    private static void loginAdmin() {
        System.out.println("Вход как администратор:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            Admin admin = adminService.loginAdmin(login, password);
            if (admin != null) {
                System.out.println("Вход выполнен успешно!");
                adminMenu();
            } else {
                System.out.println("Неправильный логин или пароль.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при входе администратора: " + e.getMessage());
        }
    }

    private static void loginClient() {
        System.out.println("Вход как клиент:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            Client client = clientService.loginClient(login, password);
            if (client != null) {
                System.out.println("Вход выполнен успешно!");
                clientMenu(client);
            } else {
                System.out.println("Неправильный логин или пароль.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при входе клиента: " + e.getMessage());
        }
    }

    private static void clientMenu(Client client) {
        while (true) {
            System.out.println("Меню клиента:");
            System.out.println("1. Просмотр товаров");
            System.out.println("2. Добавить товары в корзину");
            System.out.println("3. Просмотр корзины");
            System.out.println("4. Оформить заказ");
            System.out.println("5. Просмотр своих заказов");
            System.out.println("6. Управление учетной записью");
            System.out.println("7. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Пропустить оставшуюся строку

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    viewProducts();
                    addProductsToCart(client);
                    break;
                case 3:
                    viewCart(client);
                    break;
                case 4:
                    placeOrder(client);
                    break;
                case 5:
                    viewClientOrders(client);
                    break;
                case 6:
                    manageAccount(client);
                    break;
                case 7:
                    System.out.println("Выход из меню клиента.");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("Меню администратора:");
            System.out.println("1. Управление товарами");
            System.out.println("2. Управление заказами");
            System.out.println("3. Управление клиентами");
            System.out.println("4. Отчеты");
            System.out.println("5. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Пропустить оставшуюся строку

            switch (choice) {
                case 1:
                    manageProducts();
                    break;
                case 2:
                    manageOrders();
                    break;
                case 3:
                    manageClients();
                    break;
                case 4:
                    generateReports();
                    break;
                case 5:
                    System.out.println("Выход из меню администратора.");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }



    private static void manageProducts() {
        while (true) {
            System.out.println("Управление товарами:");
            System.out.println("1. Добавить товар");
            System.out.println("2. Обновить товар");
            System.out.println("3. Удалить товар");
            System.out.println("4. Посмотреть все товары");
            System.out.println("5. Вернуться");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Пропустить оставшуюся строку

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewAllProducts();
                    updateProduct();
                    break;
                case 3:
                    viewAllProducts();
                    deleteProduct();
                    break;
                case 4:
                    viewAllProducts();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите цену товара: ");
        double price = scanner.nextDouble();
        System.out.print("Введите количество товара: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        try {
            productService.addProduct(product);
            System.out.println("Товар успешно добавлен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private static void updateProduct() {
        System.out.print("Введите ID товара, который вы хотите обновить: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        System.out.print("Введите новое название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите новую цену товара: ");
        double price = scanner.nextDouble();
        System.out.print("Введите новое количество товара: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        try {
            productService.updateProduct(product);
            System.out.println("Товар успешно обновлен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении товара: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        System.out.print("Введите ID товара, который вы хотите удалить: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        try {
            productService.deleteProduct(productId);
            System.out.println("Товар успешно удален!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    private static void viewAllProducts() {
        try {
            for (Product product : productService.getAllProducts()) {
                System.out.println("ID: " + product.getId() + ", Название: " + product.getName() + ", Цена: " + product.getPrice() + ", Количество: " + product.getQuantity());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    private static void manageOrders() {
        while (true) {
            System.out.println("Управление заказами:");
            System.out.println("1. Просмотр всех заказов");
            System.out.println("2. Изменить статус заказа");
            System.out.println("3. Вернуться");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Пропустить оставшуюся строку

            switch (choice) {
                case 1:
                    viewAllOrders();
                    break;
                case 2:
                    viewAllOrders();
                    changeOrderStatus();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static void viewAllOrders() {
        try {
            for (Order order : orderService.getAllOrders()) {
                System.out.println("ID: " + order.getId() + ", ID клиента: " + order.getClientId() + ", ID товара: " + order.getProductId() + ", Количество: " + order.getQuantity() + ", Статус: " + order.getStatus());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка заказов: " + e.getMessage());
        }
    }

    private static void changeOrderStatus() {
        System.out.print("Введите ID заказа, статус которого вы хотите изменить: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        System.out.print("Введите новый статус заказа: ");
        String status = scanner.nextLine();

        try {
            orderService.updateOrderStatus(orderId, status);
            System.out.println("Статус заказа успешно изменен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении статуса заказа: " + e.getMessage());
        }
    }

    private static void manageClients() {
        // Пока что только просмотр клиентов
        try {
            for (Client client : clientService.getAllClients()) {
                System.out.println("ID: " + client.getId() + ", Имя: " + client.getFirstName() + ", Фамилия: " + client.getLastName() + ", Адрес: " + client.getAddress());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка клиентов: " + e.getMessage());
        }
    }

    private static void generateReports() {
        System.out.println("Генерация отчетов:");
        System.out.println("1. Отчет об объеме продаж по каждому товару");
        System.out.println("2. Отчет о общем объеме продаж");
        System.out.println("3. Вернуться");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        switch (choice) {
            case 1:
                generateProductSalesReport();
                break;
            case 2:
                generateTotalSalesReport();
                break;
            case 3:
                return;
            default:
                System.out.println("Некорректный выбор. Попробуйте снова.");
        }
    }

    // Отчет об объеме продаж по каждому товару
    private static void generateProductSalesReport() {
        String query = "SELECT p.id, p.name, SUM(o.quantity) AS total_sold " +
                "FROM orders o JOIN products p ON o.product_id = p.id " +
                "GROUP BY p.id, p.name";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Отчет об объеме продаж по каждому товару:");
            while (resultSet.next()) {
                int productId = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                int totalSold = resultSet.getInt("total_sold");

                System.out.println("ID товара: " + productId + ", Название: " + productName + ", Всего продано: " + totalSold);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при генерации отчета: " + e.getMessage());
        }
    }

    // Отчет о общем объеме продаж
    private static void generateTotalSalesReport() {
        String query = "SELECT SUM(o.quantity * p.price) AS total_revenue " +
                "FROM orders o JOIN products p ON o.product_id = p.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                double totalRevenue = resultSet.getDouble("total_revenue");
                System.out.println("Общий доход от продаж: " + totalRevenue);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при генерации отчета: " + e.getMessage());
        }
    }



    private static void viewProducts() {
        try {
            for (Product product : productService.getAllProducts()) {
                System.out.println("ID: " + product.getId() + ", Название: " + product.getName() + ", Цена: " + product.getPrice() + ", Количество: " + product.getQuantity());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    private static void addProductsToCart(Client client) {
        System.out.print("Введите ID товара, который вы хотите добавить в корзину: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        System.out.print("Введите количество товара: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        CartItem cartItem = new CartItem();
        cartItem.setClientId(client.getId());
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        try {
            cartService.addToCart(cartItem);
            System.out.println("Товар успешно добавлен в корзину!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении товара в корзину: " + e.getMessage());
        }
    }

    private static void viewCart(Client client) {
        try {
            List<CartItem> cartItems = cartService.getAllCartItemsForClient(client.getId());
            if (cartItems.isEmpty()) {
                System.out.println("Ваша корзина пуста.");
            } else {
                System.out.println("Ваша корзина:");
                for (CartItem cartItem : cartItems) {
                    Product product = productService.getProductById(cartItem.getProductId());
                    System.out.println("ID товара: " + cartItem.getProductId() + ", Название: " + product.getName() + ", Количество: " + cartItem.getQuantity() + ", Цена за единицу: " + product.getPrice());
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при просмотре корзины: " + e.getMessage());
        }
    }

    private static void placeOrder(Client client) {
        try {
            List<CartItem> cartItems = cartService.getAllCartItemsForClient(client.getId());
            if (cartItems.isEmpty()) {
                System.out.println("Ваша корзина пуста. Сначала добавьте товары в корзину.");
                return;
            }

            // Создание заказа
            for (CartItem cartItem : cartItems) {
                Order order = new Order();
                order.setClientId(client.getId());
                order.setProductId(cartItem.getProductId());
                order.setQuantity(cartItem.getQuantity());
                order.setStatus("В ожидании");
                orderService.addOrder(order);

                // Обновить количество товара
                Product product = productService.getProductById(cartItem.getProductId());
                int newQuantity = product.getQuantity() - cartItem.getQuantity();
                product.setQuantity(newQuantity);
                productService.updateProduct(product);
            }

            // Очистить корзину
            for (CartItem cartItem : cartItems) {
                cartService.deleteCartItem(cartItem.getId());
            }

            System.out.println("Заказ успешно оформлен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при оформлении заказа: " + e.getMessage());
        }
    }

    private static void viewClientOrders(Client client) {
        try {
            List<Order> orders = orderService.getOrdersForClient(client.getId());
            if (orders.isEmpty()) {
                System.out.println("У вас нет заказов.");
            } else {
                System.out.println("Ваши заказы:");
                for (Order order : orders) {
                    System.out.println("ID заказа: " + order.getId() + ", ID товара: " + order.getProductId() + ", Количество: " + order.getQuantity() + ", Статус: " + order.getStatus());
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при просмотре заказов: " + e.getMessage());
        }
    }

    private static void manageAccount(Client client) {
        System.out.println("Ваши данные:");
        System.out.println("Имя: " + client.getFirstName());
        System.out.println("Фамилия: " + client.getLastName());
        System.out.println("Адрес: " + client.getAddress());

        System.out.println("1. Изменить имя");
        System.out.println("2. Изменить фамилию");
        System.out.println("3. Изменить адрес");
        System.out.println("4. Вернуться");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся строку

        switch (choice) {
            case 1:
                System.out.print("Введите новое имя: ");
                client.setFirstName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Введите новую фамилию: ");
                client.setLastName(scanner.nextLine());
                break;
            case 3:
                System.out.print("Введите новый адрес: ");
                client.setAddress(scanner.nextLine());
                break;
            case 4:
                return;
            default:
                System.out.println("Некорректный выбор.");
                return;
        }

        try {
            clientService.updateClient(client);
            System.out.println("Данные успешно обновлены.");
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении данных клиента: " + e.getMessage());
        }
    }
}
