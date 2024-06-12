package me.harry0198.infoheads.spigot;


import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.domain.NotificationStrategy;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.repository.Repository;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public final class InfoHeads extends JavaPlugin {

    private DataStore dataStore;
    private static InfoHeadsApi api;

    private FileUtil fileUtil;
    private final File messagesFile = new File(getDataFolder(), "messages.yml");
    private FileConfiguration messagesConfig;

    public boolean papi = false;
    public boolean blockParticles = false;

    @Override
    public void onEnable() {
        load();
        new Metrics(this, 4607);

        Locale locale = Locale.forLanguageTag("en-GB");
        NotificationStrategy notificationStrategy = new SpigotNotificationStrategy();
        LocalizedMessageService localizedMessageService = new LocalizedMessageService(notificationStrategy, locale);

        Repository<Set<InfoHeadProperties>>

        InfoHeadService infoHeadService = new InfoHeadService();
        CommandHandler commandHandler = new CommandHandler();

        this.getCommand("infoheads").setExecutor(new BukkitCmdExecutor());

        getServer().getPluginManager().registerEvents(new InventoryGuiListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        if (packagesExists("org.bukkit.util.Consumer"))
            getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        api = new InfoHeadsImpl();
        getServer().getServicesManager().register(InfoHeadsApi.class,api,this, ServicePriority.Normal);

        (new UpdateChecker(InfoHeads.getInstance(), 67080)).getVersion((version) -> {
            if (InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                InfoHeads.getInstance().info("There is no new update available.");
            } else {
                InfoHeads.getInstance().info("There is a new update available. Version: " + version);
            }
        });
    }

    public void load() {
        if (!messagesFile.exists()) {
            saveResource("messages.yml", true);
            messagesFile.getParentFile().mkdirs();
            try {
                messagesFile.createNewFile();
                debug("Creating a messages.yml file");
            } catch (IOException e) {
                error("Cannot create messages.yml configuration file.");
            }
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        updateMessagesConfig();
        saveDefaultConfig();

        this.fileUtil = new FileUtil();

        this.dataStore = getFileUtil().getFile(DataStore.class).exists() ? getFileUtil().load(DataStore.class) : new DataStore();

        // InfoHead, (UUID, TimeStamp)
        // Checks and removes people who's time has elapsed the cooldown from map.

        dataStore.getInfoHeads().values().forEach(configuration -> {

            Set<Map.Entry<UUID, Long>> entrySet = configuration.getTimestamps().entrySet();

            Iterator<Map.Entry<UUID, Long>> iterator = entrySet.iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, Long> entry = iterator.next();
                Long timestamp = entry.getValue();

                if (timestamp < System.currentTimeMillis()) {
                    iterator.remove();
                }
            }
        });


        fileUtil.save(this.dataStore);

        DataStore.placerMode.clear();
        getServer().getPluginManager().registerEvents(new HeadInteract(this), this);
        getServer().getPluginManager().registerEvents(new HeadPlace(this), this);
        getServer().getPluginManager().registerEvents(new HeadBreak(this), this);
//        if (packagesExists("me.arcaniax.hdb.api.DatabaseLoadEvent", "me.arcaniax.hdb.api.HeadDatabaseAPI"))
//            getServer().getPluginManager().registerEvents(new HdbListener(this), this);
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI"))
            papi = true;
        if (packagesExists("me.badbones69.blockparticles.Methods"))
            blockParticles = true;

    }

    @Override
    public void onDisable() {
        fileUtil.save(dataStore);
    }

    private void updateMessagesConfig() {
        if (messagesConfig.contains("one-time")) {
            messagesConfig.set("oneTime", messagesConfig.get("one-time"));
            messagesConfig.set("one-time", null);
        }

        messagesConfig.options().header("This is the InfoHeads messages file.\nIn this file you can always use color codes (&) and HEX codes (&#000000)\n\nThis file is auto-updated from an internal class of the plugin.\nPlease DO NOT remove any config field, it will cause errors.");
        messagesConfig.options().copyHeader(true);
        MessageUtil.init();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ConfigTask(this), 0L, 60L);
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    /**
     * Determines if all packages in a String array are within the Classpath
     * This is the best way to determine if a specific plugin exists and will be
     * loaded. If the plugin package isn't loaded, we shouldn't bother waiting
     * for it!
     * @param packages String Array of package names to check
     * @return Success or Failure
     */
    private static boolean packagesExists(String...packages) {
        try {
            for (String pkg : packages) {
                Class.forName(pkg);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void debug(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public static ConversationFactory getInputFactory(final InfoHeadConfiguration infoHeadConfiguration, final Element.InfoHeadType element) {
        return new ConversationFactory(InfoHeads.getInstance())
                .withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix())
                .withFirstPrompt(new ElementValueInput(InfoHeads.getInstance().dataStore, infoHeadConfiguration, element)) //TODO incorrect static use here. Datstore should be singleton? no. Should use interface
                .withEscapeSequence("cancel")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public File getMessagesFile() {
        return messagesFile;
    }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

    public void warn(String msg) {
        getLogger().log(Level.WARNING, msg);
    }

    public void info(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public void error(String msg) {
        getLogger().log(Level.SEVERE, msg);
    }
}
