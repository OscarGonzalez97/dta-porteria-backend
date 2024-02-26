package com.roshka.dtaporteria.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties("dtaconfig")
public class DtaConfig {

    @Value("${dtaconfig.data.source.url}")
    private String dataSourceUrl;

    @Value("${dtaconfig.data.source.username}")
    private String dataSourceUsername;

    @Value("${dtaconfig.data.source.password}")
    private String dataSourcePassword;

    @Value("${dtaconfig.data.source.driver.class.name}")
    private String dataSourceDriverClassName;

    @Value("${dtaconfig.member.amount.month.arrears.claim}")
    private int memberAmountMonthArrearsClaim;

    @Value("${dtaconfig.member.amount.month.arrears.exclude}")
    private int memberAmountMonthArrearsExclude;

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceUsername() {
        return dataSourceUsername;
    }

    public void setDataSourceUsername(String dataSourceUsername) {
        this.dataSourceUsername = dataSourceUsername;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getDataSourceDriverClassName() {
        return dataSourceDriverClassName;
    }

    public void setDataSourceDriverClassName(String dataSourceDriverClassName) {
        this.dataSourceDriverClassName = dataSourceDriverClassName;
    }

    public int getMemberAmountMonthArrearsClaim() {
        return memberAmountMonthArrearsClaim;
    }

    public void setMemberAmountMonthArrearsClaim(int memberAmountMonthArrearsClaim) {
        this.memberAmountMonthArrearsClaim = memberAmountMonthArrearsClaim;
    }

    public int getMemberAmountMonthArrearsExclude() {
        return memberAmountMonthArrearsExclude;
    }

    public void setMemberAmountMonthArrearsExclude(int memberAmountMonthArrearsExclude) {
        this.memberAmountMonthArrearsExclude = memberAmountMonthArrearsExclude;
    }
}
