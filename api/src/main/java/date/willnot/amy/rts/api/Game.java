package date.willnot.amy.rts.api;

import date.willnot.amy.rts.api.map.Map;
import date.willnot.amy.rts.api.team.Team;

import java.util.Set;

/**
 * @author amy
 * @since 5/1/16.
 */
public interface Game {
    Map getMap();
    
    Set<Team> getTeams();
}
