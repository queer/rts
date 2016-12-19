package date.willnoy.amy.rts.team;

import date.willnot.amy.rts.api.team.Team;
import date.willnot.amy.rts.api.team.TeamColor;
import date.willnot.amy.rts.api.unit.Unit;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amy
 * @since 5/1/16.
 */
@Data
@RequiredArgsConstructor
public abstract class AbstractTeam implements Team {
    private final String name;
    private final TeamColor color;
    private final List<Unit> units = new ArrayList<>();
}
