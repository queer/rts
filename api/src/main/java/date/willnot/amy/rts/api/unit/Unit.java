package date.willnot.amy.rts.api.unit;

import date.willnot.amy.rts.api.pathing.Pathfinder;
import date.willnot.amy.rts.api.util.Vec2;
import date.willnot.amy.rts.api.team.TeamColor;

/**
 * Description of what, exactly, a unit is/can do.
 *
 * @author amy
 * @since 5/1/16.
 */
public interface Unit {
    /**
     * The type of this unit.
     *
     * @return This unit's type
     */
    UnitType getType();

    /**
     * The color of this unit's team. Color is used to inform a unit about
     * whether it has to be willing to target another unit, whether the unit is
     * controlled by the computer or one of the player AIs, etc.
     *
     * @return This unit's team color
     */
    TeamColor getTeamColor();

    /**
     * TODO: ?
     * @param color Team color to set
     */
    void setTeamColor(TeamColor color);

    /**
     * The position of this unit on the Cartesian plane. Coordinates must
     * remain within whatever the game specifies as the "board."
     *
     * @return This unit's position
     */
    Vec2 getPosition();

    /**
     * Causes this unit to move to the given position. It may not always be
     * possible for a unit to move in a straight line, so implementations of
     * this method must deal with pathfinding themselves.
     * <p />
     * TODO: Make the AI-plugins provide pathfinding implementations?
     *
     * @param position The position this unit needs to move towards
     */
    void moveToPosition(Vec2 position);

    /**
     * Instructs this unit to target the given unit. Targeting a unit means
     * that this unit needs to move as necessary in order to attack, and set up
     * any other conditions required for it to be able to attack.
     *
     * @param unit The unit for this unit to target
     * @return <tt>true</tt> if this unit can target the specified unit,
     *         <tt>false</tt> otherwise
     */
    boolean target(Unit unit);

    /**
     * Instructs this unit to target the nearest enemy unit.
     *
     * @see {@link #target(Unit)}
     *
     * @return <tt>true</tt> if a unit can be targeted nearby, <tt>false</tt>
     *         otherwise
     */
    boolean targetNearest();

    /**
     * Instructs this unit to attack the given unit. Attack implementations
     * depend on how the game implementation decides to allow attacks.
     *
     * @param unit The unit this unit is to attack
     * @return <tt>true</tt> if the attack went through, <tt>false</tt>
     *         otherwise
     */
    boolean attack(Unit unit);

    /**
     * The pathfinding implementation for this unit. May not return null.
     *
     * @return The pathfinding implementation for this unit.
     */
    Pathfinder getPathfinder();
}
