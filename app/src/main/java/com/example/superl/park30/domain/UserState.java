package com.example.superl.park30.domain;

/**
 * Created by SuperL on 2018/8/17.
 */

public class UserState {

    private static CurrentUserState currentUserState = CurrentUserState.LOGIN;
    public static CurrentUserState getUserCurrentState() {
        return currentUserState;
    }
    public static void setCurrentUserState(CurrentUserState state){
        currentUserState = state;
    }
    public enum CurrentUserState{
        LOGIN, NOLOGIN
    }
}
