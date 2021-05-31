package net.efrei.s6.databases.aurelducyoni.gui;

import com.sun.net.httpserver.HttpServer;
import net.efrei.s6.databases.aurelducyoni.controllers.RentalController;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.controllers.VehicleController;
import net.efrei.s6.databases.aurelducyoni.dbms.Datastore;
import net.efrei.s6.databases.aurelducyoni.dbms.MySQLDatastore;
import net.efrei.s6.databases.aurelducyoni.gui.views.*;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {
        Properties config = getConfig();

        // Models
        Datastore datastore;
        switch(config.getProperty("database.type", "")) {
            case "oracle":
            case "mysql":
            case "mariadb":
                datastore = new MySQLDatastore("jdbc:mysql://" + config.getProperty("database.url", ""));
                break;

            default:
                throw new IllegalArgumentException("Unsupported database.type value");
        }

        // Controllers
        UserController userController = new UserController(datastore);
        RentalController rentalController = new RentalController(datastore);
        VehicleController vehicleController = new VehicleController(datastore);

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 5);
        server.setExecutor(new ThreadPoolExecutor(0, 10, 1L, TimeUnit.MINUTES, new SynchronousQueue<>()));

        // Views
        server.createContext("/", new HomeView(userController));
        server.createContext("/login", new LoginView(userController));
        server.createContext("/logout", new LogoutView(userController));
        server.createContext("/customers", new CustomersView(userController));
        server.createContext("/customer/", new CustomerDetailsView(userController));
        server.createContext("/distributions", new DistributionsView(userController));
        server.createContext("/rentals", new RentalsView(userController, rentalController, vehicleController));
        // server.createContext("/rental/", new RentalDetailsView(userController, rentalController, vehicleController));
        server.createContext("/vehicles", new VehiclesView(userController, rentalController, vehicleController));
        // server.createContext("/vehicle/", new VehicleDetailsView(userController, rentalController, vehicleController));
        // server.createContext("/distribution/", new DistributionDetailsView(userController));
        server.createContext("/stats", new StatsView(userController));

        server.start();
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(new URI("http://localhost:8001/login"));

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                server.stop(0);
                try {
                    datastore.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private static Properties getConfig() throws IOException {
        Properties config = new Properties();

        File configFile = new File("config.properties");
        if (!configFile.isFile()) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream configStream = classLoader.getResourceAsStream("config.properties")) {
                if (configStream == null)
                    throw new FileNotFoundException("Resource config.properties is null");

                if (!configFile.createNewFile())
                    throw new IOException("Failed to create config.properties");

                try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                    int n;
                    byte[] buffer = new byte[1024];
                    while ((n = configStream.read(buffer)) > -1)
                        outputStream.write(buffer, 0, n);
                }
            }
        }

        try (FileInputStream configStream = new FileInputStream(configFile)) {
            config.load(configStream);
        }

        return config;
    }

}
