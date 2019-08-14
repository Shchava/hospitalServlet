package ua.training.servlet.hospital.controller.config;

import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityConfig {
    private static final Map<String, List<String>> accessPaths = new HashMap<String, List<String>>();
    static {
        init();
    }

    private static void init(){
        List<String> notSecuredPaths = new ArrayList<>();
        notSecuredPaths.add("/login");
        notSecuredPaths.add("/registration");
        accessPaths.put("ANYONE",notSecuredPaths);

        List<String> userPaths = new ArrayList<>(notSecuredPaths);
        userPaths.add("/");
        userPaths.add("/index.jsp");
        userPaths.add("/logout");
        userPaths.add("/patient/");
        userPaths.add("/patient/diagnosis/");
        userPaths.add("/patient/diagnosis/getMedicine/");
        userPaths.add("/patient/diagnosis/getProcedures/");
        userPaths.add("/patient/diagnosis/getSurgeries/");
        accessPaths.put("PATIENT",userPaths);

        List<String> nursePaths = new ArrayList<>(userPaths);
        nursePaths.add("/patientsList");
        nursePaths.add("/patient/diagnosis/addMedicine/");
        nursePaths.add("/patient/diagnosis/addProcedure/");
        accessPaths.put("NURSE",nursePaths);

        List<String> doctorPaths = new ArrayList<>(nursePaths);
        doctorPaths.add("/patient/diagnosis/addSurgery/");
        doctorPaths.add("/patient/addDiagnosis/");
        doctorPaths.add("/patient/diagnosis/closeDiagnosis/");
        accessPaths.put("DOCTOR",doctorPaths);
    }

    public static List<String> getPathsForRole(Roles role){
        return accessPaths.get(role.toString());
    }

    public static List<String> getPublicPaths(){
        return accessPaths.get("ANYONE");
    }
}
