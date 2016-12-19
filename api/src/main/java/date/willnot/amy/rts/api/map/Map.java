package date.willnot.amy.rts.api.map;

/**
 * Generic descriptor of the map the game is played on. A map is a simple 2D
 * array of longs, with information such as "colour" and "danger" encoded in
 * the bytes of the <tt>long</tt> corresponding to the array indices.
 *
 * @author amy
 * @since 5/6/16.
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "unused"})
public interface Map {
    /**
     * The array that contains the map information. Information for a given
     * position on the map is encoded into the respective <tt>long</tt>, with
     * the coordinates of the tile being <tt>map[x][y]</tt>. The format of each
     * long is as follows:<br />
     * <pre>
     * <code>
     * 0x
     * 00 - byte 7 - Unused
     * 00 - byte 6 - Unused
     * 00 - byte 5 - Movement penalty, on [0, FF]; TODO: Consider merging into danger byte, sharing upper/lower nybbles?
     * 00 - byte 4 - Danger level, on [0, FF]; TODO: Consider using upper and lower nybbles for different things?
     * 00 - byte 3 - Alpha level, on [0, FF]
     * 00 - byte 2 - Red level, on [0, FF]
     * 00 - byte 1 - Green level, on [0, FF]
     * 00 - byte 0 - Blue level, on [0, FF]
     * </code>
     * </pre>
     * <p />
     * The lower four bytes are used solely to encode the colour of the tile,
     * whereas the upper four bytes are used to encode data such as danger
     * level, movement penalty, and more.
     *
     * @return The 2D array containing map information.
     */
    // Note: For a full 1920x1080 fullscreen map, this will require
    // approximately 126.6MiB of RAM (((1920 * 1080 * 64) / 1048576) = 126.5625 MiB).
    long[][] getTiles();

    /**
     * Gets the movement penalty for the given location. The movement penalty
     * is calculated by masking off the irrelevant bytes so that we can extract
     * byte 5.
     *
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return The movement penalty for the tile at <tt>(x, y)</tt>.
     */
    default byte getMovementPenalty(final int x, final int y) {
        return (byte) ((getTiles()[x][y] & 0x0000FF0000000000L) >> 40);
    }

    /**
     * Gets the danger for the given location. The danger is calculated by
     * masking off the irrelevant bytes so that we can extract byte 4.
     *
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return The movement penalty for the tile at <tt>(x, y)</tt>.
     */
    default byte getDanger(final int x, final int y) {
        return (byte) ((getTiles()[x][y] & 0x000000FF00000000L) >> 32);
    }

    /**
     * Gets the colour for the given location. The colour is calculated by
     * masking off the irrelevant bytes so that we can extract bytes 0-3.
     *
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return The movement penalty for the tile at <tt>(x, y)</tt>.
     */
    default int getColour(final int x, final int y) {
        return (int) (getTiles()[x][y] & 0x00000000FFFFFFFFL);
    }
}
