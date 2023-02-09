package me.iiyoyo.greetme;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import static me.iiyoyo.greetme.hexsupport.hex;

public class eventshandler implements Listener {

    private final GreetMe plugin;
    FileConfiguration config;
    public eventshandler (GreetMe plugin){
        this.plugin = plugin;
        config = plugin.getConfig();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get the player
        Player player = event.getPlayer();

        // Create a firework
        if (config.getBoolean("JoinFirework")) {
            Firework firework = (Firework)
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);

            // Getting the metadata of the firework (the infos of it to be able to customize it)
            FireworkMeta meta = firework.getFireworkMeta();

            // setting the firework meta effects
            meta.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(true)
                    .withColor(Color.ORANGE)
                    .build());

            // Applying the meta to the firework
            firework.setFireworkMeta(meta);
        }
        // Get the greeting-message config option as a string (not a broadcast)
        String greeting = hex(config.getString("greeting-message"));

        // revise it to support placeholderapi
        String reversedgreeting = PlaceholderAPI.setPlaceholders(player, greeting);

        // Send the greeting message to the player
        event.getPlayer().sendMessage(reversedgreeting);

        // creating a string for a greeting broadcast message
        String greetingbroadcast = hex(config.getString("greeting-broadcast"));

        // revised string of greetingbroadcast
        String revisedgreetingbroadcast = PlaceholderAPI.setPlaceholders(player, greetingbroadcast);

        // make the greetingbroadcast work as a real broadcast
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                onlinePlayer.sendMessage(revisedgreetingbroadcast);
            }
        }

        // disable the joining message
        event.setJoinMessage(null);
    }


    @EventHandler
    public void onPlayerJoinSound(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        String soundvolume = config.getString("sound-volume");
        String soundpitch = config.getString("sound-pitch");
        String joinsound = config.getString("join-sound");

        int volume = Integer.valueOf(soundvolume);
        int pitch = Integer.valueOf(soundpitch);
if (config.getBoolean("JoinSound")) {
    player.playSound(player.getLocation(), Sound.valueOf(joinsound), volume, pitch);
}
    }

    @EventHandler
public void onPlayerJoinHologram (PlayerJoinEvent event){

        Player player = event.getPlayer();

        String Jointitle = hex(config.getString("Greeting-Title"));

        String JoinSubTitle = hex(config.getString("Greeting-SubTitle"));

        String fadeinn = (config.getString("FadeIn"));
        int FadeIn = Integer.valueOf(fadeinn);

        String stayy = (config.getString("Stay"));
        int Stay = Integer.valueOf(stayy);

        String fadeoutt = (config.getString("FadeOut"));
        int FadeOut = Integer.valueOf(fadeoutt);

        if (config.getBoolean("JoinTitle")){
            player.sendTitle(Jointitle, JoinSubTitle, FadeIn, Stay, FadeOut);

        }


    }

    @EventHandler
    public void onJoinParticles(PlayerJoinEvent e){

        Player player = e.getPlayer();

        player.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0,5,0),45);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        //get the player
        Player player = event.getPlayer();

        //creating a string for leaving message
        String leaving = hex(config.getString("leaving-message"));

        //resolving the leaving message to support placeholderAPI
        String resolvedleaving = PlaceholderAPI.setPlaceholders(player, leaving);

        //sending the leaving message string we made earlier
        Bukkit.broadcastMessage(resolvedleaving);


        // disable the leaving minecraft message
        event.setQuitMessage(null);
    }

}
