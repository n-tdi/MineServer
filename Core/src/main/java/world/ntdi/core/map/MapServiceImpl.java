package world.ntdi.core.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import world.ntdi.api.region.Cuboid;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MapServiceImpl implements MapService{
    private final HashSet<BlockPlaceholder> m_blocks = new HashSet<>();
    private final Cuboid m_cuboid = new Cuboid(Objects.requireNonNull(Bukkit.getWorld("world")),
            -9, 136, -9, 9, 78, -26);

    @Override
    public Location getSpawn() {
        return new Location(Bukkit.getWorld("world"), 0.5, 135, 0.5, -180, 0);
    }

    @Override
    public void teleportAllPlayersToSpawn() {
        final Location spawn = getSpawn();
        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(spawn));
    }

    @Override
    public Cuboid getMapRegion() {
        return m_cuboid;
    }

    @Override
    public void snapshotMap() {
        m_blocks.clear();
        final List<Block> blocks = m_cuboid.getBlocks();
        blocks.stream()
                .map(p_block -> new BlockPlaceholder(p_block.getType(), p_block.getBlockData(), p_block.getLocation()))
                .forEach(m_blocks::add);
    }

    @Override
    public void restoreMap() {
        final World world = Objects.requireNonNull(Bukkit.getWorld("world"));

        world.getEntities().stream().filter(p_entity -> p_entity instanceof Item).forEach(Entity::remove);

        for (BlockPlaceholder blockPlaceholder : m_blocks) {
            final Block currentBlock = world.getBlockAt(blockPlaceholder.getLocation());

            currentBlock.setType(blockPlaceholder.getMaterial());
            currentBlock.setBlockData(blockPlaceholder.getBlockData());
        }
    }


    @AllArgsConstructor
    @Getter
    private static class BlockPlaceholder {
        private final Material m_material;
        private final BlockData m_blockData;
        private final Location m_location;
    }
}
