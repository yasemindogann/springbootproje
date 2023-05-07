package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //yazmak zorunda değilim çünkü JpaRepository'yi extend ediyor. Okunurluk açısından yazıyorum.
public interface StudentRepository extends JpaRepository<Student, Long> {//Bu yapı sayesinde hazırda kullanabileceğim onlarca Java methodu geliyor.
    //Bir satır SQL kodu yazmadan çok rahat DB üzerinde CRUD operasyonları yapmamızı sağlayacak hazır methodlar geliyor.

    //JpaRepository<Student, Long>: StudentRepository'nin kullandığı Entity Class'ının ismini ve
    //@Id annotation'ı ile işaretlemiş olduğum field'ın data type'ını istiyor.

    //En az kodu burada yazıcaz çünkü JpaRepository'den gelen hazır interface'leri kullanıcaz.
    //Bütün bu methodları kullanmamızı sağlayan yapı "springframework.data.jpa"
    //Repository direk DB'ye gidiyor.Bunu nasıl yapıyor? "application.properties" sayesinde Spring Boot bu yapıyı kuruyor.

    boolean existsByEmail(String email);    // "exists" methodundan türettik.



}
