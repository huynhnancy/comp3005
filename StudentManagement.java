import java.sql.*;

public class StudentManagement {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Student";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        getAllStudents();
        addStudent("New", "Student", "new.student@example.com", "2024-03-18");
        updateStudentEmail(1, "updated.email@example.com");
        deleteStudent(2);
    }

    public static void getAllStudents() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(String firstName, String lastName, String email, String enrollmentDate) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setDate(4, Date.valueOf(enrollmentDate));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentEmail(int studentId, String newEmail) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE students SET email = ? WHERE student_id = ?"
            );
            statement.setString(1, newEmail);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int studentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM students WHERE student_id = ?"
            );
            statement.setInt(1, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
