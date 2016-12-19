package date.willnot.amy.rts.api.pathing;

import date.willnot.amy.rts.api.util.Vec2;

import java.util.List;

/**
 * The generic interface for pathfinding. A pathfinder has one main
 * responsibility:
 * <ul>
 *     <li>Getting a list of vectors that represent a path between two points.</li>
 * </ul>
 * <p />
 * The danger of a tile is intended to be used mainly for pathfinding, but may
 * also be used for things like determining where to try to force enemy units
 * to travel.
 *
 * @author amy
 * @since 5/5/16.
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface Pathfinder {
    /**
     * Should return a valid path between the two points. A valid path consists
     * of a set of absolute vectors creating a continuous path between the two
     * given points. Valid paths may attempt to force a unit to travel over
     * dangerous terrain, which the game may use to punish offending units as
     * it sees fit.
     *
     * @see
     *
     * @param source The starting point for the path.
     * @param destination The ending point for the path.
     * @return A list of vectors representing a continuous path between the two
     *         points.
     */
    List<Vec2> getPath(Vec2 source, Vec2 destination);
}
