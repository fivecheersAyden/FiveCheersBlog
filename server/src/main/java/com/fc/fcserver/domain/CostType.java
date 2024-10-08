package com.fc.fcserver.domain;

public enum CostType {
    IN("入账"),
    EAT("吃喝"),
    FUN("玩乐"),
    HOUSE("住宿"),
    TRAVEL("旅行"),
    LIVING("生活花销"),
    PRICED("大宗商品"),
    OTHER("其他");

    // 每个枚举常量的描述
    private final String description;

    /**
     * 枚举类型的构造函数
     *
     * @param description 描述枚举常量的字符串
     */
    CostType(String description) {
        this.description = description;
    }

    /**
     * 获取枚举常量的描述
     *
     * @return 描述字符串
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
