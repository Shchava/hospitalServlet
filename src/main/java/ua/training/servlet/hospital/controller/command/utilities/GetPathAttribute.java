package ua.training.servlet.hospital.controller.command.utilities;


import ua.training.servlet.hospital.entity.dto.CreationError;
import ua.training.servlet.hospital.entity.dto.CreationResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;


public class GetPathAttribute {
    private final static String USER_ID_START = "/patient/";
    private final static String DIAGNOSIS_ID_START = "/diagnosis/";
    private final static String SEPARATOR = "/";

    public static long getUserId(HttpServletRequest request){
        String path = request.getRequestURI();

        int numberStartIndex = getFirstIndex(USER_ID_START,path);
        int numberEndIndex = getLastIndex(numberStartIndex,path);

        String id = path.substring(numberStartIndex,numberEndIndex);

        return Long.parseLong(id);
    }

    public static long getDiagnosisId(HttpServletRequest request){
        String path = request.getRequestURI();

        int numberStartIndex = getFirstIndex(DIAGNOSIS_ID_START,path);
        int numberEndIndex = getLastIndex(numberStartIndex,path);

        String id = path.substring(numberStartIndex,numberEndIndex);

        return Long.parseLong(id);
    }

    public static long getDiagnosisIdOrAddError(HttpServletRequest request, CreationResponse response, ResourceBundle errors){
        try{
            return getDiagnosisId(request);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            response.addError(new CreationError("diagnosisId",errors.getString("diagnosis.id.numberForamtException")));
            return -1L;
        }
    }

    private static int getFirstIndex(String IdAnnouncement, String path){
        int numberStartIndex = path.indexOf(IdAnnouncement);
        if(numberStartIndex < 0){
            throw new NumberFormatException("cant find requested id");
        };
        return numberStartIndex + IdAnnouncement.length();
    }

    private static int getLastIndex(int firstIndex, String path){
        int numberEndIndex = path.indexOf(SEPARATOR,firstIndex);
        if(numberEndIndex < 0){
            numberEndIndex =  path .length();
        }
        return numberEndIndex;
    }

}
