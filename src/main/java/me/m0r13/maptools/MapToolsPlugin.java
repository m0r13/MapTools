/*
 * Copyright 2013 Moritz Hilscher
 *
 * This file is part of MapTools.
 *
 * MapTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapTools.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.m0r13.maptools;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MapToolsPlugin extends JavaPlugin {

    private Logger log;

    @Override
    public void onEnable() {
        log = getLogger();
        log.info("Plugin enabled!");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        MarkerUpdateTask task = new MarkerUpdateTask(this);
        task.runTaskTimer(this, 20, 20 * getConfig().getInt("interval", 5));
    }

    @Override
    public void onDisable() {
        // write an empty json file
        new MarkerUpdateTask(this).writePlayers(new Player[] {});

        log.info("Plugin disabled!");
    }
}
