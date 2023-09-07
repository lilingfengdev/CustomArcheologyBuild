package cn.myrealm.customarcheology.managers.managers;

import cn.myrealm.customarcheology.managers.AbstractManager;
import cn.myrealm.customarcheology.mechanics.players.PlayerInChunk;
import cn.myrealm.customarcheology.mechanics.players.PlayerLookAt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rzt10
 */
public class PlayerManager extends AbstractManager {
    private static PlayerManager instance;
    private Map<Player, PlayerLookAt> playerLookAtMap;
    private Map<Player, PlayerInChunk> playerInChunkMap;

    public PlayerManager(JavaPlugin plugin) {
        super(plugin);
        instance = this;
    }

    @Override
    protected void onInit() {
        super.onInit();
        playerLookAtMap = new HashMap<>(5);
        playerInChunkMap = new HashMap<>(5);
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            playerLookAtMap.put(player,new PlayerLookAt(player));
            playerInChunkMap.put(player, new PlayerInChunk(player));
        }
    }

    public static PlayerManager getInstance() {
        return instance;
    }

    public void playerJoin(Player player) {
        playerLookAtMap.put(player,new PlayerLookAt(player));
        playerInChunkMap.put(player, new PlayerInChunk(player));
    }

    public void playerQuit(Player player) {
        if(playerLookAtMap.containsKey(player)) {
            playerLookAtMap.get(player).cancelTask();
            playerLookAtMap.remove(player);
        }
        if (playerInChunkMap.containsKey(player)) {
            playerInChunkMap.get(player).cancelTask();
            playerInChunkMap.remove(player);
        }
    }
}
