// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        AddUsersServlet.java  (04/10/14)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package net.videmantay.server.shiro.web.user.dev;

import net.videmantay.server.shiro.gae.GaeUser;
import net.videmantay.server.shiro.gae.GaeUserDAO;
import net.videmantay.server.shiro.web.BaseServlet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class AddUsersServlet extends BaseServlet {
    static final Logger LOG = LoggerFactory.getLogger(AddUsersServlet.class);

    private static final int DEFAULT_COUNT = 100;
    private static final String DEFAULT_DOMAIN = "dummy.com";

   
    public AddUsersServlet(GaeUserDAO daoProvider) {
        super(daoProvider);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = intParameter("count", request, DEFAULT_COUNT);
        String domain = stringParameter("domain", request, DEFAULT_DOMAIN);

        GaeUserDAO dao = daoProvider;
        for (int i = 0; i < count; i++) {
            String nm = "user_"+i+"@" + domain;
            GaeUser user = dao.get(nm);
            if (user == null) {
                user =  new GaeUser(nm, "friend", ImmutableSet.<String>of("user"), Sets.<String>newHashSet());
                user.register();
                dao.saveUser(user, true);
            }
        }
    }


}