package com.demo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * BouncyCastle算法提供者
 * 解决no such provider: BC的问题
 */
public class GMBaseUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
