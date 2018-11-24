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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MapToolsPlugin extends JavaPlugin {
    private Logger log;
    private MarkerUpdateTask task;

    @Override
    public void onEnable() {
        log = getLogger();
        log.info("Plugin enabled!");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        task = new MarkerUpdateTask(this);
        task.runTaskTimer(this, 20, 20 * getConfig().getInt("interval", 5));
    }

    @Override
    public void onDisable() {
	// Making sure we cancel the task, so there are no aditional task registerings while the server is stopping/stopping plugin
	if (task != null) {
            task.cancel();
        }
		
	try {
	    // write an empty json file
	    File file = new File(getConfig().getString("markerFile"));
	    BufferedWriter output = new BufferedWriter(new FileWriter(file));
	    output.write("{\"players\":[]}");
	    output.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

        log.info("Plugin disabled!");
    }
}
