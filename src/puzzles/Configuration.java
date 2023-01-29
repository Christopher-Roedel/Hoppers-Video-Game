package puzzles;

import java.util.Collection;

public interface Configuration {

    public Collection<Configuration> getNeighbors();

    public boolean isSolution();
}
