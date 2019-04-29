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
public class Config {

    /**
     * Field
     */
    public static int MINIMAP_ZOOM = 4;
    public static int FIELD_WIDTH = 1000;
    public static int FIELD_HEIGHT = 1000;

    /**
     * Food
     */
    public static int MAX_FOOD = 2000;
    public static int FOOD_TO_RADIUS = 3;
    public static int FOOD_RADIUS = 5;
    public static int FOOD_DELAY = 1;

    /**
     * Players
     */
    public static int MAX_PLAYERS = 10;
    public static int PLAYER_RADIUS = 15;
    public static double PLAYER_SPEED = 2;
    public static int PLAYER_MAX_RADIUS = 200;

    /**
     * Bullets
     */
    public static int MAX_BULLETS = MAX_PLAYERS * 10;
    public static int BULLET_RADIUS = 6;
    public static int BULLET_DELAY = 100;
    public static int BULLET_COST = 5;
    public static int BULLET_TIMEOUT = 2500;
    public static double BULLET_SPEED = 5f;
    public static int BULLET_UPDATE_DELAY = 10;

}
