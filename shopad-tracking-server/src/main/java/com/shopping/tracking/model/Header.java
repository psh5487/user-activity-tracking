package com.shopping.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Header<T> {
    //api 통신시간
    //@JsonProperty("transaction_time") //json이 만들어질 때, 해당 이름으로 만들어짐
    //private LocalDateTime transactionTime; //ISO, YYYY-MM-DD HH:mm:ss 등이 있음

    private String description;
    private T data;

    //OK
    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .description("OK")
                .build();
    }

    //DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>) Header.builder()
                .description("OK")
                .data(data)
                .build();
    }

    //ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>) Header.builder()
                .description(description)
                .build();
    }
}
