package dev.kyro.arcticprinter.controllers;

import dev.kyro.arcticprinter.ArcticPrinter;
import dev.kyro.arcticprinter.enums.PrinterEndReason;
import dev.kyro.arcticprinter.objects.PrinterPlayer;
import dev.kyro.arcticprinter.utilities.AOutput;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PrinterEvents implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public static void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        PrinterPlayer printerPlayer = PrinterManager.getPrinterPlayer(player);

        double playerBalance = ArcticPrinter.VAULT.getBalance(player);
        double blockCost = ShopGuiPlusApi.getItemStackPriceBuy(player, event.getItemInHand());

        if(playerBalance < blockCost) {

            AOutput.error(player, "Out of money");
            printerPlayer.exitPrinter(PrinterEndReason.OUT_OF_MONEY);
            return;
        }

        ArcticPrinter.VAULT.withdrawPlayer(player, blockCost);
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        PrinterPlayer printerPlayer = PrinterManager.getPrinterPlayer(player);

        if(printerPlayer.fPlayer.isInOwnTerritory()) return;

        printerPlayer.exitPrinter(PrinterEndReason.LEFT_FACTION_CLAIM);
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        PrinterPlayer printerPlayer = PrinterManager.getPrinterPlayer(player);

        printerPlayer.exitPrinter(null);
    }
}
