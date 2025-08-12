package noobsdev.mineland.http;

import noobsdev.mineland.parser.GameMenuParser;
import noobsdev.mineland.parser.ScoreboardParser;
import noobsdev.mineland.utilities.PacketsUtility;
import noobsdev.mineland.utilities.Player;

public class CreateSendHttpsRequets {
    public static void start() {
        Player pl = new Player();
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 3 секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {

                httpSend games = new httpSend("https://6898bc8addf05523e55fb2b3.mockapi.io/api/v1/games");
                httpSend creative = new httpSend("https://6898bc8addf05523e55fb2b3.mockapi.io/api/v1/creative");
                pl.sendMessage(games.delete("1"));
                pl.sendMessage(games.delete("2"));
                pl.sendMessage(creative.delete("1"));

                PacketsUtility.clickSlot(9);

                Thread.sleep(1000);

                pl.sendMessage(games.postArray(GameMenuParser.getParseItemsData(pl.getClient().player.currentScreenHandler)));

                PacketsUtility.clickSlot(0);

                Thread.sleep(2000);

                pl.sendMessage(games.postArray(GameMenuParser.getParseItemsData(pl.getClient().player.currentScreenHandler)));

                Thread.sleep(1000);

                pl.getClient().execute(() -> pl.getClient().player.closeHandledScreen());
                pl.sendMessage(creative.postArray(ScoreboardParser.parse()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
