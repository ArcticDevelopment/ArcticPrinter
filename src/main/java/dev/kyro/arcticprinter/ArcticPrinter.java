package dev.kyro.arcticprinter;

import com.massivecraft.factions.FactionsPlugin;
import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.AConfig;
import dev.kyro.arcticprinter.commands.PrinterCommand;
import dev.kyro.arcticprinter.controllers.IllegalPrinterEvents;
import dev.kyro.arcticprinter.controllers.PrinterEvents;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArcticPrinter extends JavaPlugin {

    public static ArcticPrinter INSTANCE;
    public static Economy VAULT = null;
    public static FactionsPlugin FACTIONS;

    public static List<Material> illegalItems = new ArrayList<>();

    @Override
    public void onEnable() {

        Logger LOGGER = getLogger();
        INSTANCE = this;

        loadConfig();

        ArcticAPI.configInit(this, "prefix", "error-prefix");

        if (!setupEconomy()) {
            LOGGER.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {

            FACTIONS = (FactionsPlugin) Bukkit.getPluginManager().getPlugin("Factions");
        } catch (Exception exception) {

            exception.printStackTrace();
        }

        registerCommands();
        registerListeners();

        for(String stringMaterial : AConfig.getStringList("illegal-materials")) {

            Material material = Material.getMaterial(stringMaterial);

            if(material != null) illegalItems.add(material);
        }
    }

    @Override
    public void onDisable() {


    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        VAULT = rsp.getProvider();
        return VAULT != null;
    }

    private void loadConfig() {

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerCommands() {

        getCommand("printer").setExecutor(new PrinterCommand());
    }

    private void registerListeners() {

        getServer().getPluginManager().registerEvents(new PrinterEvents(), this);
        getServer().getPluginManager().registerEvents(new IllegalPrinterEvents(), this);
    }
}
