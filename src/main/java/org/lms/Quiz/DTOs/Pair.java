package org.lms.Quiz.DTOs;

//screw logback
public class Pair<F,S> {
    public F first;
    public S second;

    public Pair(){ }

    public Pair(F f, S s){
        first = f;
        second = s;
    }
}
