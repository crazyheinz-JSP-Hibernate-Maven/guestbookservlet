package be.intecbrussel.webcomponents.guestbook.dao;

import be.intecbrussel.webcomponents.guestbook.domain.GuestbookBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestbookDao {
    private String url;
    private String user;
    private String password;

    private Connection connection;

    public GuestbookDao(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        connection = DriverManager.getConnection(url, user, password);
    }

    public List<GuestbookBean> getAllMessages(){
        List<GuestbookBean> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from GuestBook");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                messages.add(new GuestbookBean(resultSet.getTimestamp("date").toLocalDateTime()
                        ,resultSet.getString("name")
                        ,resultSet.getString("message")));
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    public void createMessage(GuestbookBean message){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GuestBook(Date,Name,Message) VALUES (?,?,?)");
            statement.setTimestamp(1,Timestamp.valueOf(message.getDate()));
            statement.setString(2,message.getName());
            statement.setString(3,message.getMessage());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection(){
        return connection;
    }
    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
