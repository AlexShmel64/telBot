import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotThread extends Thread{
    TelegramBotsApi telegramBotsApi;
    BotSession botSession;
    Bot bot;
    @Override
    public void run() {
        super.run();
        try {
            bot = new Bot();
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = telegramBotsApi.registerBot(bot);
            System.out.println("Бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopRun(){
        botSession.stop();
        System.out.println("Бот остановлен");
    }
    public boolean isBotStarted(){
        return botSession.isRunning();
    }
}
