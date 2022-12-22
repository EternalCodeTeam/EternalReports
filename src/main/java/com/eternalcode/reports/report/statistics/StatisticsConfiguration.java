package com.eternalcode.reports.report.statistics;

import com.eternalcode.reports.configuration.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public class StatisticsConfiguration implements ReloadableConfig {

    public int reports = 0;

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data" + File.separator + "statistics.dat");
    }
}
