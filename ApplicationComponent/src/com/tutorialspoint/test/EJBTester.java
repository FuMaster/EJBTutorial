/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorialspoint.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Lawrence
 */
public class EJBTester {
    BufferedReader br = null;
    Properties props;
    InitialContext ctx;
    
    public EJBTester() {
        props = new Properties();
        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch( IOException e) {
            e.printStackTrace();
        }
        
        try {
            ctx = new InitialContext(props);
        } catch( NamingException e){
            e.printStackTrace();
        }
        
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static void main(String[] args) {
        EJBTester ejbTester = new EJBTester();
        
        ejbTester.testStateless();
    }
    
    private void testStateless() {
        
    }
}
