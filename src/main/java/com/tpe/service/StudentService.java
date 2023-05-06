package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    //Service katmanı olduğunu söylüyorum.
public class StudentService {

    //StudentRepository Class'ını StudentService Class'ına enjekte ettim.
    //field injection
    @Autowired  //Di
    private StudentRepository studentRepository;


    //Burası benim Business logic tarafım.Kontrol yapmam gerekiyorsa kontrol yapıcam.
    //Senaryomu düşünüyorum;
    //Tabloya gidicem data varsa getir, yoksa zaten boş gelecek.O zaman bir kontrole gerek yok.
    //Kontrole gerek yoksa Repository tarafına gitmem lazım.
    public List<Student> getAll() {
        //studentRepository bir interface ve JpaRepository interace'ini extend ediyor.
        //studentRepository. dediğimde JpaRepository'nin bütün methodlarına ulaşabilirim.
        //İhtiyacım olan Student'ları listelemek bu yüzden findAll() methodunu kullanıyorum.
//        List<Student> students = studentRepository.findAll();  //Bana dönen ne List data type'ında Student'lar.
//        return students;
        return studentRepository.findAll(); //Kısaca bu şekilde yazıyorum.
        //Repository'ye gitmedim bile.Eğer bumethodlara ulaşamasaydım StudentRepository'ye gidip kendim method yazacaktım.
        //Ama buna gerek kalmadı çünkü springframework.data.jpa'dan extend edildi  StudentRepository'de.

        //findAll  ==> Method içine girdiğimde  "List<T> findAll();" Dönen değer List data type'ında Jenerik yapı.
        //Ben T'nin Student olacağını setledim.

        //Artık listeleme methodumu Postman'den test edebilirim.
    }
}
