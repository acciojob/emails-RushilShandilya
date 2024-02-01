package com.driver;

import java.util.*;

class Object{
    private Date date;
    private String sender;
    private String message;

    Object(Date date, String sender, String message){
        this.date = date;
        this.sender = sender;
        this.message = message;
    }
    String getMessage(){
        return message;
    }
    Date getDate(){
        return date;
    }
    String getSender(){
        return sender;
    }
}

public class Gmail extends Email {

    private final int inboxCapacity; //maximum number of mails inbox can store
    LinkedHashSet<String> check = new LinkedHashSet<>();
    Deque<Object> inbox = new LinkedList<>();
    Deque<Object> trash = new LinkedList<>();

    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.

        if(!check.isEmpty() && !check.contains(message) && inbox.size()<=getInboxCapacity()) inbox.addLast(new Object(date,sender,message));
        else if(!check.isEmpty() && !check.contains(message) && inbox.size()>getInboxCapacity()){
            trash.addLast(inbox.removeFirst());
            inbox.addLast(new Object(date,sender,message));
        }else if(!check.isEmpty() && check.contains(message)) trash.addLast(new Object(date,sender,message));

        check.add(message);
        if(inbox.isEmpty()) inbox.addLast(new Object(date,sender,message));
    }
    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        int count = 0;
        if(check.contains(message)){
            for(String str : check){
                if(str.equals(message)) break;
                ++count;
            }
        }
        Deque<Object> temp = new LinkedList<>();
        int tempCount = count;
        while(tempCount!=0){
            temp.addLast(inbox.removeFirst());
            tempCount--;
        }

        tempCount=count;
        inbox.removeFirst();
        while(tempCount-- >0) inbox.addFirst(temp.removeLast());
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inbox.isEmpty()) return "";
        else return inbox.peekLast().getMessage();
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.isEmpty()) return "";
        else return inbox.peekFirst().getMessage();
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        Deque<Object> temp = new LinkedList<>();
        int count = 0;
        while(!inbox.isEmpty()){
            Object obj = inbox.removeFirst();
            Date date = obj.getDate();
            if(!start.after(date) && !end.before(date)) ++count;
            temp.addLast(obj);
        }
        while(!temp.isEmpty()) inbox.addFirst(temp.removeFirst());
        return count;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inbox.size();
    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        while(!trash.isEmpty()) trash.removeLast();
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}
