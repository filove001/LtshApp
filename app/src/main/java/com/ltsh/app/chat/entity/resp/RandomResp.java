package com.ltsh.app.chat.entity.resp;

/**
 * Created by Random on 2018/1/25.
 */

public class RandomResp extends BaseResp {
    /**
     * 随机数key
     */
    private String randomKey;
    /**
     * 随机数value
     */
    private String randomValue;

    public RandomResp(String randomKey, String randomValue) {
        this.randomKey = randomKey;
        this.randomValue = randomValue;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }
}
