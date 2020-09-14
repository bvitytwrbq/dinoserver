import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class DinoServer extends JFrame {
    private int port;
    private Connektion connektion;

    static String conStr = "jdbc:postgresql://localhost:5432/DinoBase";
    static String login = "hay";
    static String pwd = "123";
    private JButton brontoAddButton;
    private JLabel brontoLabel;
    private JButton brontoDecreaseButton;
    private JButton diploAddButton;
    private JLabel diploLabel;
    private JButton diploDecreaseButton;
    private JButton stegoAddButton;
    private JLabel stegoLabel;
    private JButton stegoDecreaseButton;
    private JTextField inputNameField;
    private JTextField inputQuantityField;
    private JButton submitStuffButton;
    private JLabel infoLabel;
    private JButton tablerButton;
    private JPanel panel;

    public DinoServer(int port){
        this.port = port;
        brontoAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    increaseQuantity("bronto",10);
                    ArrayList<String> list = getInfo("bronto","quantity");
                    brontoLabel.setText(list.get(1));
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        brontoDecreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    decreaseQuantity("bronto",10);
                    ArrayList<String> list = getInfo("bronto","quantity");
                    brontoLabel.setText(list.get(1));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        diploAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    increaseQuantity("diplo",10);
                    ArrayList<String> list = getInfo("diplo","quantity");
                    diploLabel.setText(list.get(1));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        diploDecreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    decreaseQuantity("diplo",10);
                    ArrayList<String> list = getInfo("diplo","quantity");
                    diploLabel.setText(list.get(1));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        stegoAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    increaseQuantity("stego",10);
                    ArrayList<String> list = getInfo("stego","quantity");
                    stegoLabel.setText(list.get(1));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        stegoDecreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    decreaseQuantity("stego",10);
                    ArrayList<String> list = getInfo("stego","quantity");
                    stegoLabel.setText(list.get(1));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        submitStuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("delete".equals(inputQuantityField.getText())){
                    try {
                        deleteRow(inputNameField.getText());
                        infoLabel.setText(inputNameField.getText() + " row deleted");
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                try {

                    addStuff(inputNameField.getText(),Integer.parseInt(inputQuantityField.getText()));
                    infoLabel.setText("added " + inputQuantityField.getText() + " " + inputNameField.getText());
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    try {
                        increaseQuantity(inputNameField.getText(),Integer.parseInt(inputQuantityField.getText()));
                        infoLabel.setText("added " + inputQuantityField.getText() + " " + inputNameField.getText());
                    } catch (ClassNotFoundException | SQLException exc) {
                        exc.printStackTrace();
                    }
                }

            }
        });
        tablerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tabler tabler = new Tabler();
                tabler.start();
            }
        });
    }

    public void start() throws IOException {
        setTitle("server");
        setVisible(true);
        setSize(333,333);
        setLocation(123,234);
        panel = new JPanel(new GridLayout(5,3));
        /*diploAddButton = new JButton();
        diploDecreaseButton = new JButton();
        stegoAddButton = new JButton();
        stegoDecreaseButton = new JButton();*/
        panel.setSize(333,333);
        add(panel);
        panel.add(brontoAddButton);
        panel.add(brontoDecreaseButton);
        panel.add(brontoLabel);
        panel.add(diploAddButton);
        panel.add(diploDecreaseButton);
        panel.add(diploLabel);
        panel.add(stegoAddButton);
        panel.add(stegoDecreaseButton);
        panel.add(stegoLabel);
        panel.add(inputNameField);
        panel.add(inputQuantityField);
        panel.add(submitStuffButton);
        panel.add(infoLabel);
        panel.add(tablerButton);
        try {
            brontoLabel.setText(getInfo("bronto","quantity").get(1));
            diploLabel.setText(getInfo("diplo","quantity").get(1));
            stegoLabel.setText(getInfo("stego","quantity").get(1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started...");
            while (true){
                Socket socket = serverSocket.accept();
                connektion = new Connektion(socket);
                printMessage(connektion.readMessage());
                connektion.sendMessage(new Message("server", "message received, eh!"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class ClientFred extends Thread{
        private ServerSocket socket;

        public ClientFred(ServerSocket socket) {
            this.socket = socket;
        }

/*        public void run(){

            System.out.println(Thread.currentThread().getClass());

            try (ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("Server started...");
                while (true){
                    ClientFred clientFred = new ClientFred(serverSocket);
                    clientFred.start();
                    Socket socket = serverSocket.accept();
                    connektion = new Connektion(socket);
                    printMessage(connektion.readMessage());
                    connektion.sendMessage(new Message("server", "message received, yo!"));
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }*/
        }

    private void printMessage(Message message){
        System.out.println("message received: " + message);
        if("quantity".equals(message.getText())) {
            try {
                getInfo(message.getType(), message.getText());
                ArrayList<String> list = getInfo(message.getType(), message.getText());
                connektion.sendMessage(new Message(list.get(0), list.get(1)));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        if(isNumeric(message.getText())){
            try {
                decreaseQuantity(message.getType(),Integer.parseInt(message.getText()));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        if("login".equals(message.getSender())){
            try {

                ArrayList<String> list = checkClient(message.getType(),message.getText());
                System.out.println(list.size());
                //System.out.println(list.get(0));
                if(list.size()>1){
                    System.out.println(list.get(1));
                }
                if(list.size()==0){
                    connektion.sendMessage(new Message("nolog","no such user or wrong password"));
                }
                else if(list.get(0).equals(message.getType())&&!list.get(1).equals(message.getText())){
                    connektion.sendMessage(new Message("nolog","wrong password"));
                }
                else if(list.get(0).equals(message.getType())&&list.get(1).equals(message.getText())){
                    connektion.sendMessage(new Message("log", " log"));
                }


            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        if("register".equals(message.getSender())) {
            try {
                addClient(message.getType(), message.getText());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                try {
                    connektion.sendMessage(new Message("reg ", "not unique"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if("comboBoxFiller".equals(message.getType())/* || "comboBox1".equals(message.getType())*/){
            try {
                ArrayList<String> list = getColumn("name");
                connektion.sendMessage(new Message("server","comboBox1Stuff","list here",list));
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        }
        if("getInfoButton".equals(message.getType())){
            try {
                ArrayList<String> list = getInfo(message.getText(),"quantity");
                connektion.sendMessage(new Message("forInfoButt",list.get(1)));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        }

    public static ArrayList<String> seeTables() throws SQLException {
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE  TABLE_TYPE = 'BASE_TABLE' AND TABLE_CATALOG='DinoBase'";
        ArrayList<String> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)){
            try(PreparedStatement ps = connection.prepareStatement(query)){
                //ps.setString(1,columnName);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()){
                    String item = resultSet.getString("TABLE_NAME");
                    System.out.println(item);
                    list.add(item);
                }
            }
        }
        return list;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void createDinoTable() throws ClassNotFoundException, SQLException {
        String creationQuery = "CREATE TABLE IF NOT EXISTS Dino_Table(" +
                "id SERIAL PRIMARY KEY,"+
                "name VARCHAR(50) NOT NULL UNIQUE," +
                "quantity INTEGER NOT NULL);";
        //Class.forName("org.postgresql.Driver");
        try(Connection connection = DriverManager.getConnection(conStr, login, pwd)){
            try(Statement statement = connection.createStatement()){
                int result = statement.executeUpdate(creationQuery);
                System.out.println(result);
            }
        }
    }

    public static void createClientTable() throws ClassNotFoundException, SQLException {
        String creationQuery = "CREATE TABLE IF NOT EXISTS Client_Table(" +
                "id SERIAL PRIMARY KEY,"+
                "clientId VARCHAR (50) UNIQUE NOT NULL," +
                "someInfo VARCHAR (50) NOT NULL);";
        //Class.forName("org.postgresql.Driver");
        try(Connection connection = DriverManager.getConnection(conStr, login, pwd)){
            try(Statement statement = connection.createStatement()){
                int result = statement.executeUpdate(creationQuery);
                System.out.println(result);
            }
        }
    }

    public static void addStuff(String name, int quantity) throws ClassNotFoundException, SQLException {
        String insert = "INSERT INTO dino_table (name, quantity) VALUES (?, ?);";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(conStr, login, pwd)) {
            try(PreparedStatement ps = connection.prepareStatement(insert)){
                ps.setString(1, name);
                ps.setInt(2, quantity);
                int result = ps.executeUpdate();
                System.out.println(result);
            }
        }
    }

    public static void addClient(String name, String info) throws ClassNotFoundException, SQLException {
        String insert = "INSERT INTO client_table (clientid, someinfo) VALUES (?, ?);";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(conStr, login, pwd)) {
            try(PreparedStatement ps = connection.prepareStatement(insert)){
                ps.setString(1, name);
                ps.setString(2, info);
                int result = ps.executeUpdate();
                System.out.println(result);
            }
        }
    }

    //checkin if client is present in the clientbase
    public static ArrayList<String> checkClient(String name, String info) throws ClassNotFoundException, SQLException {
        String select = "SELECT * FROM client_table WHERE clientid=? AND someinfo=?;";
        Class.forName("org.postgresql.Driver");
        ArrayList<String> list = new ArrayList<>(2);
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)){
            try (PreparedStatement ps = connection.prepareStatement(select)){
                ps.setString(1,name);
                ps.setString(2,info);

                //int result = ps.executeUpdate();
                //System.out.println(result);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()){
                String clientid = resultSet.getString("clientid");
                String someinfo = resultSet.getString("someinfo");
                System.out.println(clientid + " : " + someinfo);
                    list.add(0, clientid);
                    list.add(1,someinfo);

                }
                return list;
                /*list.add(0, name);
                list.add(1,Integer.toString(quantity));*/
                //return result;
            }
        }
    }

    public static void increaseQuantity(String name, int quantity) throws ClassNotFoundException, SQLException {
        String insert = "UPDATE dino_table SET quantity=quantity+? WHERE name=?;";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(conStr, login, pwd)) {
            try(PreparedStatement ps = connection.prepareStatement(insert)){
                ps.setString(2, name);
                ps.setInt(1, quantity);
                int result = ps.executeUpdate();
                System.out.println(result);
            }
        }
    }

    public static void decreaseQuantity(String name, int quantity) throws ClassNotFoundException, SQLException {
        String delete = "UPDATE  dino_table SET quantity=quantity-? WHERE name =?;";
        Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(conStr, login, pwd)) {
                try(PreparedStatement ps = connection.prepareStatement(delete)){
                ps.setString(2, name);
                ps.setInt(1, quantity);
                    int result = ps.executeUpdate();
                    System.out.println(result);
                }
            }
        }

    public static void requestBronto() throws ClassNotFoundException {
        String request = "SELECT * FROM dino_table WHERE name = 'bronto';";
        Class.forName("org.postgresql.Driver");
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                int quantity = resultSet.getInt("quantity");
                System.out.println("brontos " + quantity);
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getInfo(String type,String column) throws ClassNotFoundException {
        String request = "SELECT * FROM dino_table WHERE name=?";
        Class.forName("org.postgresql.Driver");
        ArrayList<String> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)){
            try (PreparedStatement ps = connection.prepareStatement(request)){
                    ps.setString(1,type);
                    //ps.setString(2,column);
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()){
                        String name = resultSet.getString("name");
                        int quantity = resultSet.getInt("quantity");
                        //System.out.println(name + " : " + quantity);
                        list.add(0, name);
                        list.add(1,Integer.toString(quantity));
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getQuantityInfo(String type,String column) throws ClassNotFoundException {
        String request = "SELECT * FROM dino_table WHERE name=?";
        Class.forName("org.postgresql.Driver");
        ArrayList<String> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)){
            try (PreparedStatement ps = connection.prepareStatement(request)){
                ps.setString(1,type);
                //ps.setString(2,column);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    int quantity = resultSet.getInt("quantity");
                    //System.out.println(name + " : " + quantity);
                    list.add(0,Integer.toString(quantity));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteRow(String name) throws ClassNotFoundException, SQLException {
        String insert = "DELETE FROM dino_table WHERE name=?;";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(conStr, login, pwd)) {
            try(PreparedStatement ps = connection.prepareStatement(insert)){
                ps.setString(1, name);
                int result = ps.executeUpdate();
                System.out.println(result);
            }
        }
    }

    public static ArrayList<String> getColumn(String columnName) throws SQLException {
        String query = "SELECT name FROM dino_table";
        ArrayList<String> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(conStr,login,pwd)){
            try(PreparedStatement ps = connection.prepareStatement(query)){
                //ps.setString(1,columnName);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()){
                    String item = resultSet.getString("name");
                    System.out.println(item);
                    list.add(item);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            createClientTable();
            createDinoTable();
            seeTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int port = 8090;
        DinoServer dinoServer = new DinoServer(port);
        try {
            dinoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}