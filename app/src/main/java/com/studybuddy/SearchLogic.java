package com.studybuddy;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchLogic {
    public static ArrayList<User> search(String search, ArrayList<User> users) {
        ArrayList<User> result = new ArrayList<User>();
        StringTokenizer tokenizer = new StringTokenizer(search);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            for (User user : users) {
                if (user.getName().toLowerCase().contains(token.toLowerCase())) {
                    result.add(user);
                }
            }
        }
        return result;
    }
}
