package com.groupfour.MedicalCare.Utill;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class Authorization {
    public boolean hasPermisson(HttpSession session, String[] permissons)
    {
        if(session.getAttribute("role") != null)
        {
            String role = (String) session.getAttribute("role");
            for(String permisson : permissons)
            {
                if(permisson.equals(role))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
