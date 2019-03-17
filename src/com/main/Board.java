package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import static java.lang.Integer.*;


public class Board {

    private ArrayList<Integer> field;
    private int colout_num;
    private int _size;
    private Integer score = 0;

    public Board() throws IOException {
        Properties property = new Properties();
        property.load(getClass().getResourceAsStream("resources/settings.properties"));
        Properties property_class = new Properties();
        property_class.load(getClass().getResourceAsStream("resources/classes.properties"));

        colout_num = parseInt(property.getProperty("colour_num"));
        _size = Integer.parseInt(property.getProperty("field_size"));
        field = new ArrayList<>();
        for(int i =0; i<_size*_size; i++)
            field.add(0);
        field.set(0, 1);
    }

    public ArrayList<Integer> getField() {
        return field;
    }
    public Integer get_score() {
        return score;
    }

    private boolean is_path(int start, int dest, int[] was_here) {
        if(start == dest)
            return true;
        was_here[start] = 1;

        if(start%_size - 1 >= 0) {
            if((field.get(start - 1) == 0) &&(was_here[start -1 ] == 0))
                if (is_path(start - 1, dest, was_here) == true)
                    return true;
        }
        if(start%_size + 1 < _size) {
            if((field.get(start + 1) == 0) && (was_here[start + 1 ] == 0))
                if (is_path(start + 1, dest, was_here) == true)
                    return true;
        }
        if((start/_size - 1) >= 0 ) {
            if((field.get(start - _size) == 0) && (was_here[start - _size ] == 0))
                if (is_path(start - _size, dest, was_here) == true)
                    return true;
        }
        if(start/_size + 1 < _size) {
            if((field.get(start + _size) == 0) && (was_here[start + _size ] == 0))
                if(is_path(start + _size, dest, was_here) == true)
                    return  true;
        }
        return  false;

    }

    public boolean player_turn(int prev_place, int place_to_set) {
        int colour = field.get(prev_place);
        if(is_path(prev_place, place_to_set, new int[_size*_size])) {
            field.set(prev_place, 0);
            field.set(place_to_set, colour);
        }
        else {
            return false;
        }
        //else throw
        String row[] = is_row(place_to_set).split(" ");
       if(Integer.parseInt(row[0]) != Integer.parseInt(row[1])) {
           if ((Integer.parseInt(row[1]) - Integer.parseInt(row[0]) + 1) >= 5 && Integer.parseInt(row[1]) / _size == Integer.parseInt(row[0]) / _size) {
               score += (Integer.parseInt(row[1]) - Integer.parseInt(row[0]) + 1) * 10;
               delete_row(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
           }
           int one = Integer.parseInt(row[1])/_size;
           int two = Integer.parseInt(row[0])/_size;
           if (one - two  >=  4 ) {
               if(Integer.parseInt(row[1]) % _size == Integer.parseInt(row[0]) % _size) {
                   score += (Integer.parseInt(row[1]) - Integer.parseInt(row[0]) + 1) * 10;
                   delete_row(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
               }
           }
       }
        return true;
    }

    private void delete_row(int first, int second) {
        if(first%_size == second%_size) {
            while(first != second){
                field.set(first, 0);
                first += _size;
            }
        }
        else {
            while(first != second + 1) {
                field.set(first, 0);
                first ++;
            }
        }
    }

    public void computer_turn() {
        int colour_set, place_to_set;
        Random ran = new Random();

        for(int i =0; i<3; i++) {
            colour_set = 1 + ran.nextInt(colout_num);
            place_to_set = ran.nextInt(_size*_size);
            while(field.get(place_to_set) != 0) {
                colour_set = 1 + ran.nextInt(colout_num);
                place_to_set = ran.nextInt(_size*_size);
            }
            field.set(place_to_set, colour_set);
        }
    }

    public boolean is_space_enough() {
        int k =0;

        for(int i= 0; i<_size; i++) {
            if(field.get(i) == 0)
                k++;
            if(k == 4)
                return true;
        }
        return false;
    }
    private String is_row(int place) {

        int up = place, down = place, right = place, left = place;
        while((left)%_size - 1 >= 0) {
            if(field.get(left-1) == field.get(place))
                left--;
            else
                break;
        }
        while((right)%_size +  1 < _size) {
            if(field.get(right+1) == field.get(place))
                right++;
            else
                break;
        }
        while((down-_size) >= 0) {
            if(field.get(down-_size) == field.get(place))
                down -= _size;
            else
                break;
        }
        while((up +_size)/_size < _size) {
            if(field.get(up +_size) == field.get(place))
                up += _size;
            else break;
        }

        if(right-left > up/_size - down/_size)
            return left + " "  + right;
        else return down + " " + up;
    }
}
