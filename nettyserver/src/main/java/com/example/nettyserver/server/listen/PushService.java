/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.listen;

import com.example.demo.listen.parent.Listener;
import com.example.demo.listen.parent.Listenerable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author chengxy
 * 2019/10/2
 */
@Service
public class PushService implements Listenerable {




    //集合的泛型参数为listener接口

    private List<Listener> list;

    private String message;

    public void setList(List<Listener> list) {
        this.list = list;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PushService(){
        list  = new ArrayList<Listener>();
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
            Listener listener = list.get(i);
            listener.update(message);
        }
    }

    public void sendMessage(String s){
        this.message = s;
        notifyListener();
    }
}
