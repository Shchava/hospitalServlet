package ua.training.servlet.hospital.controller.utilities;

import jdk.nashorn.internal.ir.RuntimeNode;
import ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute;
import ua.training.servlet.hospital.controller.config.SecurityConfig;
import ua.training.servlet.hospital.controller.filter.AuthFilter;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class SecurityUtility {
    public static String removeVariableParts(String path){
        path = path.replaceAll("\\d+/?","");
        int paramsStartIndex = path.indexOf("?");
        if(paramsStartIndex > 0) {
            return (path.substring(0, paramsStartIndex));
        }else{
            return path;
        }
    }
    public static boolean hasAccess(HttpServletRequest request){
        if(isResourcePath(request)){
            return true;
        }
        return checkUserPermission(request);
    }

    private static boolean isResourcePath(HttpServletRequest request){
        String path = request.getRequestURI();
        return path.endsWith(".css")||path.endsWith("js");
    }

    private static boolean checkUserPermission(HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("LoggedUser");
        if(user == null){
            return SecurityConfig.getPublicPaths().contains(removeVariableParts(request.getRequestURI()));
        }
        return  checkLoggedUserPermission(user,request);
    }

    private static boolean checkLoggedUserPermission(User user, HttpServletRequest request){
        if(hasAccessToPath(user,request)){
            if(user.getRole() == Roles.PATIENT){
                return isPageOwner(user,request);
            }else{
                return true;
            }
        }
        return false;
    }

    private static boolean hasAccessToPath(User user, HttpServletRequest request){
        String path = removeVariableParts(request.getRequestURI());
        return SecurityConfig.getPathsForRole(user.getRole()).contains(path);
    }

    private static boolean isPageOwner(User user, HttpServletRequest request){
        String uri = request.getRequestURI();
        if(request.getRequestURI().startsWith ("/patient/")){
            long requestPatientId = getUserId(request);
            return requestPatientId == user.getId();
        }else{
            return true;
        }
    }
}
