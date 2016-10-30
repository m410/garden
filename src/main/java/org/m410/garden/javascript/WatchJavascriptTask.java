package org.m410.garden.javascript;

import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;

/**
 * @author Michael Fortin
 */
public final class WatchJavascriptTask implements Task {
    @Override
    public String getName() {
        return "javascript-watcher";
    }

    @Override
    public String getDescription() {
        return "Watches files for javascript dependency changes and updates output";
    }

    @Override
    public void execute(BuildContext buildContext) throws Exception {
        // launch file watcher thread for each watched file
        // on change rebuild to output directory
    }
}
