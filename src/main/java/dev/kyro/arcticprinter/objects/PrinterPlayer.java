package dev.kyro.arcticprinter.objects;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticprinter.ArcticPrinter;
import dev.kyro.arcticprinter.controllers.PrinterManager;
import dev.kyro.arcticprinter.enums.PrinterEndReason;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PrinterPlayer {

    public Player player;
    public FPlayer fPlayer;

    public ArrayList<Location> recentBlocks = new ArrayList<>();

    public PrinterPlayer(Player player) {

        this.player = player;
        fPlayer = FPlayers.getInstance().getByPlayer(player);

        PrinterManager.printerPlayers.put(player.getUniqueId(), this);

        enterPrinter();

        new BukkitRunnable() {
            @Override
            public void run() {

                if(!fPlayer.hasEnemiesNearby()) return;

                AOutput.send(player, "Enemy nearby. Disabling printer");
                exitPrinter(PrinterEndReason.ENEMY_NEARBY);
            }
        }.runTaskTimer(ArcticPrinter.INSTANCE, 0L, 20L);
    }

    public void enterPrinter() {

        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().clear();

        AOutput.send(player, "Entered Printer");
    }

    public void exitPrinter(PrinterEndReason endReason) {

        PrinterManager.printerPlayers.remove(player.getUniqueId());

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        if(endReason != null) AOutput.send(player, endReason.getMessage());
    }

    public double getBalance() {

        return ArcticPrinter.VAULT.getBalance(player);
    }

    public boolean canAfford(double cost) {

        return ArcticPrinter.VAULT.getBalance(player) >= cost;
    }

    public void withdraw(double amount) {

        ArcticPrinter.VAULT.withdrawPlayer(player, amount);
    }
}