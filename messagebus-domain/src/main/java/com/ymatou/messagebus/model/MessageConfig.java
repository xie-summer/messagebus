/**
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *
 * All rights reserved.
 */
package com.ymatou.messagebus.model;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * 消息配置
 * 
 * @author wangxudong 2016年8月2日 下午5:03:33
 *
 */
@Embedded
public class MessageConfig {

    @Property("Code")
    private String code;

    @Property("Enable")
    private Boolean enable;

    @Embedded("CallbackCfgList")
    private List<CallbackConfig> callbackCfgList;

    @Property("ConsumeCfg")
    private Object consumeCfg;

    @Property("ExchangeCfg")
    private Object exchangeCfg;

    @Property("QueueCfg")
    private Object queueCfg;

    @Property("PublishCfg")
    private Object publishCfg;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the callbackCfgList
     */
    public List<CallbackConfig> getCallbackCfgList() {
        return callbackCfgList;
    }

    /**
     * @param callbackCfgList the callbackCfgList to set
     */
    public void setCallbackCfgList(List<CallbackConfig> callbackCfgList) {
        this.callbackCfgList = callbackCfgList;
    }

    /**
     * @return the consumeCfg
     */
    public Object getConsumeCfg() {
        return consumeCfg;
    }

    /**
     * @param consumeCfg the consumeCfg to set
     */
    public void setConsumeCfg(Object consumeCfg) {
        this.consumeCfg = consumeCfg;
    }

    /**
     * @return the exchangeCfg
     */
    public Object getExchangeCfg() {
        return exchangeCfg;
    }

    /**
     * @param exchangeCfg the exchangeCfg to set
     */
    public void setExchangeCfg(Object exchangeCfg) {
        this.exchangeCfg = exchangeCfg;
    }

    /**
     * @return the queueCfg
     */
    public Object getQueueCfg() {
        return queueCfg;
    }

    /**
     * @param queueCfg the queueCfg to set
     */
    public void setQueueCfg(Object queueCfg) {
        this.queueCfg = queueCfg;
    }

    /**
     * @return the publishCfg
     */
    public Object getPublishCfg() {
        return publishCfg;
    }

    /**
     * @param publishCfg the publishCfg to set
     */
    public void setPublishCfg(Object publishCfg) {
        this.publishCfg = publishCfg;
    }
}