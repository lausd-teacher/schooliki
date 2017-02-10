// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        GoogleGAERealm.java  (16-Oct-2012)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
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


package net.videmantay.server.shiro.googlegae;

import net.videmantay.server.GoogleUtils;
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.util.DB;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.util.logging.Logger;


public class GoogleGAERealm extends AuthorizingRealm {
    static final Logger LOG = Logger.getLogger(GoogleGAERealm.class.getName());

    public GoogleGAERealm() {
        super(new MemcacheManager(), new GoogleGAECredentialsMatcher());
        setAuthenticationTokenClass(GoogleGAEAuthenticationToken.class);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token != null && token instanceof GoogleGAEAuthenticationToken) {
            GoogleGAEAuthenticationToken authToken = (GoogleGAEAuthenticationToken)token;
            return new GoogleGAEAuthenticationInfo((String)authToken.getPrincipal());
        } else {
            return null;
        }
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Preconditions.checkNotNull(principals, "You can't have a null collection of principals");
        String userName = (String) getAvailablePrincipal(principals);
        if (userName == null) {
            throw new NullPointerException("Can't find a principal in the collection");
        }
        LOG.fine("Finding authorization info for " + userName + " in DB");
        AppUser user = DB.db().load().type(AppUser.class).filter("email", userName).first().now();
        if (user == null || userIsNotQualified(user)) {
            return null;
        }
        LOG.fine("Found " + userName + " in DB");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.roles);
        info.setStringPermissions(user.permissions);
        
        //must be authorized to sign in do the check here
        if(!user.isFirstLogin()){
        	info.getStringPermissions().add("login:*");
        }
        
        //must have given oauth2 perms check for cred here
        User userCheck =   UserServiceFactory.getUserService().getCurrentUser();
        try {
			Credential cred = GoogleUtils.cred(userCheck.getUserId());
			if(cred != null){
				LOG.info(userCheck.getEmail() + " has creds");
				info.getStringPermissions().add("oauth2:*");
			}
		} catch (IOException e) {
			return info;
			
		}
        
        return info;
    }
    
   
    private static boolean userIsNotQualified(AppUser user) {
        return !user.isActive();
    }
}