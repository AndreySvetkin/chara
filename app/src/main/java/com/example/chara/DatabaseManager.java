package com.example.chara;

import com.example.chara.model.Profile;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    public static boolean checkLogin(String login, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "SELECT * FROM `mobile_app`.`dbo.users` WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();

            resultSet.close();
            statement.close();
            conn.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Profile getUserProfile(String username) {
        Profile profile = new Profile();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "SELECT * FROM `mobile_app`.`dbo.users` WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                profile.setFirstName(resultSet.getString("first_name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setBirthDate(resultSet.getString("dob"));
                profile.setAvatar(resultSet.getBytes("avatar"));

                resultSet.close();
                statement.close();
                conn.close();

                return profile;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean updateUserProfile(String username, String firstName, String lastName, String birthDate, byte[] avatar) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "UPDATE `mobile_app`.`dbo.users` SET first_name = ?, last_name = ?, dob = ?, avatar = ? WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, birthDate);
            Blob blob = conn.createBlob();
            blob.setBytes(1, avatar);
            statement.setBlob(4, blob);
            statement.setString(5, username);

            int rowsUpdated = statement.executeUpdate();

            statement.close();
            conn.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPassword(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "SELECT * FROM `mobile_app`.`dbo.users` WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();

            resultSet.close();
            statement.close();
            conn.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "UPDATE `mobile_app`.`dbo.users` SET password = ? WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();

            statement.close();
            conn.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean registerUser(String firstName, String lastName, String username, String password, String dob, byte[] avatar) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "INSERT INTO `mobile_app`.`dbo.users` (first_name, last_name, username, password, dob, avatar) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, dob);
            Blob blob = conn.createBlob();
            blob.setBytes(1, avatar);
            statement.setBlob(6, blob);

            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean checkIfLoginExists(String login) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
            String query = "SELECT * FROM `mobile_app`.`dbo.users` WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();

            resultSet.close();
            statement.close();
            conn.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}