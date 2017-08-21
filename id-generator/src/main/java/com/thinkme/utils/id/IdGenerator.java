package com.thinkme.utils.id;

/**
 * Id 生成接口.
 *
 * @author gaohongtao
 */
public interface IdGenerator {

    /**
     * 生成Id.
     *
     * @return 返回生成的Id, 返回值应为long值
     */
    long generateId();
}
