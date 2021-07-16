import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {
    CommandHandler(String message){
        sortMessage(message);
    }
   private void sortMessage(String message){

        if (message.equals("/get")){
            Constant.INQUIRY_GET = true;
        }
        if (message.equals("/set")){
            Constant.INQUIRY_SET = true;
            new Bot().sendMsg("FileName - no point and /");
        }
        if (Constant.INQUIRY_SET){
            Constant.STAGE++;
            collectionDate(message);
        }
       if (Constant.INQUIRY_PASSWORD){
           readFile(message);
       }
       if (Constant.INQUIRY_GET){
           listFiles();
           Constant.INQUIRY_PASSWORD = true;
       }
       if(Constant.INQUIRY_DELETE){
           deleteFile(message);
       }
       if (message.equals("/delete")){
           listFiles();
           Constant.INQUIRY_DELETE = true;
       }
   }
   private void collectionDate(String message){
        if (Constant.STAGE == 0 && message != null){
            Constant.NAME_FILE = message;
        }
        if (Constant.STAGE == 1 && message != null){
            Constant.LOGIN = message;
        }
        if (Constant.STAGE == 2 && message != null){
            Constant.PASSWORD = message;
            Constant.STAGE = 0;
            createFile(Constant.NAME_FILE,Constant.LOGIN,Constant.PASSWORD);
            Constant.INQUIRY_SET = false;
        }


   }
   private void createFile(String nameFile, String login, String password){
        if (!(nameFile.isEmpty() && login.isEmpty() && password.isEmpty())){
            File file = new File(Constant.PATH_FILE + nameFile + ".txt");
            file.mkdirs();
            writeFile( login, password,nameFile );
        }
   }
   private void listFiles(){
        String message = "";
        int numbering = 0;
        String[] nameFileSplit;
        File file = new File(Constant.PATH_FILE);
        String[] arrayFile = file.list();
        List<String> listFile = Arrays.asList(arrayFile);
        for (Object s: listFile.toArray()){
            nameFileSplit = s.toString().split(".txt");
            message = message + nameFileSplit[0] + "\n";
            numbering++;
        }
        new Bot().sendMsg("Select site: " + "\n" + " \n" + numbering + ".  " + message);
        Constant.INQUIRY_GET = false;
   }
   private void writeFile(String login, String password, String nameFile){
        String template = new Encryption().cipher(login) + " " + new Encryption().cipher(password);
        try(FileOutputStream fileOutputStream = new FileOutputStream(Constant.PATH_FILE + nameFile + ".txt")){
            byte [] buffer = template.getBytes();
            fileOutputStream.write(buffer);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

   }
   private void readFile(String nameFile){
        Constant.INQUIRY_PASSWORD = false;
        StringBuilder textString = new StringBuilder();
        String [] date;
        try (FileInputStream fileInputStream = new FileInputStream(Constant.PATH_FILE + nameFile + ".txt")){
            int i;
            while ((i = fileInputStream.read()) != -1){
                textString.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        date = textString.toString().split(" ");
        new Bot().sendMsg("Login: " + new Encryption().decrypted(date[0]) + "\n" + "Password: " + new Encryption().decrypted(date[1]));

   }
   private void deleteFile(String nameFile){
        Constant.INQUIRY_DELETE = false;
        File file = new File(Constant.PATH_FILE + nameFile + ".txt");
        if (file.delete()){
            new Bot().sendMsg(nameFile + " - delete");
        }else {
            new Bot().sendMsg (nameFile + " - not found");
        }
   }
}
