package com.gmail.andrewandy.ascendancy.serverplugin.test.util;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.advancement.Advancement;
import org.spongepowered.api.advancement.AdvancementProgress;
import org.spongepowered.api.advancement.AdvancementTree;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.SkinPart;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.sound.SoundCategory;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.record.RecordType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.CooldownTracker;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatVisibility;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.text.translation.Translation;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.util.RelativePositions;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MockPlayer implements Player {

    private final UUID uniqueID = UUID.randomUUID();

    @Override
    public Optional<Container> getOpenInventory() {
        return Optional.empty();
    }

    @Override
    public Optional<Container> openInventory(final Inventory inventory) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Optional<Container> openInventory(final Inventory inventory, final Text displayName) {
        return Optional.empty();
    }

    @Override
    public boolean closeInventory() throws IllegalArgumentException {
        return false;
    }

    @Override
    public int getViewDistance() {
        return 0;
    }

    @Override
    public ChatVisibility getChatVisibility() {
        return null;
    }

    @Override
    public boolean isChatColorsEnabled() {
        return false;
    }

    @Override
    public MessageChannelEvent.Chat simulateChat(final Text message, final Cause cause) {
        return null;
    }

    @Override
    public Set<SkinPart> getDisplayedSkinParts() {
        return null;
    }

    @Override
    public PlayerConnection getConnection() {
        return null;
    }

    @Override
    public void sendResourcePack(final ResourcePack pack) {

    }

    @Override
    public TabList getTabList() {
        return null;
    }

    @Override
    public void kick() {

    }

    @Override
    public void kick(final Text reason) {

    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public void setScoreboard(final Scoreboard scoreboard) {

    }

    @Override
    public boolean isSleepingIgnored() {
        return false;
    }

    @Override
    public void setSleepingIgnored(final boolean sleepingIgnored) {

    }

    @Override
    public Inventory getEnderChestInventory() {
        return null;
    }

    @Override
    public boolean respawnPlayer() {
        return false;
    }

    @Override
    public Optional<Entity> getSpectatorTarget() {
        return Optional.empty();
    }

    @Override
    public void setSpectatorTarget(@Nullable final Entity entity) {

    }

    @Override
    public Optional<WorldBorder> getWorldBorder() {
        return Optional.empty();
    }

    @Override
    public void setWorldBorder(@Nullable final WorldBorder border, final Cause cause) {

    }

    @Override
    public CooldownTracker getCooldownTracker() {
        return null;
    }

    @Override
    public AdvancementProgress getProgress(final Advancement advancement) {
        return null;
    }

    @Override
    public Collection<AdvancementTree> getUnlockedAdvancementTrees() {
        return null;
    }

    @Override
    public void spawnParticles(final ParticleEffect particleEffect, final Vector3d position) {

    }

    @Override
    public void spawnParticles(final ParticleEffect particleEffect, final Vector3d position, final int radius) {

    }

    @Override
    public void playSound(final SoundType sound, final SoundCategory category, final Vector3d position, final double volume) {

    }

    @Override
    public void playSound(
            final SoundType sound,
            final SoundCategory category,
            final Vector3d position,
            final double volume,
            final double pitch
    ) {

    }

    @Override
    public void playSound(
            final SoundType sound,
            final SoundCategory category,
            final Vector3d position,
            final double volume,
            final double pitch,
            final double minVolume
    ) {

    }

    @Override
    public void stopSounds() {

    }

    @Override
    public void stopSounds(final SoundType sound) {

    }

    @Override
    public void stopSounds(final SoundCategory category) {

    }

    @Override
    public void stopSounds(final SoundType sound, final SoundCategory category) {

    }

    @Override
    public void playRecord(final Vector3i position, final RecordType recordType) {

    }

    @Override
    public void stopRecord(final Vector3i position) {

    }

    @Override
    public void sendTitle(final Title title) {

    }

    @Override
    public void sendBookView(final BookView bookView) {

    }

    @Override
    public void sendBlockChange(final int x, final int y, final int z, final BlockState state) {

    }

    @Override
    public void resetBlockChange(final int x, final int y, final int z) {

    }

    @Override
    public Optional<ItemStack> getItemInHand(final HandType handType) {
        return Optional.empty();
    }

    @Override
    public void setItemInHand(final HandType hand, @Nullable final ItemStack itemInHand) {

    }

    @Override
    public Vector3d getHeadRotation() {
        return null;
    }

    @Override
    public void setHeadRotation(final Vector3d rotation) {

    }

    @Override
    public EntityType getType() {
        return null;
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return null;
    }

    @Override
    public Random getRandom() {
        return null;
    }

    @Override
    public boolean setLocation(final Location<World> location) {
        return false;
    }

    @Override
    public Vector3d getRotation() {
        return null;
    }

    @Override
    public void setRotation(final Vector3d rotation) {

    }

    @Override
    public boolean setLocationAndRotation(final Location<World> location, final Vector3d rotation) {
        return false;
    }

    @Override
    public boolean setLocationAndRotation(
            final Location<World> location,
            final Vector3d rotation,
            final EnumSet<RelativePositions> relativePositions
    ) {
        return false;
    }

    @Override
    public Vector3d getScale() {
        return null;
    }

    @Override
    public void setScale(final Vector3d scale) {

    }

    @Override
    public Transform<World> getTransform() {
        return null;
    }

    @Override
    public boolean setTransform(final Transform<World> transform) {
        return false;
    }

    @Override
    public boolean transferToWorld(final World world, final Vector3d position) {
        return false;
    }

    @Override
    public Optional<AABB> getBoundingBox() {
        return Optional.empty();
    }

    @Override
    public List<Entity> getPassengers() {
        return null;
    }

    @Override
    public boolean hasPassenger(final Entity entity) {
        return false;
    }

    @Override
    public boolean addPassenger(final Entity entity) {
        return false;
    }

    @Override
    public void removePassenger(final Entity entity) {

    }

    @Override
    public void clearPassengers() {

    }

    @Override
    public Optional<Entity> getVehicle() {
        return Optional.empty();
    }

    @Override
    public boolean setVehicle(@Nullable final Entity entity) {
        return false;
    }

    @Override
    public Entity getBaseVehicle() {
        return null;
    }

    @Override
    public boolean isOnGround() {
        return false;
    }

    @Override
    public boolean isRemoved() {
        return false;
    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public void remove() {

    }

    @Override
    public boolean damage(final double damage, final DamageSource damageSource) {
        return false;
    }

    @Override
    public Optional<UUID> getCreator() {
        return Optional.empty();
    }

    @Override
    public Optional<UUID> getNotifier() {
        return Optional.empty();
    }

    @Override
    public void setCreator(@Nullable final UUID uuid) {

    }

    @Override
    public void setNotifier(@Nullable final UUID uuid) {

    }

    @Override
    public EntityArchetype createArchetype() {
        return null;
    }

    @Override
    public GameProfile getProfile() {
        return null;
    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.empty();
    }

    @Override
    public Vector3d getPosition() {
        return null;
    }

    @Override
    public Optional<UUID> getWorldUniqueId() {
        return Optional.empty();
    }

    @Override
    public boolean setLocation(final Vector3d position, final UUID world) {
        return false;
    }

    @Override
    public boolean validateRawData(final DataView container) {
        return false;
    }

    @Override
    public void setRawData(final DataView container) throws InvalidDataException {

    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }

    @Override
    public <T extends Property<?, ?>> Optional<T> getProperty(final Class<T> propertyClass) {
        return Optional.empty();
    }

    @Override
    public Collection<Property<?, ?>> getApplicableProperties() {
        return null;
    }

    @Override
    public <T extends DataManipulator<?, ?>> Optional<T> get(final Class<T> containerClass) {
        return Optional.empty();
    }

    @Override
    public <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(final Class<T> containerClass) {
        return Optional.empty();
    }

    @Override
    public boolean supports(final Class<? extends DataManipulator<?, ?>> holderClass) {
        return false;
    }

    @Override
    public <E> DataTransactionResult offer(final Key<? extends BaseValue<E>> key, final E value) {
        return null;
    }

    @Override
    public DataTransactionResult offer(final DataManipulator<?, ?> valueContainer, final MergeFunction function) {
        return null;
    }

    @Override
    public DataTransactionResult remove(final Class<? extends DataManipulator<?, ?>> containerClass) {
        return null;
    }

    @Override
    public DataTransactionResult remove(final Key<?> key) {
        return null;
    }

    @Override
    public DataTransactionResult undo(final DataTransactionResult result) {
        return null;
    }

    @Override
    public DataTransactionResult copyFrom(final DataHolder that, final MergeFunction function) {
        return null;
    }

    @Override
    public Collection<DataManipulator<?, ?>> getContainers() {
        return null;
    }

    @Override
    public <E> Optional<E> get(final Key<? extends BaseValue<E>> key) {
        return Optional.empty();
    }

    @Override
    public <E, V extends BaseValue<E>> Optional<V> getValue(final Key<V> key) {
        return Optional.empty();
    }

    @Override
    public boolean supports(final Key<?> key) {
        return false;
    }

    @Override
    public DataHolder copy() {
        return null;
    }

    @Override
    public Set<Key<?>> getKeys() {
        return null;
    }

    @Override
    public Set<ImmutableValue<?>> getValues() {
        return null;
    }

    @Override
    public boolean canEquip(final EquipmentType type) {
        return false;
    }

    @Override
    public boolean canEquip(final EquipmentType type, @Nullable final ItemStack equipment) {
        return false;
    }

    @Override
    public Optional<ItemStack> getEquipped(final EquipmentType type) {
        return Optional.empty();
    }

    @Override
    public boolean equip(final EquipmentType type, @Nullable final ItemStack equipment) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public <T extends Projectile> Optional<T> launchProjectile(final Class<T> projectileClass) {
        return Optional.empty();
    }

    @Override
    public <T extends Projectile> Optional<T> launchProjectile(final Class<T> projectileClass, final Vector3d velocity) {
        return Optional.empty();
    }

    @Override
    public CarriedInventory<? extends Carrier> getInventory() {
        return null;
    }

    @Override
    public Text getTeamRepresentation() {
        return null;
    }

    @Override
    public Optional<CommandSource> getCommandSource() {
        return Optional.empty();
    }

    @Override
    public SubjectCollection getContainingCollection() {
        return null;
    }

    @Override
    public SubjectReference asSubjectReference() {
        return null;
    }

    @Override
    public boolean isSubjectDataPersisted() {
        return false;
    }

    @Override
    public SubjectData getSubjectData() {
        return null;
    }

    @Override
    public SubjectData getTransientSubjectData() {
        return null;
    }

    @Override
    public Tristate getPermissionValue(final Set<Context> contexts, final String permission) {
        return null;
    }

    @Override
    public boolean isChildOf(final Set<Context> contexts, final SubjectReference parent) {
        return false;
    }

    @Override
    public List<SubjectReference> getParents(final Set<Context> contexts) {
        return null;
    }

    @Override
    public Optional<String> getOption(final Set<Context> contexts, final String key) {
        return Optional.empty();
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public Set<Context> getActiveContexts() {
        return null;
    }

    @Override
    public void sendMessage(final ChatType type, final Text message) {

    }

    @Override
    public void sendMessage(final Text message) {

    }

    @Override
    public MessageChannel getMessageChannel() {
        return null;
    }

    @Override
    public void setMessageChannel(final MessageChannel channel) {

    }

    @Override
    public Translation getTranslation() {
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return null;
    }

    @Override
    public Location<World> getLocation() {
        return null;
    }

}
