import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private int Access;
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().equals("/getPasswordDate")){
            sendMsg("Password:");
        }
            Security(update.getMessage().getText());
        if (update.getMessage().getText().equals("/stopPasswordDate")){
            Access = 0;
            Constant.STAGE = -2;
            sendMsg("Session ended");
        }
        if (Access == 1){
            new CommandHandler(update.getMessage().getText());
        }
    }
    public synchronized void sendMsg(String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(Constant.CHAT_ID);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Exception");
        }
    }
    private void Security(String message){
        if (Constant.SECURITY_PASSWORD.equals(message)){
            Access = 1;
            sendMsg("Delete password");
        }
    }

    public String getBotUsername() {
        return Constant.BOT_NAME;
    }
    public String getBotToken() {
        return Constant.TOKEN;
    }
}
