package db;

import java.sql.*;


public class DbConnection {
    public String DB_STRING = "jdbc:sqlite:db.sqlite";
    /**
     * Connect to a sample database
     */
    public void testConnection() {
        Connection conn = null;
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection(this.DB_STRING);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void updateRecordScore(int score) {
        int currentRecord = this.getCurrentRecordScore();
        if (score > currentRecord){
            this.updateRecord(score);
        }
    }

    public int getCurrentRecordScore(){
        Connection c;
        Statement stmt;
        int recordScore = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(this.DB_STRING);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT score FROM Record;" );
            while ( rs.next() ) {
                recordScore = rs.getInt("score");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Operation done successfully");
        return recordScore;
    }

    public void updateRecord(int record){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(this.DB_STRING);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE Record set score="+record+" where id=1;";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Operation done successfully");
    }

}