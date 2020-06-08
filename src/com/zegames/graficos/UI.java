package com.zegames.graficos;

import com.zegames.entities.Player;
import java.awt.*;

public class UI {
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Coins: " + Player.currentCoins + "/" + Player.maxCoins, 10, 30);
    }
}
