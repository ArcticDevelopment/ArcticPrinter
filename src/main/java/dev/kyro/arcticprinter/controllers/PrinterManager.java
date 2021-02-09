package dev.kyro.arcticprinter.controllers;

import dev.kyro.arcticprinter.objects.PrinterPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PrinterManager {

    public static HashMap<UUID, PrinterPlayer> printerPlayers = new HashMap<>();

    public static boolean inPrinter(Player player) {

        return printerPlayers.containsKey(player.getUniqueId());
    }

    public static PrinterPlayer getPrinterPlayer(Player player) {

        return printerPlayers.get(player.getUniqueId());
    }
}
