package pl.first.firstjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.first.firstjava.exception.DaoException;
import pl.first.firstjava.exception.DatabaseException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    private final String dbUrl = "jdbc:sqlite:Database";
    private final String boardName;
    private int boardID;


    public JdbcSudokuBoardDao(String name) throws DatabaseException {
        this.boardName = name;

        try (Connection conn = prepareConnection()) {
            Statement statement = conn.createStatement();
            String createTableSudokuBoard =
                            "CREATE TABLE IF NOT EXISTS SudokuBoard "
                                    + "(id integer primary key , name varchar(30) not null);";
            String createTableField =
                            "CREATE TABLE IF NOT EXISTS Field ("
                                    + "value integer not null,"
                                    + " row integer not null,"
                                    + " column integer not null,"
                                    + " board_id int not null,"
                                    + "CONSTRAINT FK_board FOREIGN KEY "
                                    + "(board_id) REFERENCES SudokuBoard(id));";
            statement.execute(createTableSudokuBoard);
            statement.execute(createTableField);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    public Connection prepareConnection() throws DatabaseException {
        try {
            Connection conn = DriverManager.getConnection(dbUrl);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        String select = "SELECT * FROM Field WHERE board_id = "
                + "(SELECT MAX(id) from SudokuBoard WHERE name = '" + boardName + "');";

        try (Connection conn = prepareConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(select);) {

            while (rs.next()) {
                board.set(
                        rs.getInt("row"),
                        rs.getInt("column"),
                        rs.getInt("value"));
            }



        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return board;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {

        String insertToBoard = "INSERT INTO SudokuBoard(name) VALUES(?);";
        String insertToField = "INSERT INTO Field(value,row,column,board_id) VALUES(?,?,?,?);";
        String getBoardId = "SELECT MAX(id) as amount FROM SudokuBoard;";
        try (Connection conn = prepareConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(getBoardId);) {

            boardID = rs.getInt("amount") + 1;


            PreparedStatement pstmtBoard = conn.prepareStatement(insertToBoard);
            PreparedStatement pstmtField = conn.prepareStatement(insertToField);
            pstmtBoard.setString(1,boardName);
            pstmtBoard.executeUpdate();

            insertFields(pstmtField,obj, boardID);

            conn.commit();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    private void insertFields(PreparedStatement pstmt, SudokuBoard obj, int boardID)
            throws DatabaseException {

        try {
            for (int i = 0; i < 9;i++) {
                for (int j = 0; j < 9;j++) {

                    pstmt.setInt(1,obj.get(i,j));
                    pstmt.setInt(2,i);
                    pstmt.setInt(3,j);
                    pstmt.setInt(4,boardID);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}

