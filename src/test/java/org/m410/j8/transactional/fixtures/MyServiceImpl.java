package org.m410.j8.transactional.fixtures;

import java.util.Arrays;
import java.util.List;

/**
 * @author m410
 */
public class MyServiceImpl implements MyService {
    @Override
    public List<String> list() {
        return Arrays.asList("a", "b", "c");
    }
}
