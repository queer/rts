package date.willnoy.amy.rts;

import date.willnot.amy.rts.api.Game;
import date.willnot.amy.rts.api.map.Map;
import date.willnot.amy.rts.api.team.Team;
import date.willnoy.amy.rts.map.MapImpl;
import date.willnoy.amy.rts.render.util.DisplayUtil;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author amy
 * @since 5/1/16.
 */
public enum Rts implements Game, Runnable {
    INSTANCE {
        @Override
        public void run() {
        
        }
    };
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    @Getter
    private final Set<Team> teams = new HashSet<>();
    
    @Getter
    @SuppressWarnings("NonFinalFieldInEnum")
    private Map map;
    
    Rts() {
    }
    
    public static void main(final String[] args) {
        INSTANCE.startGame();
    }
    
    private void startGame() {
        DisplayUtil.buildDisplay(WIDTH, HEIGHT);
        DisplayUtil.basicOpenGLInit();
        map = new MapImpl(WIDTH, HEIGHT);
    }
}
