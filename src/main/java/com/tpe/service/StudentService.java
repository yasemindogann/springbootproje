package com.tpe.service;

import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    //Service katmanı olduğunu söylüyorum.
public class StudentService {

    //StudentRepository Class'ını StudentService Class'ına enjekte ettim.
    //field injection
    @Autowired  //Di
    private StudentRepository studentRepository;


}
