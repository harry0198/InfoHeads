package me.harry0198.infoheads.core.event.handlers;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlayerJoinHandler {

    private final EventDispatcher eventDispatcher;

    @Inject
    public PlayerJoinHandler(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void onJoinEvent(OnlinePlayer player) {
        if (player.getUsername().equals("Harolds") || player.getUsername().equals("Lorenzo0111")) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, "This server is running your plugin, InfoHeads!"));
        }
    }
}
