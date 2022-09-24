package com.eternalcode.eternalreports.data;

import com.eternalcode.eternalreports.configuration.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public class Statistics implements ReloadableConfig {
    public int reports = 0;

    public int getReports() {
        return this.reports;
    }

    public void addReport() {
        this.reports += 1;
    }
    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "data/statistics.dat");
    }
}
