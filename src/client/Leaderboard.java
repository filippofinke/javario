/*
 * Copyright (C) 2019 filippofinke
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package client;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import shared.Player;

/**
 *
 * @author carlo pezzotti
 */
public class Leaderboard extends JPanel {

    public Leaderboard() {
        this.setSize(200,PlayersList.getPlayers().length * 25);
    }

    
    
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0 +this.getX(), 0 + this.getY(), this.getWidth(), this.getHeight());
        int i = 0;
        if (PlayersList.getPlayers() != null) {
            for (Player p : sortArray(PlayersList.getPlayers())) {
                if(p != null){
                    g.drawString(
                            p.getId() + ":" + p.getPoints(),
                            this.getX()+5,
                            this.getY() + 10 + (int) (g.getFontMetrics().getStringBounds(p.getId() + ":" + p.getPoints(), g)).getHeight() * i
                    );
                    i++;
                }
            }
        }
    }

    public Player[] sortArray(Player[] players) {
        Player[] playerSort = new Player[players.length];
        for (int i = 0; i < playerSort.length; i++) {
            playerSort[i] = players[i];
        }
        int n = playerSort.length;
        Player temp = players[0];

        if (temp != null) {
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < (n - i); j++) {
                    if (playerSort[j - 1] != null && playerSort[j] != null) {
                        if (playerSort[j - 1].getPoints() < playerSort[j].getPoints()) {
                            temp = playerSort[j - 1];
                            playerSort[j - 1] = playerSort[j];
                            playerSort[j] = temp;
                        }
                    }
                }
            }
        }
        return playerSort;
    }
    
    
}
