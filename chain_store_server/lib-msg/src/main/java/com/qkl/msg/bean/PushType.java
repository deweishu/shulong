package com.qkl.msg.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
public class PushType extends JpaProperty {

    public static final PushType SINGLE = newInstance(PushType.class, 10,"单个推送");

    public static final PushType GROUP = newInstance(PushType.class, 20,"群推");

}
