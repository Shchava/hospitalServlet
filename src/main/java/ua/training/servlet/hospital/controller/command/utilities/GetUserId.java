package ua.training.servlet.hospital.controller.command.utilities;


import javax.servlet.http.HttpServletRequest;


public class GetUserId {
    private final static String USER_ID_START = "/patient/";
    private final static String SEPARATOR = "/";

    public static long getUserId(HttpServletRequest request){
        String path = request.getRequestURI();

        int numberStartIndex = getFirstIndex(path);
        int numberEndIndex = getLastIndex(numberStartIndex,path);

        String id = path.substring(numberStartIndex,numberEndIndex);

        return Long.parseLong(id);
    }

    private static int getFirstIndex(String path){
        int numberStartIndex = path.indexOf(USER_ID_START);
        if(numberStartIndex < 0){
            throw new NumberFormatException("cant find user id");
        };
        return numberStartIndex + USER_ID_START.length();
    }

    private static int getLastIndex(int firstIndex, String path){
        int numberEndIndex = path.indexOf(SEPARATOR,firstIndex);
        if(numberEndIndex < 0){
            numberEndIndex =  path .length();
        }
        return numberEndIndex;
    }
}
