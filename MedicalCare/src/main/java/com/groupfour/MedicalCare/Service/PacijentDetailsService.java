package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PacijentDetailsService implements UserDetailsService {

    private PacijentRepository repository;

    @Autowired
    public PacijentDetailsService(PacijentRepository repository){
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pacijent pacijent = repository.findUserByEmail(email);
        if(pacijent == null)
            throw new UsernameNotFoundException(email);

        return new UserDetailsImpl(pacijent);
    }




}
