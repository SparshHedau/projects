package HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Sparsh@2002";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.print("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println(" 1. Add Patient ");
                System.out.println(" 2. View Patient ");
                System.out.println(" 3. View Doctors ");
                System.out.println(" 4. Book Appointment ");
                System.out.println(" 5. Exit ");
                System.out.println(" Enter your choices : ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
bookAppointment(patient,doctor,connection,scanner);
break;
                    case 5:
                        System.out.println("Thanks for choosing Sparsh app");
                        return;


                    default:
                        System.out.println("Enter valid choice!! ");


                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter patient id : ");
        int patientid = scanner.nextInt();
        System.out.println("Enter Doctor id : ");
        int doctorid = scanner.nextInt();
        System.out.println("Enter appointment date YYYY-MM_DD ");
        String appointment = scanner.next();

        if (patient.getPatient(patientid) && doctor.getPatient(doctorid)) {
            if (checkDoctorAvailabilty(doctorid, appointment,connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientid);
                    preparedStatement.setInt(2, doctorid);
                    preparedStatement.setString(3, appointment);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment booked");
                    } else {

                        System.out.println("Failed to book Appointment ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        else {
            System.out.println("Doctor not available on this date ");
        }
    }
else

    {

        System.out.println("Either doctor ot patient does not exist!!!! ");
    }
}
public static boolean checkDoctorAvailabilty(int doctorid,String appointmentdate,Connection connection) {
    String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date= ?";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,doctorid);
        preparedStatement.setString(2,appointmentdate);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            int count =resultSet.getInt(1);
            if(count==0){
                return true;
            }else {
                return false;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
}



