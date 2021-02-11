package dev.kyro.arcticprinter.controllers;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticprinter.ArcticPrinter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class IllegalPrinterEvents implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(!PrinterManager.inPrinter(player)) return;

        if(ArcticPrinter.illegalItems.contains(event.getCurrentItem().getType())) {

            AOutput.error(player, "You are not allowed to do that in printer mode");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);

        AOutput.send(player, "cancelled interact event (probably not a good idea lol)");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onEntityInteract(PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onItemPickup(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onItemDrop(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onAttack(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if(!PrinterManager.inPrinter(player)) return;
        event.setCancelled(true);
    }
}
