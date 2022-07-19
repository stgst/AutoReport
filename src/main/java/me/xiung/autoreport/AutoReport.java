package me.xiung.autoreport;

import me.xiung.autoreport.commands.report;
import me.xiung.autoreport.discord.buttons;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class AutoReport extends JavaPlugin {

    public static JDA bot;
    public static JavaPlugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        String token = getConfig().getString("Discord.Token");

        try {
            bot = (JDA) JDABuilder.createDefault(token)
                    .addEventListeners(new buttons())
                    .build();
            getCommand("report").setExecutor(new report());
        } catch (LoginException e) {
            getLogger().warning("Something wrong to connect your discord-bot. ");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("discord-bot is closing..");
        bot.shutdown();
    }
}
