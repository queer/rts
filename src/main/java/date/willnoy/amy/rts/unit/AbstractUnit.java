package date.willnoy.amy.rts.unit;

import date.willnot.amy.rts.api.team.TeamColor;
import date.willnot.amy.rts.api.unit.Unit;
import date.willnot.amy.rts.api.unit.UnitType;
import date.willnot.amy.rts.api.util.Vec2;
import lombok.Getter;
import lombok.Setter;

/**
 * @author amy
 * @since 5/1/16.
 */
abstract class AbstractUnit implements Unit {
    @Getter
    private final UnitType type;

    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private TeamColor teamColor;

    @Getter
    private final Vec2 position;

    AbstractUnit(final UnitType type, final TeamColor teamColor, final Vec2 position) {
        this.type = type;
        this.teamColor = teamColor;
        this.position = position;
    }

    @Override
    public void moveToPosition(final Vec2 position) {
        // TODO: Pathfinding
    }

    @Override
    public boolean attack(final Unit unit) {
        // TODO: Attack implementations
        return false;
    }
}
