package com.tpe.exception;

public class ConflictException extends RuntimeException { //RuntimeException'ı extend etti.
    //JVM'e diyorum ki bu class ama senin bildiğin tarz exception'lardan(RuntimeException) türetiyorum
    //Artık JVM şöyle çalışıyor; bu Runtime'dan türetildiğine göre bu da benim kabul edeceğim
    //ve bunun üzerinden exception fırlatabileceğim bir Custom exception class'ı.

    //Tek parametreli (message.String) constructor oluşturdum.
    //Bu class'tan bir instance oluştururken parametresine String ifade girersek bunun super'ine RuntimeException'ı gönderir.
    //Yani RuntimeException'ın tek parametreli constructor'ını çalıştırır,yukarda girilen mesajı da ona gönderir.
    //RuntimeException gibi çalışır ama benim gönderdiğim mesaj gider.("Email is already exist!!)
    //Çünkü RuntimeException parent olduğu için onun gönderdiği mesaj buraya anlamsız gelir, ben kendime göre hata mesajını düzenledim.
    public ConflictException(String message) {  //Parametreli Constructor'ını çağırdık
        super(message);
    }

}
