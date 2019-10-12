/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.listen.push;

import com.hx.nettycommon.listen.parent.Listener;
import com.hx.nettycommon.listen.parent.Listenerable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author chengxy
 * 2019/10/2
 */
@Service
public class PushService<T> implements Listenerable {

    //集合的泛型参数为listener接口

    private List<Listener<T>> list = new ArrayList<>();


    private T message;

    //public void setList(List<Listener<T>> list) {
    //    this.list = list;
    //}

    public void setMessage(T message) {
        this.message = message;
    }


    public PushService(){
        list  = new ArrayList<Listener<T>>();
    }

    @Override
    public void registerListener(Listener o) {
        list.add(o);
    }

    @Override
    public void removeListener(Listener o) {
        if (!list.isEmpty()){
            list.remove(o);
        }
    }

    @Override
    public void notifyListener() {
        for (int i = 0;i<list.size();i++){
            Listener<T> listener = list.get(i);
            listener.update(message);
        }
    }

    public void sendMessage(T s){
        this.message = s;
        notifyListener();
    }
}
