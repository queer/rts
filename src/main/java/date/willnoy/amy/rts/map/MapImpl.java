package date.willnoy.amy.rts.map;

import date.willnot.amy.rts.api.map.Map;
import lombok.Getter;

/**
 * @author amy
 * @since 12/3/16.
 */
public class MapImpl implements Map {
    @Getter
    private final long[][] tiles;
    
    public MapImpl(final int windowWidth, final int windowHeight) {
        tiles = new long[windowWidth][windowHeight];
    }
}
