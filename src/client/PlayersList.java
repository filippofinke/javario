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

import shared.Config;

/**
 *
 * @author filippofinke
 */
public class PlayersList {

    private static ClientPlayer players[] = new ClientPlayer[Config.MAX_PLAYERS];

    public static ClientPlayer[] getPlayers() {
        return players;
    }

    public static ClientPlayer get(int id) {
        if (id >= 0 && id < players.length) {
            return players[id];
        }
        return null;
    }

    public static void remove(int id) {
        if (id >= 0 && id < players.length) {
            players[id] = null;
        }
    }

    public static void add(ClientPlayer player) {
        int id = player.getId();
        if (id >= 0 && id < players.length) {
            players[id] = player;
        }
    }

}
