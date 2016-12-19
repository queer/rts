package date.willnot.amy.rts.api.team;

/**
 * Teams for units in the RTS. A unit may be on the Red team or the Blue team,
 * or may optionally be a game-controlled
 *
 * @author amy
 * @since 5/1/16.
 */
public enum TeamColor {
    /**
     * One of the player-controlled teams
     */
    RED,

    /**
     * One of the player-controlled teams
     */
    BLUE,

    /**
     * Game-controlled team. May not be touched by players.
     */
    GREY
}
