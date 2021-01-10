package org.m410.garden.zone.transactional.fixtures;

import java.util.Arrays;
import java.util.List;

/**
 * @author m410
 */
public final class MyServiceImpl implements MyService {
    @Override
    public List<String> list() {
        return Arrays.asList("a", "b", "c");
    }
}
