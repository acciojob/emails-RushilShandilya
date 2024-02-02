package com.driver;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar = new ArrayList<>(); // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId,Integer.MAX_VALUE);
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        calendar.add(meeting);
    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        calendar.sort((a, b) -> {
            LocalTime x = a.getStartTime();
            LocalTime y = b.getStartTime();
            return x.compareTo(y);
        });
        LocalTime prevEndTime = LocalTime.parse("00:00");
        int prevEndTimeHour = prevEndTime.getHour() , prevEndTimeMinute = prevEndTime.getMinute();
        int count = 0;
        for(Meeting meeting : calendar){
            int currStartTimeHour = meeting.getStartTime().getHour() , currStartTimeMinute = meeting.getEndTime().getHour();
            int currEndTimeHour = meeting.getEndTime().getHour() , currEndTimeMinute = meeting.getEndTime().getMinute();

            if(prevEndTimeHour<=currStartTimeHour){
                if(prevEndTimeHour<currStartTimeHour){
                    ++count;
                    prevEndTimeHour = currEndTimeHour;
                }else {
                    if(prevEndTimeMinute<currEndTimeMinute){
                        ++count;
                        prevEndTimeHour = currEndTimeHour;
                    }
                }
            }else if(prevEndTimeHour>=currEndTimeHour) prevEndTimeHour = currEndTimeHour;
        }
        return count;
    }
}
