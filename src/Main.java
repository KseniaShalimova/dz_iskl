import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию, имя, отчество, дату рождения, в формате dd.mm.yyyy , номер телефона без знаков, пол буквой f или m:");
        String enteredData = scanner.nextLine();
        scanner.close();
        String[] data = enteredData.split(" ");
        if (data.length!=6) {
            throw new IllegalArgumentException("Количество аргументов не равно 6.");
        }
        try {
            String surname = data[0];
            String name = data[1];
            String middleName = data[2];
            LocalDate birthDate = dateParse(data[3]);
            long phone = phoneParse(data[4]);
            char gender = genderParse(data[5]);
            writeFile(surname, name, middleName, birthDate, phone, gender);
        }
        catch(DateTimeParseException e){
            System.err.println("Ошибка: дата рождения должна быть введена в формате dd.mm.yyyy.");
        }
        catch (IllegalArgumentException e){
            System.err.println("Ошибка: " + e.getMessage());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeFile(String surname, String name, String middleName, LocalDate birthDate, long phone, char gender) throws IOException {
        String filename = surname +".txt";
        String line = String.format("%s %s %s %s %d %c", surname, name, middleName, birthDate, phone, gender);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))){
            writer.write(line);
            writer.newLine();
        }
    }

    private static char genderParse(String strGen) {
        if ( strGen.length()!=1 ){
            if (!(strGen.equalsIgnoreCase("f")) || !(strGen.equalsIgnoreCase("m"))){
                throw new IllegalArgumentException("Ошибка: пол должен быть записан одной буквой: f - жен, m - муж.");

            }
        }
        return strGen.charAt(0);
    }

    private static long phoneParse(String strPhone) {
        try {
            return Long.parseLong(strPhone);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка: номер телефона должен быть записан только цифрами, без использования других символов");
        }

    }

    private static LocalDate dateParse(String trDate) throws DateTimeParseException{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(trDate, dateTimeFormatter);
    }




}