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
package shared;

/**
 *
 * @author filippofinke
 */
public class Protocol {

    /**
     * Server to client
     */
    public static int SERVER_FULL = -1;
    public static int PLAYER_JOINED = -2;
    public static int PLAYER_LEFT = -3;
    public static int PLAYER_UPDATE = -4;
    public static int FOOD_SPAWNED = -5;
    public static int FOOD_EATED = -6;
    public static int BULLET_SPAWNED = -7;
    public static int BULLET_MOVED = -8;
    public static int BULLET_DESTROYED = -9;
    public static int END = -10;
    
    /**
     * Client to server
     */
    public static byte PLAYER_MOVE = 1;
    public static byte PLAYER_SHOOT = 2;

}
