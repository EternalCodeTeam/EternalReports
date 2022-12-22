package com.eternalcode.reports.report.statistics;

import com.eternalcode.reports.configuration.ConfigurationManager;

public class StatisticsService {

    private final ConfigurationManager configurationManager;
    private final StatisticsConfiguration statisticsConfiguration;

    public StatisticsService(ConfigurationManager configurationManager, StatisticsConfiguration statisticsConfiguration) {
        this.configurationManager = configurationManager;
        this.statisticsConfiguration = statisticsConfiguration;
    }

    public void addReport() {
        this.statisticsConfiguration.reports++;
        this.configurationManager.save(this.statisticsConfiguration);
    }

    public int getReports() {
        return this.statisticsConfiguration.reports;
    }
}
