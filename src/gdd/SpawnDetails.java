package gdd;

// Removed unused random import.

/**
 * Utility class for spawning enemies in different game scenes.
 *
 * <p>The existing game already includes a {@code spawnEnemies()} method in
 * {@code Scene1}, but Stage 2 was missing an equivalent call.  To keep the
 * spawning logic decoupled from the scene logic, this class exposes a
 * spawn specification used in Stage 1.  This class encapsulates a simple
 * description of which enemy type to spawn, how many should spawn, and
 * after what delay.  Stage 2 spawning logic is handled directly in
 * Scene2 and does not rely on this class.</p>
 */
public class SpawnDetails {

    // Note: Stage 2 no longer uses this class to spawn enemies.  Random
    // spawning for the second stage is implemented directly in Scene2.

    // === spawn specification fields for Stage 1 ===
    private final String type;
    private final int count;
    private final int delay;

    /**
     * Constructs a spawn specification for Stage 1.  Instances of this
     * class were used in the original implementation to describe what kind
     * of enemy to spawn, how many of them, and the delay before the next
     * spawn wave.  The absence of this constructor previously caused
     * compilation failures in Scene1 when loading spawn definitions.
     *
     * @param type  a simple identifier of the enemy class (for example
     *              {@code "Enemy"} or {@code "ZigZagEnemy"})
     * @param count the number of enemies to spawn at once
     * @param delay the number of frames before this spawn occurs
     */
    public SpawnDetails(String type, int count, int delay) {
        this.type = type;
        this.count = count;
        this.delay = delay;
    }

    /** Returns the enemy type identifier used by Scene1's spawning code. */
    public String getType() {
        return type;
    }

    /** Returns how many enemies should spawn for this specification. */
    public int getCount() {
        return count;
    }

    /** Returns the delay (in frames) associated with this spawn specification. */
    public int getDelay() {
        return delay;
    }

    // Removed randomEnemyForScene2() because Stage 2 spawning is handled
    // directly by Scene2 to avoid dependencies on sprite classes from this
    // package.  Scene1 continues to use SpawnDetails for its spawn map.
}