/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorialspoint.test;

import com.tutorialspoint.stateless.LibrarySessionBeanRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Lawrence
 */
public class EJBTester {
    BufferedReader br = null;
    Properties props;
    Context ctx;
    
    {
        props = new Properties();
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
        // username
        jndiProps.put(Context.SECURITY_PRINCIPAL, "testuser");
        // password
        jndiProps.put(Context.SECURITY_CREDENTIALS, "test");
        // This is an important property to set if you want to do EJB invocations via the remote-naming project
        jndiProps.put("jboss.naming.client.ejb.context", true);
        
     /*
        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch( IOException e) {
            e.printStackTrace();
        }
     */   
        try {
            //ctx = new InitialContext(props);
            ctx = new InitialContext(jndiProps);
        } catch( NamingException e){
            e.printStackTrace();
        }
        
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static void main(String[] args) {
        EJBTester ejbTester = new EJBTester();
        
        ejbTester.testStateless();
    }
    
    private void showGUI(){
      System.out.println("**********************");
      System.out.println("Welcome to Book Store");
      System.out.println("**********************");
      System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
   }
    
    private void testStateless() {
        final String appName = "";
        final String moduleName = "EJBComponent";
        final String sessionBeanName = "LibrarySessionBean";
        final String viewClassName = LibrarySessionBeanRemote.class.getName();
        String tmp = appName+"/"+moduleName+"/"+sessionBeanName+"!"+viewClassName;
        
        System.out.println("test Stateless");
        try {
            LibrarySessionBeanRemote libBean = 
                (LibrarySessionBeanRemote)ctx.lookup(tmp);
            String bookName;
            System.out.println("going into loop");
            while(true) {
                showGUI();
                String choice = br.readLine();
                if (choice.equals("1")) {
                    System.out.print("Enter book name: ");
                    bookName = br.readLine();
                    libBean.addBook(bookName);
                } else if (choice.equals("2")) {
                    System.out.println("Exiting");
                    break;
                } else {
                    System.out.println("please enter 1 or 2");
                }
            }
            List<String> books = libBean.getBooks();
            System.out.println("Books entered so far...");
            for (String book : books) {
                System.out.println(book);
            }

            LibrarySessionBeanRemote libBean1 = 
                    (LibrarySessionBeanRemote)ctx.lookup(tmp);
            List<String> books2 = libBean1.getBooks();
            System.out.println("***Using second lookup to get library stateless object***");
            for (String book : books2) {
                System.out.println(book);
            }

        } catch( Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
