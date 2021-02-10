package dev.kyro.arcticprinter.controllers;

import dev.kyro.arcticapi.misc.AOutput;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;

public class IllegalPrinterEvents implements Listener {

    public static ArrayList<Material> illegalItems = new ArrayList<>();

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(!PrinterManager.inPrinter(player)) return;

        if(illegalItems.contains(event.getCurrentItem().getType())) {

            AOutput.error(player, "You are not allowed to do that in printer mode");
        }
    }

    @EventHandler
    public static void onEntityInteract(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public static void onItemPickup(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }
}
